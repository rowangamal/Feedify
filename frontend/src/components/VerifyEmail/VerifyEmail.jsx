import styles from './VerifyEmail.module.css';
import { useNavigate, useLocation } from "react-router-dom";
import { useState } from 'react';
import Landing from '../Landing/Landing.jsx';
import axios from 'axios';

function VerifyEmail() {
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
      console.log(otpDTO.otp)
      console.log(email)
      try {
        const response = await axios.post('http://localhost:8080/signup/verify-email-otp', otpDTO);
      
        if (response.status === 200) {
          navigate("/login", { state: { email } });
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
        const response = await axios.post('http://localhost:8080/signup/request-verification-otp', emailDTO);
      
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
        <div className={styles['verify-email-container']}>
      <div className={styles['left-panel']}>
        <div className={styles['logo']}>
          <img src="../../feedifyIcon.png" alt="feedify logo"/>
          <h1>FEEDIFY</h1>
        </div>
        <h1 className={styles['verify-title']}>Check your Email to verify your account</h1>
        <form className={styles['verify-form']}>
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

export default VerifyEmail;
