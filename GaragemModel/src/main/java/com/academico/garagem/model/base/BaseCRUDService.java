package com.academico.garagem.model.base;

import java.util.List;
import java.util.Map;

public interface BaseCRUDService<E extends BaseEntity> {

    public void create(E entity) throws Exception;

    public List<E> findAll() throws Exception;

    public List<E> findEntities(int maxResults, int firstResult) throws Exception;

    public List<E> findByCriteria(Map<Integer, Object> criteria, int limit, int offset) throws Exception;

    public E findEntity(Integer id) throws Exception;

    public int getEntityCount() throws Exception;

    public void edit(E entity) throws Exception;

    public void destroy(Integer id) throws Exception;

    public Map<String, String> validate(Map<String, Object> fields) throws Exception;

}
