package com.AutoTrack.Networking.Controllers;

import com.AutoTrack.Domain.Vehicle;
import com.AutoTrack.Service.ServiceException;
import com.AutoTrack.Service.Services.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    // Create
    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@Valid @RequestBody Vehicle vehicle) {
        Optional<Vehicle> savedVehicle = vehicleService.save(vehicle);

        return savedVehicle.map(value -> ResponseEntity.status(HttpStatus.CREATED).body(value)).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.findAll();
        return ResponseEntity.ok(vehicles);
    }

    // Read one by id
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        return vehicleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @Valid @RequestBody Vehicle vehicleDetails) {
        return vehicleService.findById(id)
                .map(existingVehicle -> {
                    existingVehicle.setFuelCapacity(vehicleDetails.getFuelCapacity());
                    existingVehicle.setFuelConsumption(vehicleDetails.getFuelConsumption());
                    existingVehicle.setFuelLevel(vehicleDetails.getFuelLevel());
                    existingVehicle.setStatus(vehicleDetails.getStatus());
                    existingVehicle.setLatitude(vehicleDetails.getLatitude());
                    existingVehicle.setLongitude(vehicleDetails.getLongitude());
                    existingVehicle.setBrand(vehicleDetails.getBrand());
                    existingVehicle.setModel(vehicleDetails.getModel());
                    existingVehicle.setLicensePlate(vehicleDetails.getLicensePlate());
                    existingVehicle.setYearOfManufacture(vehicleDetails.getYearOfManufacture());

                    Optional<Vehicle> updatedVehicle = vehicleService.update(existingVehicle);

                    return updatedVehicle
                            .map(ResponseEntity::ok)
                            .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                })
                .orElse(ResponseEntity.notFound().build());
    }


    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        return vehicleService.findById(id)
                .map(vehicle -> {
                    vehicleService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handleServiceException(ServiceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}

