import React from 'react';
import { GoogleLogin } from '@react-oauth/google';
import { jwtDecode } from 'jwt-decode';
import { useNavigate } from 'react-router-dom';

const LOGIN_GOOGLE_URL = 'http://localhost:8080/api/auth/loginGoogle';

async function loginWithGoogle(googleTokenData) {
  const response = await fetch(LOGIN_GOOGLE_URL, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(googleTokenData),
  });

  if (!response.ok) {
    throw new Error('Login failed');
  }

  return response.text();
}

function GoogleSignin() {
  const navigate = useNavigate();

  const handleGoogleLoginSuccess = async (credentialResponse) => {
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

      await loginWithGoogle(googleTokenData);
      navigate('/home');
    } catch (error) {
      console.error('Login failed:', error);
    }
  };

  return (
    <div>
      <h2>Sign In with Google</h2>
      <GoogleLogin
        onSuccess={handleGoogleLoginSuccess}
        onError={() => console.error('Google login failed.')}
        useOneTap
      />
    </div>
  );
}

export default GoogleSignin;
