package com.AutoTrack.Service.Services;

import com.AutoTrack.Domain.Vehicle;
import com.AutoTrack.Repository.Interfaces.RepositoryCRUD;
import com.AutoTrack.Repository.Interfaces.VehicleRepository;
import com.AutoTrack.Service.ServiceException;
import jakarta.validation.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleService extends AbsCrudService<Vehicle, Long> {

    private static final Logger logger = LogManager.getLogger();
    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        super(vehicleRepository);
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Optional<Vehicle> save(Vehicle entity) {
        if(entity == null) throw new ServiceException("Entity is null");

        try {
            return vehicleRepository.save(entity);
        }
        catch (ValidationException e)
        {
            logger.error("Invalid entity: " + entity + "; error: " + e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        catch (Exception e) {
            logger.error(e);
            if (e.getMessage().contains("unique_license_plate")) {
                throw new ServiceException("License plate already exists.");
            }
            throw new ServiceException("There is a problem");
        }
    }

    @Override
    public Optional<Vehicle> update(Vehicle entity) {
        return super.update(entity);
    }
}
