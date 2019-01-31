package com.academico.garagem.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;

public final class EntityManagerProvider {

    private static final EntityManagerProvider INSTANCE = new EntityManagerProvider();

    private final EntityManagerFactory factory;

    private EntityManagerProvider() {
        this.factory = new HibernatePersistenceProvider().createEntityManagerFactory("GaragemModel", null);
    }

    public static EntityManagerProvider getInstance() {
        return INSTANCE;
    }

    public EntityManagerFactory getFactory() {
        return factory;
    }

    public EntityManager createManager() {
        return factory.createEntityManager();
    }
}
