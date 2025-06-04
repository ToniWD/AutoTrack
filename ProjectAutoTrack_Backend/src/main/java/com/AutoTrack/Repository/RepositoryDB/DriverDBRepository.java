package com.AutoTrack.Repository.RepositoryDB;

import com.AutoTrack.Domain.Driver;
import com.AutoTrack.Domain.Route;
import com.AutoTrack.Repository.Interfaces.DriverRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class DriverDBRepository extends AbsRepositoryCRUD<Driver,Long> implements DriverRepository {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    public DriverDBRepository(EntityManagerFactory emf) {
        super(emf, Driver.class);
    }

    @Override
    public List<Route> getDriverRoutes(Long driverId) {
        Optional<Driver> driver = this.findById(driverId);

        if (driver.isPresent()) {
            try (EntityManager em = emf.createEntityManager()) {
                String queryStr = "SELECT e FROM Route e WHERE e.assignedDriver.id = :driverId";
                return em.createQuery(queryStr, Route.class)
                        .setParameter("driverId", driverId)
                        .getResultList();
            } catch (Exception e) {
                logger.error("Error while finding all routes of driver with id: " + driverId, e);
                return Collections.emptyList();
            }
        }

        return Collections.emptyList();
    }

}
