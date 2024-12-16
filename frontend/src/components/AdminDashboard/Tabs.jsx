import React, { useState } from 'react';
import UserList from './UserList'; 
import AdminList from './AdminList';
import Sidebar from '../Sidebar/Sidebar';

const Tabs = () => {
  const [activeTab, setActiveTab] = useState('users');
  const [transitioning, setTransitioning] = useState(false);

  const handleTabSwitch = (tab) => {
    if (activeTab !== tab) {
      setTransitioning(true);
      setTimeout(() => {
        setActiveTab(tab);
        setTransitioning(false);
      }, 300);
    }
  };

  return (
    <div style={styles.container}>
      <Sidebar /> 
      <div style={styles.tabsContainer}>
        <div style={styles.tabButtons}>
          <button
            style={activeTab === 'users' ? { ...styles.tabButton, ...styles.activeTab } : styles.tabButton}
            onClick={() => handleTabSwitch('users')}
          >
            User List
          </button>
          <button
            style={activeTab === 'admins' ? { ...styles.tabButton, ...styles.activeTab } : styles.tabButton}
            onClick={() => handleTabSwitch('admins')}
          >
            Admin List
          </button>
        </div>

        <div style={styles.tabContentContainer}>
          <div
            style={{
              ...styles.tabContent,
              opacity: transitioning ? 0 : 1,
              transition: 'opacity 0.3s ease, transform 0.3s ease',
              transform: transitioning ? 'translateY(20px)' : 'translateY(0px)',
            }}
          >
            {activeTab === 'users' ? (
              <UserList />
            ) : (
              <AdminList />
            )}
          </div>
        </div>
      </div>
    </div>
  );
};
const styles = {
  container: {
    display: 'flex',
    height: '100vh',
    backgroundColor: '#fff',
    justifyContent: 'flex-start',
    alignItems: 'flex-start',
  },
  tabsContainer: {
    width: '100%',
    padding: '30px',
    textAlign: 'center',
    transition: 'transform 0.3s ease',
  },
  tabButtons: {
    display: 'flex',
    justifyContent: 'center',
    gap: '20px',
    marginBottom: '30px',
  },
  tabButton: {
    padding: '12px 24px',
    backgroundColor: '#202020',
    color: '#fff',
    border: 'none',
    borderRadius: '8px',
    cursor: 'pointer',
    fontSize: '1.1rem',
    fontWeight: '500',
    transition: 'background-color 0.3s, transform 0.3s',
  },
  tabButtonHover: {
    backgroundColor: '#444',
    transform: 'scale(1.05)',
  },
  activeTab: {
    backgroundColor: '#10ad10',
    transform: 'scale(1.1)',
  },
  tabContentContainer: {
    position: 'relative',
    overflow: 'hidden',
  },
  tabContent: {
    marginTop: '30px',
    fontSize: '1.2rem',
  },
};

export default Tabs;
