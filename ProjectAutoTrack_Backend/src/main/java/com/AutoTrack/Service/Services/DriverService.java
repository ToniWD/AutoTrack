package com.AutoTrack.Service.Services;

import com.AutoTrack.Domain.Driver;
import com.AutoTrack.Domain.Reports.DriverReport;
import com.AutoTrack.Domain.Route;
import com.AutoTrack.Repository.Interfaces.DriverRepository;
import com.AutoTrack.Repository.Interfaces.RepositoryCRUD;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService extends AbsCrudService<Driver, Long> {
    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        super(driverRepository);
        this.driverRepository = driverRepository;
    }

    public Optional<DriverReport> getReportForDriver(Long id) {
        Optional<Driver> driver = driverRepository.findById(id);
        if (driver.isPresent()) {
            List<Route> routes = driverRepository.getDriverRoutes(driver.get().getId());
            if(routes==null || routes.isEmpty())
            {
                return Optional.empty();
            }
            return Optional.of(new DriverReport(driver.get(), routes));
        }
        return Optional.empty();
    }
}
