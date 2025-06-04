import { useEffect, useState } from "react";
import type { Driver, DriverReports } from "../API/domain";
import { getReportsForDriver } from "../API/services";
import {
  Bar,
  BarChart,
  Legend,
  ReferenceLine,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";

export const DriverReportsPanel: React.FC<{
  driver: Driver;
  onClose: () => void;
}> = ({ driver, onClose }) => {
  const [report, setReport] = useState<DriverReports | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchRoutes = async () => {
      try {
        if (driver.id === null) {
          onClose();
          return null;
        }
        const data = await getReportsForDriver(driver.id);
        setReport(data);
        console.log("Driver report data:", data);
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
      <div className="modal-overlay" onClick={onClose}>
        <div className="modal-content" onClick={(e) => e.stopPropagation()}>
          <h1>Generating report...</h1>
          <button onClick={onClose}>Close</button>
        </div>
      </div>
    );
  if (error)
    return (
      <div className="modal-overlay" onClick={onClose}>
        <div className="modal-content" onClick={(e) => e.stopPropagation()}>
          <h1>Error: {error}</h1>
          <button onClick={onClose}>Close</button>
        </div>
      </div>
    );

  const data = [
    {
      name: "Total Rute",
      Finalizate: report?.finishedRoutes ?? 2,
      ÎnAșteptare: report?.assignedPendingRoutes ?? 3,
    },
  ];

  const routesData = report?.routesId.map((id, i) => ({
    id,
    distance: report.routesDistance[i],
    value: report.routesRouteValue[i],
  }));

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div
        className="modal-content"
        style={{ minWidth: "600px" }}
        onClick={(e) => e.stopPropagation()}
      >
        <h2>
          Driver Details - {driver.firstName} {driver.lastName}
        </h2>
        <ResponsiveContainer width="100%" height={120}>
          <BarChart
            data={data}
            layout="vertical"
            margin={{ top: 20, right: 30, left: 20, bottom: 5 }}
          >
            <XAxis type="number" />
            <YAxis type="category" dataKey="name" />
            <Tooltip />
            <Legend />
            <Bar dataKey="Finalizate" stackId="a" fill="#10b981" />{" "}
            {/* green */}
            <Bar dataKey="ÎnAșteptare" stackId="a" fill="#f59e0b" />{" "}
            {/* amber */}
          </BarChart>
        </ResponsiveContainer>

        <ResponsiveContainer width="100%" height={350}>
          <BarChart
            data={routesData}
            margin={{ top: 20, right: 30, left: 20, bottom: 5 }}
          >
            <XAxis
              dataKey="id"
              label={{
                value: "Route ID",
                position: "insideBottomRight",
                offset: 0,
              }}
            />
            <YAxis
              label={{ value: "Value", angle: -90, position: "insideLeft" }}
            />
            <Tooltip />
            <Legend />
            <Bar dataKey="distance" fill="#3b82f6" name="Distance (km)" />
            <Bar dataKey="value" fill="#10b981" name="Route Value" />
            <ReferenceLine
              y={report?.averageDistance ?? 0}
              stroke="red"
              strokeDasharray="3 3"
              label={{
                value: `Avg Distance (${report?.averageDistance ?? 0})`,
                position: "insideTopRight",
                fill: "#db2777",
              }}
            />
          </BarChart>
        </ResponsiveContainer>
        <button onClick={onClose}>Close</button>
      </div>
    </div>
  );
};
