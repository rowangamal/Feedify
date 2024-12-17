import { useState, useEffect } from 'react';
import PropTypes from "prop-types";
import Sidebar from "../Sidebar/Sidebar";
import PostCard from "../Feed/PostCard";
import "../../styles/Profile.css";
import axios from 'axios';
import EditProfilePopup from "../EditProfilePopup.jsx";

const Profile = ({ userId, username, following, followers, avatar }) => {
    const EditProfile = () => {
        setIsPopupVisible(true);
    };
    const handleClosePopup = () => {
        setIsPopupVisible(false);
    };
    const [posts, setPosts] = useState([]);
    const [avatarState, setAvatar] = useState(avatar);
    const [userFollowing, setUserFollowing] = useState([]);
    const [userFollowers, setUserFollowers] = useState([]);
    const [isFollowersPopUp, setIsFollowersPopUp] = useState([false]);
    const [isModalOpen, setModalOpen] = useState(false);
    const [isPopupVisible, setPopupVisible] = useState(false);
    const [profileData, setProfileData] = useState({
        followingCount: 0,
        followersCount: 0,
    });

    const showFollowers = async (e) => {
        e.preventDefault();
    
        try {
            const response = await axios.get('http://localhost:8080/followers',
                { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwttoken")}`} });
    
            if (response.status === 200) {
                setIsFollowersPopUp(true);
                setUserFollowers(response.data.map(follower => follower.username));
                setModalOpen(true);
                setPopupVisible(true);
            } else {
                console.error("User does not exist");
            }
        } catch (error) {
            console.error("Error during follwers request:", error);
        }
    };

    const showFollowing = async (e) => {
        e.preventDefault();
    
        try {
            const response = await axios.get('http://localhost:8080/following',
                { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwttoken")}`} });
    
            if (response.status === 200) {
                setIsFollowersPopUp(false);
                setUserFollowing(response.data.map(following => following.username));
                setModalOpen(true);
                setPopupVisible(true);
            } else {
                console.error("User does not exist");
            }
        } catch (error) {
            console.error("Error during following request:", error);
        }
    };

    const closeModal = () => {
        setModalOpen(false);
        setPopupVisible(false)
        setIsFollowersPopUp(false)
    }

    const abbreviateNumber = (number) => {
        if (number >= 1_000_000) {
            return (number / 1_000_000).toFixed(1).replace(/\.0$/, "") + "M";
        } else if (number >= 1_000) {
            return (number / 1_000).toFixed(1).replace(/\.0$/, "") + "K";
        } else {
            return number.toString();
        }
    };  
    const [IsPopupVisible, setIsPopupVisible] = useState(false);

    useEffect(() => {
        const fetchPosts = async () => {
            try {
                const response = await fetch('http://localhost:8080/userProfile/profileFeed', {
                    method: "GET",
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwttoken"),
                        "Content-Type": "application/json",
                    },
                });
                const data = await response.json();
                setPosts(data);
            } catch (error) {
                console.error('Error fetching posts:', error);
            }
        };

        const fetchFollowingCount = async () => {
            try {
                const token = localStorage.getItem("jwttoken");
                const headers = { Authorization: `Bearer ${token}` };

                const response = await axios.get(
                    `http://localhost:8080/following-count`,
                    { headers }
                );
                setProfileData((prevData) => ({
                    ...prevData,
                    followingCount: response.data,
                }));
            } catch (error) {
                console.error("Error fetching following count:", error);
            }
        };

        const fetchFollowersCount = async () => {
            try {
                const token = localStorage.getItem("jwttoken");
                const headers = { Authorization: `Bearer ${token}` };

                const response = await axios.get(
                    `http://localhost:8080/follower-count`,
                    { headers }
                );
                setProfileData((prevData) => ({
                    ...prevData,
                    followersCount: response.data,
                }));
            } catch (error) {
                console.error("Error fetching followers count:", error);
            }
        };

        fetchPosts();
        fetchFollowingCount();
        fetchFollowersCount();
    }, [userId]);

    useEffect(() => {
        if (isPopupVisible) {
            document.body.style.overflow = "hidden";
        } else {
            document.body.style.overflow = "";
        }
        return () => {
            document.body.style.overflow = "";
        };
    }, [isPopupVisible]);

    return (
        <div className="main-container">
            <Sidebar />
            <div className="profile-container">
                <div className="profile-header">
                    <div className="profile-avatar">
                        <img src={avatarState} alt="profile picture" className="avatar-image" />
                    </div>
                    <div className="profile-info">
                        <h2 className="username">{username}</h2>
                        <div className="stats-container">
                            <div className="stat" onClick={showFollowers}>
                                <span className="stat-number">{abbreviateNumber(profileData.followersCount)}</span>
                                <span className="stat-label" onClick={showFollowers}>Followers</span>
                            </div>
                            <div className="stat" onClick={showFollowing}>
                                <span className="stat-number">{abbreviateNumber(profileData.followingCount)}</span>
                                <span className="stat-label">Following</span>
                            </div>
                        </div>
                        <button className="edit-profile-btn" onClick={EditProfile}>
                            Edit Profile
                        </button>
                        {IsPopupVisible && (
                            <EditProfilePopup 
                            onClose={handleClosePopup}/>)}
                    </div>
                </div>
                <div className='posts'>
                    {posts.map((post) => (
                        <PostCard key={post.id}
                            username={username}
                            content={post.content}
                            avatar={avatarState}
                            postImage={post.image}
                            timestamp={post.createdAt} />
                    ))}
                </div>
            </div>



            {isModalOpen && (
                <>
                    <div className="backdrop" onClick={closeModal}></div>

                    <div className="modal">
                        <div className="modal-header">
                            {isFollowersPopUp ? (
                                <h2>Followers</h2>
                            ) : (
                                <h2>Following</h2>
                            )}
                            <button className="close-popup-btn" onClick={closeModal}>X</button>
                        </div>
                        <div className="modal-content">

                            {isFollowersPopUp ? (
                                userFollowers.length > 0 ? (
                                    userFollowers.map((username, index) => (
                                        <div key={index} className="username">{username}</div>
                                    ))
                                ) : (
                                    <p>No one follows you.</p>
                                )
                            ) : (
                                userFollowing.length > 0 ? (
                                    userFollowing.map((username, index) => (
                                        <div key={index} className="username">{username}</div>
                                    ))
                                ) : (
                                    <p>No followers found.</p>
                                )
                            )}
                        </div>
                    </div>
                </>
            )}
        </div>
    );
};

Profile.defaultProps = {
    username: localStorage.getItem("username"),
    followers: 0,
    following: 0,
    avatar: localStorage.getItem("profilePic"),
};

Profile.propTypes = {
    username: PropTypes.string,
    followers: PropTypes.number,
    following: PropTypes.number,
    avatar: PropTypes.string,
    userId: PropTypes.number,
};

export default Profile;
