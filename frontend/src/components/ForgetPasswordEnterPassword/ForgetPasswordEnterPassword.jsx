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
  const [showErrorPopup, setShowErrorPopup] = useState(false);
  const [passwordRequirements, setPasswordRequirements] = useState({
    length: false,
    uppercase: false,
    lowercase: false,
    number: false,
    specialChar: false,
    twoPasswordsAreEqual: true
  });

  const checkPasswordRequirements = (password, confirmPassword) => {
    setPasswordRequirements({
        length: password.length >= 8,
        uppercase: /[A-Z]/.test(password),
        lowercase: /[a-z]/.test(password),
        number: /\d/.test(password),
        specialChar: /[@$!%*?&]/.test(password),
        twoPasswordsAreEqual: password === confirmPassword
    });
  };

  const handlePasswordChange = (e) => {
    const newPassword = e.target.value;
    setPassword(newPassword);
    checkPasswordRequirements(newPassword, confirmPassword);
  };

  const handleConfirmPasswordChange = (e) => {
    const newConfirmPassword = e.target.value;
    setConfirmPassword(newConfirmPassword);
    checkPasswordRequirements(password, newConfirmPassword);
  };

  const handleResetPassword = async () => {
    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    if (!passwordPattern.test(password) || password !== confirmPassword) {
        setErrorMessage(
          'Password must be at least 8 characters long and include uppercase letters, lowercase letters, numbers, special characters and matches confirm psssword'
        );
        setShowErrorPopup(true);
        return;
    }

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
  };

  return (
    <div className={styles['forget-password-container']}>
      <div className={styles['left-panel']}>
        <div className={styles['logo']}>
          <img src="../../../public/Assets/Logo.png" alt="Feedify logo"/>
          <h1>FEEDIFY</h1>
        </div>
        <h1 className={styles['reset-title']}>Reset Your Password</h1>
        <div className={styles['input-group']}>
          <input
            type="password"
            placeholder="Enter new password"
            value={password}
            onChange={handlePasswordChange}
          />
        </div>
        <div className={styles['input-group']}>
          <input
            type="password"
            placeholder="Confirm new password"
            value={confirmPassword}
            onChange={handleConfirmPasswordChange}
          />
        </div>
        <ul className={styles['password-requirements']}>
            <li className={passwordRequirements.length ? styles['valid'] : styles['invalid']}>At least 8 characters</li>
            <li className={passwordRequirements.uppercase ? styles['valid'] : styles['invalid']}>At least one uppercase letter</li>
            <li className={passwordRequirements.lowercase ? styles['valid'] : styles['invalid']}>At least one lowercase letter</li>
            <li className={passwordRequirements.number ? styles['valid'] : styles['invalid']}>At least one number</li>
            <li className={passwordRequirements.specialChar ? styles['valid'] : styles['invalid']}>At least one special character (@$!%*?&)</li>
            <li className={passwordRequirements.twoPasswordsAreEqual ? styles['valid'] : styles['invalid']}>Two passwords match</li>
        </ul>
        <button className={styles['reset-button']} onClick={handleResetPassword}>
          Reset
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
}

export default ForgetPasswordEnterPassword;
