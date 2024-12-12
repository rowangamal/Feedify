import { useState } from 'react';
import styles from './ForgetPasswordEnterPassword.module.css';
import { useNavigate, useLocation } from "react-router-dom";
import axios from 'axios';
import Landing from '../Landing/Landing';

function ForgetPasswordEnterPassword() {
  const navigate = useNavigate();
  const location = useLocation();
  const email = location.state?.email;
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [passwordError, setPasswordError] = useState('');
  const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

  // Handle password input
  const handlePasswordChange = (e) => {
    const newPassword = e.target.value;
    setPassword(newPassword);

    // Validate password strength
    if (!passwordRegex.test(newPassword)) {
      setPasswordError(
        'Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one digit, and one special character.'
      );
    } else {
      setPasswordError('');
    }

    validatePasswords(newPassword, confirmPassword);
  };

  // Handle confirm password input
  const handleConfirmPasswordChange = (e) => {
    const newConfirmPassword = e.target.value;
    setConfirmPassword(newConfirmPassword);
    validatePasswords(password, newConfirmPassword);
  };

  // Validate passwords in real-time
  const validatePasswords = (pass, confirmPass) => {
    if (pass && confirmPass && pass !== confirmPass) {
      setErrorMessage('Passwords do not match');
    } else {
      setErrorMessage('');
    }
  };

  // Handle reset button click
  const handleReset = async () => {
    if (!passwordRegex.test(password)) {
      setPasswordError(
        'Password does not meet the required criteria.'
      );
      return;
    }

    if (password && confirmPassword && password === confirmPassword) {
      const resetPasswordDTO = { 
        email,
        newPassword: password
      };

      try {
        const response = await axios.post('http://localhost:8080/change-password', resetPasswordDTO);
        if (response.status === 200 && response.data.status === 200) {
          navigate('/login');
        } else {
          console.error("wrong otp");
        }
      } catch (error) {
        console.error('Error during OTP verification:', error);
      }
      setErrorMessage('');
    } else {
      setErrorMessage('Passwords do not match');
    }
  };

  return (
    <div className={styles['forget-password-container']}>
      <div className={styles['left-panel']}>
        <div className={styles['logo']}>
          <img
            srcSet="../../../public/Assets/logo.png 1x, ../../logo@2x.png 2x, ../../logo@3x.png 3x"
            src="../../../public/Assets/logo.png"
            alt="Feedify logo"
          />
          <h1 className={`${styles['gradient-text']} ${styles['title-name']}`}>
            FEEDIFY
          </h1>
        </div>
        <p>Password</p>
        <div className={styles['input-group']}>
          <input
            type="password"
            placeholder="Enter new password"
            value={password}
            onChange={handlePasswordChange}
          />
          {passwordError && <p className={styles['error-message']}>{passwordError}</p>}
        </div>
        <div className={styles['input-group']}>
          <input
            type="password"
            placeholder="Confirm new password"
            value={confirmPassword}
            onChange={handleConfirmPasswordChange}
          />
          {errorMessage && <p className={styles['error-message']}>{errorMessage}</p>}
        </div>
        <button className={styles['reset-button']} onClick={handleReset}>
          Reset
        </button>
      </div>
      <Landing />
    </div>
  );
}

export default ForgetPasswordEnterPassword;
