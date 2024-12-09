import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import { GoogleOAuthProvider } from '@react-oauth/google';
import App from './App.jsx';

createRoot(document.getElementById('root')).render(
    
    <StrictMode>
        <GoogleOAuthProvider clientId="1016101665022-jq4n1aal1b7gsffhnoua919ff4fpbo0m.apps.googleusercontent.com">
            <App />
        </GoogleOAuthProvider>
    </StrictMode>,
);