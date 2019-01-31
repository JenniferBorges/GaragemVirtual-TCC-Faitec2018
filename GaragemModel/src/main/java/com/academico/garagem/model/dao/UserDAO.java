package com.academico.garagem.model.dao;

import com.academico.garagem.model.base.BaseEntity_;
import com.academico.garagem.model.base.dao.BaseUserDAO;
import com.academico.garagem.model.criteria.UserCriteria;
import com.academico.garagem.model.entity.Garage;
import com.academico.garagem.model.entity.Image;
import com.academico.garagem.model.entity.Rating;
import com.academico.garagem.model.entity.User;
import com.academico.garagem.model.entity.UserPhone;
import com.academico.garagem.model.entity.User_;
import com.academico.garagem.model.entity.Vehicle;
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

public class UserDAO implements BaseUserDAO {

    @Override
    public void create(EntityManager em, User entity) throws Exception {
        List<Image> attachedImageList = new ArrayList<Image>();
        for (Image imageListImageToAttach : entity.getImageList()) {
            imageListImageToAttach = em.find(imageListImageToAttach.getClass(), imageListImageToAttach.getSrc());
            attachedImageList.add(imageListImageToAttach);
        }
        entity.setImageList(attachedImageList);
        List<Garage> attachedGarageList = new ArrayList<Garage>();
        for (Garage garageListGarageToAttach : entity.getGarageList()) {
            garageListGarageToAttach = em.find(garageListGarageToAttach.getClass(), garageListGarageToAttach.getId());
            attachedGarageList.add(garageListGarageToAttach);
        }
        entity.setGarageList(attachedGarageList);
        List<UserPhone> attachedUserPhoneList = new ArrayList<UserPhone>();
        for (UserPhone userPhoneListUserPhoneToAttach : entity.getUserPhoneList()) {
            userPhoneListUserPhoneToAttach = em.find(userPhoneListUserPhoneToAttach.getClass(), userPhoneListUserPhoneToAttach.getId());
            attachedUserPhoneList.add(userPhoneListUserPhoneToAttach);
        }
        entity.setUserPhoneList(attachedUserPhoneList);
        List<Rating> attachedRatingList = new ArrayList<Rating>();
        for (Rating ratingListRatingToAttach : entity.getRatingList()) {
            ratingListRatingToAttach = em.find(ratingListRatingToAttach.getClass(), ratingListRatingToAttach.getRatingPK());
            attachedRatingList.add(ratingListRatingToAttach);
        }
        entity.setRatingList(attachedRatingList);
        List<Vehicle> attachedVehicleList = new ArrayList<Vehicle>();
        for (Vehicle vehicleListVehicleToAttach : entity.getVehicleList()) {
            vehicleListVehicleToAttach = em.find(vehicleListVehicleToAttach.getClass(), vehicleListVehicleToAttach.getId());
            attachedVehicleList.add(vehicleListVehicleToAttach);
        }
        entity.setVehicleList(attachedVehicleList);
        em.persist(entity);
        for (Image imageListImage : entity.getImageList()) {
            User oldUserIdOfImageListImage = imageListImage.getUserId();
            imageListImage.setUserId(entity);
            imageListImage = em.merge(imageListImage);
            if (oldUserIdOfImageListImage != null) {
                oldUserIdOfImageListImage.getImageList().remove(imageListImage);
                oldUserIdOfImageListImage = em.merge(oldUserIdOfImageListImage);
            }
        }
        for (Garage garageListGarage : entity.getGarageList()) {
            User oldUserIdOfGarageListGarage = garageListGarage.getUserId();
            garageListGarage.setUserId(entity);
            garageListGarage = em.merge(garageListGarage);
            if (oldUserIdOfGarageListGarage != null) {
                oldUserIdOfGarageListGarage.getGarageList().remove(garageListGarage);
                oldUserIdOfGarageListGarage = em.merge(oldUserIdOfGarageListGarage);
            }
        }
        for (UserPhone userPhoneListUserPhone : entity.getUserPhoneList()) {
            User oldUserIdOfUserPhoneListUserPhone = userPhoneListUserPhone.getUserId();
            userPhoneListUserPhone.setUserId(entity);
            userPhoneListUserPhone = em.merge(userPhoneListUserPhone);
            if (oldUserIdOfUserPhoneListUserPhone != null) {
                oldUserIdOfUserPhoneListUserPhone.getUserPhoneList().remove(userPhoneListUserPhone);
                oldUserIdOfUserPhoneListUserPhone = em.merge(oldUserIdOfUserPhoneListUserPhone);
            }
        }
        for (Rating ratingListRating : entity.getRatingList()) {
            User oldUserOfRatingListRating = ratingListRating.getUser();
            ratingListRating.setUser(entity);
            ratingListRating = em.merge(ratingListRating);
            if (oldUserOfRatingListRating != null) {
                oldUserOfRatingListRating.getRatingList().remove(ratingListRating);
                oldUserOfRatingListRating = em.merge(oldUserOfRatingListRating);
            }
        }
        for (Vehicle vehicleListVehicle : entity.getVehicleList()) {
            User oldUserIdOfVehicleListVehicle = vehicleListVehicle.getUserId();
            vehicleListVehicle.setUserId(entity);
            vehicleListVehicle = em.merge(vehicleListVehicle);
            if (oldUserIdOfVehicleListVehicle != null) {
                oldUserIdOfVehicleListVehicle.getVehicleList().remove(vehicleListVehicle);
                oldUserIdOfVehicleListVehicle = em.merge(oldUserIdOfVehicleListVehicle);
            }
        }
    }

    @Override
    public List<User> findEntities(EntityManager em, boolean all, int maxResults, int firstResult) throws Exception {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(User.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    @Override
    public List<User> findByCriteria(EntityManager em, Map<Integer, Object> criteria, int limit, int offset) throws Exception {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> u = cq.from(User.class);

        List<Predicate> predList = new LinkedList<>();
        if (criteria != null && !criteria.isEmpty()) {
            if (criteria.containsKey(UserCriteria.ID_NE)) {
                int id = (int) criteria.get(UserCriteria.ID_NE);
                predList.add(cb.notEqual(u.get(BaseEntity_.id), id));
            }
            if (criteria.containsKey(UserCriteria.IDENTITY_EQ)) {
                String identity = (String) criteria.get(UserCriteria.IDENTITY_EQ);
                if (identity == null) {
                    predList.add(cb.isNull(u.get(User_.identity)));
                } else {
                    predList.add(cb.equal(u.get(User_.identity), identity));
                }
            }
            if (criteria.containsKey(UserCriteria.NAME_EQ)) {
                String name = (String) criteria.get(UserCriteria.NAME_EQ);
                predList.add(cb.equal(u.get(User_.name), name));
            }
            if (criteria.containsKey(UserCriteria.NAME_ILIKE)) {
                String name = (String) criteria.get(UserCriteria.NAME_ILIKE);
                predList.add(cb.like(u.get(User_.name), "%" + name + "%"));
            }
            if (criteria.containsKey(UserCriteria.LAST_NAME_EQ)) {
                String lastName = (String) criteria.get(UserCriteria.LAST_NAME_EQ);
                predList.add(cb.equal(u.get(User_.lastName), lastName));
            }
            if (criteria.containsKey(UserCriteria.LAST_NAME_ILIKE)) {
                String lastName = (String) criteria.get(UserCriteria.LAST_NAME_ILIKE);
                predList.add(cb.like(u.get(User_.lastName), "%" + lastName + "%"));
            }
            if (criteria.containsKey(UserCriteria.EMAIL_EQ)) {
                String email = (String) criteria.get(UserCriteria.EMAIL_EQ);
                predList.add(cb.equal(u.get(User_.email), email));
            }
            if (criteria.containsKey(UserCriteria.EMAIL_ILIKE)) {
                String email = (String) criteria.get(UserCriteria.EMAIL_ILIKE);
                predList.add(cb.like(u.get(User_.email), "%" + email + "%"));
            }
            if (criteria.containsKey(UserCriteria.PASSWORD_EQ)) {
                String password = (String) criteria.get(UserCriteria.PASSWORD_EQ);
                predList.add(cb.equal(u.get(User_.password), password));
            }
            if (criteria.containsKey(UserCriteria.GENDER_EQ)) {
                String gender = (String) criteria.get(UserCriteria.GENDER_EQ);
                if (gender == null) {
                    predList.add(cb.isNull(u.get(User_.gender)));
                } else {
                    predList.add(cb.equal(u.get(User_.gender), gender));
                }
            }
            if (criteria.containsKey(UserCriteria.ACTIVE_EQ)) {
                boolean active = (boolean) criteria.get(UserCriteria.ACTIVE_EQ);
                predList.add(cb.equal(u.get(User_.active), active));
            }
            if (criteria.containsKey(UserCriteria.BANNED_EQ)) {
                boolean banned = (boolean) criteria.get(UserCriteria.BANNED_EQ);
                predList.add(cb.equal(u.get(User_.banned), banned));
            }
            if (criteria.containsKey(UserCriteria.AUTH_TOKEN_EQ)) {
                String authToken = (String) criteria.get(UserCriteria.AUTH_TOKEN_EQ);
                if (authToken == null) {
                    predList.add(cb.isNull(u.get(User_.authToken)));
                } else {
                    predList.add(cb.equal(u.get(User_.authToken), authToken));
                }
            }
            if (criteria.containsKey(UserCriteria.RESET_TOKEN_EQ)) {
                String resetToken = (String) criteria.get(UserCriteria.RESET_TOKEN_EQ);
                if (resetToken == null) {
                    predList.add(cb.isNull(u.get(User_.resetToken)));
                } else {
                    predList.add(cb.equal(u.get(User_.resetToken), resetToken));
                }
            }
            if (criteria.containsKey(UserCriteria.RESET_COMPLETE_EQ)) {
                boolean resetComplete = (boolean) criteria.get(UserCriteria.RESET_COMPLETE_EQ);
                predList.add(cb.equal(u.get(User_.resetComplete), resetComplete));
            }
            if (criteria.containsKey(UserCriteria.JOINING_DATE_GT)) {
                Date joiningDate = (Date) criteria.get(UserCriteria.JOINING_DATE_GT);
                predList.add(cb.greaterThan(u.join(User_.joiningDate), joiningDate));
            }
            if (criteria.containsKey(UserCriteria.JOINING_DATE_LT)) {
                Date joiningDate = (Date) criteria.get(UserCriteria.JOINING_DATE_LT);
                predList.add(cb.lessThan(u.join(User_.joiningDate), joiningDate));
            }
            if (criteria.containsKey(UserCriteria.IS_ADMIN_EQ)) {
                boolean isAdmin = (boolean) criteria.get(UserCriteria.IS_ADMIN_EQ);
                predList.add(cb.equal(u.get(User_.isAdmin), isAdmin));
            }
            if (criteria.containsKey(UserCriteria.IS_AUTH_EQ)) {
                boolean isAuth = (boolean) criteria.get(UserCriteria.IS_AUTH_EQ);
                predList.add(cb.equal(u.get(User_.isAuth), isAuth));
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
    public User findEntity(EntityManager em, Integer id) throws Exception {
        return em.find(User.class, id);
    }

    @Override
    public int getEntityCount(EntityManager em) throws Exception {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<User> rt = cq.from(User.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void edit(EntityManager em, User entity) throws Exception {
        User persistentUser = em.find(User.class, entity.getId());
        List<Image> imageListOld = persistentUser.getImageList();
        List<Image> imageListNew = entity.getImageList();
        List<Garage> garageListOld = persistentUser.getGarageList();
        List<Garage> garageListNew = entity.getGarageList();
        List<UserPhone> userPhoneListOld = persistentUser.getUserPhoneList();
        List<UserPhone> userPhoneListNew = entity.getUserPhoneList();
        List<Rating> ratingListOld = persistentUser.getRatingList();
        List<Rating> ratingListNew = entity.getRatingList();
        List<Vehicle> vehicleListOld = persistentUser.getVehicleList();
        List<Vehicle> vehicleListNew = entity.getVehicleList();
        List<String> illegalOrphanMessages = null;
        for (Garage garageListOldGarage : garageListOld) {
            if (!garageListNew.contains(garageListOldGarage)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Garage " + garageListOldGarage + " since its userId field is not nullable.");
            }
        }
        for (Rating ratingListOldRating : ratingListOld) {
            if (!ratingListNew.contains(ratingListOldRating)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Rating " + ratingListOldRating + " since its user field is not nullable.");
            }
        }
        for (Vehicle vehicleListOldVehicle : vehicleListOld) {
            if (!vehicleListNew.contains(vehicleListOldVehicle)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Vehicle " + vehicleListOldVehicle + " since its userId field is not nullable.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        List<Image> attachedImageListNew = new ArrayList<Image>();
        for (Image imageListNewImageToAttach : imageListNew) {
            imageListNewImageToAttach = em.find(imageListNewImageToAttach.getClass(), imageListNewImageToAttach.getSrc());
            attachedImageListNew.add(imageListNewImageToAttach);
        }
        imageListNew = attachedImageListNew;
        entity.setImageList(imageListNew);
        List<Garage> attachedGarageListNew = new ArrayList<Garage>();
        for (Garage garageListNewGarageToAttach : garageListNew) {
            garageListNewGarageToAttach = em.find(garageListNewGarageToAttach.getClass(), garageListNewGarageToAttach.getId());
            attachedGarageListNew.add(garageListNewGarageToAttach);
        }
        garageListNew = attachedGarageListNew;
        entity.setGarageList(garageListNew);
        List<UserPhone> attachedUserPhoneListNew = new ArrayList<UserPhone>();
        for (UserPhone userPhoneListNewUserPhoneToAttach : userPhoneListNew) {
            userPhoneListNewUserPhoneToAttach = em.find(userPhoneListNewUserPhoneToAttach.getClass(), userPhoneListNewUserPhoneToAttach.getId());
            attachedUserPhoneListNew.add(userPhoneListNewUserPhoneToAttach);
        }
        userPhoneListNew = attachedUserPhoneListNew;
        entity.setUserPhoneList(userPhoneListNew);
        List<Rating> attachedRatingListNew = new ArrayList<Rating>();
        for (Rating ratingListNewRatingToAttach : ratingListNew) {
            ratingListNewRatingToAttach = em.find(ratingListNewRatingToAttach.getClass(), ratingListNewRatingToAttach.getRatingPK());
            attachedRatingListNew.add(ratingListNewRatingToAttach);
        }
        ratingListNew = attachedRatingListNew;
        entity.setRatingList(ratingListNew);
        List<Vehicle> attachedVehicleListNew = new ArrayList<Vehicle>();
        for (Vehicle vehicleListNewVehicleToAttach : vehicleListNew) {
            vehicleListNewVehicleToAttach = em.find(vehicleListNewVehicleToAttach.getClass(), vehicleListNewVehicleToAttach.getId());
            attachedVehicleListNew.add(vehicleListNewVehicleToAttach);
        }
        vehicleListNew = attachedVehicleListNew;
        entity.setVehicleList(vehicleListNew);
        entity = em.merge(entity);
        for (Image imageListOldImage : imageListOld) {
            if (!imageListNew.contains(imageListOldImage)) {
                imageListOldImage.setUserId(null);
                imageListOldImage = em.merge(imageListOldImage);
            }
        }
        for (Image imageListNewImage : imageListNew) {
            if (!imageListOld.contains(imageListNewImage)) {
                User oldUserIdOfImageListNewImage = imageListNewImage.getUserId();
                imageListNewImage.setUserId(entity);
                imageListNewImage = em.merge(imageListNewImage);
                if (oldUserIdOfImageListNewImage != null && !oldUserIdOfImageListNewImage.equals(entity)) {
                    oldUserIdOfImageListNewImage.getImageList().remove(imageListNewImage);
                    oldUserIdOfImageListNewImage = em.merge(oldUserIdOfImageListNewImage);
                }
            }
        }
        for (Garage garageListNewGarage : garageListNew) {
            if (!garageListOld.contains(garageListNewGarage)) {
                User oldUserIdOfGarageListNewGarage = garageListNewGarage.getUserId();
                garageListNewGarage.setUserId(entity);
                garageListNewGarage = em.merge(garageListNewGarage);
                if (oldUserIdOfGarageListNewGarage != null && !oldUserIdOfGarageListNewGarage.equals(entity)) {
                    oldUserIdOfGarageListNewGarage.getGarageList().remove(garageListNewGarage);
                    oldUserIdOfGarageListNewGarage = em.merge(oldUserIdOfGarageListNewGarage);
                }
            }
        }
        for (UserPhone userPhoneListNewUserPhone : userPhoneListNew) {
            if (!userPhoneListOld.contains(userPhoneListNewUserPhone)) {
                User oldUserIdOfUserPhoneListNewUserPhone = userPhoneListNewUserPhone.getUserId();
                userPhoneListNewUserPhone.setUserId(entity);
                userPhoneListNewUserPhone = em.merge(userPhoneListNewUserPhone);
                if (oldUserIdOfUserPhoneListNewUserPhone != null && !oldUserIdOfUserPhoneListNewUserPhone.equals(entity)) {
                    oldUserIdOfUserPhoneListNewUserPhone.getUserPhoneList().remove(userPhoneListNewUserPhone);
                    oldUserIdOfUserPhoneListNewUserPhone = em.merge(oldUserIdOfUserPhoneListNewUserPhone);
                }
            }
        }
        for (Rating ratingListNewRating : ratingListNew) {
            if (!ratingListOld.contains(ratingListNewRating)) {
                User oldUserOfRatingListNewRating = ratingListNewRating.getUser();
                ratingListNewRating.setUser(entity);
                ratingListNewRating = em.merge(ratingListNewRating);
                if (oldUserOfRatingListNewRating != null && !oldUserOfRatingListNewRating.equals(entity)) {
                    oldUserOfRatingListNewRating.getRatingList().remove(ratingListNewRating);
                    oldUserOfRatingListNewRating = em.merge(oldUserOfRatingListNewRating);
                }
            }
        }
        for (Vehicle vehicleListNewVehicle : vehicleListNew) {
            if (!vehicleListOld.contains(vehicleListNewVehicle)) {
                User oldUserIdOfVehicleListNewVehicle = vehicleListNewVehicle.getUserId();
                vehicleListNewVehicle.setUserId(entity);
                vehicleListNewVehicle = em.merge(vehicleListNewVehicle);
                if (oldUserIdOfVehicleListNewVehicle != null && !oldUserIdOfVehicleListNewVehicle.equals(entity)) {
                    oldUserIdOfVehicleListNewVehicle.getVehicleList().remove(vehicleListNewVehicle);
                    oldUserIdOfVehicleListNewVehicle = em.merge(oldUserIdOfVehicleListNewVehicle);
                }
            }
        }
    }

    @Override
    public void destroy(EntityManager em, Integer id) throws Exception {
        User user;
        try {
            user = em.find(User.class, id);
            user.getId();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
        }
        List<String> illegalOrphanMessages = null;
        List<Garage> garageListOrphanCheck = user.getGarageList();
        for (Garage garageListOrphanCheckGarage : garageListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Garage " + garageListOrphanCheckGarage + " in its garageList field has a non-nullable userId field.");
        }
        List<Rating> ratingListOrphanCheck = user.getRatingList();
        for (Rating ratingListOrphanCheckRating : ratingListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Rating " + ratingListOrphanCheckRating + " in its ratingList field has a non-nullable user field.");
        }
        List<Vehicle> vehicleListOrphanCheck = user.getVehicleList();
        for (Vehicle vehicleListOrphanCheckVehicle : vehicleListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Vehicle " + vehicleListOrphanCheckVehicle + " in its vehicleList field has a non-nullable userId field.");
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        List<UserPhone> userPhoneList = user.getUserPhoneList();
        for (UserPhone userPhoneListUserPhone : userPhoneList) {
            em.remove(userPhoneListUserPhone);
        }
        List<Image> imageList = user.getImageList();
        for (Image imageListImage : imageList) {
            em.remove(imageListImage);
        }
        em.remove(user);
    }

}
