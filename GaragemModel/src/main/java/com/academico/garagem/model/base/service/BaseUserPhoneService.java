package com.academico.garagem.model.base.service;

import com.academico.garagem.model.base.BaseCRUDService;
import com.academico.garagem.model.entity.UserPhone;

public interface BaseUserPhoneService extends BaseCRUDService<UserPhone> {

    public void destroyByUserId(Integer id) throws Exception;

}
