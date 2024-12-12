
import React from 'react';
import { GoogleLogin } from '@react-oauth/google';
import { jwtDecode } from 'jwt-decode';
import { useNavigate } from 'react-router-dom';

const SIGNUP_GOOGLE_URL = 'http://localhost:8080/api/auth/signupGoogle';

async function signupWithGoogle(googleTokenData) {
  const response = await fetch(SIGNUP_GOOGLE_URL, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(googleTokenData),
  });

  if (!response.ok) {
    throw new Error('Signup failed');
  }

  return response.text();
}

function GoogleSignup() {
  const navigate = useNavigate();

  const handleGoogleSignupSuccess = async (credentialResponse) => {
    if (!credentialResponse?.credential) {
      return;
    }

    try {
      const decodedToken = jwtDecode(credentialResponse.credential);

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

      await signupWithGoogle(googleTokenData);
      navigate('/login');
    } catch (error) {
      console.error('Signup failed:', error);
    }
  };

  return (
    <div>
      <GoogleLogin
        onSuccess={handleGoogleSignupSuccess}
        onError={() => console.error('Google Signup failed.')}
      />
    </div>
  );
}

export default GoogleSignup;
