import styles from './ForgetPasswordEnterPassword.module.css';
// import { useNavigate } from "react-router-dom";

function ForgetPasswordEnterPassword() {


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
        <p>Password</p>
        <div className={styles["input-group"]}>
          <input type="password" placeholder="Enter new password" />
        </div>
        <div className={styles["input-group"]}>
          <input type="password" placeholder="Confirm new password" />
        </div>
        <button className={styles["reset-button"]}>Reset</button>
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

export default ForgetPasswordEnterPassword;
