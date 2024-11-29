import axios from 'axios';

const API_BASE_URL = 'https://your-api-base-url.com'; // Replace with your actual API base URL

export const signup = async (formData) => {
    const url = `${API_BASE_URL}/signup`; // Ensure the URL is correctly constructed
    return await axios.post(url, formData);
};