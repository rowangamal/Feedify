import Login from '../LoginForm/LoginForm.jsx';
import LandingPage from '../Landing/Landing.jsx';
import styles from './Login.module.css';

function Login() {
    return (
        <div className={styles['login-container']}>
            <LoginForm />
            <LandingPage />
        </div>
    );
}

export default Login;