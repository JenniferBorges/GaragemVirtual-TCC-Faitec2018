package com.academico.garagem.apiclient;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.log4j.Logger;

public class APIClient implements BaseAPIClient {

    Logger log = Logger.getLogger(APIClient.class);
    private CloseableHttpClient client;
    private final String url;
    private final Map<String, String> headers = new HashMap<>();

    public APIClient(String url) {
        this(url, 1000);
    }

    public APIClient(String url, Integer timeout) {
        try {
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(timeout)
                    .setConnectionRequestTimeout(timeout)
                    .setSocketTimeout(timeout).build();
            client = HttpClients.custom().setDefaultRequestConfig(config).
                    setHostnameVerifier(new AllowAllHostnameVerifier()).
                    setSslcontext(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                        @Override
                        public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                            return true;
                        }
                    }).build()).build();
        } catch (KeyManagementException | KeyStoreException | NoSuchAlgorithmException ex) {
            log.error(ex.getMessage(), ex);
        }

        this.url = url;

    }

    @Override
    public APIClient header(String key, String value) {
        headers.put(key, value);
        return this;
    }

    private void addHeaders(HttpRequestBase request) {
        headers.entrySet().forEach(header -> request.addHeader(header.getKey(), header.getValue()));
    }

    @Override
    public RequestType get() {
        final HttpGet request = new HttpGet(url);
        addHeaders(request);
        return new RequestType(null) {
            @Override
            public Response sync() throws IOException {
                Response response = new Response(client.execute(request));
                close();
                return response;
            }

            @Override
            public void async(final IAPICallback callback) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Response response = new Response(client.execute(request));
                            close();
                            callback.onComplete(response);
                        } catch (IOException ex) {
                            log.error(ex.getMessage(), ex);
                            callback.onError(ex);
                        }
                    }
                }).start();
            }
        };
    }

    @Override
    public RequestType post(Object object) {
        final HttpPost request = new HttpPost(url);
        addHeaders(request);

        return new RequestType(object) {
            @Override
            public Response sync() throws IOException {
                request.setEntity(getEntity());
                Response response = new Response(client.execute(request));
                close();
                return response;
            }

            @Override
            public void async(final IAPICallback callback) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        request.setEntity(getEntity());
                        try {
                            Response response = new Response(client.execute(request));
                            close();
                            callback.onComplete(response);
                        } catch (IOException ex) {
                            log.error(ex.getMessage(), ex);
                            callback.onError(ex);
                        }
                    }
                }).start();
            }

        };
    }

    @Override
    public RequestType put(Object object) {
        final HttpPut request = new HttpPut(url);
        addHeaders(request);

        return new RequestType(object) {
            @Override
            public Response sync() throws IOException {
                request.setEntity(getEntity());
                Response response = new Response(client.execute(request));
                close();
                return response;
            }

            @Override
            public void async(final IAPICallback callback) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        request.setEntity(getEntity());
                        try {
                            Response r = new Response(client.execute(request));
                            close();
                            callback.onComplete(r);
                        } catch (IOException ex) {
                            log.error(ex.getMessage(), ex);
                            callback.onError(ex);
                        }
                    }
                }).start();
            }

        };
    }

    private void close() throws IOException {
        this.client.close();
    }

    @Override
    public RequestType patch(Object object) {
        final HttpPatch request = new HttpPatch(url);
        addHeaders(request);

        return new RequestType(object) {
            @Override
            public Response sync() throws IOException {
                request.setEntity(getEntity());
                Response response = new Response(client.execute(request));
                close();
                return response;
            }

            @Override
            public void async(final IAPICallback callback) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        request.setEntity(getEntity());
                        try {
                            Response r = new Response(client.execute(request));
                            close();
                            callback.onComplete(r);
                        } catch (IOException ex) {
                            log.error(ex.getMessage(), ex);
                            callback.onError(ex);
                        }
                    }
                }).start();
            }

        };
    }

    public static interface IAPICallback {

        void onComplete(Response response);

        void onError(Throwable t);
    }
}
