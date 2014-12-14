package com.vkondrat.experiment.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 */
public class JPAUtil {
    private static String PERSISTENT_UNIT_NAME = "prod-persistence";
    private static EntityManagerFactory emf;

    private JPAUtil() {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static JPAUtil getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        static JPAUtil instance = new JPAUtil();
    }

    public static void setPersistentUnitName(String name) {
        PERSISTENT_UNIT_NAME = name;
    }

    public EntityManager getEm() {
        return emf.createEntityManager();
    }
}
