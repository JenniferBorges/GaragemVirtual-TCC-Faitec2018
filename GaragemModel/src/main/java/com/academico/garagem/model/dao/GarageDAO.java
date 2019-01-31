package com.academico.garagem.model.dao;

import com.academico.garagem.model.base.BaseEntity_;
import com.academico.garagem.model.base.dao.BaseGarageDAO;
import com.academico.garagem.model.criteria.GarageCriteria;
import com.academico.garagem.model.entity.Address;
import com.academico.garagem.model.entity.Advertisement;
import com.academico.garagem.model.entity.Garage;
import com.academico.garagem.model.entity.Garage_;
import com.academico.garagem.model.entity.Image;
import com.academico.garagem.model.entity.User;
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

public class GarageDAO implements BaseGarageDAO {

    @Override
    public void create(EntityManager em, Garage entity) throws Exception {
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
        Address addressId = entity.getAddressId();
        if (addressId != null) {
            addressId = em.find(addressId.getClass(), addressId.getId());
            entity.setAddressId(addressId);
        }
        List<Advertisement> attachedAdvertisementList = new ArrayList<Advertisement>();
        for (Advertisement advertisementListAdvertisementToAttach : entity.getAdvertisementList()) {
            advertisementListAdvertisementToAttach = em.find(advertisementListAdvertisementToAttach.getClass(), advertisementListAdvertisementToAttach.getId());
            attachedAdvertisementList.add(advertisementListAdvertisementToAttach);
        }
        entity.setAdvertisementList(attachedAdvertisementList);
        em.persist(entity);
        if (userId != null) {
            userId.getGarageList().add(entity);
            userId = em.merge(userId);
        }
        for (Image imageListImage : entity.getImageList()) {
            Garage oldGarageIdOfImageListImage = imageListImage.getGarageId();
            imageListImage.setGarageId(entity);
            imageListImage = em.merge(imageListImage);
            if (oldGarageIdOfImageListImage != null) {
                oldGarageIdOfImageListImage.getImageList().remove(imageListImage);
                oldGarageIdOfImageListImage = em.merge(oldGarageIdOfImageListImage);
            }
        }
        if (addressId != null) {
            addressId.getGarageList().add(entity);
            addressId = em.merge(addressId);
        }
        for (Advertisement advertisementListAdvertisement : entity.getAdvertisementList()) {
            Garage oldGarageIdOfAdvertisementListAdvertisement = advertisementListAdvertisement.getGarageId();
            advertisementListAdvertisement.setGarageId(entity);
            advertisementListAdvertisement = em.merge(advertisementListAdvertisement);
            if (oldGarageIdOfAdvertisementListAdvertisement != null) {
                oldGarageIdOfAdvertisementListAdvertisement.getAdvertisementList().remove(advertisementListAdvertisement);
                oldGarageIdOfAdvertisementListAdvertisement = em.merge(oldGarageIdOfAdvertisementListAdvertisement);
            }
        }
    }

    @Override
    public List<Garage> findEntities(EntityManager em, boolean all, int maxResults, int firstResult) throws Exception {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Garage.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    @Override
    public List<Garage> findByCriteria(EntityManager em, Map<Integer, Object> criteria, int limit, int offset) throws Exception {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Garage> cq = cb.createQuery(Garage.class);
        Root<Garage> g = cq.from(Garage.class);

        List<Predicate> predList = new LinkedList<>();
        if (criteria != null && !criteria.isEmpty()) {
            if (criteria.containsKey(GarageCriteria.ID_NE)) {
                int id = (int) criteria.get(GarageCriteria.ID_NE);
                predList.add(cb.notEqual(g.get(BaseEntity_.id), id));
            }
            if (criteria.containsKey(GarageCriteria.HEIGHT_EQ)) {
                double height = (double) criteria.get(GarageCriteria.HEIGHT_EQ);
                predList.add(cb.equal(g.get(Garage_.height), height));
            }
            if (criteria.containsKey(GarageCriteria.HEIGHT_GT)) {
                double height = (double) criteria.get(GarageCriteria.HEIGHT_GT);
                predList.add(cb.gt(g.get(Garage_.height), height));
            }
            if (criteria.containsKey(GarageCriteria.HEIGHT_LT)) {
                double height = (double) criteria.get(GarageCriteria.HEIGHT_LT);
                predList.add(cb.lt(g.get(Garage_.height), height));
            }
            if (criteria.containsKey(GarageCriteria.LENGTH_EQ)) {
                double length = (double) criteria.get(GarageCriteria.LENGTH_EQ);
                predList.add(cb.equal(g.get(Garage_.length), length));
            }
            if (criteria.containsKey(GarageCriteria.LENGTH_GT)) {
                double length = (double) criteria.get(GarageCriteria.LENGTH_GT);
                predList.add(cb.gt(g.get(Garage_.length), length));
            }
            if (criteria.containsKey(GarageCriteria.LENGTH_LT)) {
                double length = (double) criteria.get(GarageCriteria.LENGTH_LT);
                predList.add(cb.lt(g.get(Garage_.length), length));
            }
            if (criteria.containsKey(GarageCriteria.WIDTH_EQ)) {
                double width = (double) criteria.get(GarageCriteria.WIDTH_EQ);
                predList.add(cb.equal(g.get(Garage_.width), width));
            }
            if (criteria.containsKey(GarageCriteria.WIDTH_GT)) {
                double width = (double) criteria.get(GarageCriteria.WIDTH_GT);
                predList.add(cb.gt(g.get(Garage_.width), width));
            }
            if (criteria.containsKey(GarageCriteria.WIDTH_LT)) {
                double width = (double) criteria.get(GarageCriteria.WIDTH_LT);
                predList.add(cb.lt(g.get(Garage_.width), width));
            }
            if (criteria.containsKey(GarageCriteria.ACCESS_EQ)) {
                String access = (String) criteria.get(GarageCriteria.ACCESS_EQ);
                predList.add(cb.equal(g.get(Garage_.access), access));
            }
            if (criteria.containsKey(GarageCriteria.ACCESS_ILIKE)) {
                String access = (String) criteria.get(GarageCriteria.ACCESS_ILIKE);
                predList.add(cb.like(g.get(Garage_.access), "%" + access + "%"));
            }
            if (criteria.containsKey(GarageCriteria.HAS_ROOF_EQ)) {
                boolean hasRoof = (boolean) criteria.get(GarageCriteria.HAS_ROOF_EQ);
                predList.add(cb.equal(g.get(Garage_.hasRoof), hasRoof));
            }
            if (criteria.containsKey(GarageCriteria.HAS_CAM_EQ)) {
                boolean hasCam = (boolean) criteria.get(GarageCriteria.HAS_CAM_EQ);
                predList.add(cb.equal(g.get(Garage_.hasCam), hasCam));
            }
            if (criteria.containsKey(GarageCriteria.HAS_INDEMNITY_EQ)) {
                boolean hasIndemnity = (boolean) criteria.get(GarageCriteria.HAS_INDEMNITY_EQ);
                predList.add(cb.equal(g.get(Garage_.hasIndemnity), hasIndemnity));
            }
            if (criteria.containsKey(GarageCriteria.HAS_ELECTRONIC_GATE_EQ)) {
                boolean hasElectronicGate = (boolean) criteria.get(GarageCriteria.HAS_ELECTRONIC_GATE_EQ);
                predList.add(cb.equal(g.get(Garage_.hasElectronicGate), hasElectronicGate));
            }
            if (criteria.containsKey(GarageCriteria.ADDRESS_ID_EQ)) {
                int addressId = (int) criteria.get(GarageCriteria.ADDRESS_ID_EQ);
                predList.add(cb.equal(g.get(Garage_.addressId).get(BaseEntity_.id), addressId));
            }
            if (criteria.containsKey(GarageCriteria.USER_ID_EQ)) {
                int userId = (int) criteria.get(GarageCriteria.USER_ID_EQ);
                predList.add(cb.equal(g.get(Garage_.userId).get(BaseEntity_.id), userId));
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
    public Garage findEntity(EntityManager em, Integer id) throws Exception {
        return em.find(Garage.class, id);
    }

    @Override
    public int getEntityCount(EntityManager em) throws Exception {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Garage> rt = cq.from(Garage.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void edit(EntityManager em, Garage entity) throws Exception {
        Garage persistentGarage = em.find(Garage.class, entity.getId());
        User userIdOld = persistentGarage.getUserId();
        User userIdNew = entity.getUserId();
        List<Image> imageListOld = persistentGarage.getImageList();
        List<Image> imageListNew = entity.getImageList();
        Address addressIdOld = persistentGarage.getAddressId();
        Address addressIdNew = entity.getAddressId();
        List<Advertisement> advertisementListOld = persistentGarage.getAdvertisementList();
        List<Advertisement> advertisementListNew = entity.getAdvertisementList();
        List<String> illegalOrphanMessages = null;
        for (Advertisement advertisementListOldAdvertisement : advertisementListOld) {
            if (!advertisementListNew.contains(advertisementListOldAdvertisement)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Advertisement " + advertisementListOldAdvertisement + " since its garageId field is not nullable.");
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
        if (addressIdNew != null) {
            addressIdNew = em.find(addressIdNew.getClass(), addressIdNew.getId());
            entity.setAddressId(addressIdNew);
        }
        List<Advertisement> attachedAdvertisementListNew = new ArrayList<Advertisement>();
        for (Advertisement advertisementListNewAdvertisementToAttach : advertisementListNew) {
            advertisementListNewAdvertisementToAttach = em.find(advertisementListNewAdvertisementToAttach.getClass(), advertisementListNewAdvertisementToAttach.getId());
            attachedAdvertisementListNew.add(advertisementListNewAdvertisementToAttach);
        }
        advertisementListNew = attachedAdvertisementListNew;
        entity.setAdvertisementList(advertisementListNew);
        entity = em.merge(entity);
        if (userIdOld != null && !userIdOld.equals(userIdNew)) {
            userIdOld.getGarageList().remove(entity);
            userIdOld = em.merge(userIdOld);
        }
        if (userIdNew != null && !userIdNew.equals(userIdOld)) {
            userIdNew.getGarageList().add(entity);
            userIdNew = em.merge(userIdNew);
        }
        for (Image imageListOldImage : imageListOld) {
            if (!imageListNew.contains(imageListOldImage)) {
                imageListOldImage.setGarageId(null);
                imageListOldImage = em.merge(imageListOldImage);
            }
        }
        for (Image imageListNewImage : imageListNew) {
            if (!imageListOld.contains(imageListNewImage)) {
                Garage oldGarageIdOfImageListNewImage = imageListNewImage.getGarageId();
                imageListNewImage.setGarageId(entity);
                imageListNewImage = em.merge(imageListNewImage);
                if (oldGarageIdOfImageListNewImage != null && !oldGarageIdOfImageListNewImage.equals(entity)) {
                    oldGarageIdOfImageListNewImage.getImageList().remove(imageListNewImage);
                    oldGarageIdOfImageListNewImage = em.merge(oldGarageIdOfImageListNewImage);
                }
            }
        }
        if (addressIdOld != null && !addressIdOld.equals(addressIdNew)) {
            addressIdOld.getGarageList().remove(entity);
            addressIdOld = em.merge(addressIdOld);
        }
        if (addressIdNew != null && !addressIdNew.equals(addressIdOld)) {
            addressIdNew.getGarageList().add(entity);
            addressIdNew = em.merge(addressIdNew);
        }
        for (Advertisement advertisementListNewAdvertisement : advertisementListNew) {
            if (!advertisementListOld.contains(advertisementListNewAdvertisement)) {
                Garage oldGarageIdOfAdvertisementListNewAdvertisement = advertisementListNewAdvertisement.getGarageId();
                advertisementListNewAdvertisement.setGarageId(entity);
                advertisementListNewAdvertisement = em.merge(advertisementListNewAdvertisement);
                if (oldGarageIdOfAdvertisementListNewAdvertisement != null && !oldGarageIdOfAdvertisementListNewAdvertisement.equals(entity)) {
                    oldGarageIdOfAdvertisementListNewAdvertisement.getAdvertisementList().remove(advertisementListNewAdvertisement);
                    oldGarageIdOfAdvertisementListNewAdvertisement = em.merge(oldGarageIdOfAdvertisementListNewAdvertisement);
                }
            }
        }
    }

    @Override
    public void destroy(EntityManager em, Integer id) throws Exception {
        Garage garage;
        try {
            garage = em.find(Garage.class, id);
            garage.getId();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The garage with id " + id + " no longer exists.", enfe);
        }
        List<String> illegalOrphanMessages = null;
        List<Advertisement> advertisementListOrphanCheck = garage.getAdvertisementList();
        for (Advertisement advertisementListOrphanCheckAdvertisement : advertisementListOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<String>();
            }
            illegalOrphanMessages.add("This Garage (" + garage + ") cannot be destroyed since the Advertisement " + advertisementListOrphanCheckAdvertisement + " in its advertisementList field has a non-nullable garageId field.");
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        User userId = garage.getUserId();
        if (userId != null) {
            userId.getGarageList().remove(garage);
            userId = em.merge(userId);
        }
        List<Image> imageList = garage.getImageList();
        for (Image imageListImage : imageList) {
            imageListImage.setGarageId(null);
            imageListImage = em.merge(imageListImage);
        }
        Address addressId = garage.getAddressId();
        if (addressId != null) {
            addressId.getGarageList().remove(garage);
            addressId = em.merge(addressId);
        }
        em.remove(garage);
    }

}
