import Sidebar from './Sidebar/Sidebar';
import Feed from './Feed/Feed';
import '../styles/Home.css';
import '../styles/global.css';
import { useState , useEffect } from 'react';
import CreatePost from './CreatePost';

function Home() {

    const [showCreatePost, setShowCreatePost] = useState(false);
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch("http://localhost:8080/userSettings/info", {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": "Bearer " + localStorage.getItem("jwttoken"),
                    },
                });
                if (response.status === 200) {
                    const data = await response.json();
                    localStorage.setItem("username", data.username);
                    if(data.profilePic)
                        localStorage.setItem("profilePic", "/uploads/profile/" + data.profilePic);
                    else
                        localStorage.setItem("profilePic", "/defultProfilePicture.png");
                    console.log(data);
                } else {
                    console.error("Failed to fetch user");
                }
            } catch (error) {
                console.error("Error fetching user:", error);
            }
        };
        fetchData();
    }, []);
    const createIcon = (
        <svg
            width="24"
            height="24"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            strokeWidth="2"
            strokeLinecap="round"
            strokeLinejoin="round"
        >
            <path d="M12 20h9"></path>
            <path d="M16.5 3.5a2.12 2.12 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z"></path>
        </svg>
    );

    function handleCreatePost() {
        setShowCreatePost((prev) => !prev);
    }
    return (
        <div className="home">
            <Sidebar />
            <Feed />
            <button className="create-post" onClick={handleCreatePost}>
                {createIcon}
            </button>
            {showCreatePost && (
                <div className="modal-overlay">
                    <div className="modal">
                        <CreatePost />
                        <button className="close-btn" onClick={handleCreatePost}>
                            x
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
}

export default Home;