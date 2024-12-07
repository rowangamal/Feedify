import React, { useState } from 'react';
import styles from './LoginForm.module.css';
import { login } from '../../services/api';
import { useContext } from 'react';
import { AuthContext } from '../../contexts/AuthContext';

const Login = () => {
    const [formData, setFormData] = useState({
        email: '',
        password: '',
    });
    const { setIsAdmin } = useContext(AuthContext);
    const [errorMessage, setErrorMessage] = useState('');
    const [showErrorPopup, setShowErrorPopup] = useState(false);
    const [touchedFields, setTouchedFields] = useState({});

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({ ...prevData, [name]: value }));
    };

    const handleBlur = (e) => {
        const { name } = e.target;
        setTouchedFields((prevTouched) => ({ ...prevTouched, [name]: true }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        login(formData)
            .then((response) => {
                if (response) {
                    const { userId, isAdmin } = response;
                    setIsAdmin(isAdmin);
                    window.location.href = '/';
                }
            })
            .catch((error) => {
                setErrorMessage(error.message);
                setShowErrorPopup(true);
            });
    };


    const isFieldInvalid = (fieldName) =>
        touchedFields[fieldName] && !formData[fieldName];
    

    return (
        <div className={styles['login-container']}>
            <div className={styles['form-header']}>
                <img src="/src/assets/logo.png" alt="Feedify Logo" className={styles.logo} />
                <h2>Log into your account</h2>
            </div>
            <form className={styles['form']} onSubmit={handleSubmit}>
                <div className={styles['form-group']}>
                    <label>Email</label>
                    <input
                        type="email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        required
                        placeholder="john.doe@example.com"
                        pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$"
                        title="Enter a valid email address."
                        className={isFieldInvalid('email') ? styles['invalid'] : ''}
                    />
                </div>
                <div className={styles['form-group']}>
                    <label>Password</label>
                    <input
                        type="password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        required
                        placeholder="Enter your password"
                        minLength="8"
                        title="Password should be at least 8 characters long."
                        className={isFieldInvalid('password') ? styles['invalid'] : ''}
                    />
                </div>
                {/* Forgot Password button */}
                <div className={styles['forgot-password-container']}>
                    <button
                        type="button"
                        className={styles['forgot-password-btn']}
                        onClick={() => alert("Redirecting to Forgot Password page...")} // Replace with actual functionality
                    >
                        Forgot Password?
                    </button>
                </div>
                <button type="submit" className={styles['primary-btn']}>Login</button>
            </form>
            <div className={styles.divider}>OR</div>
            <button className={styles['google-btn']}>
                <img src="/src/assets/google.png" alt="Google Icon" /> Continue with Google
            </button>
            <p className={styles['signup-link']}>
                Don’t have an account? <a href="/signup">Sign Up</a>
            </p>
            {showErrorPopup && (
                <div className={styles['error-popup']}>
                    <p>{errorMessage}</p>
                    <button className={styles['primary-btn']} onClick={() => setShowErrorPopup(false)}>
                        Close
                    </button>
                </div>
            )}
        </div>
    );
};

export default Login;
