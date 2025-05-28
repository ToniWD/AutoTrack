package com.AutoTrack.Service.Services;

import com.AutoTrack.Domain.Route;
import com.AutoTrack.Repository.Interfaces.RepositoryCRUD;
import com.AutoTrack.Repository.Interfaces.RouteRepository;
import org.springframework.stereotype.Service;

@Service
public class RouteService extends AbsCrudService<Route, Long> {
    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        super(routeRepository);
        this.routeRepository = routeRepository;
    }
}
