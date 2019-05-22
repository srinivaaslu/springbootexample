package com.example.controller;

import com.example.model.Input;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AdditionResource.class, secure = false)
public class AdditionResourceTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testAdd()throws Exception{
        Input input = new Input();
        input.setNumber1("1");
        input.setNumber2("2");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/sum")
                .accept(MediaType.APPLICATION_JSON).content(String.valueOf(input))
                .contentType(MediaType.APPLICATION_JSON);


        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

       assertEquals(200, response.getStatus());

    }

}
