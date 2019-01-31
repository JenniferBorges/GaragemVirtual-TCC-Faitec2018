package com.academico.garagem.model.dao;

import com.academico.garagem.model.base.BaseEntity_;
import com.academico.garagem.model.base.dao.BaseDisponibilityDAO;
import com.academico.garagem.model.criteria.DisponibilityCriteria;
import com.academico.garagem.model.entity.Advertisement;
import com.academico.garagem.model.entity.Disponibility;
import com.academico.garagem.model.entity.Disponibility_;
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

public class DisponibilityDAO implements BaseDisponibilityDAO {

    @Override
    public void create(EntityManager em, Disponibility entity) throws Exception {
        Advertisement advertisementId = entity.getAdvertisementId();
        if (advertisementId != null) {
            advertisementId = em.find(advertisementId.getClass(), advertisementId.getId());
            entity.setAdvertisementId(advertisementId);
        }
        em.persist(entity);
        if (advertisementId != null) {
            advertisementId.getDisponibilityList().add(entity);
            advertisementId = em.merge(advertisementId);
        }
    }

    @Override
    public List<Disponibility> findEntities(EntityManager em, boolean all, int maxResults, int firstResult) throws Exception {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Disponibility.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    @Override
    public List<Disponibility> findByCriteria(EntityManager em, Map<Integer, Object> criteria, int limit, int offset) throws Exception {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Disponibility> cq = cb.createQuery(Disponibility.class);
        Root<Disponibility> d = cq.from(Disponibility.class);

        List<Predicate> predList = new LinkedList<>();
        if (criteria != null && !criteria.isEmpty()) {
            if (criteria.containsKey(DisponibilityCriteria.DAY_EQ)) {
                int day = (int) criteria.get(DisponibilityCriteria.DAY_EQ);
                predList.add(cb.equal(d.get(Disponibility_.day), day));
            }
            if (criteria.containsKey(DisponibilityCriteria.STARTS_AT_EQ)) {
                Date startsAt = (Date) criteria.get(DisponibilityCriteria.STARTS_AT_EQ);
                predList.add(cb.equal(d.<Date>get(Disponibility_.startsAt), startsAt));
            }
            if (criteria.containsKey(DisponibilityCriteria.STARTS_AT_GT)) {
                Date startsAt = (Date) criteria.get(DisponibilityCriteria.STARTS_AT_GT);
                predList.add(cb.greaterThan(d.<Date>get(Disponibility_.startsAt), startsAt));
            }
            if (criteria.containsKey(DisponibilityCriteria.STARTS_AT_LT)) {
                Date startsAt = (Date) criteria.get(DisponibilityCriteria.STARTS_AT_LT);
                predList.add(cb.lessThan(d.<Date>get(Disponibility_.startsAt), startsAt));
            }
            if (criteria.containsKey(DisponibilityCriteria.ENDS_AT_EQ)) {
                Date endsAt = (Date) criteria.get(DisponibilityCriteria.ENDS_AT_EQ);
                predList.add(cb.equal(d.get(Disponibility_.endsAt), endsAt));
            }
            if (criteria.containsKey(DisponibilityCriteria.ENDS_AT_GT)) {
                Date endsAt = (Date) criteria.get(DisponibilityCriteria.ENDS_AT_GT);
                predList.add(cb.greaterThan(d.<Date>get(Disponibility_.endsAt), endsAt));
            }
            if (criteria.containsKey(DisponibilityCriteria.ENDS_AT_LT)) {
                Date endsAt = (Date) criteria.get(DisponibilityCriteria.ENDS_AT_LT);
                predList.add(cb.lessThan(d.<Date>get(Disponibility_.endsAt), endsAt));
            }
            if (criteria.containsKey(DisponibilityCriteria.ADVERTISEMENT_ID_EQ)) {
                int advertisementId = (int) criteria.get(DisponibilityCriteria.ADVERTISEMENT_ID_EQ);
                predList.add(cb.equal(d.get(Disponibility_.advertisementId).get(BaseEntity_.id), advertisementId));
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
    public Disponibility findEntity(EntityManager em, Integer id) throws Exception {
        return em.find(Disponibility.class, id);
    }

    @Override
    public int getEntityCount(EntityManager em) throws Exception {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Disponibility> rt = cq.from(Disponibility.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void edit(EntityManager em, Disponibility entity) throws Exception {
        Disponibility persistentDisponibility = em.find(Disponibility.class, entity.getId());
        Advertisement advertisementIdOld = persistentDisponibility.getAdvertisementId();
        Advertisement advertisementIdNew = entity.getAdvertisementId();
        if (advertisementIdNew != null) {
            advertisementIdNew = em.find(advertisementIdNew.getClass(), advertisementIdNew.getId());
            entity.setAdvertisementId(advertisementIdNew);
        }
        entity = em.merge(entity);
        if (advertisementIdOld != null && !advertisementIdOld.equals(advertisementIdNew)) {
            advertisementIdOld.getDisponibilityList().remove(entity);
            advertisementIdOld = em.merge(advertisementIdOld);
        }
        if (advertisementIdNew != null && !advertisementIdNew.equals(advertisementIdOld)) {
            advertisementIdNew.getDisponibilityList().add(entity);
            advertisementIdNew = em.merge(advertisementIdNew);
        }
    }

    @Override
    public void destroy(EntityManager em, Integer id) throws Exception {
        Disponibility disponibility;
        try {
            disponibility = em.find(Disponibility.class, id);
            disponibility.getId();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The disponibility with id " + id + " no longer exists.", enfe);
        }
        Advertisement advertisement = disponibility.getAdvertisementId();
        if (advertisement != null) {
            advertisement.getDisponibilityList().remove(disponibility);
            advertisement = em.merge(advertisement);
        }
        em.remove(disponibility);
    }

    @Override
    public void destroyByAdvertisementId(EntityManager em, Integer id) throws Exception {
        em.createNamedQuery("Disponibility.deleteByAdvertisementId")
                .setParameter("advertisementId", id)
                .executeUpdate();
    }

}
