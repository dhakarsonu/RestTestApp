package com.api.common.http;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * OpenDigitalUniversity
 * Created by sonudhakar on 23/07/17.
 */

@Data
public class HttpResponse {

    private int statusCode;

    private String responseContent;

    private Map<String, List<String>> headers;

    public boolean wasSuccessful(){
        return (statusCode >= 200 && statusCode < 299);
    }

}
