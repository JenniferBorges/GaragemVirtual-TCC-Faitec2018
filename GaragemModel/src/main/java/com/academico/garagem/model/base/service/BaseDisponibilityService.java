package com.academico.garagem.model.base.service;

import com.academico.garagem.model.base.BaseCRUDService;
import com.academico.garagem.model.entity.Disponibility;

public interface BaseDisponibilityService extends BaseCRUDService<Disponibility> {

    public void destroyByAdvertisementId(Integer id) throws Exception;

}
