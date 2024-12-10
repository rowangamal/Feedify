import React from 'react';
import { GoogleLogin } from '@react-oauth/google';
import { jwtDecode } from 'jwt-decode';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const LOGIN_GOOGLE_URL = 'http://localhost:8080/api/auth/loginGoogle';

const GoogleSignin = () => {
  const navigate = useNavigate();

  const handleGoogleLoginSuccess = async (credentialResponse) => {
    if (!credentialResponse?.credential) {
      console.error('No credential received.');
      return;
    }

    try {
      const decodedToken = jwtDecode(credentialResponse.credential);

      // Prepare data for backend login
      const googleTokenData = {
        email: decodedToken.email,
        name: decodedToken.name,
        givenName: decodedToken.given_name,
        familyName: decodedToken.family_name,
        picture: decodedToken.picture,
        locale: decodedToken.locale,
        emailVerified: decodedToken.email_verified,
        sub: decodedToken.sub,
      };

      // Send to backend for authentication
      const response = await axios.post(LOGIN_GOOGLE_URL, googleTokenData, {
        headers: { 'Content-Type': 'application/json' },
      });

      const { jwttoken, isAdmin } = response.data;

      // Save JWT and navigate to home
      localStorage.setItem('jwttoken', jwttoken);
      console.log('Login successful. Admin:', isAdmin);
      navigate('/home');
    } catch (error) {
      console.error('Google login failed:', error);
    }
  };

  const handleGoogleLoginError = () => {
    console.error('Google login failed.');
  };

  return (
    <div>
      <h2>Sign In with Google</h2>
      <GoogleLogin
        onSuccess={handleGoogleLoginSuccess}
        onError={handleGoogleLoginError}
        useOneTap
      />
    </div>
  );
};

export default GoogleSignin;
