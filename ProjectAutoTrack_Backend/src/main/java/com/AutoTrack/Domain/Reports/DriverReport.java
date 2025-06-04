package com.AutoTrack.Domain.Reports;

import com.AutoTrack.Domain.Driver;
import com.AutoTrack.Domain.Enums.RouteStatus;
import com.AutoTrack.Domain.Route;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class DriverReport {

    @Getter
    @Setter
    private String driverName;
    @Getter
    @Setter
    private int numberOfRoutes;
    @Getter
    @Setter
    private double totalDistance;
    @Getter
    @Setter
    private int totalMinutesDriven;
    @Getter
    @Setter
    private double totalRouteValue;
    @Getter
    @Setter
    private int finishedRoutes;
    @Getter
    @Setter
    private int assignedPendingRoutes;
    @Getter
    @Setter
    private List<Long> routesId;
    @Getter
    @Setter
    private List<Double> routesDistance;
    @Getter
    @Setter
    private List<Integer> routesMinutesDriven;
    @Getter
    @Setter
    private List<Double> routesRouteValue;
    @Getter
    @Setter
    private double averageDistance;
    @Getter
    @Setter
    private double averageRouteValue;


    public DriverReport(Driver driver, List<Route> routes) {
        generateReport(driver, routes);
    }

    public void generateReport(Driver driver, List<Route> routes) {
        driverName = driver.getFirstName() + " " + driver.getLastName();
        numberOfRoutes = routes.size();

        routesId = new ArrayList<Long>();
        routesDistance = new ArrayList<Double>();
        routesRouteValue = new ArrayList<Double>();
        routesMinutesDriven = new ArrayList<Integer>();
        
        routes.forEach(route -> {
            totalDistance += route.getDistanceKm();
            totalMinutesDriven += route.getDurationMinutes();
            totalRouteValue = route.getValue();

            if(RouteStatus.COMPLETED.equals(route.getStatus()))finishedRoutes++;
            if(RouteStatus.PENDING.equals(route.getStatus()))assignedPendingRoutes++;

            routesId.add(route.getId());
            routesDistance.add(route.getDistanceKm());
            routesRouteValue.add(route.getValue());
            routesMinutesDriven.add(route.getDurationMinutes());

        });

        averageDistance = Math.round(totalDistance / numberOfRoutes);
        averageRouteValue = Math.round(totalRouteValue / numberOfRoutes);
    }
}
