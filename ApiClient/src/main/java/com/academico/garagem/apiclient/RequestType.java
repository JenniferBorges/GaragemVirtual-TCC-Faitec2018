package com.academico.garagem.apiclient;

import com.google.gson.Gson;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

public abstract class RequestType {

    private final HttpEntity entity;

    public RequestType(Object obj) {
        this.entity = createEntity(obj);
    }

    public abstract Response sync() throws IOException;

    public abstract void async(APIClient.IAPICallback callback);

    public HttpEntity getEntity() {
        return entity;
    }

    private HttpEntity createEntity(Object body) {
        String json = new Gson().toJson(body);
        return new StringEntity(String.valueOf(json), ContentType.APPLICATION_JSON);
    }
}
