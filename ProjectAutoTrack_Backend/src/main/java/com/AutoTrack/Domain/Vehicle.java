    package com.AutoTrack.Domain;

    import com.AutoTrack.Domain.Enums.VehicleStatus;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.Min;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.NotNull;
    import lombok.Getter;
    import lombok.Setter;

    @Entity
    @Table(name = "vehicles")
    public class Vehicle {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Getter
        @Setter
        private Long id;

        @NotNull(message = "License plate cannot be null")
        @NotBlank(message = "License plate cannot be blank")
        @Column(name = "license_plate", nullable = false, unique = true)
        @Getter
        @Setter
        private String licensePlate;

        @NotNull(message = "Brand cannot be null")
        @NotBlank(message = "Brand cannot be blank")
        @Column(name = "brand", nullable = false)
        @Getter
        @Setter
        private String brand;

        @NotNull(message = "Model cannot be null")
        @NotBlank(message = "Model cannot be blank")
        @Column(name = "model", nullable = false)
        @Getter
        @Setter
        private String model;

        @NotNull(message = "Fuel capacity cannot be null")
        @Min(value = 0, message = "Fuel capacity must be at least 0")
        @Column(name = "year_of_manufacture", nullable = false)
        @Getter
        @Setter
        private int yearOfManufacture;

        @NotNull(message = "Fuel capacity cannot be null")
        @Min(value = 0, message = "Fuel capacity must be at least 0")
        @Column(name = "fuel_capacity", nullable = false)
        @Getter
        @Setter
        private int fuelCapacity;

        @NotNull(message = "Fuel consumption cannot be null")
        @Min(value = 0, message = "Fuel consumption must be at least 0")
        @Column(name = "fuel_consumption", nullable = false)
        @Getter
        @Setter
        private int fuelConsumption;

        @NotNull(message = "Fuel level cannot be null")
        @Min(value = 0, message = "Fuel level must be at least 0")
        @Column(name = "fuel_level", nullable = false)
        @Getter
        @Setter
        private int fuelLevel;

        @Enumerated(EnumType.STRING)
        @NotNull(message = "Status cannot be null")
        @Column(name = "status", nullable = false)
        @Getter
        @Setter
        private VehicleStatus status;

        @Column(name = "latitude", nullable = true)
        @Getter
        @Setter
        private Double latitude;

        @Column(name = "longitude", nullable = true)
        @Getter
        @Setter
        private Double longitude;


        public Vehicle() {
        }

        public Vehicle(Long id, String licensePlate, String brand, String model, int yearOfManufacture, int fuelCapacity, int fuelConsumption, int fuelLevel, VehicleStatus status, Double latitude, Double longitude) {
            this.id = id;
            this.licensePlate = licensePlate;
            this.brand = brand;
            this.model = model;
            this.yearOfManufacture = yearOfManufacture;
            this.fuelCapacity = fuelCapacity;
            this.fuelConsumption = fuelConsumption;
            this.fuelLevel = fuelLevel;
            this.status = status;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        public String toString() {
            return "Vehicle{" +
                    "id=" + id +
                    ", licensePlate='" + licensePlate + '\'' +
                    ", brand='" + brand + '\'' +
                    ", model='" + model + '\'' +
                    ", yearOfManufacture=" + yearOfManufacture +
                    ", fuelCapacity=" + fuelCapacity +
                    ", fuelConsumption=" + fuelConsumption +
                    ", fuelLevel=" + fuelLevel +
                    ", status=" + status +
                    ", latitude=" + latitude +
                    ", longitude=" + longitude +
                    '}';
        }
    }
