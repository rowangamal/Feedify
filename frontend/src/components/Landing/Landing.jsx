import styles from './Landing.module.css';
function Landing() {
  return (
      <div className={styles['landing-container']}>
          <h1>Welcome to Our Service</h1>
          <p>Sign up now to get started!</p>
      </div>
  );
}

export default Landing;