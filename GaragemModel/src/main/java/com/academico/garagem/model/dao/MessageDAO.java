package com.academico.garagem.model.dao;

import com.academico.garagem.model.base.BaseEntity_;
import com.academico.garagem.model.base.dao.BaseMessageDAO;
import com.academico.garagem.model.criteria.MessageCriteria;
import com.academico.garagem.model.entity.Message;
import com.academico.garagem.model.entity.Message_;
import com.academico.garagem.model.entity.RentGarage;
import com.academico.garagem.model.entity.User;
import com.academico.garagem.model.service.exceptions.NonexistentEntityException;
import java.util.Date;
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

public class MessageDAO implements BaseMessageDAO {

    @Override
    public void create(EntityManager em, Message entity) throws Exception {
        User userFromId = entity.getUserFromId();
        if (userFromId != null) {
            userFromId = em.find(userFromId.getClass(), userFromId.getId());
            entity.setUserFromId(userFromId);
        }
        User userToId = entity.getUserToId();
        if (userToId != null) {
            userToId = em.find(userToId.getClass(), userToId.getId());
            entity.setUserToId(userToId);
        }
        RentGarage rentGarageId = entity.getRentGarageId();
        if (rentGarageId != null) {
            rentGarageId = em.find(rentGarageId.getClass(), rentGarageId.getId());
            entity.setRentGarageId(rentGarageId);
        }
        em.persist(entity);
    }

    @Override
    public List<Message> findEntities(EntityManager em, boolean all, int maxResults, int firstResult) throws Exception {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Message.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    @Override
    public List<Message> findByCriteria(EntityManager em, Map<Integer, Object> criteria, int limit, int offset) throws Exception {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Message> cq = cb.createQuery(Message.class);
        Root<Message> m = cq.from(Message.class);

        List<Predicate> predList = new LinkedList<>();
        if (criteria != null && !criteria.isEmpty()) {
            if (criteria.containsKey(MessageCriteria.ID_NE)) {
                int id = (int) criteria.get(MessageCriteria.ID_NE);
                predList.add(cb.notEqual(m.get(BaseEntity_.id), id));
            }
            if (criteria.containsKey(MessageCriteria.DATE_GT)) {
                Date dateTime = (Date) criteria.get(MessageCriteria.DATE_GT);
                predList.add(cb.greaterThan(m.<Date>get(Message_.dateTime), dateTime));
            }
            if (criteria.containsKey(MessageCriteria.DATE_LT)) {
                Date dateTime = (Date) criteria.get(MessageCriteria.DATE_LT);
                predList.add(cb.lessThan(m.<Date>get(Message_.dateTime), dateTime));
            }
            if (criteria.containsKey(MessageCriteria.MESSAGE_ILIKE)) {
                String message = (String) criteria.get(MessageCriteria.MESSAGE_ILIKE);
                predList.add(cb.like(m.get(Message_.message), "%" + message + "%"));
            }
            if (criteria.containsKey(MessageCriteria.IS_READ_EQ)) {
                boolean isRead = (boolean) criteria.get(MessageCriteria.IS_READ_EQ);
                predList.add(cb.equal(m.get(Message_.isRead), isRead));
            }
            if (criteria.containsKey(MessageCriteria.USER_TO_EQ)) {
                int userToId = (int) criteria.get(MessageCriteria.USER_TO_EQ);
                predList.add(cb.equal(m.get(Message_.userToId).get(BaseEntity_.id), userToId));
            }
            if (criteria.containsKey(MessageCriteria.USER_FROM_EQ)) {
                int userFromId = (int) criteria.get(MessageCriteria.USER_FROM_EQ);
                predList.add(cb.equal(m.get(Message_.userFromId).get(BaseEntity_.id), userFromId));
            }
            if (criteria.containsKey(MessageCriteria.RENT_GARAGE_ID_EQ)) {
                int rentGarageId = (int) criteria.get(MessageCriteria.RENT_GARAGE_ID_EQ);
                predList.add(cb.equal(m.get(Message_.rentGarageId).get(BaseEntity_.id), rentGarageId));
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
    public Message findEntity(EntityManager em, Integer id) throws Exception {
        return em.find(Message.class, id);
    }

    @Override
    public int getEntityCount(EntityManager em) throws Exception {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Message> rt = cq.from(Message.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void edit(EntityManager em, Message entity) throws Exception {
        User userFromIdNew = entity.getUserFromId();
        User userToIdNew = entity.getUserToId();
        RentGarage rentGarageIdNew = entity.getRentGarageId();
        if (userFromIdNew != null) {
            userFromIdNew = em.find(userFromIdNew.getClass(), userFromIdNew.getId());
            entity.setUserFromId(userFromIdNew);
        }
        if (userToIdNew != null) {
            userToIdNew = em.find(userToIdNew.getClass(), userToIdNew.getId());
            entity.setUserToId(userToIdNew);
        }
        if (rentGarageIdNew != null) {
            rentGarageIdNew = em.find(rentGarageIdNew.getClass(), rentGarageIdNew.getId());
            entity.setRentGarageId(rentGarageIdNew);
        }
        entity = em.merge(entity);
    }

    @Override
    public void destroy(EntityManager em, Integer id) throws Exception {
        Message message;
        try {
            message = em.find(Message.class, id);
            message.getId();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The message with id " + id + " no longer exists.", enfe);
        }
        em.remove(message);
    }

}
