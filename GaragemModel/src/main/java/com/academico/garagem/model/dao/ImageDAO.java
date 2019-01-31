package com.academico.garagem.model.dao;

import com.academico.garagem.model.base.BaseEntity_;
import com.academico.garagem.model.base.dao.BaseImageDAO;
import com.academico.garagem.model.criteria.ImageCriteria;
import com.academico.garagem.model.entity.Garage;
import com.academico.garagem.model.entity.Image;
import com.academico.garagem.model.entity.Image_;
import com.academico.garagem.model.entity.User;
import com.academico.garagem.model.entity.Vehicle;
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

public class ImageDAO implements BaseImageDAO {

    @Override
    public void create(EntityManager em, Image entity) throws Exception {
        User userId = entity.getUserId();
        if (userId != null) {
            userId = em.find(userId.getClass(), userId.getId());
            entity.setUserId(userId);
        }
        Garage garageId = entity.getGarageId();
        if (garageId != null) {
            garageId = em.find(garageId.getClass(), garageId.getId());
            entity.setGarageId(garageId);
        }
        Vehicle vehicleId = entity.getVehicleId();
        if (vehicleId != null) {
            vehicleId = em.find(vehicleId.getClass(), vehicleId.getId());
            entity.setVehicleId(vehicleId);
        }
        em.persist(entity);
        if (userId != null) {
            userId.getImageList().add(entity);
            userId = em.merge(userId);
        }
        if (garageId != null) {
            garageId.getImageList().add(entity);
            garageId = em.merge(garageId);
        }
        if (vehicleId != null) {
            vehicleId.getImageList().add(entity);
            vehicleId = em.merge(vehicleId);
        }
    }

    @Override
    public List<Image> findEntities(EntityManager em, boolean all, int maxResults, int firstResult) throws Exception {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Image.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    @Override
    public List<Image> findByCriteria(EntityManager em, Map<Integer, Object> criteria, int limit, int offset) throws Exception {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Image> cq = cb.createQuery(Image.class);
        Root<Image> i = cq.from(Image.class);

        List<Predicate> predList = new LinkedList<>();
        if (criteria != null && !criteria.isEmpty()) {
            if (criteria.containsKey(ImageCriteria.SRC_NE)) {
                String src = (String) criteria.get(ImageCriteria.SRC_NE);
                predList.add(cb.notEqual(i.get(Image_.src), src));
            }
            if (criteria.containsKey(ImageCriteria.TYPE_EQ)) {
                int type = (int) criteria.get(ImageCriteria.TYPE_EQ);
                predList.add(cb.equal(i.get(Image_.type), type));
            }
            if (criteria.containsKey(ImageCriteria.USER_ID_EQ)) {
                Integer userId = (Integer) criteria.get(ImageCriteria.USER_ID_EQ);
                if (userId == null) {
                    predList.add(cb.isNull(i.get(Image_.userId)));
                } else {
                    predList.add(cb.equal(i.get(Image_.userId).get(BaseEntity_.id), userId));
                }
            }
            if (criteria.containsKey(ImageCriteria.GARAGE_ID_EQ)) {
                Integer garageId = (Integer) criteria.get(ImageCriteria.GARAGE_ID_EQ);
                if (garageId == null) {
                    predList.add(cb.isNull(i.get(Image_.garageId)));
                } else {
                    predList.add(cb.equal(i.get(Image_.garageId).get(BaseEntity_.id), garageId));
                }
            }
            if (criteria.containsKey(ImageCriteria.VEHICLE_ID_EQ)) {
                Integer vehicleId = (Integer) criteria.get(ImageCriteria.VEHICLE_ID_EQ);
                if (vehicleId == null) {
                    predList.add(cb.isNull(i.get(Image_.vehicleId)));
                } else {
                    predList.add(cb.equal(i.get(Image_.vehicleId).get(BaseEntity_.id), vehicleId));
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
    public Image findEntity(EntityManager em, String id) throws Exception {
        return em.find(Image.class, id);
    }

    @Override
    public int getEntityCount(EntityManager em) throws Exception {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Image> rt = cq.from(Image.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void edit(EntityManager em, Image entity) throws Exception {
        Image persistentImage = em.find(Image.class, entity.getSrc());
        User userIdOld = persistentImage.getUserId();
        User userIdNew = entity.getUserId();
        Garage garageIdOld = persistentImage.getGarageId();
        Garage garageIdNew = entity.getGarageId();
        Vehicle vehicleIdOld = persistentImage.getVehicleId();
        Vehicle vehicleIdNew = entity.getVehicleId();
        if (userIdNew != null) {
            userIdNew = em.find(userIdNew.getClass(), userIdNew.getId());
            entity.setUserId(userIdNew);
        }
        if (garageIdNew != null) {
            garageIdNew = em.find(garageIdNew.getClass(), garageIdNew.getId());
            entity.setGarageId(garageIdNew);
        }
        if (vehicleIdNew != null) {
            vehicleIdNew = em.find(vehicleIdNew.getClass(), vehicleIdNew.getId());
            entity.setVehicleId(vehicleIdNew);
        }
        entity = em.merge(entity);
        if (userIdOld != null && !userIdOld.equals(userIdNew)) {
            userIdOld.getImageList().remove(entity);
            userIdOld = em.merge(userIdOld);
        }
        if (userIdNew != null && !userIdNew.equals(userIdOld)) {
            userIdNew.getImageList().add(entity);
            userIdNew = em.merge(userIdNew);
        }
        if (garageIdOld != null && !garageIdOld.equals(garageIdNew)) {
            garageIdOld.getImageList().remove(entity);
            garageIdOld = em.merge(garageIdOld);
        }
        if (garageIdNew != null && !garageIdNew.equals(garageIdOld)) {
            garageIdNew.getImageList().add(entity);
            garageIdNew = em.merge(garageIdNew);
        }
        if (vehicleIdOld != null && !vehicleIdOld.equals(vehicleIdNew)) {
            vehicleIdOld.getImageList().remove(entity);
            vehicleIdOld = em.merge(vehicleIdOld);
        }
        if (vehicleIdNew != null && !vehicleIdNew.equals(vehicleIdOld)) {
            vehicleIdNew.getImageList().add(entity);
            vehicleIdNew = em.merge(vehicleIdNew);
        }
    }

    @Override
    public void destroy(EntityManager em, String id) throws Exception {
        Image image;
        try {
            image = em.find(Image.class, id);
            image.getSrc();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The image with id " + id + " no longer exists.", enfe);
        }
        User userId = image.getUserId();
        if (userId != null) {
            userId.getImageList().remove(image);
            userId = em.merge(userId);
        }
        Garage garageId = image.getGarageId();
        if (garageId != null) {
            garageId.getImageList().remove(image);
            garageId = em.merge(garageId);
        }
        Vehicle vehicleId = image.getVehicleId();
        if (vehicleId != null) {
            vehicleId.getImageList().remove(image);
            vehicleId = em.merge(vehicleId);
        }
        em.remove(image);
    }

}
