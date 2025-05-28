package com.AutoTrack.Repository.RepositoryDB;

import com.AutoTrack.Domain.Route;
import com.AutoTrack.Repository.Interfaces.RouteRepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RouteDBRepository extends AbsRepositoryCRUD<Route,Long> implements RouteRepository {
    @Autowired
    public RouteDBRepository(EntityManagerFactory emf) {
        super(emf, Route.class);
    }
}
