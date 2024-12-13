import { useState } from 'react';
import '../styles/CreatePost.css'; 
import PopUp from '../components/PopUp'

function CreatePost (){
    const [postText, setPostText] = useState("");
    const [selectedTypes, setSelectedTypes] = useState([]);
    const [file, setFile] = useState(null);
    const [errorMessage, setErrorMessage] = useState("");
    const [popup, setPopup] = useState({ visible: false, message: "", type: "" });
    const allTypes = ["Sport", "Technology", "Health" , "Religion" , "Troll" , "Politics"] 
    const maxChars = 1000;

    const handleTextChange = (e) => {
    const text = e.target.value;
    if (text.length <= maxChars) {
        setPostText(text);
    }
    };

    const handleFileChange = (e) => {
    const selectedFile = e.target.files[0];
    if (selectedFile) {
        setFile(URL.createObjectURL(selectedFile));
    }
    };

    const handleTypeSelect = (type) => {
    if (!selectedTypes.includes(type)) {
        setSelectedTypes([...selectedTypes, type]);
    }
    };

    const handleTypeRemove = (type) => {
    setSelectedTypes(selectedTypes.filter((item) => item !== type));
    };
    const handleImageRemove = ()=>{
        setFile(null)
    }

    const handlePostSubmit = async(e) => {
        e.preventDefault();
    if (postText.trim() === ""  ) {
        setErrorMessage("Post text cannot be empty");
    }
    else if(selectedTypes.length === 0){
        setErrorMessage("you must choose the type of post");
    }
    else {
        let typesOfpost=[]
        for(let i = 0 ; i < selectedTypes.length ; i++){
            typesOfpost.push({
                "id":allTypes.indexOf(selectedTypes[i]) + 1,
                "name":selectedTypes[i]
            })
        }
        let post = {
            "content" : postText,
            "types":typesOfpost,
            "imageURL": file
        }
        console.log(post)
        try {
            console.log(localStorage.getItem("jwttoken"));
                const response = await fetch("http://localhost:8080/post/createPost", {
                method: "POST",
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("jwttoken"),
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(post),
                });
                if (response.status === 201) {
                const newPost = await response.text();
                setPopup({
                    visible: true,
                    message: "Post created successfully!",
                    type: "success",
                });
                console.log(newPost)
                console.log("Post submitted");
                setPostText("");
                setSelectedTypes([]);
                setFile(null);
                setErrorMessage("");
                } else {
                    setPopup({
                        visible: true,
                        message: "Failed to create post.",
                        type: "error",
                    });
                    console.error("Failed to create post");
                }
            } catch (error) {
                setPopup({
                    visible: true,
                    message: "An error occurred while creating the post.",
                    type: "error",
                });
                console.error("Error submitting post:", error);
        }
        finally {
            setTimeout(() => {
                setPopup({ visible: false, message: "", type: "" });
            }, 5000);
        }
        
    }
    };
    const handleDecline = ()=>{
        setPostText("");
        setSelectedTypes([]);
        setFile(null);
        setErrorMessage("");
    }

    return (
    <div className="create-container">
        
        <div className="create-header">
        <img
            src="https://via.placeholder.com/40"
            alt="User Avatar"
            className="user-avatar"
        />
        <span>UserName</span>
        </div>

        <div className="create-types">
        {allTypes.map((type) => (
            <button
            key={type}
            className={`type-btn ${selectedTypes.includes(type) ? "selected" : ""}`}
            onClick={() => handleTypeSelect(type)}
            >
            {type}
            </button>
        ))}
        </div>

        <div className="selected-types">
        {selectedTypes.map((type) => (
            <div key={type} className="selected-type">
            {type}
            <button  onClick={() => handleTypeRemove(type)}>x</button>
            </div>
        ))}
        </div>

        <textarea
        className="create-textarea"
        value={postText}
        onChange={handleTextChange}
        placeholder="feedify your thoughts..."
        maxLength={maxChars}
        />
        
        <div className="character-count">
        {postText.length}/{maxChars} characters
        </div>

        <div className="file-input-wrapper">
        <label htmlFor="file-upload">Select an Image</label>
        <input
            id="file-upload"
            type="file"
            accept="image/jpeg, image/png"
            onChange={handleFileChange}
        />
        
        </div>

        {file && (
        <div className="create-image-preview">
            <img src={file} alt="Preview" />
            <button className='popup-close' onClick={() => handleImageRemove()}>x</button>
        </div>
        )}

        {errorMessage && <div className="create-error">{errorMessage}</div>}

        <div className="create-buttons">
        <button className="decline-btn" onClick={handleDecline}>Decline</button>
        <button className="post-btn" onClick={handlePostSubmit}>
            Post
        </button>
        </div>
        {popup.visible && (
                <PopUp
                    message={popup.message}
                    type={popup.type}
                    onClose={() => setPopup({ visible: false, message: "", type: "" })}
                />
            )}
    </div>
    );
    };

export default CreatePost;
