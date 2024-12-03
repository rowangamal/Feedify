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
        const response = login(formData);
        if (response) {
            const { userId, isAdmin } = response;
            setIsAdmin(isAdmin);
        };
        console.log('Logging in with:', formData);
    };

    const isFieldInvalid = (fieldName) =>
        touchedFields[fieldName] && !formData[fieldName];
    

    return (
        <div className={styles['login-container']}>
            <div className={styles['form-header']}>
                <img src="/src/assets/logo.png" alt="Feedify Logo" className={styles.logo} />
                <h2>Login</h2>
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
                        pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"
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
                <button type="submit" className={styles['primary-btn']}>Login</button>
            </form>
            <div className={styles.divider}>OR</div>
            <button className={styles['google-btn']}>
                <img src="/src/assets/google.png" alt="Google Icon" /> Continue with Google
            </button>
            <p className={styles['signup-link']}>
                Don’t have an account? <a href="/signup">Sign Up</a>
            </p>
        </div>
    );
};

export default Login;
