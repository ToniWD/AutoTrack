import UserProfile from "./UserProfile.tsx";
import SidebarMenu from "./SideBar.tsx";
import "../styles/HomePage.css";
import "../styles/Scrollbar.css";
import { DriversStatusPanel } from "./DriverPanel.tsx";
import { FleetStatusPanel } from "./TruckPanel.tsx";
import { ActiveRoutesPanel } from "./ActiveRoutesPanel.tsx";

const HomePage = () => {
  return (
    <div className="homepage-container">
      {/* Sidebar / Control Panel */}
      <aside className="sidebar">
        <UserProfile />
        <div className="user-controls">
          <h2 className="navigation-title">Navigation</h2>
          <div className="sidebar-menu">
            <SidebarMenu />
          </div>
        </div>
      </aside>

      {/* Main Content Panel */}
      <main className="main-content">
        <ActiveRoutesPanel />

        <DriversStatusPanel />

        <FleetStatusPanel />
      </main>
    </div>
  );
};

export default HomePage;
