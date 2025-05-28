package com.AutoTrack.Repository.RepositoryDB;

import com.AutoTrack.Domain.Vehicle;
import com.AutoTrack.Repository.Interfaces.VehicleRepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDBRepository extends AbsRepositoryCRUD<Vehicle, Long> implements VehicleRepository {
    @Autowired
    public VehicleDBRepository(EntityManagerFactory emf) {
        super(emf, Vehicle.class);
    }
}
