import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
// import Login from './login.jsx';
import Signup from './components/Signup/signup.jsx';

function App() {
    return (
        <Router>
            <Routes>
                {/*<Route path="/login" element={<Login />} />*/}
                <Route path="/signup" element={<Signup />} />
            </Routes>
        </Router>
    );
}

export default App;