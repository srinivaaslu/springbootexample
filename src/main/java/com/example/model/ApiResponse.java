package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {

    private String description;

    public String getApi_version() {
        return api_version;
    }

    public void setApi_version(String api_version) {
        this.api_version = api_version;
    }

    private String api_version;

    @JsonIgnore
    private String isError;

    public String isError() {
        return isError;
    }

    public void setIsError(String isError) {
        this.isError = isError;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public ApiResponse(){

    }

    public ApiResponse(String description, String api_version,String isError) {
        this.description = description;
        this.api_version = api_version;
        this.isError = isError;
    }
}
