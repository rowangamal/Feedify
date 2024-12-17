import { useState, useEffect } from 'react';
import { AlertTriangle } from 'lucide-react';
import '../../styles/ReportDialog.css';
import axios from "axios";

function ReportDialog({ isOpen, type, id }) {
    const [reason, setReason] = useState('');
    const [isAnimating, setIsAnimating] = useState(false);

    useEffect(() => {
        if (isOpen) {
            setIsAnimating(true);
            const timer = setTimeout(() => setIsAnimating(false), 500);
            return () => clearTimeout(timer);
        }
    }, [isOpen]);

    console.log("id",id)
    const handleSubmit = (e) => {
        e.preventDefault();
        if(type === "User"){
            reportUser();
        }
        else{
            reportPost();
        }
        onClose();
    };

    const reportPost = () => {
        axios.post('http://localhost:8080/report/post/newReport',
            {
                postID: id,
                reason: reason,
            },
            {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("jwttoken"),
                }
            }
        )
            .then(response => {
                console.log(response);
            })
            .catch(error => {
                console.log(error);
            });
    }

    const reportUser = () => {
        axios.post('http://localhost:8080/report/user/newReport',
            {
                reportedID: id,
                reason: reason,
            },
            {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("jwttoken"),
                }
            }
        )
            .then(response => {
                console.log(response);
            })
            .catch(error => {
                console.log(error);
            });
    }

    const onClose = () => {
        isOpen = false;
    }

    if (!isOpen) return null;

    return (
        <div className="dialog-overlay" onClick={onClose}>
            <div
                className={`dialog-content ${isAnimating ? 'dialog-angry' : ''}`}
                onClick={e => e.stopPropagation()}
            >
                <div className="dialog-header">
                    <AlertTriangle className="dialog-icon" />
                    <h2 className="dialog-title">Report {type}</h2>
                </div>
                <form onSubmit={handleSubmit}>
          <textarea
              value={reason}
              onChange={(e) => setReason(e.target.value)}
              placeholder={`Why are you reporting this ${type}?`}
              className="dialog-textarea"
              required
          />
                    <div className="dialog-actions">
                        <button type="button" onClick={onClose} className="dialog-button cancel">
                            Cancel
                        </button>
                        <button type="submit" className="dialog-button submit">
                            Submit Report
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}


export default ReportDialog;