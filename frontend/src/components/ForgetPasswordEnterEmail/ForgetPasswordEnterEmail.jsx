import styles from './ForgetPasswordEnterEmail.module.css';
import { useNavigate } from "react-router-dom";
import { useState } from 'react';
import Landing from '../Landing/Landing.jsx';
import axios from 'axios';

function ForgetPasswordEnterEmail() {
  const navigate = useNavigate();

  const handleFormSubmit = async (e) => {
    e.preventDefault();
    
    const validationError = validateEmail(email);
    if (validationError) {
      setError(validationError);
      return;
    }
    setError('');

    const emailDTO = {
      email,
    };

    try {
      const response = await axios.post('http://localhost:8080/request-password-reset', emailDTO);
    
      if (response.status === 200) {
        navigate("/otp-verification", { state: { email } });
      } else {
        setError(response.data.message);
      }
    } catch (error) {
      if (error.response) {
        setError(error.response.data);
      } else if (error.request) {
        setError("No response from server. Please check your connection.");
      } else {
        setError("An unexpected error occurred. Please try again.");
      }
    
      console.error("Error during password reset request:", error);
    }    
  };

  const [email, setEmail] = useState('');
  const [error, setError] = useState('');

  const validateEmail = (value) => {
    const emailRegex = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$/i;

    if (!emailRegex.test(value)) {
      return 'Invalid email address. Please enter a valid email.';
    }
    return '';
  };

  const handleInputChange = (e) => {
    const value = e.target.value;
    setEmail(value);

    const validationError = validateEmail(value);
    setError(validationError);
  };

  return (
    <div className={styles['forget-password-container']}>
      <div className={styles['left-panel']}>
        <div className={styles['logo']}>
          <img src="../../feedifyIcon.png" alt="feedify logo"/>
          <h1>FEEDIFY</h1>
        </div>
        <h1 className={styles['reset-title']}>Reset Your Password</h1>
        <form onSubmit={handleFormSubmit} className={styles['reset-form']}>
          <label htmlFor="email" className={styles['email-label']}>
            Enter Your Email
          </label>
          <div className={styles['email-input-wrapper']}>
            <input
              id="email"
              placeholder="user@example.com"
              className={`${styles['email-input']} ${error ? styles['input-error'] : ''}`}
              value={email}
              onChange={handleInputChange}
            />
          </div>
          {error && <p className={styles['error-text']}>{error}</p>}
          <button type="submit" className={styles['send-email-button']} disabled={!!error}>
            Send Email
          </button>
        </form>
      </div>
      <Landing />
    </div>
  );
}

export default ForgetPasswordEnterEmail;
