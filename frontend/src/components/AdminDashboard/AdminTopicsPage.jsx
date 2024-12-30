import React, { useState, useEffect } from "react";
import axios from "axios";
import Sidebar from "../Sidebar/Sidebar"; // Import Sidebar
import "./AdminTopicsPage.css";

const AdminTopicsPage = () => {
  const [topics, setTopics] = useState([]); // List of topics
  const [newTopic, setNewTopic] = useState(""); // New topic input
  const [error, setError] = useState(""); // Error messages
  const [isModalOpen, setIsModalOpen] = useState(false); // Modal visibility
  const [topicToDelete, setTopicToDelete] = useState(null); // Topic selected for deletion

  // Predefined topic names for which deletion is forbidden
  const forbiddenTopics = [
    "Sport",
    "Technology",
    "Health",
    "Religion",
    "Troll",
    "Politics",
    "Personal",
  ];

  // Get the token (replace with your actual method for fetching tokens)
  const token = localStorage.getItem("jwttoken");

  // Fetch topics from backend
  useEffect(() => {
    getTopics();
  }, [token]);

  const getTopics = () => {
    axios
      .get("http://localhost:8080/admin/topics", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        setTopics(response.data); // Assuming response.data is an array of topics
      })
      .catch(() => {
        setError("Failed to fetch topics");
        setTimeout(() => setError(""), 3000); // Clear error after 3 seconds
      });
  };

  // Add a new topic
  const handleAddTopic = () => {
    if (!newTopic.trim()) {
      setError("Topic name cannot be empty");
      setTimeout(() => setError(""), 3000); // Clear error after 3 seconds
      return;
    }
    axios
      .post(
        "http://localhost:8080/admin/topics/add",
        { topic: newTopic },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )
      .then((response) => {
        getTopics(); // Fetch topics again
        setNewTopic(""); // Clear input
        setError(""); // Clear error
      })
      .catch((error) => {
        setError(error.response?.data || "Failed to add topic");
        setNewTopic(""); // Clear input
        setTimeout(() => setError(""), 3000); // Clear error after 3 seconds
      });
  };

  // Open the delete confirmation modal
  const openDeleteModal = (id) => {
    setTopicToDelete(id);
    setIsModalOpen(true);
  };

  // Close the delete confirmation modal
  const closeDeleteModal = () => {
    setIsModalOpen(false);
    setTopicToDelete(null);
  };

  // Delete a topic
  const handleDeleteTopic = () => {
    if (topicToDelete === null) return;

    axios
      .delete(`http://localhost:8080/admin/topics/delete/${topicToDelete}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then(() => {
        setTopics(topics.filter((topic) => topic.id !== topicToDelete)); // Remove topic from the list
        closeDeleteModal(); // Close the modal after deletion
      })
      .catch((error) => {
        setError(error.response?.data || "Failed to delete topic");
        setTimeout(() => setError(""), 3000); // Clear error after 3 seconds
      });
  };

  return (
    <div className="admin-topics-page">
      {/* Sidebar */}
      <Sidebar />

      {/* Main content */}
      <div className="topics-content">
        <h2>Manage Topics</h2>

        {/* Error Message */}
        {error && <div className="error-message">{error}</div>}

        {/* Add New Topic */}
        <div className="add-topic-section">
          <input
            type="text"
            placeholder="Enter topic name"
            value={newTopic}
            onChange={(e) => setNewTopic(e.target.value)}
          />
          <button onClick={handleAddTopic}>Add Topic</button>
        </div>

        {/* Topics List */}
        <div className="topics-list">
          {topics.map((topic) => (
            <div key={topic.id} className="topic-item">
              <span>{topic.name}</span>
              {!forbiddenTopics.includes(topic.name) && ( // Check if the topic is not in forbidden list
                <button onClick={() => openDeleteModal(topic.id)}>
                  Delete
                </button>
              )}
            </div>
          ))}
        </div>
      </div>

      {/* Confirmation Modal */}
      {isModalOpen && (
        <div className="modal-overlay">
          <div className="modal-content">
            <button className="modal-close" onClick={closeDeleteModal}>X</button>
            <h3>Are you sure you want to delete this topic?</h3>
            <div className="modal-buttons">
              <button className="modal-cancel" onClick={closeDeleteModal}>Cancel</button>
              <button className="modal-delete" onClick={handleDeleteTopic}>Delete</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default AdminTopicsPage;
