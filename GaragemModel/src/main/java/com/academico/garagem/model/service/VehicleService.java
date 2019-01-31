/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.academico.garagem.model.service;

import com.academico.garagem.apiclient.APIClient;
import com.academico.garagem.apiclient.Response;
import com.academico.garagem.model.base.BaseService;
import com.academico.garagem.model.base.service.BaseVehicleService;
import com.academico.garagem.model.dao.VehicleDAO;
import com.academico.garagem.model.entity.Image;
import com.academico.garagem.model.entity.RentGarage;
import com.academico.garagem.model.entity.Vehicle;
import com.academico.garagem.model.service.exceptions.NonexistentEntityException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author mathe
 */
@Service
public class VehicleService extends BaseService implements Serializable, BaseVehicleService {

    public VehicleService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void create(Vehicle vehicle) throws Exception {
        if (vehicle.getImageList() == null) {
            vehicle.setImageList(new ArrayList<Image>());
        }
        if (vehicle.getRentGarageList() == null) {
            vehicle.setRentGarageList(new ArrayList<RentGarage>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            VehicleDAO dao = new VehicleDAO();
            dao.create(em, vehicle);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Vehicle> findAll() throws Exception {
        return findEntities(true, -1, -1);
    }

    @Override
    public List<Vehicle> findEntities(int maxResults, int firstResult) throws Exception {
        return findEntities(false, maxResults, firstResult);
    }

    private List<Vehicle> findEntities(boolean all, int maxResults, int firstResult) throws Exception {
        EntityManager em = getEntityManager();
        try {
            VehicleDAO dao = new VehicleDAO();
            return dao.findEntities(em, all, maxResults, firstResult);
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Vehicle> findByCriteria(Map<Integer, Object> criteria, int limit, int offset) throws Exception {
        EntityManager em = getEntityManager();
        try {
            VehicleDAO dao = new VehicleDAO();
            return dao.findByCriteria(em, criteria, limit, offset);
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public Vehicle findEntity(Integer id) throws Exception {
        EntityManager em = getEntityManager();
        try {
            VehicleDAO dao = new VehicleDAO();
            return dao.findEntity(em, id);
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public int getEntityCount() throws Exception {
        EntityManager em = getEntityManager();
        try {
            VehicleDAO dao = new VehicleDAO();
            return dao.getEntityCount(em);
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public void edit(Vehicle vehicle) throws Exception {
        if (vehicle.getImageList() == null) {
            vehicle.setImageList(new ArrayList<Image>());
        }
        if (vehicle.getRentGarageList() == null) {
            vehicle.setRentGarageList(new ArrayList<RentGarage>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            VehicleDAO dao = new VehicleDAO();
            dao.edit(em, vehicle);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vehicle.getId();
                if (findEntity(id) == null) {
                    throw new NonexistentEntityException("The vehicle with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void destroy(Integer id) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            VehicleDAO dao = new VehicleDAO();
            dao.destroy(em, id);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Map<String, String> validate(Map<String, Object> fields) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Object> getManufacturer(String type) throws UnsupportedEncodingException {
        final String url = "http://fipeapi.appspot.com/api/1/" + type + "/marcas.json";
        try {
            Response response = new APIClient(url)
                    .get().sync();

            Gson gson = new Gson();
            JsonArray json = gson.fromJson(response.getBody(), JsonArray.class);

            return gson.fromJson(json, ArrayList.class);
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Object> getModel(String type, String manufacturer) throws UnsupportedEncodingException {
        final String url = "http://fipeapi.appspot.com/api/1/" + type + "/veiculos/" + manufacturer + ".json";
        try {
            Response response = new APIClient(url)
                    .get().sync();

            Gson gson = new Gson();
            JsonArray json = gson.fromJson(response.getBody(), JsonArray.class);

            return gson.fromJson(json, ArrayList.class);
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Object> getYear(String type, String manufacturer, String model) throws UnsupportedEncodingException {
        final String url = "http://fipeapi.appspot.com/api/1/" + type + "/veiculo/" + manufacturer + "/" + model + ".json";
        try {
            Response response = new APIClient(url)
                    .get().sync();

            Gson gson = new Gson();
            JsonArray json = gson.fromJson(response.getBody(), JsonArray.class);

            return gson.fromJson(json, ArrayList.class);
        } catch (Exception ex) {
            return null;
        }
    }
}
