package com.example.study_buddy.utils;


import android.net.Uri;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtils {
    private static final OkHttpClient httpClient = new OkHttpClient();


    /**
     * Builds URL from string.
     *
     * @param urlString String to build url from.
     * @return URL or null in case there was an error in the urlString.
     */
    public static URL buildURL(String urlString) {
        Uri builtUri = Uri.parse(urlString);

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Sends a request to the specified url and returns the whole HTTP response.
     *
     * @param url The URL to send request to.
     * @return  Contents of the HTTP response.
     * @throws IOException In case of a network or stream reading error.
     */
    @NotNull
    public static String getResponseFromHttpURL(URL url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try(Response response = httpClient.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
