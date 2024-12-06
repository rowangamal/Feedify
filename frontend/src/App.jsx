import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
// import Login from './login.jsx';
import Signup from './components/Signup/signup.jsx';
import Home from './components/SignupForm/Home.jsx';

function App() {
    return (
        <Router>
            <Routes>
                {/*<Route path="/login" element={<Login />} />*/}
                <Route path="/signup" element={<Signup />} />
                <Route path="/home" element={<Home />} />
            </Routes>
        </Router>
    );
}

export default App;