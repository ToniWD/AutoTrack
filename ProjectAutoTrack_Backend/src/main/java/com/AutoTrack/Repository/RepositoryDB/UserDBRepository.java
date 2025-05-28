package com.AutoTrack.Repository.RepositoryDB;

import com.AutoTrack.Domain.User;
import com.AutoTrack.Repository.Interfaces.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDBRepository extends AbsRepositoryCRUD<User, Long> implements UserRepository {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserDBRepository(EntityManagerFactory emf) {
        super(emf, User.class);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        logger.info("Finding " + entityClass.getSimpleName() + " with username: " + username);
        if (username == null) {
            return Optional.empty();
        }



        try (EntityManager em = emf.createEntityManager()) {
            String querry = "SELECT u FROM User u WHERE u.username = :username";
            TypedQuery<User> query = em.createQuery(querry, User.class);
            query.setParameter("username", username);

            User user = query.getSingleResult();
            return Optional.ofNullable(user);
        }
        catch (Exception e) {
            logger.error("Error while finding " + entityClass.getSimpleName() + " with username: " + username, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> save(User entity) {
        String rawPassword = entity.getUsername() + entity.getPasswordHash();
        entity.setPasswordHash(passwordEncoder.encode(rawPassword));
        return super.save(entity);
    }
}
