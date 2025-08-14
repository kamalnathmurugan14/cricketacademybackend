package com.cricketacademy.api.service;

import okhttp3.*;
import java.io.IOException;

public class Infobip2FAVerifyPin {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.err.println("Usage: java Infobip2FAVerifyPin <pinId> <pinCode>");
            return;
        }
        String pinId = args[0];
        String pinCode = args[1];
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        MediaType mediaType = MediaType.parse("application/json");
        String jsonBody = String.format("{\"pin\":\"%s\"}", pinCode);
        RequestBody body = RequestBody.create(jsonBody, mediaType);
        Request request = new Request.Builder()
                .url("https://69kqdd.api.infobip.com/2fa/2/pin/" + pinId + "/verify")
                .method("POST", body)
                .addHeader("Authorization", "App d8e6109e1db62346f64a4a583cd56833-535e98b1-f2a6-4998-b6ba-52952d990796")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("Request failed: " + response.code() + " - " + response.message());
            }
            System.out.println("Response code: " + response.code());
            System.out.println("Response body: " + (response.body() != null ? response.body().string() : "null"));
        }
    }
}
