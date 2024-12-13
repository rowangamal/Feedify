import { useState } from 'react';
import styles from './LoginForm.module.css';
import { login } from '../../services/api';
import { useContext } from 'react';
import { AuthContext } from '../../contexts/AuthContext';
import GoogleSignin from '../GoogleAuth/GooglesSignin';
import { useNavigate } from 'react-router-dom';
import Landing from '../Landing/Landing.jsx';

const Login = () => {
    const navigate = useNavigate();
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
                    navigate('/home');
                }
            })
            .catch((error) => {
                setErrorMessage(error.message);
                setShowErrorPopup(true);
            });
    };

    const handleOnForgetPasswordClick = () => {
        navigate('/forget-password')
    };

    const isFieldInvalid = (fieldName) =>
        touchedFields[fieldName] && !formData[fieldName];
    
    return (
        <div className={styles['login-container']}>
            <div className={styles['left-panel']}>
                <div className={styles['form-header']}>
                    <div className={styles['logo']}>
                    <img src="../../../public/Assets/Logo.png" alt="Feedify logo"/>
                    <h1>FEEDIFY</h1>
                </div>
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
                    <div className={styles['forgot-password-container']}>
                    <button
                        type="button"
                        className={styles['forgot-password-btn']}
                        onClick={handleOnForgetPasswordClick} >
                        Forgot Password?
                    </button>
                    </div>
                    <button type="submit" className={styles['login-btn']}>Login</button>
                </form>
                <div className={styles.divider}>OR</div>
                <div className={styles['google-signin-container']}>
                    <GoogleSignin />
                </div>
                <button className={styles['secondary-btn']} onClick={() => navigate('/signup')}>
                    Don’t have an account?
                </button>
                {showErrorPopup && (
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

export default Login;