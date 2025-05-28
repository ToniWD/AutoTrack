package com.AutoTrack.Service.Services;

import com.AutoTrack.Domain.User;
import com.AutoTrack.Repository.Interfaces.*;
import com.AutoTrack.Service.Interfaces.Services;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MainService implements Services {
    private final DriverService driverService;
    private final UserService userService;
    private final RouteService routeService;
    private final VehicleService vehicleService;

    public MainService(DriverService driverService, UserService userService, RouteService routeService, VehicleService vehicleService) {
        this.driverService = driverService;
        this.userService = userService;
        this.routeService = routeService;
        this.vehicleService = vehicleService;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userService.findUserByUsername(username);
    }
}
