import '../../styles/PostCard.css';
import ReportDialog from "./ReportDialog.jsx";
import {useState, useEffect} from "react";
import { useNavigate } from 'react-router-dom';
import DropdownMenu from "./DropdownMenu.jsx";
import axios from "axios";
import toast from "react-hot-toast";
import CommentPopup from '../Comment/Comment.jsx';


import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheckCircle, faExclamationCircle, faEye } from "@fortawesome/free-solid-svg-icons";

function PostCard({
  userId,
  username,
  avatar,
  postId,
  content,
  postImage,
  likesCount,
  commentsCount,
  repostsCount,
  timestamp }) {
  const POSTID = postId ;
  const USERID = userId ;
  const [postLikeCount, setPostLikeCount] = useState(likesCount);
  const [postCommentCount, setPostCommentCount] = useState(commentsCount);
  const [likeState, setLikeState] = useState(false);
  const navigate = useNavigate();

  const selectUserProfilePicture = () => {
    if (avatar) {
      const stringParts = avatar.split('/');
      if (stringParts[1] === 'uploads' && stringParts[2] === 'profile') {
        return avatar;
      } else if (stringParts[1] === 'defultProfilePicture.png') {
        return avatar;
      } else {
        return `/uploads/profile/${avatar}`;
      }
    } else {
      return `/defultProfilePicture.png`;
    }
  };

  const selectPostPicture = () => {
    if (postImage) {
      return `/uploads/post/${postImage}`;
    } else {
      return postImage;
    }
  }
  const [reportDialogState, setReportDialogState] = useState({ isOpen: false, type: "", id: null });
  const openReportDialog = (type) => {
    closeDropDownMenu();

    if(type === "User") {
      setReportDialogState({isOpen: true, type: "User", id: userId})
    }
    else
      setReportDialogState({isOpen: true, type: "Post", id: postId})
  }

  const closeReportDialog = () => {
    setReportDialogState({ isOpen: false, type: "", id: null });
  };

  const [dropDownMenuState, setDropDownMenuState] = useState({ isOpen: false });

  const toggleDropDownMenu = () => {
    if (dropDownMenuState.isOpen) {
      closeDropDownMenu();
    }
    else
      openDropDownMenu();
  }
  const openDropDownMenu = () => {
    setDropDownMenuState({ isOpen: true });
  }

  const closeDropDownMenu = () => {
    setDropDownMenuState({ isOpen: false });
  }

  const [follow_action, setFollowAction] = useState("Follow");

  useEffect(() => {
    const fetchFollowStatus = async () => {
      try {
        const response = await axios.post(
          'http://localhost:8080/is-followed', 
          { followId: USERID },  
          { 
            headers: { 
              "Authorization": `Bearer ${localStorage.getItem("jwttoken")}` 
            }
          }
        );
        
        if (response.status === 200) {
          setFollowAction("Unfollow");
        } else {
          setFollowAction("Follow"); 
        }
      } catch (error) {
        if (error.response && error.response.status === 404) {
          setFollowAction("Follow"); 
        } else {
          console.error("Error fetching follow status", error);
        }
      }
    };

    fetchFollowStatus();
  }, [USERID]);

  const follow_unfollow = async () => {
    closeDropDownMenu();
    const action = follow_action === "Follow" ? "follow" : "unfollow";

    try {
      await toggleFollowUser(USERID, action);
      setFollowAction((prevAction) => (prevAction === "Follow" ? "Unfollow" : "Follow"));
    } catch (error) {
      console.error(`Error: ${error.message}`);
    }
  };

  const API_BASE_URL = 'http://localhost:8080';

  const toggleFollowUser = async (followId, action) => {
    try {
      const url = `${API_BASE_URL}/${action}`;
      const method = "POST";

      const response = await axios({
        url,
        method,
        data: { followId: followId },
        headers: {
          "Authorization": "Bearer " + localStorage.getItem("jwttoken"),
        }
      });

      return response.data;
    } catch (error) {
      if (error.response) {
        throw new Error(error.response.data);
      } else {
        throw new Error("Something went wrong");
      }
    }
  };

  const makeDateReadable = () =>{
    const date = new Date(timestamp);
    return `${String(date.getDate()).padStart(2, "0")}-${String(date.getMonth() + 1).padStart(2, "0")}-${date.getFullYear()} ${String(date.getHours()).padStart(2, "0")}:${String(date.getMinutes()).padStart(2, "0")}:${String(date.getSeconds()).padStart(2, "0")}`;
  }

  useEffect(() => {
    const fetchLikeStatus = async () => {
      try {
        const response = await axios.get('http://localhost:8080/like', {
          params: { postId: POSTID },
          headers: {
            "Authorization": `Bearer ${localStorage.getItem("jwttoken")}`
          }
        });

        if (response.status === 200) {
          setLikeState(true);
        } else {
          setLikeState(false);
        }
      } catch (error) {
        if (error.response && error.response.status === 404) {
          setLikeState(false);
        }
      }
    };
    fetchLikeStatus();
  });

  const likePost = async () => {
    try {
      const response = await axios.post('http://localhost:8080/like',
          { postId: POSTID },
          {
            headers: {
              "Authorization": `Bearer ${localStorage.getItem("jwttoken")}`
            }
          }
      );
      if (response.status === 200) {
        setPostLikeCount(postLikeCount + 1);
        setLikeState(true);
      } else {
        setNotification({
          message: "Failed to like",
          type: "error",
        });
      }
    } catch (error) {
      setNotification({
        message: "Failed to like",
        type: "error",
      });
    }
    setTimeout(() => setNotification({ message: "", type: "" }), 2000);
  }

  const dislikePost = async () => {
    try {
      const response = await axios.delete('http://localhost:8080/like',
          {
            data: { postId: POSTID },
            headers: {
              "Authorization": `Bearer ${localStorage.getItem("jwttoken")}`
            }
          });
      if (response.status === 200) {
        setPostLikeCount(postLikeCount - 1);
        setLikeState(false);
      }
      else{
        setNotification({
          message: "Failed to dislike",
          type: "error",
        });
      }
    }
    catch (error) {
      setNotification({
        message: "Failed to dislike",
        type: "error",
      });
    }
    setTimeout(() => setNotification({ message: "", type: "" }), 2000);
  }

  const toggleLike = () => {
    if(likeState)
      dislikePost().then(r => toast.success("Post disliked"));
    else
      likePost().then(r => toast("Post liked"));
  }

  const handleUsernameClick = () => {
    navigate(`/profile/${username}`);
  };


  const [isReposting, setIsReposting] = useState(false);
  const [notification, setNotification] = useState({ message: "", type: "" });

  const handleRepost = async () => {
    try {
      const response = await axios.post(
        'http://localhost:8080/api/reposts/repost',
        { postId },
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jwttoken")}`,
          },
        }
      );

      if (response.status === 200) {
        setNotification({ message: "Repost successful!", type: "success" });
      }
    } catch (error) {
      if (error.response && error.response.data) {
        setNotification({
          message: error.response.data.message || "Failed to repost.",
          type: "error",
        });
      } else {
        setNotification({ message: "Something went wrong.", type: "error" });
      }
    }
    setTimeout(() => setNotification({ message: "", type: "" }), 2000);
  };

  const [isPopupOpen, setIsPopupOpen] = useState(false);
  const [repostUsers, setRepostUsers] = useState([]);

  const handleViewRepostUsers = async () => {
    try {
      const response = await axios.post(
        'http://localhost:8080/api/reposts/users',
        { postId },
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jwttoken")}`,
          },
        }
      );

      if (response.status === 200) {
        setRepostUsers(response.data);
        setIsPopupOpen(true); 
      }
    } catch (error) {
      console.error("Error fetching repost users", error);
    }
  };

  const [isCommentPopupOpen, setIsCommentPopupOpen] = useState(false);



  return (
    <div className="post-card">
      <div className="post-header">
        <div className="user-info">
          <img src={selectUserProfilePicture()} alt={username} className="avatar" />
          <div>
            <h3 className="user-name" onClick={handleUsernameClick} style={{ cursor: 'pointer' }}>
              {username}
            </h3>
            <p className="timestamp">{makeDateReadable()}</p>
          </div>
        </div>
        <button className="more-button" onClick={toggleDropDownMenu}>
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
            <circle cx="12" cy="12" r="1"></circle>
            <circle cx="19" cy="12" r="1"></circle>
            <circle cx="5" cy="12" r="1"></circle>
          </svg>
        </button>
      </div>
      <div className="drop_container">
        {dropDownMenuState.isOpen && (
          <DropdownMenu
              userId={USERID}
              follow_action={follow_action}
              follow_unfollow={follow_unfollow}
              openReportDialog={openReportDialog}
              closeDropDownMenu={closeDropDownMenu}
          />
        )}
      </div>
      {reportDialogState.isOpen && (
          <ReportDialog
              isOpen={reportDialogState.isOpen}
              type={reportDialogState.type}
              id={reportDialogState.id}
              onClose={closeReportDialog}
          />
      )}
      <p className="post-content">{content}</p>
      <div className="post-image">
        <img
          src={selectPostPicture()}
          alt=""
          className="post-img"
        />
      </div>
      <div className="post-actions">
        <button className="action-button" onClick={toggleLike}>
          <svg width="20" height="20" viewBox="0 0 24 24" fill={likeState ? "red" : "none"}
               stroke={likeState ? "red" : "currentColor"} strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
            <path
                d="M19 14c1.49-1.46 3-3.21 3-5.5A5.5 5.5 0 0 0 16.5 3c-1.76 0-3 .5-4.5 2-1.5-1.5-2.74-2-4.5-2A5.5 5.5 0 0 0 2 8.5c0 2.3 1.5 4.05 3 5.5l7 7Z"></path>
          </svg>
          {postLikeCount}
        </button>
        <button className="action-button" onClick={() => setIsCommentPopupOpen(true)}>
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2"
               strokeLinecap="round" strokeLinejoin="round">
            <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
          </svg>
          {postCommentCount}
        </button>
        <button className="action-button" onClick={handleViewRepostUsers}>
          <FontAwesomeIcon icon={faEye}/>
          <span style={{marginLeft: "5px"}}>Users who reposted</span>
        </button>

        {isPopupOpen && (
            <div
                style={{
                  position: 'fixed',
                  top: 0,
                  left: 0,
                  width: '100%',
              height: '100%',
              backgroundColor: 'rgba(0, 0, 0, 0.6)',
              display: 'flex',
              justifyContent: 'center',
              alignItems: 'center',
              zIndex: 1000,
              padding: '10px',
            }}
          >
            <div
              style={{
                backgroundColor: 'white',
                padding: '20px',
                borderRadius: '16px',
                boxShadow: '0 10px 30px rgba(0, 0, 0, 0.2)',
                width: '80%',
                maxWidth: '800px',
                maxHeight: '80%',
                overflowY: 'auto',
                position: 'relative',
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'flex-start',
              }}
            >
              {/* Close Button */}
              <button
                onClick={() => setIsPopupOpen(false)}
                style={{
                  position: 'absolute',
                  top: '10px',
                  right: '10px',
                  backgroundColor: 'transparent',
                  border: 'none',
                  fontSize: '1.5rem',
                  color: '#333',
                  cursor: 'pointer',
                  transition: 'color 0.3s ease',
                }}
                onMouseEnter={(e) => (e.target.style.color = '#ff0000')}
                onMouseLeave={(e) => (e.target.style.color = '#333')}
              >
                <i className="fas fa-times"></i>
              </button>

              <h3
                style={{
                  fontSize: '1.5rem',
                  marginBottom: '20px',
                  textAlign: 'center',
                  color: '#333',
                  fontWeight: '600',
                }}
              >
                <i className="fas fa-users" style={{ marginRight: '8px' }}></i>
                Users who reposted
              </h3>
              <ul style={{ listStyle: 'none', padding: 0 }}>
                {repostUsers.length > 0 ? (
                  repostUsers.map((user, index) => (
                    <li
                      key={index}
                      style={{
                        display: 'flex',
                        alignItems: 'center',
                        padding: '12px 0',
                        borderBottom: '1px solid #f1f1f1',
                      }}
                    >
                      <img
                        src={selectUserProfilePicture(user.avatar)}
                        alt={user.username}
                        style={{
                          width: '50px',
                          height: '50px',
                          borderRadius: '50%',
                          marginRight: '15px',
                          objectFit: 'cover',
                        }}
                      />
                      <span
                        style={{
                          fontSize: '1.1rem',
                          fontWeight: '500',
                          color: '#333',
                        }}
                      >
                        {user.username}
                      </span>
                    </li>
                  ))
                ) : (
                  <p
                    style={{
                      textAlign: 'center',
                      fontSize: '1rem',
                      color: '#777',
                      marginTop: '20px',
                    }}
                  >
                    No users have reposted this post yet.
                  </p>
                )}
              </ul>
            </div>
          </div>
        )}

        <button className="action-button">
          <svg width="20" height="20" viewBox="0 0 24 24"
                fill="none" stroke="currentColor" strokeWidth="2"
                strokeLinecap="round" strokeLinejoin="round"
                onClick={handleRepost} disabled={isReposting}>
            <path d="M17 2l4 4-4 4"></path>
            <path d="M3 11v-1a4 4 0 0 1 4-4h14"></path>
            <path d="M7 22l-4-4 4-4"></path>
            <path d="M21 13v1a4 4 0 0 1-4 4H3"></path>
          </svg>
          {isReposting ? "Reposting..." : "Repost"}
        </button>
        {notification.message && (
      <div
        style={{
          position: "fixed",
          top: "10px",
          right: "10px",
          padding: "10px",
          backgroundColor: notification.type === "success" ? "#d4edda" : "#f8d7da",
          color: notification.type === "success" ? "#155724" : "#721c24",
          border: `1px solid ${
            notification.type === "success" ? "#c3e6cb" : "#f5c6cb"
          }`,
          borderRadius: "5px",
          boxShadow: "0 2px 6px rgba(0, 0, 0, 0.2)",
          display: "flex",
          alignItems: "center",
          gap: "10px",
          zIndex: 1000,
        }}
      >
        <FontAwesomeIcon
          icon={notification.type === "success" ? faCheckCircle : faExclamationCircle}
          style={{
            fontSize: "20px",
            color: notification.type === "success" ? "#28a745" : "#dc3545",
          }}
        />
        <span>{notification.message}</span>
      </div>
    )}
        <CommentPopup
            postId={postId}
            userId={userId}
            isOpen={isCommentPopupOpen}
            onClose={() => setIsCommentPopupOpen(false)}
        />

      </div>
    </div>

  );
}

export default PostCard;