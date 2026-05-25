package mas.educenter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

// Holds the EntityManagerFactory in one place so we can get an EntityManager from anywhere.
public class HibernateUtil {

    private static EntityManagerFactory factory;

    public static EntityManagerFactory getFactory() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory("educenter-pu");
        }
        return factory;
    }

    public static EntityManager getEntityManager() {
        return getFactory().createEntityManager();
    }

    public static void shutdown() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }
}
