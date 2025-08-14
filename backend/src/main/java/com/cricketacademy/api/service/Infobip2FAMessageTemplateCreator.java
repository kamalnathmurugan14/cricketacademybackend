package com.cricketacademy.api.service;

import okhttp3.*;
import java.io.IOException;

public class Infobip2FAMessageTemplateCreator {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: java Infobip2FAMessageTemplateCreator <appId>");
            return;
        }
        String appId = args[0];
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        MediaType mediaType = MediaType.parse("application/json");
        String jsonBody = "{\"pinType\":\"NUMERIC\",\"messageText\":\"Your pin is {{pin}}\",\"pinLength\":4,\"senderId\":\"ServiceSMS\"}";
        RequestBody body = RequestBody.create(jsonBody.getBytes(java.nio.charset.StandardCharsets.UTF_8), mediaType);
        Request request = new Request.Builder()
                .url("https://69kqdd.api.infobip.com/2fa/2/applications/" + appId + "/messages")
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
