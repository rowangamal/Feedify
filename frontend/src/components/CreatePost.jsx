import { useState } from 'react';
import '../styles/CreatePost.css'; 

function CreatePost (){

    const [postText, setPostText] = useState("");
    const [selectedTypes, setSelectedTypes] = useState([]);
    const [file, setFile] = useState(null);
    const [errorMessage, setErrorMessage] = useState("");

    const maxChars = 1000;

    // Handle text change
    const handleTextChange = (e) => {
    const text = e.target.value;
    if (text.length <= maxChars) {
        setPostText(text);
    }
    };

    // Handle file change
    const handleFileChange = (e) => {
    const selectedFile = e.target.files[0];
    if (selectedFile) {
        setFile(URL.createObjectURL(selectedFile));
    }
    };

    // Handle type selection
    const handleTypeSelect = (type) => {
    if (!selectedTypes.includes(type)) {
        setSelectedTypes([...selectedTypes, type]);
    }
    };

    // Handle type removal
    const handleTypeRemove = (type) => {
    setSelectedTypes(selectedTypes.filter((item) => item !== type));
    };

    // Handle post submission
    const handlePostSubmit = () => {
    if (postText.trim() === "") {
        setErrorMessage("Post text cannot be empty");
    } else {
        // Submit the post logic here
        console.log("Post submitted");
        setPostText("");
        setSelectedTypes([]);
        setFile(null);
        setErrorMessage("");
    }
    };

    return (
    <div className="create-container">
        <div className="create-header">
        <img
            src="https://via.placeholder.com/40" // Replace with actual user avatar URL
            alt="User Avatar"
            className="user-avatar"
        />
        <span>UserName</span>
        </div>

        {/* Types selection */}
        <div className="create-types">
        {["Sport", "Technology", "Music"].map((type) => (
            <button
            key={type}
            className={`type-btn ${selectedTypes.includes(type) ? "selected" : ""}`}
            onClick={() => handleTypeSelect(type)}
            >
            {type}
            </button>
        ))}
        </div>

        {/* Display selected types */}
        <div className="selected-types">
        {selectedTypes.map((type) => (
            <div key={type} className="selected-type">
            {type}
            <button onClick={() => handleTypeRemove(type)}>x</button>
            </div>
        ))}
        </div>

        {/* Text area */}
        <textarea
        className="create-textarea"
        value={postText}
        onChange={handleTextChange}
        placeholder="feedify your thoughts..."
        maxLength={maxChars}
        />
        
        {/* Character Count */}
        <div className="character-count">
        {postText.length}/{maxChars} characters
        </div>

        {/* File upload */}
        <div className="file-input-wrapper">
        <label htmlFor="file-upload">Select an Image</label>
        <input
            id="file-upload"
            type="file"
            accept="image/jpeg, image/png"
            onChange={handleFileChange}
        />
        </div>

        {/* Preview image */}
        {file && (
        <div className="create-image-preview">
            <img src={file} alt="Preview" />
        </div>
        )}

        {/* Error message */}
        {errorMessage && <div className="create-error">{errorMessage}</div>}

        {/* Buttons */}
        <div className="create-buttons">
        <button className="decline-btn">Decline</button>
        <button className="post-btn" onClick={handlePostSubmit}>
            Post
        </button>
        </div>
    </div>
    );
    };

export default CreatePost;
