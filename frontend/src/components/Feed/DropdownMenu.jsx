import React from "react";
import "../../styles/DropdownMenu.css";
import { FaFlag, FaUser, FaExclamationTriangle } from "react-icons/fa";

function DropdownMenu({ follow_action, follow_unfollow, openReportDialog }) {
    return (
        <div className="dropdown-menu">
            <button className="dropdown-item" onClick={follow_unfollow}>
                <FaUser className="menu-icon" /> {follow_action}
            </button>
            <button
                className="dropdown-item"
                onClick={() => openReportDialog("Post")}
            >
                <FaExclamationTriangle className="menu-icon" /> Report Post
            </button>
            <button
                className="dropdown-item"
                onClick={() => openReportDialog("User")}
            >
                <FaFlag className="menu-icon" /> Report User
            </button>
        </div>
    );
}

export default DropdownMenu;
