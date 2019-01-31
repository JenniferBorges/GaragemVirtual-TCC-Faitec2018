package com.academico.garagem.model.dao;

import com.academico.garagem.model.base.dao.BaseRatingDAO;
import com.academico.garagem.model.criteria.RatingCriteria;
import com.academico.garagem.model.entity.Rating;
import com.academico.garagem.model.entity.RatingPK;
import com.academico.garagem.model.entity.RatingPK_;
import com.academico.garagem.model.entity.Rating_;
import com.academico.garagem.model.entity.RentGarage;
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

public class RatingDAO implements BaseRatingDAO {

    @Override
    public void create(EntityManager em, Rating entity) throws Exception {
        RentGarage rentGarage = entity.getRentGarage();
        if (rentGarage != null) {
            rentGarage = em.find(rentGarage.getClass(), rentGarage.getId());
            entity.setRentGarage(rentGarage);
        }
        em.persist(entity);
        if (rentGarage != null) {
            rentGarage.getRatingList().add(entity);
            rentGarage = em.merge(rentGarage);
        }
    }

    @Override
    public List<Rating> findEntities(EntityManager em, boolean all, int maxResults, int firstResult) throws Exception {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Rating.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    @Override
    public List<Rating> findByCriteria(EntityManager em, Map<Integer, Object> criteria, int limit, int offset) throws Exception {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rating> cq = cb.createQuery(Rating.class);
        Root<Rating> r = cq.from(Rating.class);

        List<Predicate> predList = new LinkedList<>();
        if (criteria != null && !criteria.isEmpty()) {
            if (criteria.containsKey(RatingCriteria.RENT_GARAGE_ID_EQ)) {
                int id = (int) criteria.get(RatingCriteria.RENT_GARAGE_ID_EQ);
                predList.add(cb.equal(r.get(Rating_.ratingPK).get(RatingPK_.rentGarageId), id));
            }
            if (criteria.containsKey(RatingCriteria.USER_ID_EQ)) {
                int user = (int) criteria.get(RatingCriteria.USER_ID_EQ);
                predList.add(cb.equal(r.get(Rating_.ratingPK).get(RatingPK_.userId), user));
            }
            if (criteria.containsKey(RatingCriteria.RATING_EQ)) {
                int rating = (int) criteria.get(RatingCriteria.RATING_EQ);
                predList.add(cb.equal(r.get(Rating_.rating), rating));
            }
            if (criteria.containsKey(RatingCriteria.MESSAGE_EQ)) {
                String message = (String) criteria.get(RatingCriteria.MESSAGE_EQ);
                predList.add(cb.equal(r.get(Rating_.message), message));
            }
            if (criteria.containsKey(RatingCriteria.MESSAGE_ILIKE)) {
                String message = (String) criteria.get(RatingCriteria.MESSAGE_ILIKE);
                predList.add(cb.like(r.get(Rating_.message), "%" + message + "%"));
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
    public Rating findEntity(EntityManager em, RatingPK id) throws Exception {
        return em.find(Rating.class, id);
    }

    @Override
    public int getEntityCount(EntityManager em) throws Exception {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Rating> rt = cq.from(Rating.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void edit(EntityManager em, Rating entity) throws Exception {
        Rating persistentRating = em.find(Rating.class, entity.getRatingPK());
        RentGarage rentGarageOld = persistentRating.getRentGarage();
        RentGarage rentGarageNew = entity.getRentGarage();
        if (rentGarageNew != null) {
            rentGarageNew = em.find(rentGarageNew.getClass(), rentGarageNew.getId());
            entity.setRentGarage(rentGarageNew);
        }
        entity = em.merge(entity);
        if (rentGarageOld != null && !rentGarageOld.equals(rentGarageNew)) {
            rentGarageOld.getRatingList().remove(entity);
            rentGarageOld = em.merge(rentGarageOld);
        }
        if (rentGarageNew != null && !rentGarageNew.equals(rentGarageOld)) {
            rentGarageNew.getRatingList().add(entity);
            rentGarageNew = em.merge(rentGarageNew);
        }
    }

    @Override
    public void destroy(EntityManager em, RatingPK id) throws Exception {
        Rating rating;
        try {
            rating = em.find(Rating.class, id);
            rating.getRatingPK();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The rating with id " + id + " no longer exists.", enfe);
        }
        RentGarage rentGarage = rating.getRentGarage();
        if (rentGarage != null) {
            rentGarage.getRatingList().remove(rating);
            rentGarage = em.merge(rentGarage);
        }
        em.remove(rating);
    }

}
