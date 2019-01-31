/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.academico.garagem.model.service;

import com.academico.garagem.model.base.BaseService;
import com.academico.garagem.model.base.service.BaseGarageService;
import com.academico.garagem.model.dao.GarageDAO;
import com.academico.garagem.model.entity.Advertisement;
import com.academico.garagem.model.entity.Garage;
import com.academico.garagem.model.entity.Image;
import com.academico.garagem.model.service.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author mathe
 */
@Service
public class GarageService extends BaseService implements Serializable, BaseGarageService {

    public GarageService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void create(Garage garage) throws Exception {
        if (garage.getImageList() == null) {
            garage.setImageList(new ArrayList<Image>());
        }
        if (garage.getAdvertisementList() == null) {
            garage.setAdvertisementList(new ArrayList<Advertisement>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GarageDAO dao = new GarageDAO();
            dao.create(em, garage);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Garage> findAll() throws Exception {
        return findEntities(true, -1, -1);
    }

    @Override
    public List<Garage> findEntities(int maxResults, int firstResult) throws Exception {
        return findEntities(false, maxResults, firstResult);
    }

    private List<Garage> findEntities(boolean all, int maxResults, int firstResult) throws Exception {
        EntityManager em = getEntityManager();
        try {
            GarageDAO dao = new GarageDAO();
            return dao.findEntities(em, all, maxResults, firstResult);
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Garage> findByCriteria(Map<Integer, Object> criteria, int limit, int offset) throws Exception {
        EntityManager em = getEntityManager();
        try {
            GarageDAO dao = new GarageDAO();
            return dao.findByCriteria(em, criteria, limit, offset);
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public Garage findEntity(Integer id) throws Exception {
        EntityManager em = getEntityManager();
        try {
            GarageDAO dao = new GarageDAO();
            return dao.findEntity(em, id);
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public int getEntityCount() throws Exception {
        EntityManager em = getEntityManager();
        try {
            GarageDAO dao = new GarageDAO();
            return dao.getEntityCount(em);
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public void edit(Garage garage) throws Exception {
        if (garage.getImageList() == null) {
            garage.setImageList(new ArrayList<Image>());
        }
        if (garage.getAdvertisementList() == null) {
            garage.setAdvertisementList(new ArrayList<Advertisement>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GarageDAO dao = new GarageDAO();
            dao.edit(em, garage);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = garage.getId();
                if (findEntity(id) == null) {
                    throw new NonexistentEntityException("The garage with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void destroy(Integer id) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GarageDAO dao = new GarageDAO();
            dao.destroy(em, id);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Map<String, String> validate(Map<String, Object> fields) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
