/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.academico.garagem.model.service;

import com.academico.garagem.model.ServiceLocator;
import com.academico.garagem.model.base.BaseService;
import com.academico.garagem.model.base.service.BaseRentGarageService;
import com.academico.garagem.model.criteria.AdvertisementCriteria;
import com.academico.garagem.model.dao.RentGarageDAO;
import com.academico.garagem.model.entity.Advertisement;
import com.academico.garagem.model.entity.Message;
import com.academico.garagem.model.entity.Rating;
import com.academico.garagem.model.entity.RentGarage;
import com.academico.garagem.model.service.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
public class RentGarageService extends BaseService implements Serializable, BaseRentGarageService {

    public RentGarageService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void create(RentGarage rentGarage) throws Exception {
        if (rentGarage.getRatingList() == null) {
            rentGarage.setRatingList(new ArrayList<Rating>());
        }
        if (rentGarage.getMessageList() == null) {
            rentGarage.setMessageList(new ArrayList<Message>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RentGarageDAO dao = new RentGarageDAO();
            dao.create(em, rentGarage);
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
    public List<RentGarage> findAll() throws Exception {
        return findEntities(true, -1, -1);
    }

    @Override
    public List<RentGarage> findEntities(int maxResults, int firstResult) throws Exception {
        return findEntities(false, maxResults, firstResult);
    }

    private List<RentGarage> findEntities(boolean all, int maxResults, int firstResult) throws Exception {
        EntityManager em = getEntityManager();
        try {
            RentGarageDAO dao = new RentGarageDAO();
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
    public List<RentGarage> findByCriteria(Map<Integer, Object> criteria, int limit, int offset) throws Exception {
        EntityManager em = getEntityManager();
        try {
            RentGarageDAO dao = new RentGarageDAO();
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
    public RentGarage findEntity(Integer id) throws Exception {
        EntityManager em = getEntityManager();
        try {
            RentGarageDAO dao = new RentGarageDAO();
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
            RentGarageDAO dao = new RentGarageDAO();
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
    public void edit(RentGarage rentGarage) throws Exception {
        if (rentGarage.getRatingList() == null) {
            rentGarage.setRatingList(new ArrayList<Rating>());
        }
        if (rentGarage.getMessageList() == null) {
            rentGarage.setMessageList(new ArrayList<Message>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RentGarageDAO dao = new RentGarageDAO();
            dao.edit(em, rentGarage);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rentGarage.getId();
                if (findEntity(id) == null) {
                    throw new NonexistentEntityException("The rentGarage with id " + id + " no longer exists.");
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
            RentGarageDAO dao = new RentGarageDAO();
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
        Map<String, String> errors = new HashMap<>();

        Integer advertisement = (Integer) fields.get("advertisement");
        if (advertisement == null || advertisement == 0) {
            errors.put("advertisement", "Campo obrigatório!");
        }

        Integer vehicle = (Integer) fields.get("vehicle");
        if (vehicle == null || vehicle == 0) {
            errors.put("vehicle", "Campo obrigatório!");
        }

        Date dateTime = (Date) fields.get("dateTime");

        if (dateTime == null) {
            errors.put("dateTime", "Campo obrigatório!");
        } else {
            Calendar now = Calendar.getInstance();
            now.set(Calendar.SECOND, 0);
            now.set(Calendar.MILLISECOND, 0);
            if (dateTime.before(now.getTime())) {
                errors.put("dateTime", "Garagem não disponível nesse período");
            } else {
                Calendar c = Calendar.getInstance();
                c.setTime(dateTime);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

                Map<Integer, Object> criteria = new HashMap<>();
                criteria.put(AdvertisementCriteria.ID_EQ, advertisement);
                criteria.put(AdvertisementCriteria.DISPONIBILITY_LIST_DAY_EQ, dayOfWeek);
                criteria.put(AdvertisementCriteria.DISPONIBILITY_LIST_STARTS_AT_LT, dateTime);
                criteria.put(AdvertisementCriteria.DISPONIBILITY_LIST_ENDS_AT_GT, dateTime);
                List<Advertisement> advertisementList = ServiceLocator.getInstance().getAdvertisementService().findByCriteria(criteria, 0, 0);
                if (advertisementList == null || advertisementList.isEmpty()) {
                    errors.put("dateTime", "Garagem não disponível nesse período");
                }
            }
        }

        return errors;
    }

}
