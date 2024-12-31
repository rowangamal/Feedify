import { useEffect, useState } from 'react';
import  Logo  from './Logo';
import SidebarLink from '../Sidebar/SidebarLink';
import CreatePost from '../CreatePost';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUserShield, faUsers } from '@fortawesome/free-solid-svg-icons';
import '../../styles/Sidebar.css';
import { isAdmin } from '../../services/api';
import axios from 'axios';

const icons = {
  home: <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="m3 9 9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path><polyline points="9 22 9 12 15 12 15 22"></polyline></svg>,
  user: <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"></path><circle cx="12" cy="7" r="4"></circle></svg>,
  bell: <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M6 8a6 6 0 0 1 12 0c0 7 3 9 3 9H3s3-2 3-9"></path><path d="M10.3 21a1.94 1.94 0 0 0 3.4 0"></path></svg>,
  settings: <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M12.22 2h-.44a2 2 0 0 0-2 2v.18a2 2 0 0 1-1 1.73l-.43.25a2 2 0 0 1-2 0l-.15-.08a2 2 0 0 0-2.73.73l-.22.38a2 2 0 0 0 .73 2.73l.15.1a2 2 0 0 1 1 1.72v.51a2 2 0 0 1-1 1.74l-.15.09a2 2 0 0 0-.73 2.73l.22.38a2 2 0 0 0 2.73.73l.15-.08a2 2 0 0 1 2 0l.43.25a2 2 0 0 1 1 1.73V20a2 2 0 0 0 2 2h.44a2 2 0 0 0 2-2v-.18a2 2 0 0 1 1-1.73l.43-.25a2 2 0 0 1 2 0l.15.08a2 2 0 0 0 2.73-.73l.22-.39a2 2 0 0 0-.73-2.73l-.15-.08a2 2 0 0 1-1-1.74v-.5a2 2 0 0 1 1-1.74l.15-.09a2 2 0 0 0 .73-2.73l-.22-.38a2 2 0 0 0-2.73-.73l-.15.08a2 2 0 0 1-2 0l-.43-.25a2 2 0 0 1-1-1.73V4a2 2 0 0 0-2-2z"></path><circle cx="12" cy="12" r="3"></circle></svg>,
  create: <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M12 20h9"></path><path d="M16.5 3.5a2.12 2.12 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z"></path></svg>,
  logout: <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path><polyline points="16 17 21 12 16 7"></polyline><line x1="21" y1="12" x2="9" y2="12"></line></svg>
};

const handleSignout = async () => {
  try{
    const response = await axios.post(`http://localhost:8080/signout`, {}, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('jwttoken')}`,
      },
    });

    if (response.status === 200) {
      localStorage.removeItem('jwttoken');
      localStorage.removeItem('isAdmin');
      localStorage.removeItem('userId');
      window.location.href = '/login';
    }

  } catch (error) {
    console.error(error);
  }
  
};



function Sidebar() {
  const isAdmin2 = JSON.parse(localStorage.getItem('isAdmin'));

  useEffect(() => {
    isAdmin();
  }, []);

  return (
    <div className="sidebar">
      <Logo />
      <nav className="nav">
        <SidebarLink icon={icons.home} label="Home" to="/home" active />
        <SidebarLink icon={icons.user} label="Profile" to="/profile" />
        {isAdmin2 && (
          <>
            <SidebarLink icon={<FontAwesomeIcon icon={faUserShield} />} label="Administration" to="/admin" />
            <SidebarLink icon={<FontAwesomeIcon icon={faUserShield} />} label="Admin Reports" to="/admin/report" />
            <SidebarLink icon={<FontAwesomeIcon icon={faUsers} />} label="Admin Topics Management" to="/admin/topics" />
          </>
        )}
      </nav>
      <div className="nav-footer">
        {/* Replace SidebarLink with a button */}
        <button className="sidebar-link logout-btn" onClick={handleSignout}>
          <span className="sidebar-link-icon">{icons.logout}</span> Log out
        </button>
      </div>
    </div>
  );
}

export default Sidebar;
