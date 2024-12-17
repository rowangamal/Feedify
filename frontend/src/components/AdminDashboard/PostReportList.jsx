import React, { useState, useEffect } from "react";
import "./PostReportList.css"; // Import your custom CSS file
import PostCard from "../Feed/PostCard"; // Adjust the import path based on your project structure

const PostReportList = () => {
  const [reports, setReports] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [confirmAction, setConfirmAction] = useState({ show: false, type: "", reportID: null });
  const [viewPostDetails, setViewPostDetails] = useState({ show: false, post: null });

  useEffect(() => {
    fetchReports();
  }, []);

  const fetchReports = async () => {
    try {
      const token = localStorage.getItem("jwttoken");
      const response = await fetch(
        "http://localhost:8080/admin/report/post/all",
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
          ? `http://localhost:8080/admin/report/post/approve/${confirmAction.reportID}`
          : `http://localhost:8080/admin/report/post/deny/${confirmAction.reportID}`;

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

  const handleViewPost = async (postID) => {
    try {
      const token = localStorage.getItem("jwttoken");
      const response = await fetch(
        `http://localhost:8080/post/${postID}`,
        {
          method: "GET",
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      if (!response.ok) {
        throw new Error("Failed to fetch post details");
      }
      const post = await response.json();
      setViewPostDetails({ show: true, post });
    } catch (err) {
      alert(err.message);
    }
  };

  const closePostDetails = () => {
    setViewPostDetails({ show: false, post: null });
  };

  if (loading) return <div className="loading">Loading reports...</div>;
  if (error) return <div className="error">Error: {error}</div>;

  return (
    <div className="report-list-container">
      <h1>Reported Posts</h1>
      {reports.length === 0 ? (
        <div className="no-reports">No reports available.</div>
      ) : (
        <div className="report-list">
          {reports.map((report) => (
            <div className="report-card" key={report.reportID}>
              <div className="report-details">
                <p><strong>Report ID:</strong> {report.reportID}</p>
                <p><strong>Post ID:</strong> {report.postID}</p>
                <p><strong>Reporter Email:</strong> {report.email}</p>
                <p><strong>Reason:</strong> {report.reason}</p>
                <p><strong>Time:</strong> {new Date(report.reportTime).toLocaleString()}</p>
              </div>
              <div className="report-actions">
                <button
                  className="view-post-btn"
                  onClick={() => handleViewPost(report.postID)}
                >
                  View Post
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

      {/* Post Details Modal */}
      {viewPostDetails.show && (
        <>
          <div className="modal-overlay" onClick={closePostDetails}></div> {/* Background overlay */}
          <div className="post-details-modal">
            <div className="modal-content">
              <button className="close-btn" onClick={closePostDetails}>
                &times;
              </button>
              {viewPostDetails.post && (
                <PostCard
                  username={
                    <span className="user-name" title={viewPostDetails.post.username}>
                      {viewPostDetails.post.username}
                    </span>
                  }
                  avatar={viewPostDetails.post.avatar}
                  content={viewPostDetails.post.content}
                  timestamp={new Date(viewPostDetails.post.createdAt).toLocaleString()}
                />
              )}
            </div>
          </div>
        </>
      )}


    </div>
  );
};

export default PostReportList;
