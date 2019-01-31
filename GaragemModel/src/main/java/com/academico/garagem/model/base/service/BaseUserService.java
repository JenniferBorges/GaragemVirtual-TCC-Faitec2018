package com.academico.garagem.model.base.service;

import com.academico.garagem.model.base.BaseCRUDService;
import com.academico.garagem.model.entity.User;

public interface BaseUserService extends BaseCRUDService<User> {

    public User findByEmail(String email);

    public User login(String email, String password);

}
