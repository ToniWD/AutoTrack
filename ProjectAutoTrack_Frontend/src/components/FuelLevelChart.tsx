import {
  RadialBarChart,
  RadialBar,
  PolarAngleAxis,
  ResponsiveContainer,
} from "recharts";

// const data = [
//     {
//         name: 'Fuel Level',
//         value: 20,
//         fill: '#34d399', // verde
//     },
// ];

type FuelLevelData = {
  name: string;
  value: number;
  fill: string;
};

type FuelLevelProps = {
  data: FuelLevelData[];
};

const FuelLevelChart = ({ data }: FuelLevelProps) => {
  const value = data?.[0]?.value ?? 0;

  return (
    <div
      style={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        fontSize: 16,
        fontWeight: "bold",
      }}
    >
      Fuel Level
      <div
        className="fuel-level"
        style={{
          width: 100,
          height: 100,
          position: "relative",
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          justifyContent: "center",
        }}
      >
        <ResponsiveContainer width="100%" height="100%">
          <RadialBarChart
            cx="50%"
            cy="50%"
            innerRadius="70%"
            outerRadius="100%"
            barSize={10}
            data={data}
            startAngle={90}
            endAngle={-270}
          >
            <PolarAngleAxis
              type="number"
              domain={[0, 100]}
              angleAxisId={0}
              tick={false}
            />
            <RadialBar background dataKey="value" cornerRadius={10} />
          </RadialBarChart>
        </ResponsiveContainer>
        <div
          style={{
            position: "absolute",
            fontSize: 16,
            fontWeight: "bold",
          }}
        >
          {value}%
        </div>
      </div>
    </div>
  );
};

// const FuelLevelChart = ({data}: FuelLevelProps) => {

//     return (
//         <div className={"fuel-level"} style={{ width: 50, height: 50 }}>
//             <ResponsiveContainer>
//                 <RadialBarChart
//                     cx="50%"
//                     cy="50%"
//                     innerRadius="70%"
//                     outerRadius="100%"
//                     barSize={20}
//                     data={data}
//                     startAngle={90}
//                     endAngle={-270}
//                 >
//                     <PolarAngleAxis type="number" domain={[0, 100]} angleAxisId={0} tick={false} />
//                     <RadialBar dataKey="value" background />
//                 </RadialBarChart>
//             </ResponsiveContainer>
//         </div>
//     );
// };

export default FuelLevelChart;
