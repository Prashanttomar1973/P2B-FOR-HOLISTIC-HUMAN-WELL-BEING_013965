import axios from 'axios';
import { API_ENDPOINTS } from './apiConfig';

/**
 * Real-world Application: 
 * Hum Axios use kar rahe hain kyunki ye automatically JSON handle karta hai 
 * aur error handling mein 'fetch' se behtar hai.
 */
export const generateP2BReport = async (formData) => {
    try {
        const response = await axios.post(API_ENDPOINTS.GENERATE_REPORT, formData);
        // Response mein Groq AI ka generate kiya hua string content aayega
        return response.data; 
    } catch (error) {
        console.error("Backend Sync Error:", error.response ? error.response.data : error.message);
        throw error;
    }
};