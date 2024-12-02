import React from 'react';
import { GoogleLogin } from '@react-oauth/google';
import { jwtDecode } from 'jwt-decode';
import { useNavigate } from 'react-router-dom';

function GoogleSignup() {
  const navigate = useNavigate();

  const handleGoogleSignupSuccess = async (credentialResponse) => {
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

      // Send signup request to the backend
      const response = await fetch('http://localhost:8080/api/auth/signupGoogle', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(googleTokenData),
      });

      const result = await response.text();

      if (response.ok) {
        alert(result || 'User registered successfully!');
        navigate('/home');
      } else {
        alert('Signup failed: ' + result);
      }
    } catch (error) {
      console.error('Error during signup:', error);
      alert('An error occurred during signup. Please try again.');
    }
  };

  return (
    <div>
      <h2>Sign Up with Google</h2>
      <GoogleLogin
        onSuccess={handleGoogleSignupSuccess}
        onError={() => alert('Signup error')}
      />
    </div>
  );
}

export default GoogleSignup;
