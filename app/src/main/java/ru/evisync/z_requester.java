package ru.evisync;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CertificatePinner;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class z_requester {

    public interface z_requestsListenerInterface {
        void onError(IOException e);
        void onResult(String value);
    }
    public abstract static class z_requestsListener implements z_requestsListenerInterface {
        @Override
        public void onError(IOException e) {

        }

        @Override
        public void onResult(String value) {

        }
    }

    public void special_request(Request request, z_requestsListenerInterface listener) {
        new Thread(() -> {
            OkHttpClient client = new OkHttpClient.Builder().readTimeout(25, TimeUnit.SECONDS).certificatePinner(new CertificatePinner.Builder()
                    .add("api.eviger.ru", "sha256/TiNyS1OoQIAzbv/Rc8rQkuplaF9mcu2Rcl/tUin1TAc=")
                    .build()).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    listener.onError(e);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        listener.onResult(Objects.requireNonNull(response.body()).string());
                    }
                }
            });
        }).start();
    }

    private static String requestGet(String url, String[][] params) {
        z_requester requester = new z_requester();
        HttpUrl.Builder builder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        for (String[] param : params) {
            if (param.length == 0) continue;
            builder.addQueryParameter(param[0], param[1]);
        }
        Request request = new Request.Builder()
                .url(builder.build())
                .build();
        final String result = null;
        z_requester.z_requestsListener requesterListener = new z_requestsListener() {
            @Override
            public void onResult(String value) {
                super.onResult(value);
                result = value;
            }
        };
        requester.special_request(request, requesterListener);
    }

    private static void requestPost(String url, String data) {
        z_requester requester = new z_requester();
        RequestBody requestBody = RequestBody.create(data, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        z_requester.z_requestsListener requesterListener = new z_requestsListener() {
            @Override
            public void onResult(String value) {
                super.onResult(value);
            }
        };
        requester.special_request(request, requesterListener);
    }

    public void request() {

    }

}
