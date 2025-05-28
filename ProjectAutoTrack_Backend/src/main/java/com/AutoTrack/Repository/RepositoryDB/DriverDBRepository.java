package com.AutoTrack.Repository.RepositoryDB;

import com.AutoTrack.Domain.Driver;
import com.AutoTrack.Repository.Interfaces.DriverRepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

@Repository
public class DriverDBRepository extends AbsRepositoryCRUD<Driver,Long> implements DriverRepository {
    @Autowired
    public DriverDBRepository(EntityManagerFactory emf) {
        super(emf, Driver.class);
    }
}
