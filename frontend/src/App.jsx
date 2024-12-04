import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ForgetPasswordEnterEmail from './Components/ForgetPasswordEnterEmail/ForgetPasswordEnterEmail';
import ForgetPasswordEnterOTP from './Components/ForgetPasswordEnterOTP/ForgetPasswordEnterOTP';
import ForgetPasswordEnterPassword from './Components/ForgetPasswordEnterPassword/ForgetPasswordEnterPassword';

function App() {
  return (
    <Router>
      <Routes>
          <Route path="/forget-password" element={<ForgetPasswordEnterEmail />} />
          <Route path="/otp-verification" element={<ForgetPasswordEnterOTP />} />
          <Route path="/new-password-confirmation" element={<ForgetPasswordEnterPassword />} />
      </Routes>
    </Router>
  );
}

export default App;
