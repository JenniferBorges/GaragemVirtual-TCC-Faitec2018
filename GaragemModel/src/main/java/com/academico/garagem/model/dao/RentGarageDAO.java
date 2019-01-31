package com.academico.garagem.model.dao;

import com.academico.garagem.model.base.BaseEntity_;
import com.academico.garagem.model.base.dao.BaseRentGarageDAO;
import com.academico.garagem.model.criteria.RentGarageCriteria;
import com.academico.garagem.model.entity.Advertisement;
import com.academico.garagem.model.entity.Advertisement_;
import com.academico.garagem.model.entity.Garage;
import com.academico.garagem.model.entity.Garage_;
import com.academico.garagem.model.entity.Message;
import com.academico.garagem.model.entity.Rating;
import com.academico.garagem.model.entity.RentGarage;
import com.academico.garagem.model.entity.RentGarage_;
import com.academico.garagem.model.entity.Vehicle;
import com.academico.garagem.model.entity.Vehicle_;
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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RentGarageDAO implements BaseRentGarageDAO {

    @Override
    public void create(EntityManager em, RentGarage entity) throws Exception {
        Advertisement advertisementId = entity.getAdvertisementId();
        if (advertisementId != null) {
            advertisementId = em.find(advertisementId.getClass(), advertisementId.getId());
            entity.setAdvertisementId(advertisementId);
        }
        Vehicle vehicleId = entity.getVehicleId();
        if (vehicleId != null) {
            vehicleId = em.find(vehicleId.getClass(), vehicleId.getId());
            entity.setVehicleId(vehicleId);
        }
        List<Rating> attachedRatingList = new ArrayList<Rating>();
        for (Rating ratingListRatingToAttach : entity.getRatingList()) {
            ratingListRatingToAttach = em.find(ratingListRatingToAttach.getClass(), ratingListRatingToAttach.getRatingPK());
            attachedRatingList.add(ratingListRatingToAttach);
        }
        entity.setRatingList(attachedRatingList);
        List<Message> attachedMessageList = new ArrayList<Message>();
        for (Message messageListMessageToAttach : entity.getMessageList()) {
            messageListMessageToAttach = em.find(messageListMessageToAttach.getClass(), messageListMessageToAttach.getId());
            attachedMessageList.add(messageListMessageToAttach);
        }
        entity.setMessageList(attachedMessageList);
        em.persist(entity);
        if (advertisementId != null) {
            advertisementId.getRentGarageList().add(entity);
            advertisementId = em.merge(advertisementId);
        }
        for (Rating ratingListRating : entity.getRatingList()) {
            RentGarage oldRentGarageOfRatingListRating = ratingListRating.getRentGarage();
            ratingListRating.setRentGarage(entity);
            ratingListRating = em.merge(ratingListRating);
            if (oldRentGarageOfRatingListRating != null) {
                oldRentGarageOfRatingListRating.getRatingList().remove(ratingListRating);
                oldRentGarageOfRatingListRating = em.merge(oldRentGarageOfRatingListRating);
            }
        }
        for (Message messageListMessage : entity.getMessageList()) {
            RentGarage oldRentGarageIdOfMessageListMessage = messageListMessage.getRentGarageId();
            messageListMessage.setRentGarageId(entity);
            messageListMessage = em.merge(messageListMessage);
            if (oldRentGarageIdOfMessageListMessage != null) {
                oldRentGarageIdOfMessageListMessage.getMessageList().remove(messageListMessage);
                oldRentGarageIdOfMessageListMessage = em.merge(oldRentGarageIdOfMessageListMessage);
            }
        }
    }

    @Override
    public List<RentGarage> findEntities(EntityManager em, boolean all, int maxResults, int firstResult) throws Exception {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(RentGarage.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    @Override
    public List<RentGarage> findByCriteria(EntityManager em, Map<Integer, Object> criteria, int limit, int offset) throws Exception {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<RentGarage> cq = cb.createQuery(RentGarage.class);
        Root<RentGarage> rg = cq.from(RentGarage.class);

        List<Predicate> predList = new LinkedList<>();
        if (criteria != null && !criteria.isEmpty()) {
            if (criteria.containsKey(RentGarageCriteria.ID_NE)) {
                int id = (int) criteria.get(RentGarageCriteria.ID_NE);
                predList.add(cb.notEqual(rg.get(BaseEntity_.id), id));
            }
            if (criteria.containsKey(RentGarageCriteria.DATE_TIME_EQ)) {
                Date dateTime = (Date) criteria.get(RentGarageCriteria.DATE_TIME_EQ);
                if (dateTime == null) {
                    predList.add(cb.isNull(rg.get(RentGarage_.dateTime)));
                } else {
                    predList.add(cb.equal(rg.get(RentGarage_.dateTime), dateTime));
                }
            }
            if (criteria.containsKey(RentGarageCriteria.DATE_TIME_GT)) {
                Date dateTime = (Date) criteria.get(RentGarageCriteria.DATE_TIME_GT);
                if (dateTime == null) {
                    predList.add(cb.isNull(rg.get(RentGarage_.dateTime)));
                } else {
                    predList.add(cb.greaterThan(rg.<Date>get(RentGarage_.dateTime), dateTime));
                }
            }
            if (criteria.containsKey(RentGarageCriteria.DATE_TIME_LT)) {
                Date dateTime = (Date) criteria.get(RentGarageCriteria.DATE_TIME_LT);
                if (dateTime == null) {
                    predList.add(cb.isNull(rg.get(RentGarage_.dateTime)));
                } else {
                    predList.add(cb.lessThan(rg.<Date>get(RentGarage_.dateTime), dateTime));
                }
            }
            if (criteria.containsKey(RentGarageCriteria.IS_AUTH_EQ)) {
                Boolean isAuth = (Boolean) criteria.get(RentGarageCriteria.IS_AUTH_EQ);
                if (isAuth == null) {
                    predList.add(cb.isNull(rg.get(RentGarage_.isAuth)));
                } else {
                    predList.add(cb.equal(rg.get(RentGarage_.isAuth), isAuth));
                }
            }
            if (criteria.containsKey(RentGarageCriteria.ADVERTISEMENT_ID_EQ)) {
                int advertisementId = (int) criteria.get(RentGarageCriteria.ADVERTISEMENT_ID_EQ);
                predList.add(cb.equal(rg.get(RentGarage_.advertisementId).get(BaseEntity_.id), advertisementId));
            }
            if (criteria.containsKey(RentGarageCriteria.ADVERTISEMENT_ID_NE)) {
                int advertisementId = (int) criteria.get(RentGarageCriteria.ADVERTISEMENT_ID_NE);
                predList.add(cb.notEqual(rg.get(RentGarage_.advertisementId).get(BaseEntity_.id), advertisementId));
            }
            if (criteria.containsKey(RentGarageCriteria.VEHICLE_ID_EQ)) {
                int vehicleId = (int) criteria.get(RentGarageCriteria.VEHICLE_ID_EQ);
                predList.add(cb.equal(rg.get(RentGarage_.vehicleId).get(BaseEntity_.id), vehicleId));
            }
            if (criteria.containsKey(RentGarageCriteria.VEHICLE_ID_NE)) {
                int vehicleId = (int) criteria.get(RentGarageCriteria.VEHICLE_ID_NE);
                predList.add(cb.notEqual(rg.get(RentGarage_.vehicleId).get(BaseEntity_.id), vehicleId));
            }
            if (criteria.containsKey(RentGarageCriteria.ADVERTISEMENT_ID_GARAGE_ID_USER_ID_EQ)) {
                int userId = (int) criteria.get(RentGarageCriteria.ADVERTISEMENT_ID_GARAGE_ID_USER_ID_EQ);
                Join<RentGarage, Advertisement> a = rg.join(RentGarage_.advertisementId);
                Join<Advertisement, Garage> g = a.join(Advertisement_.garageId);
                predList.add(cb.equal(g.get(Garage_.userId).get(BaseEntity_.id), userId));
            }
            if (criteria.containsKey(RentGarageCriteria.VEHICLE_ID_USER_ID_EQ)) {
                int userId = (int) criteria.get(RentGarageCriteria.VEHICLE_ID_USER_ID_EQ);
                Join<RentGarage, Vehicle> a = rg.join(RentGarage_.vehicleId);
                predList.add(cb.equal(a.get(Vehicle_.userId).get(BaseEntity_.id), userId));
            }
            if (criteria.containsKey(RentGarageCriteria.START_DATE_TIME_EQ)) {
                Date initialDateTime = (Date) criteria.get(RentGarageCriteria.START_DATE_TIME_EQ);
                if (initialDateTime == null) {
                    predList.add(cb.isNull(rg.get(RentGarage_.initialDateTime)));
                } else {
                    predList.add(cb.equal(rg.get(RentGarage_.initialDateTime), initialDateTime));
                }
            }
            if (criteria.containsKey(RentGarageCriteria.START_DATE_TIME_NE)) {
                Date initialDateTime = (Date) criteria.get(RentGarageCriteria.START_DATE_TIME_NE);
                if (initialDateTime == null) {
                    predList.add(cb.isNotNull(rg.get(RentGarage_.initialDateTime)));
                } else {
                    predList.add(cb.notEqual(rg.get(RentGarage_.initialDateTime), initialDateTime));
                }
            }
            if (criteria.containsKey(RentGarageCriteria.START_DATE_TIME_GT)) {
                Date initialDateTime = (Date) criteria.get(RentGarageCriteria.START_DATE_TIME_GT);
                if (initialDateTime == null) {
                    predList.add(cb.isNull(rg.get(RentGarage_.initialDateTime)));
                } else {
                    predList.add(cb.greaterThan(rg.<Date>get(RentGarage_.initialDateTime), initialDateTime));
                }
            }
            if (criteria.containsKey(RentGarageCriteria.START_DATE_TIME_LT)) {
                Date initialDateTime = (Date) criteria.get(RentGarageCriteria.START_DATE_TIME_LT);
                if (initialDateTime == null) {
                    predList.add(cb.isNull(rg.get(RentGarage_.initialDateTime)));
                } else {
                    predList.add(cb.lessThan(rg.<Date>get(RentGarage_.initialDateTime), initialDateTime));
                }
            }
            if (criteria.containsKey(RentGarageCriteria.FINAL_DATE_TIME_EQ)) {
                Date finalDateTime = (Date) criteria.get(RentGarageCriteria.FINAL_DATE_TIME_EQ);
                if (finalDateTime == null) {
                    predList.add(cb.isNull(rg.get(RentGarage_.finalDateTime)));
                } else {
                    predList.add(cb.equal(rg.get(RentGarage_.finalDateTime), finalDateTime));
                }
            }
            if (criteria.containsKey(RentGarageCriteria.FINAL_DATE_TIME_NE)) {
                Date finalDateTime = (Date) criteria.get(RentGarageCriteria.FINAL_DATE_TIME_NE);
                if (finalDateTime == null) {
                    predList.add(cb.isNotNull(rg.get(RentGarage_.finalDateTime)));
                } else {
                    predList.add(cb.notEqual(rg.get(RentGarage_.finalDateTime), finalDateTime));
                }
            }
            if (criteria.containsKey(RentGarageCriteria.FINAL_DATE_TIME_GT)) {
                Date finalDateTime = (Date) criteria.get(RentGarageCriteria.FINAL_DATE_TIME_GT);
                if (finalDateTime == null) {
                    predList.add(cb.isNull(rg.get(RentGarage_.finalDateTime)));
                } else {
                    predList.add(cb.greaterThan(rg.<Date>get(RentGarage_.finalDateTime), finalDateTime));
                }
            }
            if (criteria.containsKey(RentGarageCriteria.FINAL_DATE_TIME_LT)) {
                Date finalDateTime = (Date) criteria.get(RentGarageCriteria.FINAL_DATE_TIME_LT);
                if (finalDateTime == null) {
                    predList.add(cb.isNull(rg.get(RentGarage_.finalDateTime)));
                } else {
                    predList.add(cb.lessThan(rg.<Date>get(RentGarage_.finalDateTime), finalDateTime));
                }
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
    public RentGarage findEntity(EntityManager em, Integer id) throws Exception {
        return em.find(RentGarage.class, id);
    }

    @Override
    public int getEntityCount(EntityManager em) throws Exception {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<RentGarage> rt = cq.from(RentGarage.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void edit(EntityManager em, RentGarage entity) throws Exception {
        RentGarage persistentRentGarage = em.find(RentGarage.class, entity.getId());
        Advertisement advertisementIdOld = persistentRentGarage.getAdvertisementId();
        Advertisement advertisementIdNew = entity.getAdvertisementId();
        Vehicle vehicleIdNew = entity.getVehicleId();
        List<Rating> ratingListOld = persistentRentGarage.getRatingList();
        List<Rating> ratingListNew = entity.getRatingList();
        List<Message> messageListOld = persistentRentGarage.getMessageList();
        List<Message> messageListNew = entity.getMessageList();
        List<String> illegalOrphanMessages = null;
        for (Rating ratingListOldRating : ratingListOld) {
            if (!ratingListNew.contains(ratingListOldRating)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Rating " + ratingListOldRating + " since its rentGarage field is not nullable.");
            }
        }
        for (Message messageListOldMessage : messageListOld) {
            if (!messageListNew.contains(messageListOldMessage)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Message " + messageListOldMessage + " since its rentGarageId field is not nullable.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        if (advertisementIdNew != null) {
            advertisementIdNew = em.find(advertisementIdNew.getClass(), advertisementIdNew.getId());
            entity.setAdvertisementId(advertisementIdNew);
        }
        if (vehicleIdNew != null) {
            vehicleIdNew = em.find(vehicleIdNew.getClass(), vehicleIdNew.getId());
            entity.setVehicleId(vehicleIdNew);
        }
        List<Rating> attachedRatingListNew = new ArrayList<Rating>();
        for (Rating ratingListNewRatingToAttach : ratingListNew) {
            ratingListNewRatingToAttach = em.find(ratingListNewRatingToAttach.getClass(), ratingListNewRatingToAttach.getRatingPK());
            attachedRatingListNew.add(ratingListNewRatingToAttach);
        }
        ratingListNew = attachedRatingListNew;
        entity.setRatingList(ratingListNew);
        List<Message> attachedMessageListNew = new ArrayList<Message>();
        for (Message messageListNewMessageToAttach : messageListNew) {
            messageListNewMessageToAttach = em.find(messageListNewMessageToAttach.getClass(), messageListNewMessageToAttach.getId());
            attachedMessageListNew.add(messageListNewMessageToAttach);
        }
        messageListNew = attachedMessageListNew;
        entity.setMessageList(messageListNew);
        entity = em.merge(entity);
        if (advertisementIdOld != null && !advertisementIdOld.equals(advertisementIdNew)) {
            advertisementIdOld.getRentGarageList().remove(entity);
            advertisementIdOld = em.merge(advertisementIdOld);
        }
        if (advertisementIdNew != null && !advertisementIdNew.equals(advertisementIdOld)) {
            advertisementIdNew.getRentGarageList().add(entity);
            advertisementIdNew = em.merge(advertisementIdNew);
        }
        for (Rating ratingListNewRating : ratingListNew) {
            if (!ratingListOld.contains(ratingListNewRating)) {
                RentGarage oldRentGarageOfRatingListNewRating = ratingListNewRating.getRentGarage();
                ratingListNewRating.setRentGarage(entity);
                ratingListNewRating = em.merge(ratingListNewRating);
                if (oldRentGarageOfRatingListNewRating != null && !oldRentGarageOfRatingListNewRating.equals(entity)) {
                    oldRentGarageOfRatingListNewRating.getRatingList().remove(ratingListNewRating);
                    oldRentGarageOfRatingListNewRating = em.merge(oldRentGarageOfRatingListNewRating);
                }
            }
        }
        for (Message messageListNewMessage : messageListNew) {
            if (!messageListOld.contains(messageListNewMessage)) {
                RentGarage oldRentGarageIdOfMessageListNewMessage = messageListNewMessage.getRentGarageId();
                messageListNewMessage.setRentGarageId(entity);
                messageListNewMessage = em.merge(messageListNewMessage);
                if (oldRentGarageIdOfMessageListNewMessage != null && !oldRentGarageIdOfMessageListNewMessage.equals(entity)) {
                    oldRentGarageIdOfMessageListNewMessage.getMessageList().remove(messageListNewMessage);
                    oldRentGarageIdOfMessageListNewMessage = em.merge(oldRentGarageIdOfMessageListNewMessage);
                }
            }
        }
    }

    @Override
    public void destroy(EntityManager em, Integer id) throws Exception {
        RentGarage rentGarage;
        try {
            rentGarage = em.find(RentGarage.class, id);
            rentGarage.getId();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The rentGarage with id " + id + " no longer exists.", enfe);
        }
        List<String> illegalOrphanMessages = null;
        List<Rating> ratingListOrphanCheck = rentGarage.getRatingList();
        for (Rating ratingListOrphanCheckRating : ratingListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This RentGarage (" + rentGarage + ") cannot be destroyed since the Rating " + ratingListOrphanCheckRating + " in its ratingList field has a non-nullable rentGarage field.");
        }
        List<Message> messageListOrphanCheck = rentGarage.getMessageList();
        for (Message messageListOrphanCheckMessage : messageListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This RentGarage (" + rentGarage + ") cannot be destroyed since the Message " + messageListOrphanCheckMessage + " in its messageList field has a non-nullable rentGarageId field.");
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        Advertisement advertisementId = rentGarage.getAdvertisementId();
        if (advertisementId != null) {
            advertisementId.getRentGarageList().remove(rentGarage);
            advertisementId = em.merge(advertisementId);
        }
        em.remove(rentGarage);
    }

}
