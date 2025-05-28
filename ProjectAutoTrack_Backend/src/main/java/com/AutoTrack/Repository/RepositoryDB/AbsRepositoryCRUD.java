package com.AutoTrack.Repository.RepositoryDB;

import com.AutoTrack.Repository.Interfaces.RepositoryCRUD;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;


public class AbsRepositoryCRUD<E ,I> implements RepositoryCRUD<E,I> {

    private static final Logger logger = LogManager.getLogger();
    protected EntityManagerFactory emf;
    protected final Class<E> entityClass;

//    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
private static final ValidatorFactory factory = Validation
        .byDefaultProvider()
        .configure()
        .messageInterpolator(new ParameterMessageInterpolator())
        .buildValidatorFactory();

    private static final Validator validator = factory.getValidator();

    public AbsRepositoryCRUD(EntityManagerFactory emf, Class<E> entityClass) {
        this.emf = emf;
        this.entityClass = entityClass;
    }

    protected void validate(E entity) throws ValidationException {
        Set<ConstraintViolation<E>> violations = validator.validate(entity);

        if (!violations.isEmpty()) {

            // Dacă există erori de validare, le logăm și aruncăm excepția
            StringBuilder errorMessage = new StringBuilder();
            for (ConstraintViolation<E> violation : violations) {
                errorMessage.append(violation.getMessage()).append("\n");
                logger.error("Validation error: " + violation.getMessage());
            }
            throw new ValidationException(errorMessage.toString());
        }
    }

    @Override
    public Optional<E> save(E entity) {
        logger.info("Saving entity: " + entity.toString());

        validate(entity);


        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.flush();
            em.getTransaction().commit();
            logger.info("Saved entity: " + entity.toString());
            return Optional.of(entity);
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error("Error while saving entity: " + entity.toString(), e);
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<E> update(E entity) {
        logger.info("Updating entity: " + entity.toString());

        validate(entity);


        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            E updatedEntity = em.merge(entity);
            em.getTransaction().commit();
            logger.info("Updated entity: " + entity.toString());
            return Optional.of(updatedEntity);
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error("Error while Updating entity: " + entity.toString(), e);
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<E> deleteById(I i) {
        logger.info("Deleting entity with id: " + i);

        if (i == null) {
            return Optional.empty();
        }

        EntityManager em = emf.createEntityManager();
        try {
            E entity = em.find(entityClass, i);
            if (entity == null) {
                return Optional.empty();
            }

            em.getTransaction().begin();
            em.remove(entity);
            em.getTransaction().commit();
            logger.info("Deleted entity: " + entity);
            return Optional.of(entity);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error("Error while deleting " + entityClass.getSimpleName() + " with id: " + i, e);
            return Optional.empty();

        } finally {
            em.close();
        }
    }

    @Override
    public Optional<E> findById(I id) {
        logger.info("Finding " + entityClass.getSimpleName() + " with id: " + id);
        if (id == null) {
            return Optional.empty();
        }

        try (EntityManager em = emf.createEntityManager()) {
            return Optional.ofNullable(em.find(entityClass, id));
        }
        catch (Exception e) {
            logger.error("Error while finding " + entityClass.getSimpleName() + " with id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<E> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            String queryStr = "SELECT e FROM " + entityClass.getSimpleName() + " e";
            return em.createQuery(queryStr, entityClass).getResultList();
        }
        catch (Exception e) {
            logger.error("Error while finding all entities", e);
            return null;
        }
    }

}
