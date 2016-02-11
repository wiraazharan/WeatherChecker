package com.magic.wiraazharan.openweathermaprest;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by wiraazharan on 7/18/15.
 */
public class Helper {

    private static final String CheckWeatherUrl =
            "http://api.openweathermap.org/data/2.5/weather?q=";
    private static final String secondhalf = "&APPID=ff5db73d4dc6f1160b3c103ea1a3a71d";
    private static final int HTTP_STATUS_OK = 200;
    private static byte[] buff = new byte[1024];
    private static final String logTag = "OpenWeatherHelper";


    public static class ApiException extends Exception {
        private static final long serialVersionUID = 1L;

        public ApiException (String msg)
        {
            super (msg);
        }

        public ApiException (String msg, Throwable thr)
        {
            super (msg, thr);
        }
    }




    protected static synchronized String downloadFromServer (String... params)
            throws ApiException
    {
        String retval = null;
        String requestedcity = params[0];


        String url = CheckWeatherUrl
                 + requestedcity + secondhalf;

        Log.d(logTag, "Fetching " + url);

        // create an http client and a request object.
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        try {

            // execute the request
            HttpResponse response = client.execute(request);
            StatusLine status = response.getStatusLine();
            if (status.getStatusCode() != HTTP_STATUS_OK) {
                // handle error here
                throw new ApiException("Invalid response from last.fm" +
                        status.toString());
            }

            // process the content.
            HttpEntity entity = response.getEntity();
            InputStream ist = entity.getContent();
            ByteArrayOutputStream content = new ByteArrayOutputStream();

            int readCount = 0;
            while ((readCount = ist.read(buff)) != -1) {
                content.write(buff, 0, readCount);
            }
            retval = new String (content.toByteArray());

        } catch (Exception e) {
            throw new ApiException("Problem connecting to the server " +
                    e.getMessage(), e);
        }

        return retval;
    }




}
