import '../../styles/PostCard.css';
import ReportDialog from "./ReportDialog.jsx";
import {useState, useEffect} from "react";
import DropdownMenu from "./DropdownMenu.jsx";
import axios from "axios";
import toast from "react-hot-toast";

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
  const [likeState, setLikeState] = useState(false);

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
        throw new Error("Error liking post");
      }
    } catch (error) {
      throw new Error("Error liking post");
    }
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
        throw new Error("Error disliking post");
      }
    }
    catch (error) {
      throw new Error("Error disliking post");
    }
  }

  const toggleLike = () => {
    if(likeState)
      dislikePost().then(r => toast.success("Post disliked"));
    else
      likePost().then(r => toast("Post liked"));
  }

  return (
    <div className="post-card">
      <div className="post-header">
        <div className="user-info">
          <img src={selectUserProfilePicture()} alt={username} className="avatar" />
          <div>
            <h3 className="user-name">{username}</h3>
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
        <div className="comment-section">
          <input type="text" placeholder="Add a comment" className="comment-input"/>
          <button className="comment-button">Comment</button>
        </div>
        <button className="action-button">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2"
               strokeLinecap="round" strokeLinejoin="round">
            <path d="M17 2l4 4-4 4"></path>
            <path d="M3 11v-1a4 4 0 0 1 4-4h14"></path>
            <path d="M7 22l-4-4 4-4"></path>
            <path d="M21 13v1a4 4 0 0 1-4 4H3"></path>
          </svg>
        </button>
      </div>
    </div>
  );
}

export default PostCard;