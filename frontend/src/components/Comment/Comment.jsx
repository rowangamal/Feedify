import { useState, useEffect } from 'react';
import axios from 'axios';
import './styles.css';

function Comment({ postId, userId, isOpen, onClose }) {
    const [comments, setComments] = useState([]);
    const [newComment, setNewComment] = useState('');
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        if (isOpen) {
            fetchComments();
        }
    }, [isOpen, postId]);

    const fetchComments = async () => {
        try {
            console.log(postId);
            console.log(localStorage.getItem('jwttoken'));
            const response = await axios.get(`http://localhost:8080/comment`, {
                params: {
                    postId: postId
                },
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('jwttoken')}`
                }
            });
            setComments(response.data);
            setIsLoading(false);
        } catch (error) {
            console.error('Error fetching comments:', error);
            setIsLoading(false);
        }
    };

    const handleSubmitComment = async () => {
        if (!newComment.trim()) return;

        try {
            await axios.post('http://localhost:8080/comment', {
                content: newComment,
                postId: postId,
            }, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('jwttoken')}`
                }
            });
            setNewComment('');
            fetchComments();
        } catch (error) {
            console.error('Error posting comment:', error);
        }
    };

    const handleDeleteComment = async (commentId) => {
        try {
            await axios.delete(`http://localhost:8080/comment?id=${commentId}`,
                {
                    headers: {
                        "Authorization": `Bearer ${localStorage.getItem("jwttoken")}`
                    }
                });
            fetchComments();
        } catch (error) {
            console.error('Error deleting comment:', error);
        }
    };

    if (!isOpen) return null;

    return (
        <div className="comment-popup-overlay" onClick={onClose}>
            <div className="comment-popup-container" onClick={e => e.stopPropagation()}>
                <div className="comment-popup-header">
                    <h2 className="comment-popup-title">Comments</h2>
                    <button className="comment-popup-close" onClick={onClose}>✕</button>
                </div>

                <div className="comment-list">
                    {isLoading ? (
                        <div className="loading-message">Loading comments...</div>
                    ) : comments.length === 0 ? (
                        <div className="empty-message">No comments yet</div>
                    ) : (
                        comments.map((comment) => (
                            <div key={comment.id} className="comment-item">
                                <div className="comment-header">
                                    <div className="comment-user-info">
                                        <div className="comment-username">{comment.username}</div>
                                        <div className="comment-timestamp">{comment.createdAt}</div>
                                    </div>
                                    {userId === comment.userId && (
                                        <button
                                            className="comment-delete"
                                            onClick={() => handleDeleteComment(comment.id)}
                                        >
                                            Delete
                                        </button>
                                    )}
                                </div>
                                <p className="comment-content">{comment.content}</p>
                            </div>
                        ))
                    )}
                </div>

                <div className="comment-input-container">
                    <div className="comment-input-wrapper">
                        <input
                            type="text"
                            value={newComment}
                            onChange={(e) => setNewComment(e.target.value)}
                            placeholder="Write a comment..."
                            className="comment-input"
                        />
                        <button
                            onClick={handleSubmitComment}
                            className="comment-submit"
                        >
                            Comment
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Comment;