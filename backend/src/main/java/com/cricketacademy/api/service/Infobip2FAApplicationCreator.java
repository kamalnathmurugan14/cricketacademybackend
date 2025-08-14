package com.cricketacademy.api.service;

import okhttp3.*;
import java.io.IOException;

public class Infobip2FAApplicationCreator {
    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(
                "{\"name\":\"2fa test application\",\"enabled\":true,\"configuration\":{\"pinAttempts\":10,\"allowMultiplePinVerifications\":true,\"pinTimeToLive\":\"15m\",\"verifyPinLimit\":\"1/3s\",\"sendPinPerApplicationLimit\":\"100/1d\",\"sendPinPerPhoneNumberLimit\":\"10/1d\"}}",
                mediaType);
        Request request = new Request.Builder()
                .url("https://69kqdd.api.infobip.com/2fa/2/applications")
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
