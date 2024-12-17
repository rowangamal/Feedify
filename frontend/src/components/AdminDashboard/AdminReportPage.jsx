import React, { useState } from "react";
import UserReportList from "./UserReportList"; 
import PostReportList from "./PostReportList"; 
import "./AdminReportPage.css"; 
import Sidebar from "../Sidebar/Sidebar"; 

const AdminReportPage = () => {
  const [activeTab, setActiveTab] = useState("userReports"); // Default tab

  const renderTabContent = () => {
    switch (activeTab) {
      case "userReports":
        return <UserReportList />;
      case "postReports":
        return <PostReportList />;
      default:
        return null;
    }
  };

  return (
    <div className="admin-report-page">
      {/* Sidebar */}
      <Sidebar />

      {/* Main content */}
      <div className="admin-report-main">
        <div className="tab-container">
          <button
            className={`tab-button ${activeTab === "userReports" ? "active" : ""}`}
            onClick={() => setActiveTab("userReports")}
          >
            User Reports
          </button>
          <button
            className={`tab-button ${activeTab === "postReports" ? "active" : ""}`}
            onClick={() => setActiveTab("postReports")}
          >
            Post Reports
          </button>
        </div>
        <div className="tab-content">{renderTabContent()}</div>
      </div>
    </div>
  );
};

export default AdminReportPage;
