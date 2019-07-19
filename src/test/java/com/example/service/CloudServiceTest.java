package com.example.service;

import com.example.exception.ProcessingException;
import com.example.model.ApiResponse;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CloudServiceTest {

    @InjectMocks
    private CloudService cloudService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testGetAllApiResponseInfo(){
        given(restTemplate.getForObject(anyString(), any())).willReturn("{\"name\":\"\",\"build\":\"\",\"support\":\"https://support.run.pivotal.io\",\"version\":0,\"description\":\"Cloud Foundry sponsored by Pivotal\",\"authorization_endpoint\":\"https://login.run.pivotal.io\",\"token_endpoint\":\"https://uaa.run.pivotal.io\",\"min_cli_version\":\"6.22.0\",\"min_recommended_cli_version\":\"latest\",\"app_ssh_endpoint\":\"ssh.run.pivotal.io:2222\",\"app_ssh_host_key_fingerprint\":\"e7:13:4e:32:ee:39:62:df:54:41:d7:f7:8b:b2:a7:6b\",\"app_ssh_oauth_client\":\"ssh-proxy\",\"doppler_logging_endpoint\":\"wss://doppler.run.pivotal.io:443\",\"api_version\":\"2.138.0\",\"osbapi_version\":\"2.14\",\"routing_endpoint\":\"https://api.run.pivotal.io/routing\"}");
        List<ApiResponse> allApisResponse = cloudService.getAllApiResponseInfo();
        assertEquals(2,allApisResponse.size());
        verify(restTemplate,times(2)).getForObject(anyString(), any());

    }


    @Rule
    public ExpectedException thrown= ExpectedException.none();
    @Test
    public void testGetAllApiResponseInfoExceptionScenario(){
        given(restTemplate.getForObject(anyString(), any())).willThrow(RestClientException.class);
        List<ApiResponse> allApisResponse = cloudService.getAllApiResponseInfo();
        verify(restTemplate,times(2)).getForObject(anyString(), any());
        assertEquals(2,allApisResponse.size());

    }
    @Test
    public void testGetSpecificApiResponseInfo(){
        given(restTemplate.getForObject(anyString(), any())).willReturn("{\"description\":\"Cloud Foundry sponsored by Pivotal\",\"authorization_endpoint\":\"https://login.run.pivotal.io\",\"token_endpoint\":\"https://uaa.run.pivotal.io\",\"min_cli_version\":\"6.22.0\",\"min_recommended_cli_version\":\"latest\",\"app_ssh_endpoint\":\"ssh.run.pivotal.io:2222\",\"app_ssh_host_key_fingerprint\":\"e7:13:4e:32:ee:39:62:df:54:41:d7:f7:8b:b2:a7:6b\",\"app_ssh_oauth_client\":\"ssh-proxy\",\"doppler_logging_endpoint\":\"wss://doppler.run.pivotal.io:443\",\"api_version\":\"2.138.0\",\"osbapi_version\":\"2.14\",\"routing_endpoint\":\"https://api.run.pivotal.io/routing\"}");
        ApiResponse allApisResponse = cloudService.getSpecificApiResponseInfo("PWC");
        assertEquals("Cloud Foundry sponsored by Pivotal",allApisResponse.getDescription());
        verify(restTemplate,times(1)).getForObject(anyString(), any());

    }

    @Test
    public void testGetAllApiResponseWhenJSonParseIssuePresent(){
        given(restTemplate.getForObject(anyString(), any())).willReturn("\"description1\":\"Cloud Foundry sponsored by Pivotal\",\"apiversion\":\"2.138.0\",\"osbapi_version\":\"2.14\",\"routing_endpoint\":\"https://api.run.pivotal.io/routing\"");
        thrown.expect(ProcessingException.class);
        cloudService.getAllApiResponseInfo();
        verify(restTemplate,times(1)).getForObject(anyString(), any());

    }


    @Test
    public void testGetSpecificApiResponseInfoInExceptionCase(){
        given(restTemplate.getForObject(anyString(), any())).willThrow(ResourceAccessException.class);
        try {
            cloudService.getSpecificApiResponseInfo("PWC");
            Assert.fail();
        }catch (RestClientException ex){
            Assert.assertEquals("IO exception occurred while executing", ex.getMessage());
        }

        verify(restTemplate,times(1)).getForObject(anyString(), any());

    }


//    private SimpleClientHttpRequestFactory customHttpRequestFactory() {
//        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
//        simpleClientHttpRequestFactory.setReadTimeout(2);
//        simpleClientHttpRequestFactory.setConnectTimeout(2);
//        return simpleClientHttpRequestFactory;
//    }


}
