package com.academico.garagem.model.base.dao;

import com.academico.garagem.model.entity.Rating;
import com.academico.garagem.model.entity.RatingPK;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

public interface BaseRatingDAO {

    public void create(EntityManager em, Rating entity) throws Exception;

    public List<Rating> findEntities(EntityManager em, boolean all, int maxResults, int firstResult) throws Exception;

    public List<Rating> findByCriteria(EntityManager em, Map<Integer, Object> criteria, int limit, int offset) throws Exception;

    public Rating findEntity(EntityManager em, RatingPK id) throws Exception;

    public int getEntityCount(EntityManager em) throws Exception;

    public void edit(EntityManager em, Rating entity) throws Exception;

    public void destroy(EntityManager em, RatingPK id) throws Exception;

}
