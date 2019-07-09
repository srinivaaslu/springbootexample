package com.example.controller;

import com.example.model.Input;
import com.example.model.Output;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AdditionController.class, secure = false)
public class AdditionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonMappingException,IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }


    @Test
      public void testAdd() throws Exception {
        Input input = new Input();
        input.setNumber1("1");
        input.setNumber2("2");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/sum")
                .accept(MediaType.APPLICATION_JSON).content(mapToJson(input))
                .contentType(MediaType.APPLICATION_JSON);


        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Output output = mapFromJson(result.getResponse().getContentAsString(), Output.class);

        assertEquals(200, result.getResponse().getStatus());
        assertEquals("3", output.getResult());

    }

    @Test
    public void testAddWithOneEmptyvalue() throws Exception {
        Input input = new Input();
        input.setNumber1("");
        input.setNumber2("2");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/sum")
                .accept(MediaType.APPLICATION_JSON).content(mapToJson(input))
                .contentType(MediaType.APPLICATION_JSON);


        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Output output = mapFromJson(result.getResponse().getContentAsString(), Output.class);

        assertEquals(200, result.getResponse().getStatus());
        //Response should be equal to 2 as one input is null
        assertEquals("2", output.getResult());

    }


    @Test
    public void testAddWithNullObject() throws Exception {
        Input input = new Input();
        input.setNumber1(null);
        input.setNumber2(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/sum")
                .accept(MediaType.APPLICATION_JSON).content(mapToJson(input))
                .contentType(MediaType.APPLICATION_JSON);


        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Output output = mapFromJson(result.getResponse().getContentAsString(), Output.class);

        assertEquals(200, result.getResponse().getStatus());
        //Response should be equal to 2 as one input is null
        assertEquals("0", output.getResult());

    }

    @Test
    public void testAddWithEmptyValues() throws Exception {
        Input input = new Input();
        input.setNumber1("");
        input.setNumber2("");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/sum")
                .accept(MediaType.APPLICATION_JSON).content(mapToJson(input))
                .contentType(MediaType.APPLICATION_JSON);


        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Output output = mapFromJson(result.getResponse().getContentAsString(), Output.class);

        assertEquals(200, result.getResponse().getStatus());
        //Response should be equal to 2 as one input is null
        assertEquals("0", output.getResult());

    }

    @Test
     public void testWithWrongUri() throws Exception {
        Input input = new Input();
        input.setNumber1("1");
        input.setNumber2("2");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/sum1")
                .accept(MediaType.APPLICATION_JSON).content(String.valueOf(input))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //Should give 404 as method not existing
        assertEquals(404, result.getResponse().getStatus());

    }



    @Test
    public void testAddNegativeNumber() throws Exception {
        Input input = new Input();
        input.setNumber1("1");
        input.setNumber2("-2");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/sum")
                .accept(MediaType.APPLICATION_JSON).content(mapToJson(input))
                .contentType(MediaType.APPLICATION_JSON);


        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //Should get 400 as one of the number is negative
        assertEquals(400, result.getResponse().getStatus());

    }

    @Test
    public void testAddWhenOneNumberExceedsThousand() throws Exception {
        Input input = new Input();
        input.setNumber1("1");
        input.setNumber2("1200");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/sum")
                .accept(MediaType.APPLICATION_JSON).content(mapToJson(input))
                .contentType(MediaType.APPLICATION_JSON);


        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //Should get 400 as one of the number is negative
        assertEquals(400, result.getResponse().getStatus());

    }

    @Test
    public void testAddWithNonNumericValues() throws Exception {
        Input input = new Input();
        input.setNumber1("ac");
        input.setNumber2("2");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/sum")
                .accept(MediaType.APPLICATION_JSON).content(mapToJson(input))
                .contentType(MediaType.APPLICATION_JSON);


        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //Should get 400 as it will throw NumberformatException
        assertEquals(400, result.getResponse().getStatus());

    }



}
