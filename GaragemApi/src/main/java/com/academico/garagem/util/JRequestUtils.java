package com.academico.garagem.util;

import java.net.URI;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.UriInfo;

public class JRequestUtils {

    public static String getURI(ContainerRequestContext requestContext) {
        final UriInfo uriInfo = requestContext.getUriInfo();

        String log = requestContext.getMethod() + "  |  /" + uriInfo.getPath();
        final String query = uriInfo.getRequestUri().getQuery();
        log += query != null ? "?" + query : "";

        return log;
    }

    public static String getURL(ContainerRequestContext requestContext) {
        final UriInfo uriInfo = requestContext.getUriInfo();
        final URI uri = uriInfo.getRequestUri();
        String scheme = uri.getScheme() + "://";
        String host = uri.getHost();
        String port = ":" + (uri.getPort() == -1 ? 80 : uri.getPort());
        String context = uri.getPath();
        context = context.substring(0, context.indexOf("/api"));

        System.out.println(scheme + host + port + context);
        //return scheme + host + port + context;
        return "https://demo.leucotron.com.br/leucobot";
    }

}
