import React, { useState, useEffect } from 'react';
import createSocket from '../../services/Socket.js';
import '../../styles/Notification.css'; 

function Notification() {
    const [notifications, setNotifications] = useState(() => {
        const storedNotifications = localStorage.getItem('notifications');
        return storedNotifications ? JSON.parse(storedNotifications) : [];
    });    
    
    const handleNotification = (notification) => {
        if(notification.image === null){
            notification.image = "./defultProfilePicture.png"
        }
        else{
            notification.image = "../../../public/uploads/profile/" + notification.image;
        }
        const currentTime = new Date().toLocaleTimeString();
        const enhancedNotification = { ...notification, time: currentTime};
        setNotifications((prevNotifications) => {
            const updatedNotifications = [enhancedNotification, ...prevNotifications];
            localStorage.setItem('notifications', JSON.stringify(updatedNotifications)); 
            return updatedNotifications;
        });
    };
    
    const clearNotifications = () => {
        setNotifications([]);
        localStorage.removeItem('notifications');
    };

    const removeNotification = (index) => {
        setNotifications((prevNotifications) => {
            const updatedNotifications = prevNotifications.filter((_, i) => i !== index);
            localStorage.setItem('notifications', JSON.stringify(updatedNotifications)); 
            return updatedNotifications;
        });
    };
    // listen to like notifications
    useEffect(() => {
        const id = localStorage.getItem('id');
        const deactivateSocket = createSocket(`/topic/like/${id}`, handleNotification);
        return () => {
            deactivateSocket();
        };
    }, []);
    // listen to comment notifications
    useEffect(() => {
        const id = localStorage.getItem('id');
        const deactivateSocket = createSocket(`/topic/comment/${id}`, handleNotification);
        return () => {
            deactivateSocket();
        };
    }, []);
    // listen to repost notifications
    useEffect(() => {
        const id = localStorage.getItem('id');
        const deactivateSocket = createSocket(`/topic/repost/${id}`, handleNotification);
        return () => {
            deactivateSocket();
        };
    }, []);
    // listen to follow notifications
    useEffect(() => {
        const id = localStorage.getItem('id');
        const deactivateSocket = createSocket(`/topic/follow/${id}`, handleNotification);
        return () => {
            deactivateSocket();
        };
    }, []);
    // listen to reports notifications only for admin
    useEffect(() => {
        const role = localStorage.getItem('isAdmin');
        if (role === 'true') {
            const deactivateSocket = createSocket(`/topic/report`, handleNotification);
            return () => {
                deactivateSocket();
            };
        }
    }, []);

    

    return (
        <div className="notification-container">
            {notifications.length > 0 && (
                <button className="clear-button" onClick={clearNotifications}>
                    Clear All
                </button>
            )}
            <div className="notification-list">
                {notifications.map((msg, index) => (
                    <div key={index} className="notification">
                        <div className="notification-content">
                            <img src={msg.image} alt="Notification" className="notification-image" />
                            <p>{msg.message}</p>
                        </div>
                        <div className="notification-footer">
                            <span>{msg.time}</span>
                            <button
                                className="close-button"
                                onClick={() => removeNotification(index)}
                            >
                                &times;
                            </button>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default Notification;
