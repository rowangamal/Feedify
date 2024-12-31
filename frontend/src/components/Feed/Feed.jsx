import { useState, useEffect } from "react";
import SearchBar from "./SearchBar";
import FeedTabs from "./FeedTabs";
import PostCard from "./PostCard";
import "../../styles/Feed.css";

function Feed() {
  const [feedType, setFeedType] = useState("for-you");
  const [posts, setPosts] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);

  const fetchPosts = async (type, currentPage) => {
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
        body: JSON.stringify({ pageNumber: currentPage - 1, pageSize: 10 }),
      });

      const data = await response.json();
      setPosts(data.postResponses);
      setTotalPages(data.totalPages);
      console.log(data.postResponses);
    } catch (error) {
      console.error("Error fetching posts:", error);
      setPosts([]);
    }
  };

  useEffect(() => {
    fetchPosts(feedType, currentPage);
  }, [feedType, currentPage]);

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
  );
}

export default Feed;
