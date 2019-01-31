package com.academico.garagem.model.base.dao;

import com.academico.garagem.model.base.BaseDAO;
import com.academico.garagem.model.entity.UserPhone;
import javax.persistence.EntityManager;

public interface BaseUserPhoneDAO extends BaseDAO<UserPhone> {

    public void destroyByUserId(EntityManager em, Integer id);

}
