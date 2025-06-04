import "../styles/CustomPanel.css";
import { UserIcon } from "lucide-react";
import { isDriver, type DTORoute } from "../API/domain.tsx";
import { useEffect, useState } from "react";
import { getAllRoutes } from "../API/services.tsx";
import "../styles/Modal.css";

interface RoutePanelProps {
  route: DTORoute;
}

export const DriverModal: React.FC<{
  driver: DTORoute["assignedDriver"];
  onClose: () => void;
}> = ({ driver, onClose }) => {
  if (!driver) return null;
  if (!isDriver(driver)) {
    return null;
  }

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <h2>Driver Details</h2>
        <p>
          <strong>Name:</strong> {driver.firstName} {driver.lastName}
        </p>
        <p>
          <strong>Email:</strong> {driver.email}
        </p>
        <p>
          <strong>Phone:</strong> {driver.phone}
        </p>
        <button onClick={onClose}>Close</button>
      </div>
    </div>
  );
};

export const RoutePanel: React.FC<RoutePanelProps> = ({ route }) => {
  const [showDriverModal, setShowDriverModal] = useState(false);

  return (
    <div className="custom-panel">
      <div>
        <h1 className="panel-title">
          {route.startLocationAddress} - {route.endLocationAddress}
        </h1>
        <p className="panel-info">Route ID: {route.id}</p>
        <p className="panel-info">
          ETA:{" "}
          {new Date(route.endDate).toLocaleString(undefined, {
            year: "numeric",
            month: "2-digit",
            day: "2-digit",
            hour: "2-digit",
            minute: "2-digit",
            hour12: false, // sau true dacă vrei AM/PM
          })}
        </p>
      </div>
      <div className="left-div">
        <button
          className="driver-button"
          aria-label="Show Driver Details"
          onClick={() => setShowDriverModal(true)}
        >
          <UserIcon size={20} />
        </button>
      </div>

      {showDriverModal && (
        <DriverModal
          driver={route.assignedDriver}
          onClose={() => setShowDriverModal(false)}
        />
      )}
    </div>
  );
};

export const ActiveRoutesPanel: React.FC = () => {
  const [routes, setRoutes] = useState<DTORoute[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchRoutes = async () => {
      try {
        const data = await getAllRoutes();
        setRoutes(data);
      } catch (e) {
        setError("Failed to load routes");
      } finally {
        setLoading(false);
      }
    };

    fetchRoutes();
  }, []);

  if (loading)
    return (
      <section className="active-routes-panel">
        <div>Loading routes...</div>
      </section>
    );
  if (error)
    return (
      <section className="active-routes-panel">
        <div>Error: {error}</div>
      </section>
    );

  return (
    <section className="active-routes-panel">
      <h2 className="section-title">Active Routes</h2>
      <ul className="section-list overflow-y-auto scrollable">
        {routes.map((route) => (
          <RoutePanel key={route.id} route={route} />
        ))}
      </ul>
    </section>
  );
};
