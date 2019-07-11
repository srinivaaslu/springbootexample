package com.example.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex {

    public static void main(String []args){

        String pattern = "^PWC|BLU$";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher("BLU");
        System.out.print("result is "+m.find());
    }

}
