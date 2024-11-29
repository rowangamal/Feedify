import React, { useState } from 'react';
import styles from './SignupForm.module.css';

const Signup = () => {
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        username: '',
        gender: '',
        dateOfBirth: '',
        email: '',
        password: '',
    });

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
        // Form submission logic here
        console.log('Form Submitted', formData);
    };

    const isFieldInvalid = (fieldName) =>
        touchedFields[fieldName] && !formData[fieldName];

    return (
        <div className={styles['signup-container']}>
            <div className={styles['form-header']}>
                <img src="/src/assets/logo.png" alt="Feedify Logo" className={styles.logo} />
                <h2>Create New Account</h2>
            </div>
            <form className={styles['form']} onSubmit={handleSubmit}>
                <div className={styles['form-group-row']}>
                    <label>First Name</label>
                    <input
                        type="text"
                        name="firstName"
                        value={formData.firstName}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        required
                        placeholder="John"
                        pattern="[A-Za-z]+"
                        title="First name should only contain letters."
                        className={isFieldInvalid('firstName') ? styles['invalid'] : ''}
                    />
                </div>
                <div className={styles['form-group-row']}>
                    <label>Last Name</label>
                    <input
                        type="text"
                        name="lastName"
                        value={formData.lastName}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        required
                        placeholder="Doe"
                        pattern="[A-Za-z]+"
                        title="Last name should only contain letters."
                        className={isFieldInvalid('lastName') ? styles['invalid'] : ''}
                    />
                </div>
                <div className={styles['form-group']}>
                    <label>Username</label>
                    <input
                        type="text"
                        name="username"
                        value={formData.username}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        required
                        placeholder="johndoe"
                        minLength="3"
                        maxLength="15"
                        title="Username should be 3-15 characters long."
                        className={isFieldInvalid('username') ? styles['invalid'] : ''}
                    />
                </div>
                <div className={styles['form-group']}>
                    <label>Gender</label>
                    <select
                        name="gender"
                        value={formData.gender}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        required
                        className={isFieldInvalid('gender') ? styles['invalid'] : ''}
                    >
                        <option value="">Choose Gender</option>
                        <option value="male">Male</option>
                        <option value="female">Female</option>
                    </select>
                </div>
                <div className={styles['form-group-row']}>
                    <label>Date of Birth</label>
                    <input
                        type="date"
                        name="dateOfBirth"
                        value={formData.dateOfBirth}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        required
                        className={isFieldInvalid('dateOfBirth') ? styles['invalid'] : ''}
                    />
                </div>
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
                <button className={styles['primary-btn']}>Sign Up</button>
            </form>
            <div className={styles.divider}>OR</div>
            <button className={styles['secondary-btn']}>Already Have an account? Login</button>
            <button className={styles['google-btn']}>
                <img src="/src/assets/google.png" alt="Google Icon" /> Continue with Google
            </button>
        </div>
    );
};

export default Signup;
