// types.ts
export interface DTOUser {
  id: number | null;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  address: string;
  username: string;
  role: string;
}

export const RouteStatus = {
  PENDING: "PENDING",
  IN_PROGRESS: "IN_PROGRESS",
  COMPLETED: "COMPLETED",
  CANCELLED: "CANCELLED",
} as const;

export type RouteStatus = (typeof RouteStatus)[keyof typeof RouteStatus];

export const VehicleStatus = {
  AVAILABLE: "AVAILABLE", // disponibil pentru o nouă cursă
  IN_USE: "IN_USE", // utilizat într-o cursă activă
  IN_MAINTENANCE: "IN_MAINTENANCE", // la service, indisponibil
  OUT_OF_SERVICE: "OUT_OF_SERVICE", // scos din uz (temporar sau permanent)
  RESERVED: "RESERVED", // rezervat pentru o cursă viitoare
  UNAVAILABLE: "UNAVAILABLE", // nu poate fi folosit (fără motiv detaliat)
} as const;

export type VehicleStatus = (typeof VehicleStatus)[keyof typeof VehicleStatus];

export interface DriverReference {
  id: number;
}

export interface VehicleReference {
  id: number;
}

export interface Driver {
  id: number | null;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  address: string;
  licenseNumber: string;
  licenseCategory: string;
  licenseExpirationDate: Date;
  medicalCheckExpirationDate: Date;
  hiredDate: Date;
  employmentStatus: string;
  drivingHoursToday: number;
  status: string;
}

export const isDriver = (obj: any): obj is Driver => {
  return (
    obj && typeof obj === "object" && "firstName" in obj && "lastName" in obj
  );
};

export interface Vehicle {
  id: number | null;
  licensePlate: string;
  brand: string;
  model: string;
  yearOfManufacture: number;
  fuelCapacity: number;
  fuelConsumption: number;
  fuelLevel: number;
  status: VehicleStatus;
  latitude: number | null;
  longitude: number | null;
}

export interface DTORoute {
  id: number | null;
  status: RouteStatus;
  distanceKm: number;
  durationMinutes: number;
  assignedDriver: Driver | DriverReference;
  assignedVehicle: Vehicle | VehicleReference;
  startLatitude: number;
  startLongitude: number;
  endLatitude: number;
  endLongitude: number;
  description: string;
  startLocationAddress: string;
  endLocationAddress: string;
  startDate: Date;
  endDate: Date;
  value: number;
}

// Reports -------------------------------------------------------------------------------------------------------------------------

export interface DriverReports {
  driverName: string;
  numberOfRoutes: number;
  totalDistance: number;
  totalMinutesDriven: number;
  totalRouteValue: number;
  finishedRoutes: number;
  assignedPendingRoutes: number;
  routesId: number[];
  routesDistance: number[];
  routesMinutesDriven: number[];
  routesRouteValue: number[];
  averageDistance: number;
  averageRouteValue: number;
}
