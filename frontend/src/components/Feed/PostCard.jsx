import '../../styles/PostCard.css';

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

  const makeDateReadable = () =>{
    const date = new Date(timestamp);
    return `${String(date.getDate()).padStart(2, "0")}-${String(date.getMonth() + 1).padStart(2, "0")}-${date.getFullYear()} ${String(date.getHours()).padStart(2, "0")}:${String(date.getMinutes()).padStart(2, "0")}:${String(date.getSeconds()).padStart(2, "0")}`;
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
        <button className="more-button">
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
            <circle cx="12" cy="12" r="1"></circle>
            <circle cx="19" cy="12" r="1"></circle>
            <circle cx="5" cy="12" r="1"></circle>
          </svg>
        </button>
      </div>
      <p className="post-content">{content}</p>
      <div className="post-image">
        <img
          src={selectPostPicture()}
          alt=""
          className="post-img"
        />
      </div>
      <div className="post-actions">
        <button className="action-button">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
            <path d="M19 14c1.49-1.46 3-3.21 3-5.5A5.5 5.5 0 0 0 16.5 3c-1.76 0-3 .5-4.5 2-1.5-1.5-2.74-2-4.5-2A5.5 5.5 0 0 0 2 8.5c0 2.3 1.5 4.05 3 5.5l7 7Z"></path>
          </svg>
        </button>
        <div className="comment-section">
          <input type="text" placeholder="Add a comment" className="comment-input" />
          <button className="comment-button">Comment</button>
        </div>
        <button className="action-button">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
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