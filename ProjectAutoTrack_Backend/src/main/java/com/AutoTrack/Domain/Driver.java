package com.AutoTrack.Domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "drivers")
public class Driver extends Person {

    @NotNull(message = "License number cannot be null")
    @NotBlank(message = "License number cannot be blank")
    @Column(name = "license_number", nullable = false)
    private String licenseNumber;

    @NotNull(message = "License category cannot be null")
    @NotBlank(message = "License category cannot be blank")
    @Column(name = "license_category", nullable = false)
    private String licenseCategory;


    @Column(name = "license_expiration_date", nullable = false)
    private LocalDateTime licenseExpirationDate;


    @Column(name = "medical_check_expiration_date", nullable = false)
    private LocalDateTime medicalCheckExpirationDate;

    @NotNull(message = "Hired date cannot be null")
    @Column(name = "hired_date", nullable = false)
    private LocalDateTime hiredDate;

    @NotNull(message = "Employment status cannot be null")
    @NotBlank(message = "Employment status cannot be blank")
    @Column(name = "employment_status", nullable = false)
    private String employmentStatus; // "active", "on leave", "suspended"

//    @ManyToOne
//    @JoinColumn(name = "assigned_vehicle_id", nullable = true)
//    private Vehicle assignedVehicle;

//    @OneToMany(mappedBy = "assignedDriver") // One-to-many relation
//    private List<Route> routes;

    @NotNull(message = "Driving hours today cannot be null")
    @Min(value = 0, message = "Driving hours today must be greater than or equal to 0")
    @Column(name = "driving_hours_today", nullable = false)
    private Float drivingHoursToday;

    @NotNull(message = "Status cannot be null")
    @NotBlank(message = "Status cannot be blank")
    @Column(name = "status", nullable = false)
    private String status; // "active", "inactive", etc.
    public Driver() {
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseCategory() {
        return licenseCategory;
    }

    public void setLicenseCategory(String licenseCategory) {
        this.licenseCategory = licenseCategory;
    }

    public LocalDateTime getLicenseExpirationDate() {
        return licenseExpirationDate;
    }

    public void setLicenseExpirationDate(LocalDateTime licenseExpirationDate) {
        this.licenseExpirationDate = licenseExpirationDate;
    }

    public LocalDateTime getMedicalCheckExpirationDate() {
        return medicalCheckExpirationDate;
    }

    public void setMedicalCheckExpirationDate(LocalDateTime medicalCheckExpirationDate) {
        this.medicalCheckExpirationDate = medicalCheckExpirationDate;
    }

    public LocalDateTime getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(LocalDateTime hiredDate) {
        this.hiredDate = hiredDate;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }





    public Float getDrivingHoursToday() {
        return drivingHoursToday;
    }

    public void setDrivingHoursToday(Float drivingHoursToday) {
        this.drivingHoursToday = drivingHoursToday;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    @Override
    public String toString() {
        return "Driver{" +
                "licenseNumber='" + licenseNumber + '\'' +
                ", licenseCategory='" + licenseCategory + '\'' +
                ", licenseExpirationDate=" + licenseExpirationDate +
                ", medicalCheckExpirationDate=" + medicalCheckExpirationDate +
                ", hiredDate=" + hiredDate +
                ", employmentStatus='" + employmentStatus + '\'' +
                ", drivingHoursToday=" + drivingHoursToday +
                ", status='" + status + '\'' +
                '}';
    }

}
