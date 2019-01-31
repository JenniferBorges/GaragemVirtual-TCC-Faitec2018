package com.academico.garagem.model.base.service;

import com.academico.garagem.model.entity.Rating;
import com.academico.garagem.model.entity.RatingPK;
import java.util.List;
import java.util.Map;

public interface BaseRatingService {

    public void create(Rating entity) throws Exception;

    public List<Rating> findAll() throws Exception;

    public List<Rating> findEntities(int maxResults, int firstResult) throws Exception;

    public List<Rating> findByCriteria(Map<Integer, Object> criteria, int limit, int offset) throws Exception;

    public Rating findEntity(RatingPK id) throws Exception;

    public int getEntityCount() throws Exception;

    public void edit(Rating entity) throws Exception;

    public void destroy(RatingPK id) throws Exception;

    public Map<String, String> validate(Map<String, Object> fields) throws Exception;

}
