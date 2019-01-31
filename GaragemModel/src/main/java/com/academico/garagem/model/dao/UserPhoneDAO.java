package com.academico.garagem.model.dao;

import com.academico.garagem.model.base.BaseEntity_;
import com.academico.garagem.model.base.dao.BaseUserPhoneDAO;
import com.academico.garagem.model.criteria.UserPhoneCriteria;
import com.academico.garagem.model.entity.User;
import com.academico.garagem.model.entity.UserPhone;
import com.academico.garagem.model.entity.UserPhone_;
import com.academico.garagem.model.service.exceptions.NonexistentEntityException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserPhoneDAO implements BaseUserPhoneDAO {

    @Override
    public void create(EntityManager em, UserPhone entity) throws Exception {
        User userId = entity.getUserId();
        if (userId != null) {
            userId = em.find(userId.getClass(), userId.getId());
            entity.setUserId(userId);
        }
        em.persist(entity);
        if (userId != null) {
            userId.getUserPhoneList().add(entity);
            userId = em.merge(userId);
        }
    }

    @Override
    public List<UserPhone> findEntities(EntityManager em, boolean all, int maxResults, int firstResult) throws Exception {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(UserPhone.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    @Override
    public List<UserPhone> findByCriteria(EntityManager em, Map<Integer, Object> criteria, int limit, int offset) throws Exception {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserPhone> cq = cb.createQuery(UserPhone.class);
        Root<UserPhone> up = cq.from(UserPhone.class);

        List<Predicate> predList = new LinkedList<>();
        if (criteria != null && !criteria.isEmpty()) {
            if (criteria.containsKey(UserPhoneCriteria.ID_NE)) {
                int id = (int) criteria.get(UserPhoneCriteria.ID_NE);
                predList.add(cb.notEqual(up.get(BaseEntity_.id), id));
            }
            if (criteria.containsKey(UserPhoneCriteria.NUMBER_EQ)) {
                String phone = (String) criteria.get(UserPhoneCriteria.NUMBER_EQ);
                predList.add(cb.equal(up.get(UserPhone_.number), phone));
            }
            if (criteria.containsKey(UserPhoneCriteria.USER_ID_EQ)) {
                int user = (int) criteria.get(UserPhoneCriteria.USER_ID_EQ);
                predList.add(cb.equal(up.get(UserPhone_.userId).get(BaseEntity_.id), user));
            }
        }
        Predicate[] predArray = new Predicate[predList.size()];
        predList.toArray(predArray);

        cq.where(predArray);
        Query q = em.createQuery(cq);
        if (limit > 0) {
            q.setMaxResults(limit);
        }
        if (offset > 0) {
            q.setFirstResult(offset);
        }
        return q.getResultList();
    }

    @Override
    public UserPhone findEntity(EntityManager em, Integer id) throws Exception {
        return em.find(UserPhone.class, id);
    }

    @Override
    public int getEntityCount(EntityManager em) throws Exception {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<UserPhone> rt = cq.from(UserPhone.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void edit(EntityManager em, UserPhone entity) throws Exception {
        UserPhone persistentUserPhone = em.find(UserPhone.class, entity.getId());
        User userIdOld = persistentUserPhone.getUserId();
        User userIdNew = entity.getUserId();
        if (userIdNew != null) {
            userIdNew = em.find(userIdNew.getClass(), userIdNew.getId());
            entity.setUserId(userIdNew);
        }
        entity = em.merge(entity);
        if (userIdOld != null && !userIdOld.equals(userIdNew)) {
            userIdOld.getUserPhoneList().remove(entity);
            userIdOld = em.merge(userIdOld);
        }
        if (userIdNew != null && !userIdNew.equals(userIdOld)) {
            userIdNew.getUserPhoneList().add(entity);
            userIdNew = em.merge(userIdNew);
        }
    }

    @Override
    public void destroy(EntityManager em, Integer id) throws Exception {
        UserPhone userPhone;
        try {
            userPhone = em.find(UserPhone.class, id);
            userPhone.getId();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The userPhone with id " + id + " no longer exists.", enfe);
        }
        User userId = userPhone.getUserId();
        if (userId != null) {
            userId.getUserPhoneList().remove(userPhone);
            userId = em.merge(userId);
        }
        em.remove(userPhone);
    }

    @Override
    public void destroyByUserId(EntityManager em, Integer id) {
        em.createNamedQuery("UserPhone.deleteByUserId")
                .setParameter("userId", id)
                .executeUpdate();
    }

}
