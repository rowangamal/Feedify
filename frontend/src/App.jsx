import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Login from './items/LoginForm/LoginForm.jsx';
import Signup from './items/SignupForm/SignupForm.jsx';
import { AuthProvider } from './contexts/AuthContext';
import Home from './items/Home.jsx';
import ForgetPasswordEnterEmail from './items/ForgetPasswordEnterEmail/ForgetPasswordEnterEmail';
import ForgetPasswordEnterOTP from './items/ForgetPasswordEnterOTP/ForgetPasswordEnterOTP';
import ForgetPasswordEnterPassword from './items/ForgetPasswordEnterPassword/ForgetPasswordEnterPassword';
import Profile from './items/UserProfile/Profile.jsx';

const isAuthenticated = () => {
    return localStorage.getItem('jwttoken') !== null;
};

// Protected Routes
const PrivateRoute = ({ element }) => {
    return isAuthenticated() ? element : <Navigate to="/login" />;
};

function App() {
    return (
        <AuthProvider>
            <Router>
                <Routes>
                    <Route path="/" element={isAuthenticated() ? <Home />: <Login />} />
                    <Route path="/login" element={isAuthenticated() ? <Home />: <Login />} />
                    <Route path="/forget-password" element={<ForgetPasswordEnterEmail />} />
                    <Route path="/otp-verification" element={<ForgetPasswordEnterOTP />} />
                    <Route path="/new-password-confirmation" element={<ForgetPasswordEnterPassword />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/signup" element={<Signup />} />
                    <Route path="/profile" element={<Profile />} />
                    <Route path='/Home' element={<Home/>}></Route>
                    {/* <Route path="/notifications" element={<Notifications />} />
                    <Route path="/settings" element={<Settings />} /> */}
                </Routes>
            </Router>
        </AuthProvider>
    );
}

export default App;

