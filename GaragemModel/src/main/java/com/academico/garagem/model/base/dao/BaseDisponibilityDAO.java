package com.academico.garagem.model.base.dao;

import com.academico.garagem.model.base.BaseDAO;
import com.academico.garagem.model.entity.Disponibility;
import javax.persistence.EntityManager;

public interface BaseDisponibilityDAO extends BaseDAO<Disponibility> {

    public void destroyByAdvertisementId(EntityManager em, Integer id) throws Exception;

}
