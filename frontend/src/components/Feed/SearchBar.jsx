import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { FaUserAlt, FaTimes } from "react-icons/fa";

function SearchBar() {
  const [searchQuery, setSearchQuery] = useState("");
  const [searchCategory, setSearchCategory] = useState("Username");
  const [searchResults, setSearchResults] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");
  const [isModalOpen, setIsModalOpen] = useState(false);
  const navigate = useNavigate();

  const token = localStorage.getItem("jwttoken");

  const handleSearch = async () => {
    let url = "";
    if (searchCategory === "Username") {
      url = `http://localhost:8080/search/username/${searchQuery}`;
    } else if (searchCategory === "Email") {
      url = `http://localhost:8080/search/email/${searchQuery}`;
    }

    try {
      const response = await fetch(url, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      const data = await response.json();
      console.log(data)

      if (data && data.length > 0) {
        setSearchResults(data);
        setErrorMessage("");
        setIsModalOpen(true);
      } else {
        setSearchResults([]);
        setErrorMessage("No users found");
      }
    } catch (error) {
      console.error("Error fetching search results:", error);
      setErrorMessage("An error occurred while fetching the results.");
    }
  };

  const handleProfileRedirect = (username) => {
    navigate(`/profile/${username}`);
    console.log(username);
    setIsModalOpen(false);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  return (
    <div className="search-bar" style={{ position: "relative", padding: "20px" }}>
      <div className="search-input-wrapper" style={{ position: "relative" }}>
        <svg
          className="search-icon"
          width="20"
          height="20"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          strokeWidth="2"
          strokeLinecap="round"
          strokeLinejoin="round"
          style={{
            position: "absolute",
            left: "12px",
            top: "50%",
            transform: "translateY(-50%)",
            zIndex: "2",
          }}
        >
          <circle cx="11" cy="11" r="8"></circle>
          <path d="m21 21-4.3-4.3"></path>
        </svg>
        <input
          type="text"
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
          placeholder="Search Username or Email"
          className="search-input"
          style={{
            padding: "14px 40px 14px 40px",
            borderRadius: "15px",
            border: "1px solid #ccc",
            width: "200px",
            fontSize: "16px",
            fontWeight: "500",
            backgroundColor: "#f7f7f8",
            transition: "all 0.3s ease",
          }}
        />
      </div>
      <select
        className="category-select"
        value={searchCategory}
        onChange={(e) => setSearchCategory(e.target.value)}
        style={{
          padding: "12px",
          borderRadius: "15px",
          border: "1px solid #ccc",
          marginLeft: "15px",
          fontSize: "14px",
          cursor: "pointer",
          backgroundColor: "#f7f7f8",
          transition: "all 0.3s ease",
        }}
      >
        <option>Username</option>
        <option>Email</option>
      </select>
      <button
        className="search-button"
        onClick={handleSearch}
        style={{
          padding: "12px 18px",
          backgroundColor: "#007bff",
          color: "#fff",
          borderRadius: "20px",
          border: "none",
          cursor: "pointer",
          marginLeft: "15px",
          fontSize: "16px",
          transition: "background-color 0.3s ease, transform 0.2s",
        }}
        onMouseOver={(e) => (e.target.style.backgroundColor = "#0056b3")}
        onMouseOut={(e) => (e.target.style.backgroundColor = "#007bff")}
      >
        Search
      </button>
      {isModalOpen && (
        <div
          style={{
            position: "fixed",
            top: "0",
            left: "0",
            right: "0",
            bottom: "0",
            backgroundColor: "rgba(0, 0, 0, 0.3)",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            zIndex: "1000",
          }}
        >
          <div
            style={{
              backgroundColor: "#fff",
              padding: "20px",
              borderRadius: "20px",
              width: "80%",
              maxWidth: "800px",
              boxShadow: "0 6px 12px rgba(0, 0, 0, 0.1)",
              overflowY: "auto",
              position: "relative",
            }}
          >
            <FaTimes
              onClick={closeModal}
              style={{
                position: "absolute",
                top: "10px",
                right: "10px",
                fontSize: "20px",
                color: "#999",
                cursor: "pointer",
              }}
            />
            <h2
              style={{
                fontSize: "18px",
                marginBottom: "15px",
                fontWeight: "600",
                color: "#333",
              }}
            >
              Search Results
            </h2>
            {errorMessage && (
              <p
                style={{
                  color: "red",
                  marginTop: "10px",
                  fontSize: "14px",
                  fontWeight: "bold",
                }}
              >
                {errorMessage}
              </p>
            )}
            {searchResults.length > 0 && (
              <ul
                style={{
                  listStyleType: "none",
                  padding: "0",
                  margin: "0",
                }}
              >
                {searchResults.map((user) => (
                  <li
                    key={user.id}
                    style={{
                      padding: "15px",
                      borderBottom: "1px solid #eee",
                      display: "flex",
                      justifyContent: "space-between",
                      alignItems: "center",
                      transition: "background-color 0.3s ease",
                      cursor: "pointer",
                    }}
                  >
                    <div style={{ fontSize: "14px", fontWeight: "500", color: "#333" }}>
                      <strong>{user.username}</strong> ({user.email})
                    </div>
                    <FaUserAlt
                      onClick={() => handleProfileRedirect(user.username)}
                      style={{
                        color: "#ff6f61",
                        fontSize: "18px",
                        cursor: "pointer",
                        transition: "color 0.3s ease",
                      }}
                    />
                  </li>
                ))}
              </ul>
            )}
          </div>
        </div>
      )}
    </div>
  );
}

export default SearchBar;