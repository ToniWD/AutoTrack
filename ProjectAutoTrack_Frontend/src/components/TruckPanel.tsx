import type { Vehicle } from "../API/domain.tsx";
import { getAllVehicles } from "../API/services.tsx";
import FuelLevelChart from "./FuelLevelChart.tsx";
import { useEffect, useState } from "react";
import '../styles/CustomPanel.css'

type TruckProps = {
    truck: Vehicle;
}

export const TruckPanel: React.FC<TruckProps> = ({truck}: TruckProps) => {
    if (!truck) return null;

    let color = '#4ade80'; // culoarea verde

    if(truck.fuelLevel < 20)
    {
        color = "#f87171"; // culoarea rosie
    }

    const fuelData = [
        {
            name: 'Fuel Level',
            value: truck.fuelLevel, // procentul de combustibil
            fill: color,
        },
    ];


    return (
        <div className="custom-panel">
            <div>
                <h1 className={"panel-title"}>Truck: {truck.licensePlate}</h1>
                <p className={"panel-info"}>Status: {truck.status}</p>
                <p className={"panel-info"}>Brand: {truck.brand}</p>
                <p className={"panel-info"}>Model: {truck.model}</p>
                <p className={"panel-info"}>Year: {truck.yearOfManufacture}</p>
            </div>
            <div className="left-div">
              <FuelLevelChart data={fuelData} />
            </div>
        </div>
    );
}

export const FleetStatusPanel: React.FC = () =>
{
    const [vehicles, setVehicles] = useState<Vehicle[]>([]);
      const [loading, setLoading] = useState(true);
      const [error, setError] = useState<string | null>(null);
    
      useEffect(() => {
        const fetchRoutes = async () => {
          try {
            const data = await getAllVehicles();
            setVehicles(data);
          } catch (e) {
            setError('Failed to load routes');
          } finally {
            setLoading(false);
          }
        };
    
        fetchRoutes();
      }, []);
    
      if (loading) return (
        <section className="active-routes-panel">
            <div>Loading routes...</div>
        </section>
      );
      if (error) return (
        <section className="active-routes-panel">
            <div>Error: {error}</div>
        </section>
      );

    return(
        <section className="fleet-status">
                            <h2 className="section-title">Fleet Status</h2>
                            <ul className="section-list overflow-y-auto scrollable">
                                {vehicles.map(truck => (
                                    <TruckPanel key={truck.id} truck={truck} />
                                ))}
                            </ul>
                        </section>
    );
};
