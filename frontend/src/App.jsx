import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Login from './components/LoginForm/LoginForm.jsx';
import Signup from './components/SignupForm/SignupForm.jsx';
import { AuthProvider } from './contexts/AuthContext';
import Home from './components/Home.jsx';
import ForgetPasswordEnterEmail from './components/ForgetPasswordEnterEmail/ForgetPasswordEnterEmail';
import ForgetPasswordEnterOTP from './components/ForgetPasswordEnterOTP/ForgetPasswordEnterOTP';
import ForgetPasswordEnterPassword from './components/ForgetPasswordEnterPassword/ForgetPasswordEnterPassword';
import Profile from './components/UserProfile/Profile.jsx';
import Tabs from './components/AdminDashboard/Tabs.jsx';

const isAuthenticated = () => {
    // localStorage.removeItem('jwttoken');
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
                    <Route path='/home' element={<Home/>}></Route>
                    {/* <Route path='/admin' element={<AdminList/>}></Route>
                    <Route path='/user' element={<UserList/>}></Route> */}
                    <Route path='/admin' element={<Tabs/>}></Route>
                    {/* <Route path="/notifications" element={<Notifications />} />
                    <Route path="/settings" element={<Settings />} /> */}
                </Routes>
            </Router>
        </AuthProvider>
    );
}

export default App;

