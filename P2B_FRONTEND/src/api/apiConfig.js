/**
 * P2B Central API Configuration
 * 
 * Sabhi API calls is file se BASE_URL lete hain.
 * .env file mein VITE_API_BASE_URL set karo:
 *   - Local:      http://localhost:8080
 *   - Production: https://your-railway-app.up.railway.app
 */
export const BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

export const API_ENDPOINTS = {
  // Auth
  LOGIN:          `${BASE_URL}/api/auth/login`,
  SIGNUP:         `${BASE_URL}/api/auth/signup`,
  GOOGLE_AUTH:    `${BASE_URL}/api/auth/google`,
  CHANGE_PASSWORD:`${BASE_URL}/api/auth/change-password`,
  DELETE_ACCOUNT: `${BASE_URL}/api/auth/delete`,

  // Report Generation
  GENERATE_REPORT:`${BASE_URL}/api/generate/report`,

  // Chat
  CHAT:           `${BASE_URL}/api/chat`,

  // User Reports / Stats
  USER_STATS:     `${BASE_URL}/api/p2b-report/user-stats`,
  REPORT_DETAILS: (id) => `${BASE_URL}/api/p2b-report/details/${id}`,
  DELETE_REPORT:  (id) => `${BASE_URL}/api/p2b-report/${id}`,

  // Tutorials
  TUTORIALS:      `${BASE_URL}/api/tutorials`,
};
