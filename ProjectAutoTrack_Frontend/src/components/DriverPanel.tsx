import type { Driver } from '../API/domain';
import { getAllDrivers } from '../API/services';
import '../styles/CustomPanel.css'
import {UserIcon} from "lucide-react";
import { useEffect, useState } from 'react';
import { DriverModal } from './ActiveRoutesPanel';

type DriverPanelProps = {
    driver: Driver;
}

export const DriverPanel: React.FC<DriverPanelProps> = ({driver}: DriverPanelProps) => {
    if(!driver) return null;
    const [showDriverModal, setShowDriverModal] = useState(false);


    return (
        <div className="custom-panel">
            <div>
                <h1 className={"panel-title"}>{driver.firstName} {driver.lastName}</h1>
                <p className="panel-info">Driving hours today: {driver.drivingHoursToday}</p>
                <p className="panel-info">Status: {driver.status}</p>
            </div>
            <div className='left-div'>
                <button 
                className="driver-button" 
                aria-label="Assign Driver"
                onClick={() => setShowDriverModal(true)}
                >
                    <UserIcon size={20}/>
                </button>
            </div>

            {showDriverModal && (
                <DriverModal
                driver={driver}
                onClose={() => setShowDriverModal(false)}
                />
            )}
        </div>
    );
}

export const DriversStatusPanel: React.FC = () => {
    const [drivers, setDrivers] = useState<Driver[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchRoutes = async () => {
          try {
            const data = await getAllDrivers();
            setDrivers(data);
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


    return (
        <section className="drivers-status">
                    <h2 className="section-title">Drivers Status</h2>
                    <ul className="section-list overflow-y-auto scrollable">
                        {drivers.map((driver) => (
                            <DriverPanel key={driver.id} driver={driver}/>
                            ))}
                    </ul>
                </section>
    );
};

export default DriversStatusPanel