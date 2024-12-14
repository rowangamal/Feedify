import { useState, useEffect } from 'react';
import PropTypes from "prop-types";
import Sidebar from "../Sidebar/Sidebar";
import PostCard from "../Feed/PostCard";
import "../../styles/Profile.css";
import axios from 'axios';

const Profile = ({ userId, username, following, followers, avatar }) => {
    const EditProfile = () => { };

    const [posts, setPosts] = useState([]);
    const [followingState, setFollowingState] = useState(following);
    const [followersState, setFollowersState] = useState(followers);
    const [avatarState, setAvatar] = useState(avatar);
    const [userFollowing, setUserFollowing] = useState([]);
    const [userFollowers, setUserFollowers] = useState([]);
    const [isFollowersPopUp, setIsFollowersPopUp] = useState([false]);
    const [isModalOpen, setModalOpen] = useState(false);
    const [isPopupVisible, setPopupVisible] = useState(false);

    const showFollowers = async (e) => {
        e.preventDefault();
    
        try {
            const response = await axios.get('http://localhost:8080/followers',
                { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwttoken")}`} });
    
            console.log(response)
            if (response.status === 200) {
                setIsFollowersPopUp(true);
                setUserFollowers(response.data.map(follower => follower.username));
                setModalOpen(true);
                setPopupVisible(true);
            } else {
                console.log("sad")
                // setError("Current Email doesn't exist in system"); 
            }
        } catch (error) {
            console.error("Error during password reset request:", error);
        }
    };

    const showFollowing = async (e) => {
        e.preventDefault();
    
        try {
            const response = await axios.get('http://localhost:8080/following',
                { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwttoken")}`} });
    
            console.log(response)
            if (response.status === 200) {
                setUserFollowing(response.data.map(following => following.username));
                setModalOpen(true);
                setPopupVisible(true)
            } else {
                // setError("Current Email doesn't exist in system");
            }
        } catch (error) {
            console.error("Error during password reset request:", error);
            // setError("An error occurred. Please try again later.");
        }
    };

    const closeModal = () => {
        setModalOpen(false);
        setPopupVisible(false)
        setIsFollowersPopUp(false)
    }

    useEffect(() => {
        const fetchPosts = async () => {
            try {
                const response = await fetch('http://localhost:8080/userProfile/profileFeed', {
                    method: 'POST',
                    headers: {
                        "Authorization": "Bearer " + localStorage.getItem("jwttoken"),
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({ userId }),
                });
                const data = await response.json();
                setPosts(data);
            } catch (error) {
                console.error('Error fetching posts:', error);
            }
        };

        fetchPosts();
    }, [userId]);

    useEffect(() => {
        if (isPopupVisible) {
            document.body.style.overflow = "hidden"; // Disable scroll
        } else {
            document.body.style.overflow = ""; // Re-enable scroll
        }
        return () => {
            document.body.style.overflow = ""; // Ensure cleanup
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
                                <span className="stat-number">{followersState}</span>
                                <span className="stat-label" onClick={showFollowers}>Followers</span>
                            </div>
                            <div className="stat" onClick={showFollowing}>
                                <span className="stat-number">{followingState}</span>
                                <span className="stat-label">Following</span>
                            </div>
                        </div>
                        <button className="edit-profile-btn" onClick={EditProfile}>
                            Edit Profile
                        </button>
                    </div>
                </div>
                <div className='posts'>
                    {posts.map((post) => (
                        <PostCard key={post.id}
                            username={username}
                            avatar={avatarState}
                            content={post.content}
                            createdAt={post.createdAt} />
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
    username: "UserName",
    followers: 0,
    following: 0,
    avatar: "/defultProfilePicture.png",
};

Profile.propTypes = {
    username: PropTypes.string,
    followers: PropTypes.number,
    following: PropTypes.number,
    avatar: PropTypes.string,
    userId: PropTypes.number,
};

export default Profile;
