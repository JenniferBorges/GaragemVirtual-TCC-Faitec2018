package com.academico.garagem.model.dao;

import com.academico.garagem.model.base.BaseEntity_;
import com.academico.garagem.model.base.dao.BaseAddressDAO;
import com.academico.garagem.model.criteria.AddressCriteria;
import com.academico.garagem.model.entity.Address;
import com.academico.garagem.model.entity.Address_;
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

public class AddressDAO implements BaseAddressDAO {

    @Override
    public void create(EntityManager em, Address entity) throws Exception {
        em.persist(entity);
    }

    @Override
    public List<Address> findEntities(EntityManager em, boolean all, int maxResults, int firstResult) throws Exception {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Address.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    @Override
    public List<Address> findByCriteria(EntityManager em, Map<Integer, Object> criteria, int limit, int offset) throws Exception {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Address> cq = cb.createQuery(Address.class);
        Root<Address> a = cq.from(Address.class);

        List<Predicate> predList = new LinkedList<>();
        if (criteria != null && !criteria.isEmpty()) {
            if (criteria.containsKey(AddressCriteria.ID_NE)) {
                int id = (int) criteria.get(AddressCriteria.ID_NE);
                predList.add(cb.notEqual(a.get(BaseEntity_.id), id));
            }
            if (criteria.containsKey(AddressCriteria.STREET_EQ)) {
                String street = (String) criteria.get(AddressCriteria.STREET_EQ);
                predList.add(cb.equal(a.get(Address_.street), street));
            }
            if (criteria.containsKey(AddressCriteria.STREET_ILIKE)) {
                String street = (String) criteria.get(AddressCriteria.STREET_ILIKE);
                predList.add(cb.like(a.get(Address_.street), "%" + street + "%"));
            }
            if (criteria.containsKey(AddressCriteria.NUMBER_EQ)) {
                String number = (String) criteria.get(AddressCriteria.NUMBER_EQ);
                predList.add(cb.equal(a.get(Address_.number), number));
            }
            if (criteria.containsKey(AddressCriteria.NEIGHBORHOOD_EQ)) {
                String neighborhood = (String) criteria.get(AddressCriteria.NEIGHBORHOOD_EQ);
                predList.add(cb.equal(a.get(Address_.neighborhood), neighborhood));
            }
            if (criteria.containsKey(AddressCriteria.NEIGHBORHOOD_ILIKE)) {
                String neighborhood = (String) criteria.get(AddressCriteria.NEIGHBORHOOD_ILIKE);
                predList.add(cb.like(a.get(Address_.neighborhood), "%" + neighborhood + "%"));
            }
            if (criteria.containsKey(AddressCriteria.CITY_EQ)) {
                String city = (String) criteria.get(AddressCriteria.CITY_EQ);
                predList.add(cb.equal(a.get(Address_.city), city));
            }
            if (criteria.containsKey(AddressCriteria.CITY_ILIKE)) {
                String city = (String) criteria.get(AddressCriteria.CITY_ILIKE);
                predList.add(cb.like(a.get(Address_.city), "%" + city + "%"));
            }
            if (criteria.containsKey(AddressCriteria.STATE_EQ)) {
                String state = (String) criteria.get(AddressCriteria.STATE_EQ);
                predList.add(cb.equal(a.get(Address_.state), state));
            }
            if (criteria.containsKey(AddressCriteria.STATE_ILIKE)) {
                String state = (String) criteria.get(AddressCriteria.STATE_ILIKE);
                predList.add(cb.like(a.get(Address_.state), "%" + state + "%"));
            }
            if (criteria.containsKey(AddressCriteria.ZIP_EQ)) {
                String zip = (String) criteria.get(AddressCriteria.ZIP_EQ);
                predList.add(cb.equal(a.get(Address_.zip), zip));
            }
            if (criteria.containsKey(AddressCriteria.LATITUDE_EQ)) {
                double latitude = (double) criteria.get(AddressCriteria.LATITUDE_EQ);
                predList.add(cb.equal(a.get(Address_.latitude), latitude));
            }
            if (criteria.containsKey(AddressCriteria.LATITUDE_GT)) {
                double latitude = (double) criteria.get(AddressCriteria.LATITUDE_GT);
                predList.add(cb.gt(a.get(Address_.latitude), latitude));
            }
            if (criteria.containsKey(AddressCriteria.LATITUDE_LT)) {
                double latitude = (double) criteria.get(AddressCriteria.LATITUDE_LT);
                predList.add(cb.lt(a.get(Address_.latitude), latitude));
            }
            if (criteria.containsKey(AddressCriteria.LONGITUDE_EQ)) {
                double longitude = (double) criteria.get(AddressCriteria.LONGITUDE_EQ);
                predList.add(cb.equal(a.get(Address_.longitude), longitude));
            }
            if (criteria.containsKey(AddressCriteria.LONGITUDE_GT)) {
                double longitude = (double) criteria.get(AddressCriteria.LONGITUDE_GT);
                predList.add(cb.gt(a.get(Address_.longitude), longitude));
            }
            if (criteria.containsKey(AddressCriteria.LONGITUDE_LT)) {
                double longitude = (double) criteria.get(AddressCriteria.LONGITUDE_LT);
                predList.add(cb.lt(a.get(Address_.longitude), longitude));
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
    public Address findEntity(EntityManager em, Integer id) throws Exception {
        return em.find(Address.class, id);
    }

    @Override
    public int getEntityCount(EntityManager em) throws Exception {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Address> rt = cq.from(Address.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void edit(EntityManager em, Address entity) throws Exception {
        entity = em.merge(entity);
    }

    @Override
    public void destroy(EntityManager em, Integer id) throws Exception {
        Address address;
        try {
            address = em.find(Address.class, id);
            address.getId();
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The address with id " + id + " no longer exists.", enfe);
        }
        em.remove(address);
    }

}
