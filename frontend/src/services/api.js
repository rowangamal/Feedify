import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080'; // Replace with your actual API base URL

export const signup = async (formData) => {
    const url = `${API_BASE_URL}/signup`; // Ensure the URL is correctly constructed
    return await axios.post(url, formData);
};

export const login = async (formData) => {
    const url = `${API_BASE_URL}/login`; 
    
    try{
        const response = await axios.post(url, formData, {
            headers: {
                'Content-Type': 'application/json',
            }
        });
        const { userId, username, isAdmin, jwttoken } = response.data;
        localStorage.setItem('jwttoken', jwttoken);
        window.location.href = '/';
        return { userId, isAdmin };
    } catch (error) {
        console.error('Login failed:', error);
    }

};