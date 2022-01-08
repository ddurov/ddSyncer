package ru.evisync;

import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.CertificatePinner;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class z_globals {

    private static final OkHttpClient client = new OkHttpClient.Builder().certificatePinner(
            new CertificatePinner.Builder()
                    .add("domain", "cert_SHA-256").build()
    ).build();

    public static String requestGet(String url, String[][] params) {
        HttpUrl.Builder builder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        for (String[] param : params) {
            if (param.length == 0) continue;
            builder.addQueryParameter(param[0], param[1]);
        }

        Request request = new Request.Builder()
                .url(builder.build())
                .build();

        String response = null;

        try {
            callbackRequest future = new callbackRequest();
            client.newCall(request).enqueue(future);
            response = Objects.requireNonNull(future.get().body()).string();
        } catch (Exception ex) {
            writeErrorInLog(ex);
        }

        return response;

    }

    public static String requestPost(String url, String data) {
        RequestBody requestBody = RequestBody.create(data, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        String response = null;

        try {
            callbackRequest future = new callbackRequest();
            client.newCall(request).enqueue(future);
            response = Objects.requireNonNull(future.get().body()).string();
        } catch (Exception ex) {
            writeErrorInLog(ex);
        }

        return response;

    }

    public static void writeErrorInLog(Exception ex) {
        Toast.makeText(z_fakeContext.getInstance().getApplicationContext(), "Ошибка неизвестного характера, записываю в лог", Toast.LENGTH_SHORT).show();

        special_createLogFile(ex.getMessage(), stackTraceToString(ex));
    }

    public static String stackTraceToString(Exception ex) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : ex.getStackTrace()) {
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void log(Object message) {
        Log.d("l/e", String.valueOf(message));
    }

    private static void special_createLogFile(String error, String stacktrace) {
        try {

            File log = new File(z_fakeContext.getInstance().getApplicationContext().getDataDir(), "log.txt");
            log.createNewFile();

            FileWriter fr = new FileWriter(log, true);
            fr.write("================= BEGIN LOGS =================\n");
            fr.write("Ошибка: " + error + "\n" + "Стек ошибки: " + stacktrace);
            fr.write("================= END LOGS =================\n");
            fr.close();

        } catch (Exception ex) {
            writeErrorInLog(ex);
        }
    }

}
