package com.academico.garagem.apiclient;

import com.google.gson.Gson;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

public class Response {

    private final int statusCode;
    private final String body;
    private final Map<String, String> headers;

    public Response(HttpResponse response) {
        this.statusCode = response.getStatusLine().getStatusCode();
        String mBody = null;
        try {
            mBody = EntityUtils.toString(response.getEntity());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        this.body = mBody;
        headers = new HashMap<>();
        initializeHeaders(response.getAllHeaders());
    }

    private void initializeHeaders(Header[] headers) {
        for (Header header : headers) {
            this.headers.put(header.getName(), header.getValue());
        }
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getStatus() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public <T> T readEntity(Class<T> t) {
        return new Gson().fromJson(body, t);
    }

    public <T> T readEntity(final Type type) {
        return new Gson().fromJson(body, type);
    }

}
