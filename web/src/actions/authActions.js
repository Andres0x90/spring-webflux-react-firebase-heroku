
export const LOGIN = 'LOGIN'
export const LOGOUT = 'LOGOUT'
export const AUTH_ERROR = 'AUTH_ERROR'

export const login = (email, uid) => ({ type: LOGIN, payload: {email, uid} })

export const logout = () => ({
    type: LOGOUT
});

export const authFailure = (error) => ({type:AUTH_ERROR, payload: {error: error}})




