package com.academico.garagem.controller;

import com.google.gson.JsonObject;
import java.util.Calendar;
import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/info")
public class ApiInfoController {

    @GET
    @PermitAll
    @Path("/time")
    @Produces(MediaType.APPLICATION_JSON)
    public Response apiTime() {
        Calendar dateTime = Calendar.getInstance();
        int hourOfDay = dateTime.get(Calendar.HOUR_OF_DAY);
        int minuteOfHour = dateTime.get(Calendar.MINUTE);

        JsonObject json = new JsonObject();
        json.addProperty("serverTime", String.format("%02d", hourOfDay) + ":" + String.format("%02d", minuteOfHour));

        return Response.ok(json.toString()).build();

    }

}
