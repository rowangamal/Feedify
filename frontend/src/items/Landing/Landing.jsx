import styles from './Landing.module.css';
function Landing() {
  return (
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
  );
}

export default Landing;