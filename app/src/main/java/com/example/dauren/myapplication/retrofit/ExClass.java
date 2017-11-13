package com.example.dauren.myapplication.retrofit;

/**
 * Created by dauren on 1/17/17.
 */
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({
//        "response"
//})
public class ExClass {

//    @JsonProperty("response")
    public String response;

    @Override
    public String toString() {
        return "The boolean is " + response;
    }
}
