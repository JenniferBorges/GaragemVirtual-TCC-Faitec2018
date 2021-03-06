/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.academico.garagem.model.service;

import com.academico.garagem.model.base.BaseService;
import com.academico.garagem.model.base.service.BaseRatingService;
import com.academico.garagem.model.dao.RatingDAO;
import com.academico.garagem.model.entity.Rating;
import com.academico.garagem.model.entity.RatingPK;
import com.academico.garagem.model.service.exceptions.NonexistentEntityException;
import java.io.Serializable;
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
public class RatingService extends BaseService implements Serializable, BaseRatingService {

    public RatingService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void create(Rating rating) throws Exception {
        if (rating.getRatingPK() == null) {
            rating.setRatingPK(new RatingPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RatingDAO dao = new RatingDAO();
            dao.create(em, rating);
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
    public List<Rating> findAll() throws Exception {
        return findEntities(true, -1, -1);
    }

    @Override
    public List<Rating> findEntities(int maxResults, int firstResult) throws Exception {
        return findEntities(false, maxResults, firstResult);
    }

    private List<Rating> findEntities(boolean all, int maxResults, int firstResult) throws Exception {
        EntityManager em = getEntityManager();
        try {
            RatingDAO dao = new RatingDAO();
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
    public List<Rating> findByCriteria(Map<Integer, Object> criteria, int limit, int offset) throws Exception {
        EntityManager em = getEntityManager();
        try {
            RatingDAO dao = new RatingDAO();
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
    public Rating findEntity(RatingPK id) throws Exception {
        EntityManager em = getEntityManager();
        try {
            RatingDAO dao = new RatingDAO();
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
            RatingDAO dao = new RatingDAO();
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
    public void edit(Rating rating) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RatingDAO dao = new RatingDAO();
            dao.edit(em, rating);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                RatingPK id = rating.getRatingPK();
                if (findEntity(id) == null) {
                    throw new NonexistentEntityException("The rating with id " + id + " no longer exists.");
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
    public void destroy(RatingPK id) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RatingDAO dao = new RatingDAO();
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
