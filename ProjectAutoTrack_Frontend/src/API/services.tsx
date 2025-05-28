import API from './api.tsx';
import type { DTOUser, DTORoute, Driver, Vehicle } from './domain.tsx';

export const handleLogout = () => {
  localStorage.removeItem('token');
  window.location.href = '/login'; // sau navigate('/login') dacă folosești react-router
};

export const handleLogin = async ({
  username,
  password,
}: {
  username: string;
  password: string;
}) => {
  const res = await API.post('/auth/login', { username, password });
  localStorage.setItem('token', res.data.token);
  return true;
};

export const me = async (): Promise<DTOUser> => {
  const res = await API.get<DTOUser>('/me');
  return res.data;
};


function parseDTORoute(raw: any): DTORoute {
  return {
    ...raw,
    startDate: new Date(raw.startDate),
    endDate: new Date(raw.endDate),
    assignedDriver: {
      ...raw.assignedDriver,
      licenseExpirationDate: new Date(raw.assignedDriver.licenseExpirationDate),
      medicalCheckExpirationDate: new Date(raw.assignedDriver.medicalCheckExpirationDate),
      hiredDate: new Date(raw.assignedDriver.hiredDate),
    },
    assignedVehicle: { ...raw.assignedVehicle },
  };
}

function parseDriver(raw: any): Driver {
  return {
    ...raw,
    licenseExpirationDate: new Date(raw.licenseExpirationDate),
    medicalCheckExpirationDate: new Date(raw.medicalCheckExpirationDate),
    hiredDate: new Date(raw.hiredDate),
  };
}


export const getAllRoutes = async (): Promise<DTORoute[]> => {
  const res = await API.get<DTORoute[]>('/routes');
  const routes = res.data.map(parseDTORoute);

  return routes;
};

export const getAllDrivers = async (): Promise<Driver[]> => {
  const res = await API.get<DTORoute[]>('/drivers');
  const drivers = res.data.map(parseDriver);

  return drivers;
};

export const getAllVehicles = async (): Promise<Vehicle[]> => {
  const res = await API.get<Vehicle[]>('/vehicles');

  return res.data;
};

export const postVehicle = async (vehicle: Vehicle): Promise<Vehicle> => {
  const res = await API.post<Vehicle>('/vehicles', vehicle);
  return res.data;
}


export const postDriver = async (driver: Driver): Promise<Driver> => {
  const res = await API.post<Driver>('/drivers', driver);
  return res.data;
}
