/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.academico.garagem.model.service;

import com.academico.garagem.model.base.BaseService;
import com.academico.garagem.model.base.service.BaseUserService;
import com.academico.garagem.model.criteria.UserCriteria;
import com.academico.garagem.model.dao.UserDAO;
import com.academico.garagem.model.entity.Garage;
import com.academico.garagem.model.entity.Image;
import com.academico.garagem.model.entity.Rating;
import com.academico.garagem.model.entity.User;
import com.academico.garagem.model.entity.UserPhone;
import com.academico.garagem.model.entity.Vehicle;
import com.academico.garagem.model.service.exceptions.NonexistentEntityException;
import com.academico.garagem.model.util.CNPHelper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author mathe
 */
@Service
public class UserService extends BaseService implements Serializable, BaseUserService {

    public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void create(User user) throws Exception {
        if (user.getImageList() == null) {
            user.setImageList(new ArrayList<Image>());
        }
        if (user.getGarageList() == null) {
            user.setGarageList(new ArrayList<Garage>());
        }
        if (user.getUserPhoneList() == null) {
            user.setUserPhoneList(new ArrayList<UserPhone>());
        }
        if (user.getRatingList() == null) {
            user.setRatingList(new ArrayList<Rating>());
        }
        if (user.getVehicleList() == null) {
            user.setVehicleList(new ArrayList<Vehicle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserDAO dao = new UserDAO();
            dao.create(em, user);
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
    public List<User> findAll() throws Exception {
        return findEntities(true, -1, -1);
    }

    @Override
    public List<User> findEntities(int maxResults, int firstResult) throws Exception {
        return findEntities(false, maxResults, firstResult);
    }

    private List<User> findEntities(boolean all, int maxResults, int firstResult) throws Exception {
        EntityManager em = getEntityManager();
        try {
            UserDAO dao = new UserDAO();
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
    public List<User> findByCriteria(Map<Integer, Object> criteria, int limit, int offset) throws Exception {
        EntityManager em = getEntityManager();
        try {
            UserDAO dao = new UserDAO();
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
    public User findEntity(Integer id) throws Exception {
        EntityManager em = getEntityManager();
        try {
            UserDAO dao = new UserDAO();
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
            UserDAO dao = new UserDAO();
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
    public void edit(User user) throws Exception {
        if (user.getImageList() == null) {
            user.setImageList(new ArrayList<Image>());
        }
        if (user.getGarageList() == null) {
            user.setGarageList(new ArrayList<Garage>());
        }
        if (user.getUserPhoneList() == null) {
            user.setUserPhoneList(new ArrayList<UserPhone>());
        }
        if (user.getRatingList() == null) {
            user.setRatingList(new ArrayList<Rating>());
        }
        if (user.getVehicleList() == null) {
            user.setVehicleList(new ArrayList<Vehicle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserDAO dao = new UserDAO();
            dao.edit(em, user);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = user.getId();
                if (findEntity(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
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
            UserDAO dao = new UserDAO();
            dao.destroy(em, id);
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
    public Map<String, String> validate(Map<String, Object> fields) throws Exception {
        Map<String, String> errors = new HashMap<>();

        String name = (String) fields.get("name");
        if (name != null && name.isEmpty()) {
            errors.put("name", "Campo obrigatório!");
        }

        String lastName = (String) fields.get("lastName");
        if (lastName != null && lastName.isEmpty()) {
            errors.put("lastName", "Campo obrigatório!");
        }

        String email = (String) fields.get("email");
        if (email != null && email.isEmpty()) {
            errors.put("email", "Campo obrigatório!");
        } else if (email != null && !email.isEmpty()) {
            try {
                InternetAddress emailAddr = new InternetAddress(email);
                emailAddr.validate();
                Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
                if (!matcher.find()) {
                    errors.put("email", "Email inválido!");
                } else {
                    Map<Integer, Object> criteria = new HashMap<>();
                    criteria.put(UserCriteria.EMAIL_EQ, email);
                    Integer id = (Integer) fields.get("id");
                    if (id != null && id > 0) {
                        criteria.put(UserCriteria.ID_NE, id);
                    }
                    List<User> found = findByCriteria(criteria, 0, 0);
                    if (found != null && found.size() > 0) {
                        errors.put("email", "Email já está em uso!");
                    }
                }
            } catch (AddressException e) {
                errors.put("email", "Email inválido!");
            }
        }

        String password = (String) fields.get("password");
        if (password != null && !password.isEmpty()) {
            String oldPassword = (String) fields.get("oldPassword");
            if (oldPassword != null && !oldPassword.isEmpty()) {
                if (email == null || email.isEmpty()) {
                    errors.put("email", "Email inválido!");
                } else {
                    User user = findByEmail(email);
                    if (user == null || !user.getActive()) {
                        errors.put("email", "Email inválido!");
                    } else if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                        errors.put("oldPassword", "Senha não confere!");
                    }
                }
            }
            String confirm = (String) fields.get("confirmPassword");
            if (confirm == null || confirm.isEmpty()) {
                errors.put("confirmPassword", "Campo obrigatório!");
            } else if (!password.equals(confirm)) {
                errors.put("confirmPassword", "Senha não confere!");
            }
        }

        List<UserPhone> phoneList = (List<UserPhone>) fields.get("phoneList");
        if (phoneList != null && phoneList.size() > 0) {
            for (UserPhone phone : phoneList) {
                if (phone.getNumber() == null || phone.getNumber().isEmpty()) {
                    errors.put("phoneList", "Telefone inválido!");
                }
            }
        }

        String gender = (String) fields.get("gender");
        if (gender != null && !gender.isEmpty()) {
            if (!gender.equals("M") && !gender.equals("F")) {
                errors.put("gender", "Sexo inválido!");
            }
        }

        String identity = (String) fields.get("identity");
        if (identity != null && !identity.isEmpty()) {
            if (!CNPHelper.isValidCPF(identity)) {
                errors.put("identity", "CPF inválido!");
            }
        }

        return errors;
    }

    @Override
    public User findByEmail(String email) {
        EntityManager em = getEntityManager();

        try {
            return em.createNamedQuery("User.findByEmail", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
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
    public User login(String email, String password) {
        User result = null;
        User entity = findByEmail(email);

        if (entity != null && passwordEncoder.matches(password, entity.getPassword()) && entity.getActive()) {
            result = entity;
        }

        return result;
    }
}
