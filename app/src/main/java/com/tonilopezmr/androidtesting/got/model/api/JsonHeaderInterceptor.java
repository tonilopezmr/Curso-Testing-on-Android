package com.tonilopezmr.androidtesting.got.model.api;


import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Interceptor para añadir a todas las peticiones los header Accept y Content-type.
 *
 */
public class JsonHeaderInterceptor implements Interceptor {

    @Override public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("ContentType", "application/json")
                .build();
        return chain.proceed(request);
    }
}
