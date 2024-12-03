import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Login from './components/Login/login.jsx';
import Signup from './components/Signup/signup.jsx';
import { AuthProvider } from './contexts/AuthContext';  


const isAuthenticated = () => {
    localStorage.removeItem('jwttoken');
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
                    {/* Public Routes */}
                    {/* <Route path="/" element={isAuthenticated() ? <Home />: <Login />} />  */}
                    {/* <Route path="/login" element={isAuthenticated() ? <Home />: <Login />} />  */}

                    <Route path="/login" element={<Login />} />
                    <Route path="/signup" element={<Signup />} />


                </Routes>
            </Router>
        </AuthProvider>
    );
}

export default App;
