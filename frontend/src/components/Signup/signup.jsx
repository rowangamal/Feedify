import SignupForm from '../SignupForm/SignupForm.jsx';
import LandingPage from '../Landing/Landing.jsx';
import styles from './Signup.module.css';

function Signup() {
    return (
        <div className={styles['signup-container']}>
            <SignupForm />
            <LandingPage />
        </div>
    );
}

export default Signup;