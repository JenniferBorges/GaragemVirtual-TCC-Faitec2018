package com.academico.garagem.model.base.service;

import com.academico.garagem.model.entity.Image;
import java.io.File;
import java.util.List;
import java.util.Map;

public interface BaseImageService {

    public void create(Image disponibility) throws Exception;

    public List<Image> findAll() throws Exception;

    public List<Image> findEntities(int maxResults, int firstResult) throws Exception;

    public List<Image> findByCriteria(Map<Integer, Object> criteria, int limit, int offset) throws Exception;

    public Image findEntity(String id) throws Exception;

    public int getEntityCount() throws Exception;

    public void edit(Image disponibility) throws Exception;

    public void destroy(String id) throws Exception;

    public Map<String, String> validate(Map<String, Object> fields) throws Exception;

    public void savePhoto(byte[] bytes, String path) throws Exception;

    public File getPhoto(String path) throws Exception;

    public void deletePhoto(String path) throws Exception;

}
