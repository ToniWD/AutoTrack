package com.AutoTrack.Repository.Interfaces;

import com.AutoTrack.Domain.Driver;
import com.AutoTrack.Domain.Route;

import java.util.List;

public interface DriverRepository extends RepositoryCRUD<Driver, Long> {

    public List<Route> getDriverRoutes(Long driverId);
}
