package com.cricketacademy.api.service;

import okhttp3.*;
import java.io.IOException;

public class Infobip2FADeliverPin {
    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.err.println("Usage: java Infobip2FADeliverPin <applicationId> <messageId> <toPhoneNumber>");
            return;
        }
        String applicationId = args[0];
        String messageId = args[1];
        String toPhone = args[2];
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        MediaType mediaType = MediaType.parse("application/json");
        String jsonBody = String.format(
                "{\"applicationId\":\"%s\",\"messageId\":\"%s\",\"from\":\"447491163443\",\"to\":\"%s\"}",
                applicationId, messageId, toPhone);
        RequestBody body = RequestBody.create(jsonBody, mediaType);
        Request request = new Request.Builder()
                .url("https://69kqdd.api.infobip.com/2fa/2/pin")
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
