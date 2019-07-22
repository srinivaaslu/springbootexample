package com.example.controller;


import com.example.exception.ErrorDetails;
import com.example.exception.ErrorResponse;
import com.example.exception.InvalidInputParameter;
import com.example.model.ApiResponse;
import com.example.model.Output;
import com.example.service.CloudService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value=CloudController.class, secure=false)
public class CloudControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CloudService cloudService;

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonMappingException,IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    @Test
    public void testGetAPIResponse() throws Exception {
        List<ApiResponse> mockAPiResponse = new ArrayList<>();
        mockAPiResponse.add(new ApiResponse("SampleAPI","version1"));

        given(cloudService.getAllApiResponseInfo()).willReturn(mockAPiResponse);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cf");
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                                       .andExpect(content().json("[{'description': 'SampleAPI','api_version': 'version1'}]"));


    }

    @Test
    public void testGetSpecificAPIResponseForPathParamPWC() throws Exception {

        given(cloudService.getSpecificApiResponseInfo(anyString())).willReturn(new ApiResponse("SampleAPI","version1"));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cf/pwc");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        ApiResponse output = mapFromJson(result.getResponse().getContentAsString(), ApiResponse.class);
        assertEquals("SampleAPI",output.getDescription());


    }

    @Test
    public void testGetSpecificAPIResponseForPathParamBLU() throws Exception {

        given(cloudService.getSpecificApiResponseInfo(anyString())).willReturn(new ApiResponse("SampleBLU","version1"));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cf/blu");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        ApiResponse output = mapFromJson(result.getResponse().getContentAsString(), ApiResponse.class);
        assertEquals("SampleBLU",output.getDescription());


    }

    @Test
    public void testGetSpecificAPIResponseForInvalidPathParam() throws Exception {

        given(cloudService.getSpecificApiResponseInfo(anyString())).willReturn(new ApiResponse("SampleBLU","version1"));
            RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cf/blusdf");
            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            ErrorDetails output = mapFromJson(result.getResponse().getContentAsString(), ErrorDetails.class);
            assertEquals("Invalid path parameter found",output.getDescription());

    }

}
