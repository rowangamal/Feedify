import { useState, useEffect } from 'react';
import PropTypes from "prop-types";
import Sidebar from "../Sidebar/Sidebar";
import PostCard from "../Feed/PostCard";
import "../../styles/Profile.css";

const Profile = ({ userId, username, following, followers, avatar }) => {
    const EditProfile = () => { };

    const [posts, setPosts] = useState([]);
    const [followingState, setFollowingState] = useState(following);
    const [followersState, setFollowersState] = useState(followers);
    const [avatarState, setAvatar] = useState(avatar);

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

    return (
        <div className="main-container">
            <Sidebar />
            <div className="profile-container">
                <div className="profile-header">
                    <div className="profile-avatar">
                        <img src={avatarState} alt="" className="avatar-image" />
                    </div>
                    <div className="profile-info">
                        <h2 className="username">{username}</h2>
                        <div className="stats-container">
                            <div className="stat">
                                <span className="stat-number">{followersState}</span>
                                <span className="stat-label">Followers</span>
                            </div>
                            <div className="stat">
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
