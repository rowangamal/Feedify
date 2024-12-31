import { useState, useEffect } from 'react';
import PropTypes from "prop-types";
import Sidebar from "../Sidebar/Sidebar";
import PostCard from "../Feed/PostCard";
import "../../styles/Profile.css";
import axios from 'axios';
import EditProfilePopup from "../EditProfilePopup.jsx";
import Notification from "../Notification/Notification.jsx";
import { useParams, useNavigate } from 'react-router-dom';

const Profile = () => {
    const { usernameInPath } = useParams();
    const navigate = useNavigate();

    const EditProfile = () => {
        setIsPopupVisible(true);
    };

    const handleClosePopup = () => {
        setIsPopupVisible(false);
    };

    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const [userMainData, setUserMainData] = useState({});
    const [posts, setPosts] = useState([]);
    const [avatarState, setAvatar] = useState();
    const [userFollowing, setUserFollowing] = useState([]);
    const [userFollowers, setUserFollowers] = useState([]);
    const [isFollowersPopUp, setIsFollowersPopUp] = useState([false]);
    const [isModalOpen, setModalOpen] = useState(false);
    const [isPopupVisible, setPopupVisible] = useState(false);
    const [profileData, setProfileData] = useState({
        followingCount: 0,
        followersCount: 0,
    });
    const [username, setUsername] = useState();

    useEffect(() => {
        const fetchUserMainData = async (usernameToFetch) => {
            try {
                const response = await fetch('http://localhost:8080/userProfile/getUser', {
                    method: "Post",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: "Bearer " + localStorage.getItem("jwttoken"),
                    },
                    body: JSON.stringify({ username: usernameToFetch }),
                });
                const data = await response.json();
                setUserMainData(data);

            } catch (error) {
                console.error('Error fetching user data:', error);
            }
        };

        if (usernameInPath) {
            setUsername(usernameInPath);
            fetchUserMainData(usernameInPath);
        }
        else {
            setUsername(localStorage.getItem("username"));
            fetchUserMainData(localStorage.getItem("username"));
        }
    }, [usernameInPath]);

    useEffect(() => {
        if (userMainData.profilePic && userMainData.profilePic !== "") {
            setAvatar("/uploads/profile/" + userMainData.profilePic);
        } else {
            setAvatar("/defultProfilePicture.png");
        }
    }, [userMainData]);

    const showFollowers = async (e) => {
        e.preventDefault();

        try {
            const token = localStorage.getItem("jwttoken");
            const headers = { 'Authorization': `Bearer ${token}` };
            const data = { username: username };

            const response = await axios.post(
                'http://localhost:8080/followers',
                data,
                { headers }
            );

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
            const token = localStorage.getItem("jwttoken");
            const headers = { 'Authorization': `Bearer ${token}` };
            const data = { username: username };

            const response = await axios.post(
                'http://localhost:8080/following',
                data,
                { headers }
            );

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
                    method: "POST",
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwttoken"),
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({ username: username, pageNumber: currentPage - 1, pageSize: 10 }),
                });

                const data = await response.json();
                setPosts(data.posts);
                setTotalPages(data.totalPages);
            } catch (error) {
                console.error('Error fetching posts:', error);
            }
        };

        const fetchFollowingCount = async () => {
            try {
                const token = localStorage.getItem("jwttoken");
                const headers = { Authorization: `Bearer ${token}` };
                const body = { username: username };

                const response = await axios.post(
                    `http://localhost:8080/following-count`,
                    body,
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
                const body = { username: username };

                const response = await axios.post(
                    `http://localhost:8080/follower-count`,
                    body,
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
    }, [username, currentPage]);

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

    const handlePreviousPage = () => {
        if (currentPage > 1) {
            setCurrentPage(currentPage - 1);
        }
    };

    const handleNextPage = () => {
        if (currentPage < totalPages) {
            setCurrentPage(currentPage + 1);
        }
    };

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
                        {!(usernameInPath && usernameInPath !== localStorage.getItem("username")) && (
                            <button className="edit-profile-btn" onClick={EditProfile}>
                                Edit Profile
                            </button>
                        )}
                        {IsPopupVisible && (
                            <EditProfilePopup
                                onClose={handleClosePopup} />)}
                    </div>
                </div>
                <div className="posts">
                    {posts.length > 0 ? (
                        posts.map((post) => (
                            <PostCard
                                key={post.id}
                                postId={post.id}
                                userId={post.userId}
                                username={username}
                                content={post.content}
                                likesCount={post.likesCount}
                                commentsCount={post.commentsCount}
                                repostsCount={post.repostsCount}
                                avatar={avatarState}
                                postImage={post.image}
                                timestamp={post.createdAt}
                            />
                        ))
                    ) : (
                        <p>No posts available</p>
                    )}
                </div>
                <div className="pagination">
                    <button
                        className="pagination-button"
                        onClick={handlePreviousPage}
                        disabled={currentPage === 1}
                    >
                        Previous
                    </button>
                    <span className="pagination-info">
                        Page {currentPage} of {totalPages}
                    </span>
                    <button
                        className="pagination-button"
                        onClick={handleNextPage}
                        disabled={currentPage === totalPages}
                    >
                        Next
                    </button>
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
                                        <div
                                            key={index}
                                            className="username"
                                            onClick={() => {
                                                closeModal();
                                                navigate(`/profile/${username}`);
                                            }}
                                            style={{ cursor: "pointer" }}
                                        >
                                            {username}
                                        </div>
                                    ))
                                ) : (
                                    <p>No one follows you.</p>
                                )
                            ) : (
                                userFollowing.length > 0 ? (
                                    userFollowing.map((username, index) => (
                                        <div
                                            key={index}
                                            className="username"
                                            onClick={() => {
                                                closeModal();
                                                navigate(`/profile/${username}`)
                                            }}
                                            style={{ cursor: "pointer" }}
                                        >
                                            {username}
                                        </div>
                                    ))
                                ) : (
                                    <p>No followers found.</p>
                                )
                            )}
                        </div>
                    </div>
                </>
            )}
            <Notification />

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