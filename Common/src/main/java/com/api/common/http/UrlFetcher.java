package com.api.common.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * OpenDigitalUniversity
 * Created by sonudhakar on 23/07/17.
 */
public class UrlFetcher {

    /**
     * Make get request.
     *
     * @param urlString the url string
     * @return the http response
     * @throws IOException the iO exception
     */
    public static HttpResponse makeGetRequest(String urlString) throws IOException {

        return makeRequest(new HttpRequest(urlString, HttpMethod.GET));
    }

    /**
     * Make request.
     *
     * @param request the request
     * @return the http response
     * @throws IOException the iO exception
     */
    public static HttpResponse makeRequest(HttpRequest request) throws IOException {

        System.out.println("url : " + request.getUrl().toString());

        HttpURLConnection conn = (HttpURLConnection) request.getUrl().openConnection();

        conn.setRequestMethod(request.getMethod().toString());

        if (request.getConnectionTimeOut() > 0)
            conn.setConnectTimeout(request.getConnectionTimeOut() * 1000);

        if (request.getHeaders() != null) {

            for (String key : request.getHeaders().keySet()) {
                conn.setRequestProperty(key, request.getHeaderValue(key));
            }
        }

        if (request.getMethod() != HttpMethod.GET) {

            conn.setRequestProperty("Content-Type", request.getContentType());

            if (request.getPayload() != null) {
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                os.write(request.getPayload());
                os.flush();
            }
        }

        StringBuffer responseContent = null;

        HttpResponse httpResponse = new HttpResponse();
        try {
            if (conn.getInputStream() != null) {

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String inputLine;
                responseContent = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    responseContent.append(inputLine);
                }
                in.close();
            }

            httpResponse.setStatusCode(conn.getResponseCode());
            httpResponse.setResponseContent(responseContent == null ? null : responseContent.toString());

            httpResponse.setHeaders(conn.getHeaderFields());

        } catch(Exception e) {
            System.out.println("Exception while fetching url ");
            e.printStackTrace();
        }

        conn.disconnect();

        return httpResponse;
    }
}
