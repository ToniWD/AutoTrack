import { useEffect, useState } from "react";
import "../styles/Scrollbar.css";
import {
  parseDriverSafe,
  parseRouteSafe,
  parseVehicleSafe,
} from "../API/parsers"; // Adjust the import path as necessary
import {
  getAllDrivers,
  getAllVehicles,
  postDriver,
  postRoute,
  postVehicle,
} from "../API/services";
import {
  RouteStatus,
  VehicleStatus,
  type Driver,
  type DTORoute,
  type Vehicle,
} from "../API/domain";

// Allowed entity types in the form
export type EntityType = "vehicle" | "driver" | "route";

interface EntityFormProps {
  entityType: EntityType;
  onSubmit: () => void;
}

export type FieldConfig = {
  name: string;
  label: string;
  type: string; // "text", "number", "date", "email", "select"
  required?: boolean;
  options?:
    | { label: string; value: string | number }[] // Static options
    | (() => Promise<{ label: string; value: string | number }[]>); // Dynamic options as a function returning a promise
};

// Convert enum to options for select fields
export function enumToOptions<T extends Record<string, string | number>>(e: T) {
  return Object.entries(e)
    .filter(([key, value]) => isNaN(Number(key))) // Filter out numeric keys (for string enums)
    .map(([key, value]) => ({
      label: String(value),
      value: key,
    }));
}

// Configuration for the form fields based on entity type
// You can expand it as needed
export const formConfigs: Record<EntityType, FieldConfig[]> = {
  vehicle: [
    {
      name: "licensePlate",
      label: "License Plate",
      type: "text",
      required: true,
    },
    { name: "brand", label: "Brand", type: "text", required: true },
    { name: "model", label: "Model", type: "text", required: true },
    {
      name: "yearOfManufacture",
      label: "Year of Manufacture",
      type: "number",
      required: true,
    },
    {
      name: "fuelCapacity",
      label: "Fuel Capacity (L)",
      type: "number",
      required: true,
    },
    {
      name: "fuelConsumption",
      label: "Fuel Consumption (L/100km)",
      type: "number",
      required: true,
    },
    {
      name: "fuelLevel",
      label: "Fuel Level (%)",
      type: "number",
      required: true,
    },
    {
      name: "status",
      label: "Status",
      type: "select",
      required: true,
      options: enumToOptions(VehicleStatus),
    }, // ideal: dropdown/select
  ],
  driver: [
    { name: "firstName", label: "First Name", type: "text", required: true },
    { name: "lastName", label: "Last Name", type: "text", required: true },
    { name: "email", label: "Email", type: "email", required: true },
    { name: "phone", label: "Phone", type: "text", required: true },
    { name: "address", label: "Address", type: "text", required: true },
    {
      name: "licenseNumber",
      label: "License Number",
      type: "text",
      required: true,
    },
    {
      name: "licenseCategory",
      label: "License Category",
      type: "text",
      required: true,
    },
    {
      name: "licenseExpirationDate",
      label: "License Expiration Date",
      type: "date",
      required: true,
    },
    {
      name: "medicalCheckExpirationDate",
      label: "Medical Check Expiration",
      type: "date",
      required: true,
    },
    { name: "hiredDate", label: "Hired Date", type: "date", required: true },
    {
      name: "employmentStatus",
      label: "Employment Status",
      type: "text",
      required: true,
    },
    {
      name: "drivingHoursToday",
      label: "Driving Hours Today",
      type: "number",
      required: true,
    },
    { name: "status", label: "Status", type: "text", required: true },
  ],
  route: [
    {
      name: "status",
      label: "Status",
      type: "select",
      required: true,
      options: enumToOptions(RouteStatus),
    },
    {
      name: "distanceKm",
      label: "Distance (km)",
      type: "number",
      required: true,
    },
    {
      name: "durationMinutes",
      label: "Duration (minutes)",
      type: "number",
      required: true,
    },
    {
      name: "assignedDriver",
      label: "Assigned Driver",
      type: "select",
      required: true,
      options: async () => {
        const drivers = await getAllDrivers();
        return drivers
          .filter((d) => d && d.id != null)
          .map((d) => ({
            label: `${d.firstName} ${d.lastName}`,
            value: Number(d.id),
          }));
      },
    },
    {
      name: "assignedVehicle",
      label: "Assigned Vehicle",
      type: "select",
      required: true,
      options: async () => {
        const vehicle = await getAllVehicles();
        return vehicle
          .filter((e) => e && e.id != null)
          .map((e) => ({
            label: `${e.brand} ${e.model} (${e.licensePlate})`,
            value: Number(e.id),
          }));
      },
    },
    {
      name: "startLatitude",
      label: "Start Latitude",
      type: "number",
      required: true,
    },
    {
      name: "startLongitude",
      label: "Start Longitude",
      type: "number",
      required: true,
    },
    {
      name: "endLatitude",
      label: "End Latitude",
      type: "number",
      required: true,
    },
    {
      name: "endLongitude",
      label: "End Longitude",
      type: "number",
      required: true,
    },
    { name: "description", label: "Description", type: "text", required: true },
    {
      name: "startLocationAddress",
      label: "Start Address",
      type: "text",
      required: true,
    },
    {
      name: "endLocationAddress",
      label: "End Address",
      type: "text",
      required: true,
    },
    { name: "startDate", label: "Start Date", type: "date", required: true },
    { name: "endDate", label: "End Date", type: "date", required: true },
    { name: "value", label: "Value", type: "number", required: true },
  ],
  // Add more entity types and their respective fields as needed
};

export type HandlesConfig = {
  handlePost: (entity: any, close: () => void) => Promise<void>;
  handleParse?: (data: any) => any; // Optional parsing function
};

const handlePostVehicle = async (vehicle: Vehicle, close: () => void) => {
  try {
    const savedVehicle = await postVehicle(vehicle);
    alert("Vehicle saved successfully!");
    // poți face redirect sau curățare form aici
    close();
  } catch (error: any) {
    // Dacă folosești axios, mesajul e de obicei în error.response.data
    const errMsg = error.response?.data || "Unknown error occurred";
    alert(`Error: ${errMsg}`);
  }
};

const handlePostDriver = async (entity: Driver, close: () => void) => {
  try {
    const saved = await postDriver(entity);
    alert("Driver saved successfully!");
    close();
  } catch (error: any) {
    const errMsg = error.response?.data || "Unknown error occurred";
    alert(`Error: ${errMsg}`);
  }
};

const handlePostRoute = async (entity: DTORoute, close: () => void) => {
  try {
    const saved = await postRoute(entity);
    alert("Route saved successfully!");
    close();
  } catch (error: any) {
    const errMsg = error.response?.data || "Unknown error occurred";
    alert(`Error: ${errMsg}`);
  }
};

export const handlesConfig: Record<EntityType, HandlesConfig> = {
  vehicle: {
    handlePost: handlePostVehicle,
    handleParse: parseVehicleSafe,
  },
  driver: {
    handlePost: handlePostDriver,
    handleParse: parseDriverSafe,
  },
  route: {
    handlePost: handlePostRoute,
    handleParse: parseRouteSafe,
  },
};

export const EntityForm = ({ entityType, onSubmit }: EntityFormProps) => {
  const fields = formConfigs[entityType];
  const [formData, setFormData] = useState<Record<string, string | number>>({});
  const [errors, setErrors] = useState<Record<string, string>>({});
  const [dynamicOptions, setDynamicOptions] = useState<
    Record<string, { label: string; value: string | number }[]>
  >({});

  //
  useEffect(() => {
    fields.forEach((field) => {
      if (typeof field.options === "function") {
        field.options().then((opts) => {
          setDynamicOptions((prev) => ({ ...prev, [field.name]: opts }));
        });
      }
    });
  }, [fields]);

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value, type } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === "number" ? Number(value) : value,
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (handlesConfig[entityType].handleParse) {
      const result = handlesConfig[entityType].handleParse(formData);
      if (result.errors) {
        setErrors(result.errors);
        return;
      } else {
        setErrors({});
        console.log(result.vehicle);
        if (result[entityType])
          handlesConfig[entityType].handlePost(result[entityType], onSubmit);
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
            {field.type === "select" ? (
              <select
                name={field.name}
                required={field.required}
                value={formData[field.name] ?? ""}
                onChange={handleChange}
                className="border rounded px-2 py-1"
              >
                <option value="">Select...</option>
                {(typeof field.options === "function"
                  ? dynamicOptions[field.name] ?? []
                  : field.options ?? []
                ).map((opt) => (
                  <option key={opt.value} value={opt.value}>
                    {opt.label}
                  </option>
                ))}
              </select>
            ) : (
              <input
                name={field.name}
                type={field.type}
                required={field.required}
                value={formData[field.name] ?? ""}
                onChange={handleChange}
                className="border rounded px-2 py-1"
              />
            )}

            {errors[field.name] && (
              <p className="text-red-500 text-sm mt-1">{errors[field.name]}</p>
            )}
          </div>
        ))}

        <button
          type="submit"
          className="bg-blue-500 text-white px-4 py-2 rounded"
        >
          Add {entityType}
        </button>
      </form>
    </div>
  );
};
