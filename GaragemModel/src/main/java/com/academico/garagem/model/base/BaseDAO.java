package com.academico.garagem.model.base;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

public interface BaseDAO<E extends BaseEntity> {

    public void create(EntityManager em, E entity) throws Exception;

    public List<E> findEntities(EntityManager em, boolean all, int maxResults, int firstResult) throws Exception;

    public List<E> findByCriteria(EntityManager em, Map<Integer, Object> criteria, int limit, int offset) throws Exception;

    public E findEntity(EntityManager em, Integer id) throws Exception;

    public int getEntityCount(EntityManager em) throws Exception;

    public void edit(EntityManager em, E entity) throws Exception;

    public void destroy(EntityManager em, Integer id) throws Exception;

}
