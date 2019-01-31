package com.academico.garagem.model.dao;

import com.academico.garagem.model.base.BaseEntity_;
import com.academico.garagem.model.base.dao.BaseAdvertisementDAO;
import com.academico.garagem.model.criteria.AdvertisementCriteria;
import com.academico.garagem.model.entity.Advertisement;
import com.academico.garagem.model.entity.Advertisement_;
import com.academico.garagem.model.entity.Disponibility;
import com.academico.garagem.model.entity.Disponibility_;
import com.academico.garagem.model.entity.Garage;
import com.academico.garagem.model.entity.Garage_;
import com.academico.garagem.model.entity.RentGarage;
import com.academico.garagem.model.service.exceptions.IllegalOrphanException;
import com.academico.garagem.model.service.exceptions.NonexistentEntityException;
import java.util.ArrayList;
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

public class AdvertisementDAO implements BaseAdvertisementDAO {

    @Override
    public void create(EntityManager em, Advertisement entity) throws Exception {
        Garage garageId = entity.getGarageId();
        if (garageId != null) {
            garageId = em.find(garageId.getClass(), garageId.getId());
            entity.setGarageId(garageId);
        }
        List<Disponibility> attachedDisponibilityList = new ArrayList<Disponibility>();
        for (Disponibility disponibilityListDisponibilityToAttach : entity.getDisponibilityList()) {
            disponibilityListDisponibilityToAttach = em.find(disponibilityListDisponibilityToAttach.getClass(), disponibilityListDisponibilityToAttach.getId());
            attachedDisponibilityList.add(disponibilityListDisponibilityToAttach);
        }
        entity.setDisponibilityList(attachedDisponibilityList);
        List<RentGarage> attachedRentGarageList = new ArrayList<RentGarage>();
        for (RentGarage rentGarageListRentGarageToAttach : entity.getRentGarageList()) {
            rentGarageListRentGarageToAttach = em.find(rentGarageListRentGarageToAttach.getClass(), rentGarageListRentGarageToAttach.getId());
            attachedRentGarageList.add(rentGarageListRentGarageToAttach);
        }
        entity.setRentGarageList(attachedRentGarageList);
        em.persist(entity);
        if (garageId != null) {
            garageId.getAdvertisementList().add(entity);
            garageId = em.merge(garageId);
        }
        for (Disponibility disponibilityListDisponibility : entity.getDisponibilityList()) {
            Advertisement oldAdvertisementIdOfDisponibilityListDisponibility = disponibilityListDisponibility.getAdvertisementId();
            disponibilityListDisponibility.setAdvertisementId(entity);
            disponibilityListDisponibility = em.merge(disponibilityListDisponibility);
            if (oldAdvertisementIdOfDisponibilityListDisponibility != null) {
                oldAdvertisementIdOfDisponibilityListDisponibility.getDisponibilityList().remove(disponibilityListDisponibility);
                oldAdvertisementIdOfDisponibilityListDisponibility = em.merge(oldAdvertisementIdOfDisponibilityListDisponibility);
            }
        }
        for (RentGarage rentGarageListRentGarage : entity.getRentGarageList()) {
            Advertisement oldAdvertisementIdOfRentGarageListRentGarage = rentGarageListRentGarage.getAdvertisementId();
            rentGarageListRentGarage.setAdvertisementId(entity);
            rentGarageListRentGarage = em.merge(rentGarageListRentGarage);
            if (oldAdvertisementIdOfRentGarageListRentGarage != null) {
                oldAdvertisementIdOfRentGarageListRentGarage.getRentGarageList().remove(rentGarageListRentGarage);
                oldAdvertisementIdOfRentGarageListRentGarage = em.merge(oldAdvertisementIdOfRentGarageListRentGarage);
            }
        }
    }

    @Override
    public List<Advertisement> findEntities(EntityManager em, boolean all, int maxResults, int firstResult) throws Exception {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Advertisement.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    @Override
    public List<Advertisement> findByCriteria(EntityManager em, Map<Integer, Object> criteria, int limit, int offset) throws Exception {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Advertisement> cq = cb.createQuery(Advertisement.class);
        Root<Advertisement> a = cq.from(Advertisement.class);

        List<Predicate> predList = new LinkedList<>();
        if (criteria != null && !criteria.isEmpty()) {
            if (criteria.containsKey(AdvertisementCriteria.ID_EQ)) {
                int id = (int) criteria.get(AdvertisementCriteria.ID_EQ);
                predList.add(cb.equal(a.get(BaseEntity_.id), id));
            }
            if (criteria.containsKey(AdvertisementCriteria.TITLE_EQ)) {
                String title = (String) criteria.get(AdvertisementCriteria.TITLE_EQ);
                predList.add(cb.equal(a.get(Advertisement_.title), title));
            }
            if (criteria.containsKey(AdvertisementCriteria.TITLE_ILIKE)) {
                String title = (String) criteria.get(AdvertisementCriteria.TITLE_ILIKE);
                predList.add(cb.like(a.get(Advertisement_.title), "%" + title + "%"));
            }
            if (criteria.containsKey(AdvertisementCriteria.DESCRIPTION_EQ)) {
                String description = (String) criteria.get(AdvertisementCriteria.DESCRIPTION_EQ);
                predList.add(cb.equal(a.get(Advertisement_.description), description));
            }
            if (criteria.containsKey(AdvertisementCriteria.DESCRIPTION_ILIKE)) {
                String description = (String) criteria.get(AdvertisementCriteria.DESCRIPTION_ILIKE);
                predList.add(cb.like(a.get(Advertisement_.description), "%" + description + "%"));
            }
            if (criteria.containsKey(AdvertisementCriteria.PRICE_EQ)) {
                double price = (double) criteria.get(AdvertisementCriteria.PRICE_EQ);
                predList.add(cb.equal(a.get(Advertisement_.price), price));
            }
            if (criteria.containsKey(AdvertisementCriteria.PRICE_GT)) {
                double price = (double) criteria.get(AdvertisementCriteria.PRICE_GT);
                predList.add(cb.lt(a.get(Advertisement_.price), price));
            }
            if (criteria.containsKey(AdvertisementCriteria.PRICE_LT)) {
                double price = (double) criteria.get(AdvertisementCriteria.PRICE_LT);
                predList.add(cb.gt(a.get(Advertisement_.price), price));
            }
            if (criteria.containsKey(AdvertisementCriteria.ACTIVE_EQ)) {
                boolean active = (boolean) criteria.get(AdvertisementCriteria.ACTIVE_EQ);
                predList.add(cb.equal(a.get(Advertisement_.active), active));
            }
            if (criteria.containsKey(AdvertisementCriteria.GARAGE_ID_EQ)) {
                int garageId = (int) criteria.get(AdvertisementCriteria.GARAGE_ID_EQ);
                predList.add(cb.equal(a.get(Advertisement_.garageId).get(BaseEntity_.id), garageId));
            }
            if (criteria.containsKey(AdvertisementCriteria.GARAGE_ID_USER_ID_EQ)) {
                int userId = (int) criteria.get(AdvertisementCriteria.GARAGE_ID_USER_ID_EQ);
                predList.add(cb.equal(a.get(Advertisement_.garageId).get(Garage_.userId).get(BaseEntity_.id), userId));
            }
            if (criteria.containsKey(AdvertisementCriteria.DISPONIBILITY_LIST_DAY_EQ)) {
                int day = (int) criteria.get(AdvertisementCriteria.DISPONIBILITY_LIST_DAY_EQ);
                predList.add(cb.equal(a.join(Advertisement_.disponibilityList).get(Disponibility_.day), day));
            }
            if (criteria.containsKey(AdvertisementCriteria.DISPONIBILITY_LIST_STARTS_AT_EQ)) {
                Date startsAt = (Date) criteria.get(AdvertisementCriteria.DISPONIBILITY_LIST_STARTS_AT_EQ);
                predList.add(cb.equal(a.join(Advertisement_.disponibilityList).<Date>get(Disponibility_.startsAt), startsAt));
            }
            if (criteria.containsKey(AdvertisementCriteria.DISPONIBILITY_LIST_STARTS_AT_GT)) {
                Date startsAt = (Date) criteria.get(AdvertisementCriteria.DISPONIBILITY_LIST_STARTS_AT_GT);
                predList.add(cb.greaterThan(a.join(Advertisement_.disponibilityList).<Date>get(Disponibility_.startsAt), startsAt));
            }
            if (criteria.containsKey(AdvertisementCriteria.DISPONIBILITY_LIST_STARTS_AT_LT)) {
                Date startsAt = (Date) criteria.get(AdvertisementCriteria.DISPONIBILITY_LIST_STARTS_AT_LT);
                predList.add(cb.lessThan(a.join(Advertisement_.disponibilityList).<Date>get(Disponibility_.startsAt), startsAt));
            }
            if (criteria.containsKey(AdvertisementCriteria.DISPONIBILITY_LIST_ENDS_AT_EQ)) {
                Date endsAt = (Date) criteria.get(AdvertisementCriteria.DISPONIBILITY_LIST_ENDS_AT_EQ);
                predList.add(cb.equal(a.join(Advertisement_.disponibilityList).get(Disponibility_.endsAt), endsAt));
            }
            if (criteria.containsKey(AdvertisementCriteria.DISPONIBILITY_LIST_ENDS_AT_GT)) {
                Date endsAt = (Date) criteria.get(AdvertisementCriteria.DISPONIBILITY_LIST_ENDS_AT_GT);
                predList.add(cb.greaterThan(a.join(Advertisement_.disponibilityList).<Date>get(Disponibility_.endsAt), endsAt));
            }
            if (criteria.containsKey(AdvertisementCriteria.DISPONIBILITY_LIST_ENDS_AT_LT)) {
                Date endsAt = (Date) criteria.get(AdvertisementCriteria.DISPONIBILITY_LIST_ENDS_AT_LT);
                predList.add(cb.lessThan(a.join(Advertisement_.disponibilityList).<Date>get(Disponibility_.endsAt), endsAt));
            }
        }
        Predicate[] predArray = new Predicate[predList.size()];
        predList.toArray(predArray);

        cq.where(predArray);
        cq.groupBy(a.get(BaseEntity_.id));
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
    public Advertisement findEntity(EntityManager em, Integer id) throws Exception {
        return em.find(Advertisement.class, id);
    }

    @Override
    public int getEntityCount(EntityManager em) throws Exception {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Advertisement> rt = cq.from(Advertisement.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void edit(EntityManager em, Advertisement entity) throws Exception {
        Advertisement persistentAdvertisement = em.find(Advertisement.class, entity.getId());
        Garage garageIdOld = persistentAdvertisement.getGarageId();
        Garage garageIdNew = entity.getGarageId();
        List<Disponibility> disponibilityListOld = persistentAdvertisement.getDisponibilityList();
        List<Disponibility> disponibilityListNew = entity.getDisponibilityList();
        List<RentGarage> rentGarageListOld = persistentAdvertisement.getRentGarageList();
        List<RentGarage> rentGarageListNew = entity.getRentGarageList();
        List<String> illegalOrphanMessages = null;
        for (RentGarage rentGarageListOldRentGarage : rentGarageListOld) {
            if (!rentGarageListNew.contains(rentGarageListOldRentGarage)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain RentGarage " + rentGarageListOldRentGarage + " since its advertisementId field is not nullable.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        if (garageIdNew != null) {
            garageIdNew = em.find(garageIdNew.getClass(), garageIdNew.getId());
            entity.setGarageId(garageIdNew);
        }
        List<Disponibility> attachedDisponibilityListNew = new ArrayList<Disponibility>();
        for (Disponibility disponibilityListNewDisponibilityToAttach : disponibilityListNew) {
            disponibilityListNewDisponibilityToAttach = em.find(disponibilityListNewDisponibilityToAttach.getClass(), disponibilityListNewDisponibilityToAttach.getId());
            attachedDisponibilityListNew.add(disponibilityListNewDisponibilityToAttach);
        }
        disponibilityListNew = attachedDisponibilityListNew;
        entity.setDisponibilityList(disponibilityListNew);
        List<RentGarage> attachedRentGarageListNew = new ArrayList<RentGarage>();
        for (RentGarage rentGarageListNewRentGarageToAttach : rentGarageListNew) {
            rentGarageListNewRentGarageToAttach = em.find(rentGarageListNewRentGarageToAttach.getClass(), rentGarageListNewRentGarageToAttach.getId());
            attachedRentGarageListNew.add(rentGarageListNewRentGarageToAttach);
        }
        rentGarageListNew = attachedRentGarageListNew;
        entity.setRentGarageList(rentGarageListNew);
        entity = em.merge(entity);
        if (garageIdOld != null && !garageIdOld.equals(garageIdNew)) {
            garageIdOld.getAdvertisementList().remove(entity);
            garageIdOld = em.merge(garageIdOld);
        }
        if (garageIdNew != null && !garageIdNew.equals(garageIdOld)) {
            garageIdNew.getAdvertisementList().add(entity);
            garageIdNew = em.merge(garageIdNew);
        }
        for (Disponibility disponibilityListNewDisponibility : disponibilityListNew) {
            if (!disponibilityListOld.contains(disponibilityListNewDisponibility)) {
                Advertisement oldAdvertisementIdOfDisponibilityListNewDisponibility = disponibilityListNewDisponibility.getAdvertisementId();
                disponibilityListNewDisponibility.setAdvertisementId(entity);
                disponibilityListNewDisponibility = em.merge(disponibilityListNewDisponibility);
                if (oldAdvertisementIdOfDisponibilityListNewDisponibility != null && !oldAdvertisementIdOfDisponibilityListNewDisponibility.equals(entity)) {
                    oldAdvertisementIdOfDisponibilityListNewDisponibility.getDisponibilityList().remove(disponibilityListNewDisponibility);
                    oldAdvertisementIdOfDisponibilityListNewDisponibility = em.merge(oldAdvertisementIdOfDisponibilityListNewDisponibility);
                }
            }
        }
        for (RentGarage rentGarageListNewRentGarage : rentGarageListNew) {
            if (!rentGarageListOld.contains(rentGarageListNewRentGarage)) {
                Advertisement oldAdvertisementIdOfRentGarageListNewRentGarage = rentGarageListNewRentGarage.getAdvertisementId();
                rentGarageListNewRentGarage.setAdvertisementId(entity);
                rentGarageListNewRentGarage = em.merge(rentGarageListNewRentGarage);
                if (oldAdvertisementIdOfRentGarageListNewRentGarage != null && !oldAdvertisementIdOfRentGarageListNewRentGarage.equals(entity)) {
                    oldAdvertisementIdOfRentGarageListNewRentGarage.getRentGarageList().remove(rentGarageListNewRentGarage);
                    oldAdvertisementIdOfRentGarageListNewRentGarage = em.merge(oldAdvertisementIdOfRentGarageListNewRentGarage);
                }
            }
        }
    }

    @Override
    public void destroy(EntityManager em, Integer id) throws Exception {
        Advertisement advertisement;
        try {
            advertisement = em.find(Advertisement.class, id);
            advertisement.getId();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The advertisement with id " + id + " no longer exists.", enfe);
        }
        List<String> illegalOrphanMessages = null;
        List<RentGarage> rentGarageListOrphanCheck = advertisement.getRentGarageList();
        for (RentGarage rentGarageListOrphanCheckRentGarage : rentGarageListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Advertisement (" + advertisement + ") cannot be destroyed since the RentGarage " + rentGarageListOrphanCheckRentGarage + " in its rentGarageList field has a non-nullable advertisementId field.");
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        Garage garageId = advertisement.getGarageId();
        if (garageId != null) {
            garageId.getAdvertisementList().remove(advertisement);
            garageId = em.merge(garageId);
        }
        em.remove(advertisement);
    }

}
