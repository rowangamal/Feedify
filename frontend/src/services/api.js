import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080'; 

export const signup = async (formData) => {
    const url = `${API_BASE_URL}/signup`;

    try {
        const response = await axios.post(url, formData, {
            headers: {
                'Content-Type': 'application/json',
            }
        });
        return response;
    } catch (error) {
        if (error.response && error.response.data) {
            throw new Error(error.response.data);
        }
        throw error;
    }
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
        if (error.response && error.response.data) {
            throw new Error(error.response.data);  
        }
        throw error;  
    }

};