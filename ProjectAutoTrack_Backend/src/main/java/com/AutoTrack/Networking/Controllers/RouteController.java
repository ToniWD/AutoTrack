package com.AutoTrack.Networking.Controllers;

import com.AutoTrack.Domain.Route;
import com.AutoTrack.Service.ServiceException;
import com.AutoTrack.Service.Services.RouteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    // Create
    @PostMapping
    public ResponseEntity<Route> createRoute(@Valid @RequestBody Route route) {
        Optional<Route> savedRoute = routeService.save(route);

        if (savedRoute.isPresent())
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRoute.get());
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<Route>> getAllRoutes() {
        List<Route> routes = routeService.findAll();
        return ResponseEntity.ok(routes);
    }

    // Read one by id
    @GetMapping("/{id}")
    public ResponseEntity<Route> getRouteById(@PathVariable Long id) {
        return routeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Route> updateRoute(@PathVariable Long id, @Valid @RequestBody Route routeDetails) {
        return routeService.findById(id)
                .map(existingRoute -> {
                    existingRoute.setStatus(routeDetails.getStatus());
                    existingRoute.setDistanceKm(routeDetails.getDistanceKm());
                    existingRoute.setDurationMinutes(routeDetails.getDurationMinutes());
                    existingRoute.setAssignedDriver(routeDetails.getAssignedDriver());
                    existingRoute.setAssignedVehicle(routeDetails.getAssignedVehicle());
                    existingRoute.setStartLatitude(routeDetails.getStartLatitude());
                    existingRoute.setStartLongitude(routeDetails.getStartLongitude());
                    existingRoute.setEndLatitude(routeDetails.getEndLatitude());
                    existingRoute.setEndLongitude(routeDetails.getEndLongitude());
                    existingRoute.setDescription(routeDetails.getDescription());
                    existingRoute.setStartLocationAddress(routeDetails.getStartLocationAddress());
                    existingRoute.setEndLocationAddress(routeDetails.getEndLocationAddress());
                    existingRoute.setStartDate(routeDetails.getStartDate());
                    existingRoute.setEndDate(routeDetails.getEndDate());
                    existingRoute.setValue(routeDetails.getValue());

                    Optional<Route> updatedRoute = routeService.update(existingRoute);

                    return updatedRoute
                            .map(ResponseEntity::ok)
                            .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        return routeService.findById(id)
                .map(route -> {
                    routeService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}

