package com.academico.garagem.apiclient;

public interface BaseAPIClient {

    BaseAPIClient header(String key, String value);

    RequestType get();

    RequestType post(Object object);

    RequestType put(Object object);

    RequestType patch(Object object);
}
