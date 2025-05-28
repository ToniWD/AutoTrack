import { useState } from "react";
import '../styles/Scrollbar.css'
import { parseDriverSafe, parseVehicleSafe } from "../API/parsers"; // Adjust the import path as necessary
import { postDriver, postVehicle } from "../API/services";
import type { Driver, Vehicle } from "../API/domain";

export type EntityType = "vehicle" | "driver";

interface EntityFormProps {
  entityType: EntityType;
  onSubmit: () => void;
}

export type FieldConfig = {
  name: string;
  label: string;
  type: string;
  required?: boolean;
};

export const formConfigs: Record<EntityType, FieldConfig[]> = {
  vehicle: [
  { name: "licensePlate", label: "License Plate", type: "text", required: true },
  { name: "brand", label: "Brand", type: "text", required: true },
  { name: "model", label: "Model", type: "text", required: true },
  { name: "yearOfManufacture", label: "Year of Manufacture", type: "number", required: true },
  { name: "fuelCapacity", label: "Fuel Capacity (L)", type: "number", required: true },
  { name: "fuelConsumption", label: "Fuel Consumption (L/100km)", type: "number", required: true },
  { name: "fuelLevel", label: "Fuel Level (%)", type: "number", required: true },
  { name: "status", label: "Status", type: "text", required: true }, // ideal: dropdown/select
],
  driver: [
  { name: "firstName", label: "First Name", type: "text", required: true },
  { name: "lastName", label: "Last Name", type: "text", required: true },
  { name: "email", label: "Email", type: "email", required: true },
  { name: "phone", label: "Phone", type: "text", required: true },
  { name: "address", label: "Address", type: "text", required: true },
  { name: "licenseNumber", label: "License Number", type: "text", required: true },
  { name: "licenseCategory", label: "License Category", type: "text", required: true },
  { name: "licenseExpirationDate", label: "License Expiration Date", type: "date", required: true },
  { name: "medicalCheckExpirationDate", label: "Medical Check Expiration", type: "date", required: true },
  { name: "hiredDate", label: "Hired Date", type: "date", required: true },
  { name: "employmentStatus", label: "Employment Status", type: "text", required: true },
  { name: "drivingHoursToday", label: "Driving Hours Today", type: "number", required: true },
  { name: "status", label: "Status", type: "text", required: true },
],
  // Add more entity types and their respective fields as needed
};

const handlePostVehicle = async (vehicle: Vehicle, close: ()=> void) => {
    try {
      const savedVehicle = await postVehicle(vehicle);
      alert('Vehicle saved successfully!');
      // poți face redirect sau curățare form aici
      close();
    } catch (error: any) {
      // Dacă folosești axios, mesajul e de obicei în error.response.data
      const errMsg = error.response?.data || 'Unknown error occurred';
      alert(`Error: ${errMsg}`);
    }
  };

  const handlePostDriver = async (entity: Driver, close: ()=> void) => {
    try {
      const saved = await postDriver(entity);
      alert('Driver saved successfully!');
      close();
    } catch (error: any) {
      const errMsg = error.response?.data || 'Unknown error occurred';
      alert(`Error: ${errMsg}`);
    }
  };

export const EntityForm = ({ entityType, onSubmit }: EntityFormProps) => {
  const fields = formConfigs[entityType];
  const [formData, setFormData] = useState<Record<string, string | number>>({});
  const [errors, setErrors] = useState<Record<string, string>>({});


  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value, type } = e.target;
    const parsedValue = type === "number" ? parseInt(value) : value;
    setFormData((prev) => ({ ...prev, [name]: parsedValue }));
  };

  const handleSubmit = (e: React.FormEvent) => {
  e.preventDefault();

  if (entityType === "vehicle") {
    const result = parseVehicleSafe(formData);
    if (result.errors) {
      setErrors(result.errors); // aici salvăm erorile
      return;
    } else {
      setErrors({}); // curățăm erorile dacă totul e valid
      console.log(result.vehicle);
      if(result.vehicle)handlePostVehicle(result.vehicle, onSubmit); // trimitem vehiculul validat
      return;
    }
  }

  if (entityType === "driver") {
    const result = parseDriverSafe(formData);
    if (result.errors) {
      setErrors(result.errors); // aici salvăm erorile
      return;
    } else {
      setErrors({}); // curățăm erorile dacă totul e valid
      console.log(result.driver);
      if(result.driver)handlePostDriver(result.driver, onSubmit); // trimitem vehiculul validat
      return;
    }
  }
};


  return (
    <div className="max-h-[80vh] overflow-y-auto p-4 scrollable">
  <form onSubmit={handleSubmit} className="space-y-4">
    {fields.map((field) => (
  <div key={field.name} className="flex flex-col">
    <label className="mb-1">{field.label}</label>
    <input
      name={field.name}
      type={field.type}
      required={field.required}
      value={formData[field.name] ?? ""}
      onChange={handleChange}
      className="border rounded px-2 py-1"
    />
    {errors[field.name] && (
      <p className="text-red-500 text-sm mt-1">
        {errors[field.name]}
      </p>
    )}
  </div>
))}


    <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded">
      Add {entityType}
    </button>
  </form>
</div>

  );
};
