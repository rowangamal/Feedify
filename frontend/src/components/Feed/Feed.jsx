import { useState, useEffect } from "react";
import SearchBar from "./SearchBar";
import FeedTabs from "./FeedTabs";
import PostCard from "./PostCard";
import "../../styles/Feed.css";

function Feed() {
  const [feedType, setFeedType] = useState("for-you");
  const [posts, setPosts] = useState([]);

  const fetchPosts = async (type) => {
    try {
      let request;
      if (type === "for-you") {
        request = "http://localhost:8080/userProfile/topicsFeed";
      } else {
        request = "http://localhost:8080/userProfile/followingFeed";
      }

      const response = await fetch(request, {
        method: "POST",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("jwttoken"),
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ userId: 0 }),
      });

      const data = await response.json();
      if (Array.isArray(data)) {
        setPosts(data);
      } else {
        console.error("Expected an array but got:", data);
        setPosts([]);
      }
    } catch (error) {
      console.error("Error fetching posts:", error);
      setPosts([]);
    }
  };

  useEffect(() => {
    fetchPosts(feedType);
  }, [feedType]);

  return (
    <div className="feed">
      <SearchBar />
      <FeedTabs onTabChange={setFeedType} activeTab={feedType} />
      <div className="posts">
        {posts.length > 0 ? (
          posts.map((post, index) => (
            <PostCard
              key={index}
              userId={post.userId}
              username={post.username}
              avatar={post.userPicture}
              postId={post.postId}
              content={post.content}
              postImage={post.postImage}
              likesCount={post.likesCount}
              commentsCount={post.commentsCount}
              repostsCount={post.repostsCount}
              timestamp={post.createdAt}
            />
          ))
        ) : (
          <p>No posts available</p>
        )}
      </div>
    </div>
  );
}

export default Feed;
