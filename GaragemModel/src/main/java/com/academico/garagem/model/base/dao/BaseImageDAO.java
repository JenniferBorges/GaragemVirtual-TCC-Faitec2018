package com.academico.garagem.model.base.dao;

import com.academico.garagem.model.entity.Image;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

public interface BaseImageDAO {

    public void create(EntityManager em, Image entity) throws Exception;

    public List<Image> findEntities(EntityManager em, boolean all, int maxResults, int firstResult) throws Exception;

    public List<Image> findByCriteria(EntityManager em, Map<Integer, Object> criteria, int limit, int offset) throws Exception;

    public Image findEntity(EntityManager em, String id) throws Exception;

    public int getEntityCount(EntityManager em) throws Exception;

    public void edit(EntityManager em, Image entity) throws Exception;

    public void destroy(EntityManager em, String id) throws Exception;

}
