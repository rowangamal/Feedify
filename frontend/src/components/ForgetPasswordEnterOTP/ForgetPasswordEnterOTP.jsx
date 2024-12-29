import styles from './ForgetPasswordEnterOTP.module.css';
import { useNavigate, useLocation } from "react-router-dom";
import { useState } from 'react';
import Landing from '../Landing/Landing.jsx';
import axios from 'axios';

function ForgetPasswordEnterOTP() {
    const location = useLocation();
    const email = location.state?.email;
    const navigate = useNavigate();
    const [error, setError] = useState('');

    const handleOTPSubmit = async (e) => {
      e.preventDefault();
      const otpDTO = { 
        email,
        otp: otp.join('')
      };

      try {
        const response = await axios.post('http://localhost:8080/verify-otp', otpDTO);
      
        if (response.status === 200) {
          navigate("/new-password-confirmation", { state: { email } });
        } else {
          setError(response.data.message || "Unexpected error occurred.");
        }
      } catch (error) {
        if (error.response) {
          setError(error.response.data.message || "Invalid OTP. Please try again.");
        } else if (error.request) {
          setError("No response from server. Please check your connection.");
        } else {
          setError("An unexpected error occurred. Please try again.");
        }
        console.error("Error during OTP verification:", error);
      }
    };

    const handleResend = async (e) => {
      e.preventDefault();

      const emailDTO = {
        email,
      };
  
      try {
        const response = await axios.post('http://localhost:8080/request-password-reset', emailDTO);
      
        if (response.status === 200) {
          // DO nothing
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
    }

    const [otp, setOtp] = useState(["", "", "", "", ""]);

    const handleChange = (value, index) => {
      if (/^[0-9]$/.test(value) || value === "") {
        const newOtp = [...otp];
        newOtp[index] = value;
        setOtp(newOtp);
  
        if (value !== "" && index < otp.length - 1) {
          document.getElementById(`otp-${index + 1}`).focus();
        }
      }
    };
  
    const handleKeyDown = (e, index) => {
      if (e.key === "Backspace" && !otp[index] && index > 0) {
        document.getElementById(`otp-${index - 1}`).focus();
      }
    };

    return (
        <div className={styles['forget-password-container']}>
      <div className={styles['left-panel']}>
        <div className={styles['logo']}>
          <img src="../../feedifyIcon.png" alt="feedify logo"/>
          <h1>FEEDIFY</h1>
        </div>
        <h1 className={styles['reset-title']}>Reset Your Password</h1>
        <form className={styles['reset-form']}>
          <label htmlFor="email" className={styles['email-label']}>
            Enter Your Email
          </label>
          <div className={styles['email-input-wrapper']}>
            <input
              id="email"
              placeholder={email || "user@example.com"}
              className={`${styles['email-input']} ${styles['disabled']}`}
              disabled
            />
          </div>
          <div className={styles["otp-container"]}>
              <h2>OTP Verification</h2>
              <p className={styles["enter-code"]}>Enter the verification code we just sent to your email address</p>
              <div className={styles["otp-inputs"]}>
              {otp.map((_, index) => (
                  <input
                  key={index}
                  id={`otp-${index}`}
                  type="text"
                  maxLength="1"
                  value={otp[index]}
                  onChange={(e) => handleChange(e.target.value, index)}
                  onKeyDown={(e) => handleKeyDown(e, index)}
                  className={styles["otp-input"]}
                  />
              ))}
              </div>
              {error && <p className={styles['error-text']}>{error}</p>}
              <button onClick={handleOTPSubmit} className={styles["verify-button"]}>
              Verify
              </button>
              <p className={styles["resend"]}>
              Did not receive a code?{" "}
              <span onClick={handleResend} className={styles["resend-link"]}>
                  Resend
              </span>
              </p>
          </div>
        </form>
      </div>
      <Landing />
    </div>
    );
  };

export default ForgetPasswordEnterOTP;
