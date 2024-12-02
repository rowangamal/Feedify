import React from 'react';
import { GoogleLogin } from '@react-oauth/google';
import { jwtDecode } from 'jwt-decode';
import { useNavigate } from 'react-router-dom';

function GoogleSignin() {
  const navigate = useNavigate();

  const handleGoogleLoginSuccess = async (credentialResponse) => {
    try {
      const decodedToken = jwtDecode(credentialResponse.credential);
      console.log("Decoded Token:", decodedToken);

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

      // Send login request to the backend
      const response = await fetch('http://localhost:8080/api/auth/loginGoogle', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(googleTokenData),
      });

      const result = await response.text();

      if (response.ok) {
        alert(result || 'User signed in successfully!');
        navigate('/home');
      } else {
        alert('Login failed: ' + result);
      }
    } catch (error) {
      console.error('Error during login:', error);
      alert('An error occurred during login. Please try again.');
    }
  };

  return (
    <div>
      <h2>Sign In with Google</h2>
      <GoogleLogin
        onSuccess={handleGoogleLoginSuccess}
        onError={() => alert('Login error')}
      />
    </div>
  );
}

export default GoogleSignin;
