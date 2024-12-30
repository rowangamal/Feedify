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
import AdminList from './components/AdminDashboard/AdminList.jsx';
import PostReportList from './components/AdminDashboard/PostReportList.jsx';
import UserReportList from './components/AdminDashboard/UserReportList.jsx';
import AdminReportPage from './components/AdminDashboard/AdminReportPage.jsx';
import AdminTopicsPage from './components/AdminDashboard/AdminTopicsPage.jsx';

const isAuthenticated = () => {
    // localStorage.removeItem('jwttoken');
    return localStorage.getItem('jwttoken') !== null;
};

// Protected Routes
const PrivateRoute = () => {
    console.log(localStorage.getItem('isAdmin'));
    return isAuthenticated() && localStorage.getItem('isAdmin') == 'true' ? true : false;
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
                    {/* <Route path="/login" element={<Login />} /> */}
                    <Route path="/signup" element={isAuthenticated() ?<Home />: <Signup />} />
                    <Route path="/profile/:usernameInPath"element={isAuthenticated() ? <Profile /> : <Login />}/>
                    <Route path="/profile"element={isAuthenticated() ? <Profile /> : <Login />}/>
                    <Route path='/home' element={isAuthenticated() ? <Home />: <Login />}></Route>
                    <Route path='/admin' element={ PrivateRoute() ? <Tabs/> : <Home />}></Route>
                    <Route path='/admin/report' element={ PrivateRoute() ? <AdminReportPage/> : <Home />}></Route>
                    <Route path='admin/topics' element={ PrivateRoute() ? <AdminTopicsPage/> : <Home />}></Route>
                </Routes>
            </Router>
        </AuthProvider>
    );
}

export default App;

