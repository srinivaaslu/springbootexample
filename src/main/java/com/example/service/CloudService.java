package com.example.service;

import com.example.exception.ProcessingException;
import com.example.model.ApiResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CloudService {

    @Autowired
    @Qualifier("restTemplateReadTimeout")
    private RestTemplate restTemplate;

    public static String REST_ENDPOINT_URL_1="https://api.run.pivotal.io/v2/info";
    public static String REST_ENDPOINT_URL_2="https://api.ng.bluemix.net/v2/info";

    protected <T> T mapFromJson(String json, Class<T> clazz) throws JsonMappingException ,JsonParseException,IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    public List<ApiResponse> getAllApiResponseInfo() {

        List<ApiResponse> apiResponseList = new ArrayList<>();
        extractAPIResponseToOutputList(apiResponseList, REST_ENDPOINT_URL_1);
        extractAPIResponseToOutputList(apiResponseList, REST_ENDPOINT_URL_2);


        return apiResponseList;
    }

    private void extractAPIResponseToOutputList(List<ApiResponse> apiResponseList, String endpointURL) {
        String jsonResponse=null;
        try {
            jsonResponse = getResponseFromRestClient(endpointURL);
        }catch(RestClientException ex){
            ApiResponse errorResponse = new ApiResponse("Error occur while accessing Link ","Error occur while accessing "+endpointURL,"true");
            errorResponse.setIsError("true");
            apiResponseList.add(errorResponse);
        }
        if(jsonResponse!=null) {
            ApiResponse apiResponse = mapResponseToPojo(jsonResponse);
            apiResponseList.add(apiResponse);
        }
    }

    public ApiResponse getSpecificApiResponseInfo(String code) {
        ApiResponse apiResponse = null;
        String jsonResponse;
        if("PWC".equalsIgnoreCase(code)) {
            jsonResponse = getResponseFromRestClient(REST_ENDPOINT_URL_1);
        }else {
            jsonResponse = getResponseFromRestClient(REST_ENDPOINT_URL_2);
        }
        apiResponse = mapResponseToPojo(jsonResponse);
        return apiResponse;
    }

    private ApiResponse mapResponseToPojo(String jsonResponse) {
        ApiResponse apiResponse;
        try {
            apiResponse = mapFromJson(jsonResponse, ApiResponse.class);
        } catch (IOException e) {
            throw new ProcessingException("Exception on processing json");
        }
        return apiResponse;
    }


    public String getResponseFromRestClient(String restEndPointUrl) throws RestClientException{
            return restTemplate.getForObject(restEndPointUrl, String.class);
    }


}
