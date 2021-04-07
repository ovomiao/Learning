package moe.maonaing.network.java.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ProgressInterceptor implements Interceptor {

    private ProgressListener mListener;

    ProgressInterceptor(ProgressListener listener) {
        this.mListener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse  = chain.proceed(chain.request());

        ResponseBody responseBody = originalResponse.body();
        String url = originalResponse.request().url().toString();

        ProgressResponseBody progressResponseBody = new ProgressResponseBody(
                responseBody,
                mListener,
                url
        );

        return originalResponse.newBuilder()
                .body(progressResponseBody)
                .build();
    }

}