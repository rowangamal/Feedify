import styles from './ForgetPasswordEnterOTP.module.css';
import { useNavigate } from "react-router-dom";
import { useState } from 'react';

function ForgetPasswordEnterOTP() {
    const navigate = useNavigate();

    const handleOTPSubmit = (e) => {
        e.preventDefault();
        navigate("/new-password-confirmation");
    };

    const handleResend = () => {
      
    }

    const [otp, setOtp] = useState(["", "", "", "", ""]);

    const handleChange = (value, index) => {
    if (value.length <= 1) {
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
        <form className={styles['reset-form']}>
          <label htmlFor="email" className={styles['email-label']}>
            Enter Your Email
          </label>
          <div className={styles['email-input-wrapper']}>
            <input
              id="email"
              placeholder="user@example.com"
              className={styles['email-input']}
            />
          </div>
          <div className={styles["otp-container"]}>
              <h2>OTP Verification</h2>
              <p>Enter the verification code we just sent to your email address</p>
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
  };

export default ForgetPasswordEnterOTP;
