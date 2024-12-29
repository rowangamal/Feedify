import React, { useEffect, useState } from "react";
import "../styles/EditProfilePopup.css";

import PopUp from "./PopUp.jsx";
import { use } from "react";

const EditProfilePopup = ({ onClose }) => {
  const [username, setUsername] = useState("");
  const [oldPassword, setOldPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [profilePic, setProfilePic] = useState(null);
  const [interests, setInterests] = useState([]);
  const [availableInterests , setAvailableInterests] = useState([]);
  const [sentProfile, setSentProfile] = useState(null);
  const [popup, setPopup] = useState({ visible: false, message: "", type: "" });

  useEffect(() => {
    const fetchData = async () => {
        try {
            const response = await fetch("http://localhost:8080/post/getTypes", {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + localStorage.getItem("jwttoken"),
                },
            });

            if (response.status === 200) {
                const data = await response.json();
                const availableNames = data.map((interest) => interest.name);
                setAvailableInterests(availableNames); 
                const userResponse = await fetch("http://localhost:8080/userSettings/interests", {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": "Bearer " + localStorage.getItem("jwttoken"),
                    },
                });

                if (userResponse.status === 200) {
                    const userData = await userResponse.json();
                    setInterests(userData.map((index) => availableNames[index - 1] ));
                } else {
                    console.error("Failed to fetch user interests");
                }
            } else {
                console.error("Failed to fetch available interests");
            }
        } catch (error) {
            console.error("Error fetching interests:", error);
        }
    };

    fetchData();
}, []);


  const handleProfilePicChange = (e) => {
    setProfilePic(URL.createObjectURL(e.target.files[0]));
    setSentProfile(e.target.files[0]);
  };

  const toggleInterest = (interest) => {
    if (interests.includes(interest)) {
      setInterests(interests.filter((i) => i !== interest));
    } else {
      setInterests([...interests, interest]);
    }
  };

  const saveUsername = async(e) => {
    e.preventDefault();
    if(!username)
      setPopup({
        visible: true,
        message: "Username cannot be empty!",
        type: "error",
      });
      else{
        const formData = new FormData();
formData.append("newUsername", username);

try {
    const response = await fetch("http://localhost:8080/userSettings/changeUsername", {
        method: "PUT",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("jwttoken"),
        },
        body: formData,
    });
    const message = await response.text();

    if (response.ok) {
        localStorage.setItem("username", username);
        setUsername("");
        setPopup({
            visible: true,
            message: "username name has been updated successfully!", 
            type: "success",
        });
    } else {
        setPopup({
            visible: true,
            message: message, 
            type: "error",
        });
    }
} catch (error) {
    console.error("Error:", error);
    setPopup({
        visible: true,
        message: "An unexpected error occurred.",
        type: "error",
    });
}
finally {
  setTimeout(() => {
      setPopup({ visible: false, message: "", type: "" });
  }, 5000);
}
}};

  const savePassword = async(e) => {
    e.preventDefault();
    if(!oldPassword || !newPassword || !confirmPassword){
      setPopup({
        visible: true,
        message: "All fields are required!",
        type: "error",
      });
      return;
    }
    else{
        if(newPassword !== confirmPassword){
          setPopup({
            visible: true,
            message: "Passwords do not match!",
            type: "error",
          });
          return;
        }
        if(newPassword === oldPassword){
          setPopup({
            visible: true,
            message: "New password must be different from the old password!",
            type: "error",
          });
          return;
        }
        const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        if(!newPassword.match(passwordPattern)){
          setPopup({
            visible: true,
            message: "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one number and one special character!",
            type: "error",
          });
          return;
        }  
        const passwordData = {
          newPassword: newPassword,
          oldPassword: oldPassword,
        }
try {
    const response = await fetch("http://localhost:8080/userSettings/changePassword", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + localStorage.getItem("jwttoken"),
        },
        body: JSON.stringify(passwordData),
    });
    const message = await response.text();

    if (response.ok) {
        
        setOldPassword("");
        setNewPassword("");
        setConfirmPassword("");
        setPopup({
            visible: true,
            message: "Password has been changed successfully!", 
            type: "success",
        });
    } else {
        setPopup({
            visible: true,
            message: message, 
            type: "error",
        });
    }
} catch (error) {
    console.error("Error:", error);
    setPopup({
        visible: true,
        message: "An unexpected error occurred.",
        type: "error",
    });
}
finally {
  setTimeout(() => {
      setPopup({ visible: false, message: "", type: "" });
  }, 5000);
}
}
  };

  const saveProfilePic = async(e) => {
    e.preventDefault();
    if(!sentProfile){
      setPopup({
        visible: true,
        message: "Please select an image!",
        type: "error",
      });
      return;
    }
    const formData = new FormData();
    formData.append("imageURL", sentProfile);
    try{
    const response = await fetch("http://localhost:8080/userSettings/changeProfilePic", {
      method: "PUT",
      headers: {
          "Authorization": "Bearer " + localStorage.getItem("jwttoken"),
      },
          body: formData,
      });
      const message = await response.text();
      if (response.ok) {
          setProfilePic(null);
          setSentProfile(null);
          localStorage.setItem("profilePic", "/uploads/profile/" + message);
          setPopup({
              visible: true,
              message: "Profile picture has been updated successfully!", 
              type: "success",
          });
      } else {
          setPopup({
              visible: true,
              message: message, 
              type: "error",
          });
      }
    } catch (error) {
      console.error("Error:", error);
      setPopup({
          visible: true,
          message: "An unexpected error occurred.",
          type: "error",
      });
    }finally {
      setTimeout(() => {
          setPopup({ visible: false, message: "", type: "" });
      }, 5000);
    }
    };

  const saveInterests = async(e) => {
    e.preventDefault();
    const interestIndices = interests.map((interest) => availableInterests.indexOf(interest) + 1);
    const formData = new FormData();
    formData.append("interests", JSON.stringify(interestIndices));
    try{

    const  response = await fetch("http://localhost:8080/userSettings/changeInterests", {
      method: "POST",
      headers: {
        "Authorization": "Bearer " + localStorage.getItem("jwttoken"),
      },
      body: formData,
    })
    const message = await response.text();
        if (response.ok) {
          setPopup({
            visible: true,
            message: "Interests have been updated successfully!",
            type: "success",
          });
        } else {
          setPopup({
            visible: true,
            message: message,
            type: "error",
          });
        }
      }catch{
        setPopup({
          visible: true,
          message: "An unexpected error occurred.",
          type: "error",
        });
      }finally {
        setTimeout(() => {
            setPopup({ visible: false, message: "", type: "" });
        }, 5000);
      }
      
      
  };

  return (
    <div className="popup-overlay">
      <div className="popup-content">
        <h2>Edit Profile</h2>
        <div className="edit-section">
          <label>Change Username:</label>
          <input
            type="text"
            placeholder="New username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <button onClick={saveUsername} className="save-button">Save</button>
        </div>

        <div className="edit-section">
          <label>Change Password:</label>
          <input
            type="password"
            placeholder="Old password"
            value={oldPassword}
            onChange={(e) => setOldPassword(e.target.value)}
          />
          <input
            type="password"
            placeholder="New password"
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
          />
          <input
            type="password"
            placeholder="Confirm new password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
          />
          <button onClick={savePassword} className="save-button">Save</button>
        </div>

        <div className="edit-section">
          <label>Select Interests:</label>
          <div className="interests-container">
            {availableInterests.map((interest) => (
              <span
                key={interest}
                className={`interest-tag ${
                  interests.includes(interest) ? "selected" : ""
                }`}
                onClick={() => toggleInterest(interest)}
              >
                {interest}
              </span>
            ))}
          </div>
          <br />
          <button onClick={saveInterests} className="save-button">Save</button>
        </div>  
        <div className="edit-section">
          <label>Change Profile Picture:</label>
          <div className="profile-pic-container">
            {profilePic && (
              <div className="profile-pic-preview">
                <img src={profilePic} alt="Preview" />
              </div>
            )}
           <div className="file-input-wrapper">
              <label htmlFor="file-upload">Select an Image</label>
           <input type="file" accept="image/*" onChange={handleProfilePicChange} />
           </div>
            
          </div>
          <button onClick={saveProfilePic} className="save-button">Save</button>
        </div>

        <div className="popup-actions">
          <button onClick={onClose}>Close</button>
        </div>
      </div>
      {popup.visible && (
                <PopUp
                    message={popup.message}
                    type={popup.type}
                    onClose={() => setPopup({ visible: false, message: "", type: "" })}
                />
            )}
    </div>
  );
};

export default EditProfilePopup;
