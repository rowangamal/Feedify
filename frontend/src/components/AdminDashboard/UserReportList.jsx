import React, { useState, useEffect } from "react";
import "./UserReportList.css";

const UserReportList = () => {
  const [reports, setReports] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [confirmAction, setConfirmAction] = useState({ show: false, type: "", reportID: null });

  useEffect(() => {
    fetchReports();
  }, []);

  const fetchReports = async () => {
    try {
      const token = localStorage.getItem("jwttoken");
      const response = await fetch(
        "http://localhost:8080/admin/report/user/all",
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );
      if (!response.ok) {
        throw new Error("Failed to fetch reports");
      }
      const data = await response.json();
      setReports(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleApprove = (reportID) => {
    setConfirmAction({ show: true, type: "approve", reportID });
  };

  const handleDeny = (reportID) => {
    setConfirmAction({ show: true, type: "deny", reportID });
  };

  const confirmActionHandler = async (confirmed) => {
    if (!confirmed) {
      setConfirmAction({ show: false, type: "", reportID: null });
      return;
    }

    try {
      const token = localStorage.getItem("jwttoken");
      const endpoint =
        confirmAction.type === "approve"
          ? `http://localhost:8080/admin/report/user/approve/${confirmAction.reportID}`
          : `http://localhost:8080/admin/report/user/deny/${confirmAction.reportID}`;

      const response = await fetch(endpoint, {
        method: "DELETE",
        headers: { Authorization: `Bearer ${token}` },
      });

      if (!response.ok) {
        throw new Error(`Failed to ${confirmAction.type} report`);
      } else {
        fetchReports();
      }

      setConfirmAction({ show: false, type: "", reportID: null });

    } catch (err) {
      alert(err.message);
    }
  };

  const handleViewProfile = async (userID) => {
    try {
      const token = localStorage.getItem("jwttoken");
      const response = await fetch(`http://localhost:8080/api/users/${userID}`, {
        method: "GET",
        headers: { Authorization: `Bearer ${token}` },
      });
      if (!response.ok) {
        throw new Error("Failed to fetch user profile");
      }
      const user = await response.json();
      alert(
        `User Details:\nName: ${user.name}\nEmail: ${user.email}\nRole: ${user.role}`
      );
    } catch (err) {
      alert(err.message);
    }
  };

  if (loading) return <div className="loading">Loading reports...</div>;
  if (error) return <div className="error">Error: {error}</div>;

  return (
    <div className="report-list-container">
      <h1>Reported Users</h1>
      {reports.length === 0 ? (
        <div className="no-reports">No reports available.</div>
      ) : (
        <div className="report-list">
          {reports.map((report) => (
            <div className="report-card" key={report.reportID}>
              <div className="report-details">
                <p><strong>Report ID:</strong> {report.reportID}</p>
                <p><strong>Reporter Email:</strong> {report.emailReporter}</p>
                <p><strong>Reported Email:</strong> {report.emailReported}</p>
                <p><strong>Reason:</strong> {report.reason}</p>
                <p><strong>Time:</strong> {new Date(report.reportTime).toLocaleString()}</p>
              </div>
              <div className="report-actions">
                <button
                  className="view-profile-btn"
                  onClick={() => handleViewProfile(report.reportedID)}
                >
                  View Profile
                </button>
                <button
                  className="approve-btn"
                  onClick={() => handleApprove(report.reportID)}
                >
                  Approve
                </button>
                <button
                  className="deny-btn"
                  onClick={() => handleDeny(report.reportID)}
                >
                  Deny
                </button>
              </div>
            </div>
          ))}
        </div>
      )}

      {/* Confirmation Dialog */}
      {confirmAction.show && (
        <div className="confirmation-dialog">
          <div className="dialog-header">
            <span className="close-btn" onClick={() => setConfirmAction({ show: false, type: "", reportID: null })}>
              &times;
            </span>
          </div>
          <p>Are you sure you want to {confirmAction.type} this report?</p>
          <div className="dialog-actions">
            <button className="yes-btn" onClick={() => confirmActionHandler(true)}>
              Yes
            </button>
            <button className="no-btn" onClick={() => confirmActionHandler(false)}>
              No
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default UserReportList;
