/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.academico.garagem.model.service;

import com.academico.garagem.model.base.BaseService;
import com.academico.garagem.model.base.service.BaseDisponibilityService;
import com.academico.garagem.model.dao.DisponibilityDAO;
import com.academico.garagem.model.entity.Disponibility;
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
public class DisponibilityService extends BaseService implements Serializable, BaseDisponibilityService {

    public DisponibilityService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void create(Disponibility disponibility) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DisponibilityDAO dao = new DisponibilityDAO();
            dao.create(em, disponibility);
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
    public List<Disponibility> findAll() throws Exception {
        return findEntities(true, -1, -1);
    }

    @Override
    public List<Disponibility> findEntities(int maxResults, int firstResult) throws Exception {
        return findEntities(false, maxResults, firstResult);
    }

    private List<Disponibility> findEntities(boolean all, int maxResults, int firstResult) throws Exception {
        EntityManager em = getEntityManager();
        try {
            DisponibilityDAO dao = new DisponibilityDAO();
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
    public List<Disponibility> findByCriteria(Map<Integer, Object> criteria, int limit, int offset) throws Exception {
        EntityManager em = getEntityManager();
        try {
            DisponibilityDAO dao = new DisponibilityDAO();
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
    public Disponibility findEntity(Integer id) throws Exception {
        EntityManager em = getEntityManager();
        try {
            DisponibilityDAO dao = new DisponibilityDAO();
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
            DisponibilityDAO dao = new DisponibilityDAO();
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
    public void edit(Disponibility disponibility) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DisponibilityDAO dao = new DisponibilityDAO();
            dao.edit(em, disponibility);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = disponibility.getId();
                if (findEntity(id) == null) {
                    throw new NonexistentEntityException("The disponibility with id " + id + " no longer exists.");
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
            DisponibilityDAO dao = new DisponibilityDAO();
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
    public void destroyByAdvertisementId(Integer id) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DisponibilityDAO dao = new DisponibilityDAO();
            dao.destroyByAdvertisementId(em, id);
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
