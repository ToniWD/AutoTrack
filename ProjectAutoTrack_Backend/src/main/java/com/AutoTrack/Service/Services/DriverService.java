package com.AutoTrack.Service.Services;

import com.AutoTrack.Domain.Driver;
import com.AutoTrack.Repository.Interfaces.DriverRepository;
import com.AutoTrack.Repository.Interfaces.RepositoryCRUD;
import org.springframework.stereotype.Service;

@Service
public class DriverService extends AbsCrudService<Driver, Long> {
    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        super(driverRepository);
        this.driverRepository = driverRepository;
    }
}
