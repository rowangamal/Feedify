import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';

export const signup = async (formData) => {
    /**
     * API call to sign up a user.
     * formData : {
     *     "firstName": string,
     *     "lastName": string,
     *     "username": string,
     *     "gender": boolean,
     *     "dateOfBirth": Date,
     *     "email": string,
     *     "password": string
     * }
     */
    const url = `${API_BASE_URL}/signup`; 
    
    try{
        const response = await axios.post(url, formData, {
            headers: {
                'Content-Type': 'application/json',
            }
        });
    } catch (error) {
        if (error.response && error.response.data) {
            throw new Error(error.response.data);  
        }
        throw error;  
    }

};