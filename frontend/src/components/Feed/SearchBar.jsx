import React, { useState } from "react";
import { useNavigate } from "react-router-dom"; // Import useNavigate
import axios from "axios";

function SearchBar() {
  const [searchQuery, setSearchQuery] = useState("");
  const [searchType, setSearchType] = useState("username");
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate(); // Initialize the navigate hook

  const handleSearch = async () => {
    // Validate the search input
    if (!searchQuery) {
      setError("Please enter a search query");
      return;
    }
    setError("");  // Clear any previous error
    setLoading(true);  // Set loading state

    const endpoint =
      searchType === "username"
        ? `http://localhost:8080/search/username/${encodeURIComponent(searchQuery)}`
        : `http://localhost:8080/search/email/${encodeURIComponent(searchQuery)}`;

    try {
      const response = await axios.get(endpoint, {
        headers: {
          "Content-Type": "application/json",
          "Accept": "application/json",
        },
      });

      setResults(response.data); // Set the results from the backend
      console.log(response.data);

    } catch (error) {
      console.error("Error searching:", error);
      setResults([]); // Clear results on error
      setError(error.response ? error.response.data : "Failed to fetch results"); // Display error message
    } finally {
      setLoading(false);  // Reset loading state
    }
  };

  // Navigate to the user profile page when clicked
  const handleUserClick = (id) => {
  };

  return (
    <div style={styles.container}>
      <div style={styles.searchBox}>
        <div style={styles.inputWrapper}>
          <svg style={styles.icon} width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
            <circle cx="11" cy="11" r="8"></circle>
            <path d="m21 21-4.3-4.3"></path>
          </svg>
          <input
            type="text"
            placeholder="Search by username or email..."
            style={styles.input}
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
          />
        </div>

        <select
          style={styles.select}
          value={searchType}
          onChange={(e) => setSearchType(e.target.value)}
        >
          <option value="username">Username</option>
          <option value="email">Email</option>
        </select>

        <button
          style={styles.button}
          onClick={handleSearch}
          disabled={loading}
        >
          {loading ? "Searching..." : "Search"}
        </button>
      </div>

      {error && <div style={styles.error}>{error}</div>}

      <div style={styles.resultsContainer}>
        {results.length > 0 ? (
          results.map((result) => (
            <div key={result.id} style={styles.resultItem} onClick={() => handleUserClick(result.id)}>
              <p><strong>Username:</strong> {result.username}</p>
              <p><strong>Email:</strong> {result.email}</p>
            </div>
          ))
        ) : (
          <p style={styles.noResults}>No results found.</p>
        )}
      </div>
    </div>
  );
}

const styles = {
  container: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    width: '100%',
    padding: '40px 20px',
    backgroundColor: '#f9f9f9',
    minHeight: '100vh',
    boxSizing: 'border-box',
  },
  searchBox: {
    display: 'flex',
    alignItems: 'center',
    width: '100%',
    maxWidth: '800px',
    padding: '20px',
    border: '1px solid #ddd',
    borderRadius: '8px',
    backgroundColor: '#fff',
    boxShadow: '0 4px 12px rgba(0, 0, 0, 0.1)',
    marginBottom: '40px',
    transition: 'box-shadow 0.3s ease',
  },
  inputWrapper: {
    display: 'flex',
    alignItems: 'center',
    flex: 1,
    paddingRight: '15px',
    borderRight: '1px solid #ddd',
  },
  icon: {
    marginRight: '15px',
    color: '#888',
  },
  input: {
    width: '80%',  // Increased width
    padding: '14px 20px',  // Increased padding for a larger field
    fontSize: '17px',
    border: '1px solid #ddd',
    borderRadius: '5px',
    outline: 'none',
    backgroundColor: '#fafafa',
    transition: 'border-color 0.3s ease',
  },
  inputFocus: {
    borderColor: '#007bff',
  },
  select: {
    padding: '10px 15px',  // Smaller padding
    fontSize: '16px',  // Slightly smaller font size
    marginRight: '20px',
    maxWidth: '190px',
    border: '1px solid #ddd',
    borderRadius: '5px',
    backgroundColor: '#fff',
    marginLeft: '20px',
    transition: 'border-color 0.3s ease',
    minWidth: '100px',  // Smaller width
  },
  button: {
    padding: '12px 25px',
    fontSize: '17px',
    cursor: 'pointer',
    backgroundColor: '#007bff',
    color: '#fff',
    border: 'none',
    borderRadius: '5px',
    transition: 'background-color 0.3s, transform 0.2s ease-in-out',
    outline: 'none',
    minWidth: '120px',
  },
  buttonDisabled: {
    backgroundColor: '#c0c0c0',
    cursor: 'not-allowed',
  },
  error: {
    color: 'red',
    fontSize: '15px',
    marginTop: '15px',
    fontWeight: '500',
  },
  resultsContainer: {
    width: '100%',
    maxWidth: '800px',
    marginTop: '30px',
  },
  resultItem: {
    padding: '18px',
    marginBottom: '20px',
    border: '1px solid #eee',
    borderRadius: '6px',
    backgroundColor: '#f9f9f9',
    boxShadow: '0 1px 6px rgba(0, 0, 0, 0.1)',
    fontSize: '16px',
  },
  noResults: {
    textAlign: 'center',
    color: '#888',
    fontSize: '17px',
    fontStyle: 'italic',
  },
};

export default SearchBar;