package com.AutoTrack.Domain.Enums;

public enum VehicleStatus {
    AVAILABLE,       // disponibil pentru o nouă cursă
    IN_USE,          // utilizat într-o cursă activă
    IN_MAINTENANCE,  // la service, indisponibil
    OUT_OF_SERVICE,  // scos din uz (temporar sau permanent)
    RESERVED,        // rezervat pentru o cursă viitoare
    UNAVAILABLE      // nu poate fi folosit (fără motiv detaliat)
}
