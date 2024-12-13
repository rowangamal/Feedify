import { signup } from "../../services/api.js";
import { useState } from 'react';
import styles from './SignupForm.module.css';
import GoogleSignin from '../GoogleAuth/GooglesSignin.jsx';
import { useNavigate } from "react-router-dom";
import Landing from '../Landing/Landing.jsx';

const Signup = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        username: '',
        gender: '',
        dateOfBirth: '',
        email: '',
        password: '',
    });
    const [errorMessage, setErrorMessage] = useState('');
    const [showErrorPopup, setShowErrorPopup] = useState(false);
    const [touchedFields, setTouchedFields] = useState({});
    const [passwordRequirements, setPasswordRequirements] = useState({
        length: false,
        uppercase: false,
        lowercase: false,
        number: false,
        specialChar: false,
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({ ...prevData, [name]: value }));

        if (name === 'password') {
            checkPasswordRequirements(value);
        }
    };

    const handleBlur = (e) => {
        const { name } = e.target;
        setTouchedFields((prevTouched) => ({ ...prevTouched, [name]: true }));
    };

    const checkPasswordRequirements = (password) => {
        setPasswordRequirements({
            length: password.length >= 8,
            uppercase: /[A-Z]/.test(password),
            lowercase: /[a-z]/.test(password),
            number: /\d/.test(password),
            specialChar: /[@$!%*?&]/.test(password),
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        formData.gender = formData.gender === 'male';
        console.log('Form data:', formData);

        const emailPattern = /^[a-z0-9._%+-]+@gmail\.com$/;
        if (!emailPattern.test(formData.email)) {
            setErrorMessage('Please enter a valid Gmail address.');
            setShowErrorPopup(true);
            return;
        }

        if (formData.username.length < 4) {
            setErrorMessage('Username must be at least 4 characters long.');
            setShowErrorPopup(true);
            return;
        }

        const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        if (!passwordPattern.test(formData.password)) {
            setErrorMessage('Password must be at least 8 characters long and include uppercase letters, lowercase letters, numbers, and special characters.');
            setShowErrorPopup(true);
            return;
        }

        try {
            const response = await signup(formData);
            if (response && response.status === 201) {
                navigate('/login');
            } else {
                setErrorMessage('Signup failed. Please try again.');
                setShowErrorPopup(true);
            }
        } catch (error) {
            console.error('Signup error:', error);
            if (error.response && error.response.status === 403) {
                setErrorMessage('You do not have permission to perform this action.');
            } else {
                setErrorMessage(error.message || 'Network error. Please try again.');
            }
            setShowErrorPopup(true);
        }
    };

    const today = new Date().toISOString().split('T')[0];


    const isFieldInvalid = (fieldName) =>
        touchedFields[fieldName] && !formData[fieldName];

    return (
        <div className={styles['signup-container']}>
            <div className={styles['left-panel']}>
                <div className={styles['form-header']}>
                    <div className={styles['form-header']}>
                        <div className={styles['logo']}>
                        <img
                            srcSet="../../../public/Assets/logo.png 1x, ../../logo@2x.png 2x, ../../logo@3x.png 3x"
                            src="../../../public/Assets/logo.png"
                            alt="Feedify logo"/>
                        <h1>FEEDIFY</h1>
                        </div>
                    </div>
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

                        {isFieldInvalid('firstName') && <span className={styles['error-text']}>First name is required.</span>}

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

                        {isFieldInvalid('lastName') && <span className={styles['error-text']}>Last name is required.</span>}

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

                        {isFieldInvalid('username') && <span className={styles['error-text']}>Username is required and must be at least 4 characters long.</span>}

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

                        {isFieldInvalid('gender') && <span className={styles['error-text']}>Gender is required.</span>}

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

                            max={today}
                            className={isFieldInvalid('dateOfBirth') ? styles['invalid'] : ''}
                        />
                        {isFieldInvalid('dateOfBirth') && <span className={styles['error-text']}>Date of birth is required.</span>}

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

                            placeholder="john.doe@gmail.com"
                            pattern="[a-z0-9._%+-]+@gmail\\.com$"
                            title="Enter a valid email address."
                            className={isFieldInvalid('email') ? styles['invalid'] : ''}
                        />
                        {isFieldInvalid('email') && <span className={styles['error-text']}>A valid Gmail address is required.</span>}

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

                        <ul className={styles['password-requirements']}>
                            <li className={passwordRequirements.length ? styles['valid'] : styles['invalid']}>At least 8 characters</li>
                            <li className={passwordRequirements.uppercase ? styles['valid'] : styles['invalid']}>At least one uppercase letter</li>
                            <li className={passwordRequirements.lowercase ? styles['valid'] : styles['invalid']}>At least one lowercase letter</li>
                            <li className={passwordRequirements.number ? styles['valid'] : styles['invalid']}>At least one number</li>
                            <li className={passwordRequirements.specialChar ? styles['valid'] : styles['invalid']}>At least one special character (@$!%*?&)</li>
                        </ul>
                    </div>
                    <button className={styles['signup-btn']}>Sign Up</button>
                </form>
                <div className={styles['divider']}>OR</div>
                <div className={styles['google-signup-container']}>
                    <GoogleSignin/>
                </div>
                <button className={styles['secondary-btn']} onClick={() => navigate('/login')}>
                    Already Have an account? Login
                </button>
                {showErrorPopup && errorMessage && (
                    <div className={styles['error-popup']}>
                        <p>{errorMessage}</p>
                        <button className={styles['primary-btn']} onClick={() => setShowErrorPopup(false)}>
                            Close
                        </button>
                    </div>
                )}
            </div>
            <Landing />
        </div>
    );
};

export default Signup;

