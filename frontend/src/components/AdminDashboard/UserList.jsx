import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '@fortawesome/fontawesome-free/css/all.min.css';

const UserList = () => {
  const [users, setUsers] = useState([]);
  const [sortOrder, setSortOrder] = useState('asc');
  const [showConfirmation, setShowConfirmation] = useState(false);
  const [userToPromote, setUserToPromote] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');


  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const token = localStorage.getItem('jwttoken');
        if (!token) {
          console.error('No token found. Please log in.');
          return;
        }

        const response = await axios.get('http://localhost:8080/fetch/users',
          { headers: { Authorization: `Bearer ${token}` } }
        );
        setUsers(response.data); 
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    };

    fetchUsers();
  }, []); 

  const handlePromote = (id) => {
    setUserToPromote(id);
    setShowConfirmation(true);
  };
  
  const confirmPromote = async () => {
    try {
      const token = localStorage.getItem('jwttoken');
      if (!token) {
        console.error('No token found. Please log in.');
        return;
      }

      const data = { userId: userToPromote };  
      const response = await axios.post(
        'http://localhost:8080/fetch/promote',
        data,
        { headers: { Authorization: `Bearer ${token}` } }
      );
  
      if (response.status === 200) {
        const usersResponse = await axios.get('http://localhost:8080/fetch/users', {
          headers: { Authorization: `Bearer ${token}` }
        });
        setUsers(usersResponse.data);
        setShowConfirmation(false);
        setUserToPromote(null);
      } else {
        console.error('Failed to promote the user');
      }
    } catch (error) {
      console.error('Error promoting user:', error);
    }
  };
  
  

  const cancelPromote = () => {
    setShowConfirmation(false);
    setUserToPromote(null);
  };

  const sortUsers = (field) => {
    const sortedUsers = [...users].sort((a, b) => {
      if (field === 'id') {
        return sortOrder === 'asc' ? a.id - b.id : b.id - a.id;
      } else {
        return sortOrder === 'asc'
          ? a.email.localeCompare(b.email)
          : b.email.localeCompare(a.email);
      }
    });
    setUsers(sortedUsers);
    setSortOrder(sortOrder === 'asc' ? 'desc' : 'asc');
  };

  const filteredUsers = users.filter((user) =>
    user.email.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (

    <div style={styles.container}>
      <h2 style={styles.title}>User Management</h2>

      <input
        type="text"
        placeholder="Search by email..."
        style={styles.searchInput}
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
      />

      <div style={styles.sortControls}>
        <button style={styles.sortButton} onClick={() => sortUsers('id')}>
          <i className="fas fa-sort-numeric-up" /> sort by ID
        </button>
        <button style={styles.sortButton} onClick={() => sortUsers('email')}>
          <i className="fas fa-sort-alpha-up" /> sort by Email
        </button>
      </div>



      <table style={styles.table}>
        <thead>
          <tr>
            <th style={styles.tableHeader}>ID</th>
            <th style={styles.tableHeader}>Email</th>
            <th style={styles.tableHeader}>Action</th>
          </tr>
        </thead>
        <tbody>
          {filteredUsers.map((user) => (
            <tr key={user.id} style={styles.tableRow}>
              <td style={styles.tableCell}>{user.id}</td>
              <td style={styles.tableCell}>{user.email}</td>
              <td style={styles.tableCell}>
                <button
                  style={styles.promoteButton}
                  onClick={() => handlePromote(user.id)}
                >
                  Promote
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {showConfirmation && (
        <div style={styles.confirmationDialog}>
          <p style={styles.confirmationText}>
            Are you sure you want to promote this user to admin?
          </p>
          <div>
            <button style={styles.confirmButton} onClick={confirmPromote}>
              Yes
            </button>
            <button style={styles.cancelButton} onClick={cancelPromote}>
              No
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

const styles = {
  container: {
    width: '100%',
    maxWidth: '900px',
    margin: '0 auto',
    padding: '40px',
    backgroundColor: '#fff',
    borderRadius: '20px',
    border: '1px solid #ddd',
    textAlign: 'center',
  },
  title: {
    fontSize: '2.4rem',
    color: '#333',
    marginBottom: '30px',
    fontWeight: '600',
    textAlign: 'left',
    width: '100%',
  },
  searchInput: {
    padding: '12px',
    marginBottom: '20px',
    width: '100%',
    maxWidth: '500px',
    borderRadius: '12px',
    fontSize: '1.1rem',
    border: '1px solid #ddd',
    outline: 'none',
    transition: 'border-color 0.3s ease',
  },
  searchInputFocus: {
    borderColor: '#007bff', 
  },
  sortControls: {
    marginBottom: '20px',
  },
  sortButton: {
    padding: '10px 20px',
    backgroundColor: '#ff6f61',
    color: '#fff',
    border: 'none',
    borderRadius: '12px',
    fontSize: '1rem',
    cursor: 'pointer',
    marginRight: '10px',
    transition: 'background-color 0.3s ease, transform 0.3s ease',
  },
  sortButtonHover: {
    transform: 'scale(1.05)',
  },
  table: {
    width: '100%',
    borderCollapse: 'collapse',
    marginTop: '20px',
  },
  tableRow: {
    transition: 'background-color 0.3s ease',
  },
  tableHeader: {
    padding: '18px',
    textAlign: 'left',
    borderBottom: '1px solid #eee',
    fontSize: '1.2rem',
    fontWeight: '600',
    color: '#666',
  },
  tableCell: {
    padding: '18px',
    textAlign: 'left',
    borderBottom: '1px solid #eee',
    fontSize: '1.2rem',
    color: '#333',
  },
  promoteButton: {
    backgroundColor: '#28a745',
    color: '#fff',
    border: 'none',
    padding: '12px 30px',
    borderRadius: '12px',
    fontSize: '1.1rem',
    cursor: 'pointer',
    transition: 'background-color 0.3s ease, transform 0.3s ease',
  },
  promoteButtonHover: {
    backgroundColor: '#218838',
    transform: 'scale(1.05)',
  },
  removeButton: {
    backgroundColor: '#dc3545',
    color: '#fff',
    border: 'none',
    padding: '12px 30px',
    borderRadius: '12px',
    fontSize: '1.1rem',
    cursor: 'pointer',
    transition: 'background-color 0.3s ease, transform 0.3s ease', 
  },
  removeButtonHover: {
    backgroundColor: '#c82333',
    transform: 'scale(1.05)',
  },
  confirmationDialog: {
    position: 'fixed',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    backgroundColor: '#fff',
    padding: '20px',
    borderRadius: '10px',
    border: '1px solid #ddd', 
    animation: 'fadeIn 0.5s ease-in-out',
  },
  confirmationText: {
    fontSize: '1.2rem',
    marginBottom: '20px',
    textAlign: 'center',
  },
  confirmButton: {
    backgroundColor: '#28a745',
    color: '#fff',
    padding: '10px 20px',
    border: 'none',
    borderRadius: '12px',
    fontSize: '1rem',
    cursor: 'pointer',
    marginRight: '10px',
    transition: 'background-color 0.3s ease',
  },
  cancelButton: {
    backgroundColor: '#dc3545',
    color: '#fff',
    padding: '10px 20px',
    border: 'none',
    borderRadius: '12px',
    fontSize: '1rem',
    cursor: 'pointer',
    transition: 'background-color 0.3s ease',
  },
};

const fadeInKeyframes = `
  @keyframes fadeIn {
    0% { opacity: 0; }
    100% { opacity: 1; }
  }
`;

export default UserList;
