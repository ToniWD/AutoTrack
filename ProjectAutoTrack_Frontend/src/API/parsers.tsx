import { VehicleStatus, type Driver, type Vehicle } from "./domain";

export type ValidationResult<T> = {
  value?: T;
  error?: string;
};

//validateString, validateNumber, validateMin, validateEnum ---------------------------------------------------------------------------------------------
const validateString = (value: any, fieldName: string): ValidationResult<string> => {
  if (value === null || value === undefined) {
    return { error: `${fieldName} cannot be null` };
  }
  if (typeof value !== 'string') {
    return { error: `${fieldName} must be a string` };
  }
  if (value.trim() === '') {
    return { error: `${fieldName} cannot be blank` };
  }
  return { value: value.trim() };
};

const validateNumber = (value: any, fieldName: string): ValidationResult<number> => {
  if (value === null || value === undefined) {
    return { error: `${fieldName} cannot be null` };
  }
  const num = Number(value);
  if (isNaN(num)) {
    return { error: `${fieldName} must be a number` };
  }
  return { value: num };
};

const validateMin = (
  result: ValidationResult<number>,
  fieldName: string,
  min: number
): ValidationResult<number> => {
  if (result.error) return result;
  if (result.value! < min) {
    return { error: `${fieldName} must be at least ${min}` };
  }
  return result;
};

const validateEnum = <T extends Record<string, string | number>>(
  value: any,
  enumType: T,
  fieldName: string
): ValidationResult<T[keyof T]> => {
  if (!Object.values(enumType).includes(value)) {
    return { error: `${fieldName} is invalid: ${value}` };
  }
  return { value: value as T[keyof T] };
};

export const validateDate = (
  value: any,
  fieldName: string
): { value?: Date; error?: string } => {
  if (value === null || value === undefined || value === '') {
    return { error: `${fieldName} cannot be null or empty` };
  }

  const date = new Date(value);

  if (isNaN(date.getTime())) {
    return { error: `${fieldName} must be a valid date` };
  }

  return { value: date };
};

//-------------------------------------------------------------------------------------------------





//parsers-------------------------------------------------------------------------------------------------
export const parseVehicleSafe = (data: any): { vehicle?: Vehicle; errors?: Record<string, string> } => {
  const errors: Record<string, string> = {};

  const licensePlate = validateString(data.licensePlate, 'License plate');
  if (licensePlate.error) errors.licensePlate = licensePlate.error;

  const brand = validateString(data.brand, 'Brand');
  if (brand.error) errors.brand = brand.error;

  const model = validateString(data.model, 'Model');
  if (model.error) errors.model = model.error;

  const yearOfManufacture = validateMin(
    validateNumber(data.yearOfManufacture, 'Year of manufacture'),
    'Year of manufacture',
    0
  );
  if (yearOfManufacture.error) errors.yearOfManufacture = yearOfManufacture.error;

  const fuelCapacity = validateMin(
    validateNumber(data.fuelCapacity, 'Fuel capacity'),
    'Fuel capacity',
    0
  );
  if (fuelCapacity.error) errors.fuelCapacity = fuelCapacity.error;

  const fuelConsumption = validateMin(
    validateNumber(data.fuelConsumption, 'Fuel consumption'),
    'Fuel consumption',
    0
  );
  if (fuelConsumption.error) errors.fuelConsumption = fuelConsumption.error;

  const fuelLevel = validateMin(
    validateNumber(data.fuelLevel, 'Fuel level'),
    'Fuel level',
    0
  );
  if (fuelLevel.error) errors.fuelLevel = fuelLevel.error;

  const status = validateEnum(data.status, VehicleStatus, 'Status');
  if (status.error) errors.status = status.error;

  const latitude = data.latitude !== undefined && data.latitude !== null ? Number(data.latitude) : null;
  const longitude = data.longitude !== undefined && data.longitude !== null ? Number(data.longitude) : null;

  if (Object.keys(errors).length > 0) {
    return { errors };
  }

  return {
    vehicle: {
      id: data.id ? Number(data.id) : null,
      licensePlate: licensePlate.value!,
      brand: brand.value!,
      model: model.value!,
      yearOfManufacture: yearOfManufacture.value!,
      fuelCapacity: fuelCapacity.value!,
      fuelConsumption: fuelConsumption.value!,
      fuelLevel: fuelLevel.value!,
      status: status.value!,
      latitude,
      longitude,
    },
  };
};


export const parseDriverSafe = (data: any): { driver?: Driver; errors?: Record<string, string> } => {
  const errors: Record<string, string> = {};

  const firstName = validateString(data.firstName, 'First name');
  if (firstName.error) errors.firstName = firstName.error;

  const lastName = validateString(data.lastName, 'Last name');
  if (lastName.error) errors.lastName = lastName.error;

  const email = validateString(data.email, 'Email');
  if (email.error) errors.email = email.error;

  const phone = validateString(data.phone, 'Phone');
  if (phone.error) errors.phone = phone.error;

  const address = validateString(data.address, 'Address');
  if (address.error) errors.address = address.error;

  const licenseNumber = validateString(data.licenseNumber, 'License number');
  if (licenseNumber.error) errors.licenseNumber = licenseNumber.error;

  const licenseCategory = validateString(data.licenseCategory, 'License category');
  if (licenseCategory.error) errors.licenseCategory = licenseCategory.error;

  const licenseExpirationDate = validateDate(data.licenseExpirationDate, 'License expiration date');
  if (licenseExpirationDate.error) errors.licenseExpirationDate = licenseExpirationDate.error;

  const medicalCheckExpirationDate = validateDate(data.medicalCheckExpirationDate, 'Medical check expiration date');
  if (medicalCheckExpirationDate.error) errors.medicalCheckExpirationDate = medicalCheckExpirationDate.error;

  const hiredDate = validateDate(data.hiredDate, 'Hired date');
  if (hiredDate.error) errors.hiredDate = hiredDate.error;

  const employmentStatus = validateString(data.employmentStatus, 'Employment status');
  if (employmentStatus.error) errors.employmentStatus = employmentStatus.error;

  const drivingHoursToday = validateMin(
    validateNumber(data.drivingHoursToday, 'Driving hours today'),
    'Driving hours today',
    0
  );
  if (drivingHoursToday.error) errors.drivingHoursToday = drivingHoursToday.error;

  const status = validateString(data.status, 'Status');
  if (status.error) errors.status = status.error;

  if (Object.keys(errors).length > 0) {
    return { errors };
  }

  return {
    driver: {
      id: data.id ? Number(data.id) : null,
      firstName: firstName.value!,
      lastName: lastName.value!,
      email: email.value!,
      phone: phone.value!,
      address: address.value!,
      licenseNumber: licenseNumber.value!,
      licenseCategory: licenseCategory.value!,
      licenseExpirationDate: new Date(licenseExpirationDate.value!),
      medicalCheckExpirationDate: new Date(medicalCheckExpirationDate.value!),
      hiredDate: new Date(hiredDate.value!),
      employmentStatus: employmentStatus.value!,
      drivingHoursToday: drivingHoursToday.value!,
      status: status.value!,
    },
  };
};
