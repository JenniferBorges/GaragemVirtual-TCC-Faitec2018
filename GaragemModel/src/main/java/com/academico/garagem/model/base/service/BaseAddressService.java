package com.academico.garagem.model.base.service;

import com.academico.garagem.model.base.BaseCRUDService;
import com.academico.garagem.model.entity.Address;
import com.google.gson.JsonObject;

public interface BaseAddressService extends BaseCRUDService<Address> {

    public JsonObject getLatLngFromAddress(String address) throws Exception;

}
