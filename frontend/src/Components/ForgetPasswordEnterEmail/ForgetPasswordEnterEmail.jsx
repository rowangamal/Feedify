import styles from './ForgetPasswordEnterEmail.module.css';
import { useNavigate } from "react-router-dom";
import { useState } from 'react';

function ForgetPasswordEnterEmail() {
  const navigate = useNavigate();

  const handleFormSubmit = (e) => {
    e.preventDefault();
    navigate("/otp-verification");
  };

  const [email, setEmail] = useState('');
  const [error, setError] = useState('');

  const validateEmail = (value) => {
    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

    if (!emailRegex.test(value)) {
      return 'Invalid email address. Please enter a valid email.';
    }

    const allowedDomains = ['gmail.com', 'yahoo.com', 'outlook.com'];
    const domain = value.split('@')[1];
    if (domain && !allowedDomains.includes(domain)) {
      return 'Email must belong to a supported domain (e.g., gmail.com, yahoo.com).';
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
        <button className={styles['back-button']}>←</button>
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
      <div className={styles['right-panel']}>
        <div className={styles['graphic-container']}>
          <img
            srcSet="../../../public/Assets/main_character.png 1x, ../../main_character@2x.png 2x, ../../main_character@3x.png 3x"
            src="../../../public/Assets/main_character.png"
            alt="main character"
          />
        </div>
        <p className={styles['slogan']}>World Between Yours</p>
      </div>
    </div>
  );
}

export default ForgetPasswordEnterEmail;
