package com.academico.garagem.filter;

import com.academico.garagem.util.JRequestUtils;
import com.google.gson.JsonParser;
import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import org.apache.log4j.Logger;

@Provider
public class JLogFilter implements ContainerResponseFilter {

    public static final Logger LOGGER = Logger.getLogger(JLogFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        String log = JRequestUtils.getURI(requestContext);
        log += "  |  " + responseContext.getStatus();

        if (responseContext.getStatusInfo().getFamily().equals(Response.Status.Family.SERVER_ERROR)) {
            LOGGER.error(log);
            LOGGER.error("Exception não tratada", (Throwable) responseContext.getEntity());
            responseContext.setEntity(new JsonParser().parse("{status:500,message:\"Ops.. Algo de errado aconteceu\"").toString());
        } else {
            LOGGER.info(log);
        }

    }

}
