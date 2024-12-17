import React, { useState } from "react";
import UserReportList from "./UserReportList"; // Import the user reports component
import PostReportList from "./PostReportList"; // Import the post reports component
import "./AdminReportPage.css"; // Custom styling for the tabs and layout

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
  );
};

export default AdminReportPage;
