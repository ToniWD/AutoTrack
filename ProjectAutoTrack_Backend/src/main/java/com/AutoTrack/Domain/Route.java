package com.AutoTrack.Domain;

import com.AutoTrack.Domain.Enums.RouteStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status cannot be null")
    @Column(name = "status", nullable = false)
    private RouteStatus status; // PENDING, COMPLETED, etc.

    @NotNull(message = "Distance cannot be null")
    @Min(value = 0, message = "Distance must be greater than or equal to 0")
    @Column(name = "distance_km", nullable = false)
    private Double distanceKm;

    @NotNull(message = "Duration cannot be null")
    @Min(value = 0, message = "Duration must be greater than or equal to 0")
    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = true) // Cheia străină către driver
    private Driver assignedDriver;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = true) // Cheia străină către vehicle
    private Vehicle assignedVehicle;

    @Column(name = "start_latitude", nullable = false)
    private Double startLatitude;

    @Column(name = "start_longitude", nullable = false)
    private Double startLongitude;

    @Column(name = "end_latitude", nullable = false)
    private Double endLatitude;

    @Column(name = "end_longitude", nullable = false)
    private Double endLongitude;

    @Column(name = "description")
    private String description;

    @Column(name = "start_location_address")
    private String startLocationAddress;

    @Column(name = "end_location_address")
    private String endLocationAddress;

    @NotNull(message = "Start date cannot be null")
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @NotNull(message = "End date cannot be null")
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @NotNull(message = "Value cannot be null")
    @Min(value = 0, message = "Value must be greater than or equal to 0")
    @Column(name = "value", nullable = false)
    private Double value;
    public Route() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RouteStatus getStatus() {
        return status;
    }

    public void setStatus(RouteStatus status) {
        this.status = status;
    }

    public Double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(Double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Driver getAssignedDriver() {
        return assignedDriver;
    }

    public void setAssignedDriver(Driver assignedDriver) {
        this.assignedDriver = assignedDriver;
    }

    public Vehicle getAssignedVehicle() {
        return assignedVehicle;
    }

    public void setAssignedVehicle(Vehicle assignedVehicle) {
        this.assignedVehicle = assignedVehicle;
    }

    public Double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(Double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public Double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(Double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public Double getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(Double endLatitude) {
        this.endLatitude = endLatitude;
    }

    public Double getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(Double endLongitude) {
        this.endLongitude = endLongitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartLocationAddress() {
        return startLocationAddress;
    }

    public void setStartLocationAddress(String startLocationAddress) {
        this.startLocationAddress = startLocationAddress;
    }

    public String getEndLocationAddress() {
        return endLocationAddress;
    }

    public void setEndLocationAddress(String endLocationAddress) {
        this.endLocationAddress = endLocationAddress;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", status=" + status +
                ", distanceKm=" + distanceKm +
                ", durationMinutes=" + durationMinutes +
                ", assignedDriver=" + assignedDriver +
                ", assignedVehicle=" + assignedVehicle +
                ", startLatitude=" + startLatitude +
                ", startLongitude=" + startLongitude +
                ", endLatitude=" + endLatitude +
                ", endLongitude=" + endLongitude +
                ", description='" + description + '\'' +
                ", startLocationAddress='" + startLocationAddress + '\'' +
                ", endLocationAddress='" + endLocationAddress + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", value=" + value +
                '}';
    }
}
