package com.AutoTrack.Networking.Controllers;

import com.AutoTrack.Domain.Driver;
import com.AutoTrack.Domain.Reports.DriverReport;
import com.AutoTrack.Service.ServiceException;
import com.AutoTrack.Service.Services.DriverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    @Autowired
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    // Create
    @PostMapping
    public ResponseEntity<Driver> createDriver(@Valid @RequestBody Driver driver) {
        Optional<Driver> savedDriver = driverService.save(driver);

        if(savedDriver.isPresent())
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDriver.get());
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<Driver>> getAllDrivers() {
        List<Driver> drivers = driverService.findAll();
        System.out.println("merge");
        for(Driver driver : drivers)
            System.out.println(driver);
        return ResponseEntity.ok(drivers);
    }

    // Read one by id
    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable Long id) {
        return driverService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/report")
    public ResponseEntity<DriverReport> getDriverReportById(@PathVariable Long id) {
        return driverService.getReportForDriver(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Driver> updateDriver(@PathVariable Long id, @Valid @RequestBody Driver driverDetails) {
        return driverService.findById(id)
                .map(existingDriver -> {
                    existingDriver.setLicenseNumber(driverDetails.getLicenseNumber());
                    existingDriver.setLicenseCategory(driverDetails.getLicenseCategory());
                    existingDriver.setLicenseExpirationDate(driverDetails.getLicenseExpirationDate());
                    existingDriver.setMedicalCheckExpirationDate(driverDetails.getMedicalCheckExpirationDate());
                    existingDriver.setHiredDate(driverDetails.getHiredDate());
                    existingDriver.setEmploymentStatus(driverDetails.getEmploymentStatus());
                    existingDriver.setDrivingHoursToday(driverDetails.getDrivingHoursToday());
                    existingDriver.setStatus(driverDetails.getStatus());

                    Optional<Driver> updatedDriver = driverService.update(existingDriver);

                    return updatedDriver
                            .map(ResponseEntity::ok)
                            .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        return driverService.findById(id)
                .map(driver -> {
                    driverService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handleServiceException(ServiceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


}

