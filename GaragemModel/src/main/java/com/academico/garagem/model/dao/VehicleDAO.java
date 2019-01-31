package com.academico.garagem.model.dao;

import com.academico.garagem.model.base.BaseEntity_;
import com.academico.garagem.model.base.dao.BaseVehicleDAO;
import com.academico.garagem.model.criteria.VehicleCriteria;
import com.academico.garagem.model.entity.Image;
import com.academico.garagem.model.entity.RentGarage;
import com.academico.garagem.model.entity.User;
import com.academico.garagem.model.entity.Vehicle;
import com.academico.garagem.model.entity.Vehicle_;
import com.academico.garagem.model.service.exceptions.IllegalOrphanException;
import com.academico.garagem.model.service.exceptions.NonexistentEntityException;
import java.util.ArrayList;
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

public class VehicleDAO implements BaseVehicleDAO {

    @Override
    public void create(EntityManager em, Vehicle entity) throws Exception {
        User userId = entity.getUserId();
        if (userId != null) {
            userId = em.find(userId.getClass(), userId.getId());
            entity.setUserId(userId);
        }
        List<Image> attachedImageList = new ArrayList<Image>();
        for (Image imageListImageToAttach : entity.getImageList()) {
            imageListImageToAttach = em.find(imageListImageToAttach.getClass(), imageListImageToAttach.getSrc());
            attachedImageList.add(imageListImageToAttach);
        }
        entity.setImageList(attachedImageList);
        List<RentGarage> attachedRentGarageList = new ArrayList<RentGarage>();
        for (RentGarage rentGarageListRentGarageToAttach : entity.getRentGarageList()) {
            rentGarageListRentGarageToAttach = em.find(rentGarageListRentGarageToAttach.getClass(), rentGarageListRentGarageToAttach.getId());
            attachedRentGarageList.add(rentGarageListRentGarageToAttach);
        }
        entity.setRentGarageList(attachedRentGarageList);
        em.persist(entity);
        if (userId != null) {
            userId.getVehicleList().add(entity);
            userId = em.merge(userId);
        }
        for (Image imageListImage : entity.getImageList()) {
            Vehicle oldVehicleIdOfImageListImage = imageListImage.getVehicleId();
            imageListImage.setVehicleId(entity);
            imageListImage = em.merge(imageListImage);
            if (oldVehicleIdOfImageListImage != null) {
                oldVehicleIdOfImageListImage.getImageList().remove(imageListImage);
                oldVehicleIdOfImageListImage = em.merge(oldVehicleIdOfImageListImage);
            }
        }
        for (RentGarage rentGarageListRentGarage : entity.getRentGarageList()) {
            Vehicle oldVehicleIdOfRentGarageListRentGarage = rentGarageListRentGarage.getVehicleId();
            rentGarageListRentGarage.setVehicleId(entity);
            rentGarageListRentGarage = em.merge(rentGarageListRentGarage);
            if (oldVehicleIdOfRentGarageListRentGarage != null) {
                oldVehicleIdOfRentGarageListRentGarage.getRentGarageList().remove(rentGarageListRentGarage);
                oldVehicleIdOfRentGarageListRentGarage = em.merge(oldVehicleIdOfRentGarageListRentGarage);
            }
        }
    }

    @Override
    public List<Vehicle> findEntities(EntityManager em, boolean all, int maxResults, int firstResult) throws Exception {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Vehicle.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    @Override
    public List<Vehicle> findByCriteria(EntityManager em, Map<Integer, Object> criteria, int limit, int offset) throws Exception {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Vehicle> cq = cb.createQuery(Vehicle.class);
        Root<Vehicle> v = cq.from(Vehicle.class);

        List<Predicate> predList = new LinkedList<>();
        if (criteria != null && !criteria.isEmpty()) {
            if (criteria.containsKey(VehicleCriteria.ID_NE)) {
                int id = (int) criteria.get(VehicleCriteria.ID_NE);
                predList.add(cb.notEqual(v.get(BaseEntity_.id), id));
            }
            if (criteria.containsKey(VehicleCriteria.PLATE_EQ)) {
                String plate = (String) criteria.get(VehicleCriteria.PLATE_EQ);
                if (plate == null) {
                    predList.add(cb.isNull(v.get(Vehicle_.plate)));
                } else {
                    predList.add(cb.equal(v.get(Vehicle_.plate), plate));
                }
            }
            if (criteria.containsKey(VehicleCriteria.PLATE_ILIKE)) {
                String plate = (String) criteria.get(VehicleCriteria.PLATE_ILIKE);
                if (plate == null) {
                    predList.add(cb.isNull(v.get(Vehicle_.plate)));
                } else {
                    predList.add(cb.like(v.get(Vehicle_.plate), "%" + plate + "%"));
                }
            }
            if (criteria.containsKey(VehicleCriteria.TYPE_EQ)) {
                String type = (String) criteria.get(VehicleCriteria.TYPE_EQ);
                if (type == null) {
                    predList.add(cb.isNull(v.get(Vehicle_.type)));
                } else {
                    predList.add(cb.equal(v.get(Vehicle_.type), type));
                }
            }
            if (criteria.containsKey(VehicleCriteria.TYPE_ILIKE)) {
                String type = (String) criteria.get(VehicleCriteria.TYPE_ILIKE);
                if (type == null) {
                    predList.add(cb.isNull(v.get(Vehicle_.type)));
                } else {
                    predList.add(cb.like(v.get(Vehicle_.type), "%" + type + "%"));
                }
            }
            if (criteria.containsKey(VehicleCriteria.MANUFACTURER_EQ)) {
                String manufacturer = (String) criteria.get(VehicleCriteria.MANUFACTURER_EQ);
                if (manufacturer == null) {
                    predList.add(cb.isNull(v.get(Vehicle_.manufacturer)));
                } else {
                    predList.add(cb.equal(v.get(Vehicle_.manufacturer), manufacturer));
                }
            }
            if (criteria.containsKey(VehicleCriteria.MANUFACTURER_ILIKE)) {
                String manufacturer = (String) criteria.get(VehicleCriteria.MANUFACTURER_ILIKE);
                if (manufacturer == null) {
                    predList.add(cb.isNull(v.get(Vehicle_.manufacturer)));
                } else {
                    predList.add(cb.like(v.get(Vehicle_.manufacturer), "%" + manufacturer + "%"));
                }
            }
            if (criteria.containsKey(VehicleCriteria.MODEL_EQ)) {
                String model = (String) criteria.get(VehicleCriteria.MODEL_EQ);
                if (model == null) {
                    predList.add(cb.isNull(v.get(Vehicle_.model)));
                } else {
                    predList.add(cb.equal(v.get(Vehicle_.model), model));
                }
            }
            if (criteria.containsKey(VehicleCriteria.MODEL_ILIKE)) {
                String model = (String) criteria.get(VehicleCriteria.MODEL_ILIKE);
                if (model == null) {
                    predList.add(cb.isNull(v.get(Vehicle_.model)));
                } else {
                    predList.add(cb.like(v.get(Vehicle_.model), "%" + model + "%"));
                }
            }
            if (criteria.containsKey(VehicleCriteria.YEAR_EQ)) {
                String year = (String) criteria.get(VehicleCriteria.YEAR_EQ);
                if (year == null) {
                    predList.add(cb.isNull(v.get(Vehicle_.year)));
                } else {
                    predList.add(cb.equal(v.get(Vehicle_.year), year));
                }
            }
            if (criteria.containsKey(VehicleCriteria.YEAR_ILIKE)) {
                String year = (String) criteria.get(VehicleCriteria.YEAR_ILIKE);
                if (year == null) {
                    predList.add(cb.isNull(v.get(Vehicle_.year)));
                } else {
                    predList.add(cb.like(v.get(Vehicle_.year), "%" + year + "%"));
                }
            }
            if (criteria.containsKey(VehicleCriteria.COLOR_EQ)) {
                String color = (String) criteria.get(VehicleCriteria.COLOR_EQ);
                if (color == null) {
                    predList.add(cb.isNull(v.get(Vehicle_.color)));
                } else {
                    predList.add(cb.equal(v.get(Vehicle_.color), color));
                }
            }
            if (criteria.containsKey(VehicleCriteria.COLOR_ILIKE)) {
                String color = (String) criteria.get(VehicleCriteria.COLOR_ILIKE);
                if (color == null) {
                    predList.add(cb.isNull(v.get(Vehicle_.color)));
                } else {
                    predList.add(cb.like(v.get(Vehicle_.color), "%" + color + "%"));
                }
            }
            if (criteria.containsKey(VehicleCriteria.CHASSIS_EQ)) {
                Integer chassis = (Integer) criteria.get(VehicleCriteria.CHASSIS_EQ);
                if (chassis == null) {
                    predList.add(cb.isNull(v.get(Vehicle_.chassis)));
                } else {
                    predList.add(cb.equal(v.get(Vehicle_.chassis), chassis));
                }
            }
            if (criteria.containsKey(VehicleCriteria.IS_AUTH_EQ)) {
                boolean isAuth = (boolean) criteria.get(VehicleCriteria.IS_AUTH_EQ);
                predList.add(cb.equal(v.get(Vehicle_.isAuth), isAuth));
            }
            if (criteria.containsKey(VehicleCriteria.USER_ID_EQ)) {
                int user = (int) criteria.get(VehicleCriteria.USER_ID_EQ);
                predList.add(cb.equal(v.get(Vehicle_.userId).get(BaseEntity_.id), user));
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
    public Vehicle findEntity(EntityManager em, Integer id) throws Exception {
        return em.find(Vehicle.class, id);
    }

    @Override
    public int getEntityCount(EntityManager em) throws Exception {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Vehicle> rt = cq.from(Vehicle.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void edit(EntityManager em, Vehicle entity) throws Exception {
        Vehicle persistentVehicle = em.find(Vehicle.class, entity.getId());
        User userIdOld = persistentVehicle.getUserId();
        User userIdNew = entity.getUserId();
        List<Image> imageListOld = persistentVehicle.getImageList();
        List<Image> imageListNew = entity.getImageList();
        List<RentGarage> rentGarageListOld = persistentVehicle.getRentGarageList();
        List<RentGarage> rentGarageListNew = entity.getRentGarageList();
        List<String> illegalOrphanMessages = null;
        for (RentGarage rentGarageListOldRentGarage : rentGarageListOld) {
            if (!rentGarageListNew.contains(rentGarageListOldRentGarage)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain RentGarage " + rentGarageListOldRentGarage + " since its vehicleId field is not nullable.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        if (userIdNew != null) {
            userIdNew = em.find(userIdNew.getClass(), userIdNew.getId());
            entity.setUserId(userIdNew);
        }
        List<Image> attachedImageListNew = new ArrayList<Image>();
        for (Image imageListNewImageToAttach : imageListNew) {
            imageListNewImageToAttach = em.find(imageListNewImageToAttach.getClass(), imageListNewImageToAttach.getSrc());
            attachedImageListNew.add(imageListNewImageToAttach);
        }
        imageListNew = attachedImageListNew;
        entity.setImageList(imageListNew);
        List<RentGarage> attachedRentGarageListNew = new ArrayList<RentGarage>();
        for (RentGarage rentGarageListNewRentGarageToAttach : rentGarageListNew) {
            rentGarageListNewRentGarageToAttach = em.find(rentGarageListNewRentGarageToAttach.getClass(), rentGarageListNewRentGarageToAttach.getId());
            attachedRentGarageListNew.add(rentGarageListNewRentGarageToAttach);
        }
        rentGarageListNew = attachedRentGarageListNew;
        entity.setRentGarageList(rentGarageListNew);
        entity = em.merge(entity);
        if (userIdOld != null && !userIdOld.equals(userIdNew)) {
            userIdOld.getVehicleList().remove(entity);
            userIdOld = em.merge(userIdOld);
        }
        if (userIdNew != null && !userIdNew.equals(userIdOld)) {
            userIdNew.getVehicleList().add(entity);
            userIdNew = em.merge(userIdNew);
        }
        for (Image imageListOldImage : imageListOld) {
            if (!imageListNew.contains(imageListOldImage)) {
                imageListOldImage.setVehicleId(null);
                imageListOldImage = em.merge(imageListOldImage);
            }
        }
        for (Image imageListNewImage : imageListNew) {
            if (!imageListOld.contains(imageListNewImage)) {
                Vehicle oldVehicleIdOfImageListNewImage = imageListNewImage.getVehicleId();
                imageListNewImage.setVehicleId(entity);
                imageListNewImage = em.merge(imageListNewImage);
                if (oldVehicleIdOfImageListNewImage != null && !oldVehicleIdOfImageListNewImage.equals(entity)) {
                    oldVehicleIdOfImageListNewImage.getImageList().remove(imageListNewImage);
                    oldVehicleIdOfImageListNewImage = em.merge(oldVehicleIdOfImageListNewImage);
                }
            }
        }
        for (RentGarage rentGarageListNewRentGarage : rentGarageListNew) {
            if (!rentGarageListOld.contains(rentGarageListNewRentGarage)) {
                Vehicle oldVehicleIdOfRentGarageListNewRentGarage = rentGarageListNewRentGarage.getVehicleId();
                rentGarageListNewRentGarage.setVehicleId(entity);
                rentGarageListNewRentGarage = em.merge(rentGarageListNewRentGarage);
                if (oldVehicleIdOfRentGarageListNewRentGarage != null && !oldVehicleIdOfRentGarageListNewRentGarage.equals(entity)) {
                    oldVehicleIdOfRentGarageListNewRentGarage.getRentGarageList().remove(rentGarageListNewRentGarage);
                    oldVehicleIdOfRentGarageListNewRentGarage = em.merge(oldVehicleIdOfRentGarageListNewRentGarage);
                }
            }
        }
    }

    @Override
    public void destroy(EntityManager em, Integer id) throws Exception {
        Vehicle vehicle;
        try {
            vehicle = em.find(Vehicle.class, id);
            vehicle.getId();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The vehicle with id " + id + " no longer exists.", enfe);
        }
        List<String> illegalOrphanMessages = null;
        List<RentGarage> rentGarageListOrphanCheck = vehicle.getRentGarageList();
        for (RentGarage rentGarageListOrphanCheckRentGarage : rentGarageListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Vehicle (" + vehicle + ") cannot be destroyed since the RentGarage " + rentGarageListOrphanCheckRentGarage + " in its rentGarageList field has a non-nullable vehicleId field.");
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        User userId = vehicle.getUserId();
        if (userId != null) {
            userId.getVehicleList().remove(vehicle);
            userId = em.merge(userId);
        }
        List<Image> imageList = vehicle.getImageList();
        for (Image imageListImage : imageList) {
            imageListImage.setVehicleId(null);
            imageListImage = em.merge(imageListImage);
        }
        em.remove(vehicle);
    }

}
