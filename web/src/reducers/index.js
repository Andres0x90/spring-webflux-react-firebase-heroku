import { combineReducers } from 'redux'
import questionsReducer from './questionsReducer';
import authReducer from './authReducer';
import userReducer from './userReducer';

const rootReducer = combineReducers({
    question: questionsReducer,
    auth: authReducer,
    user: userReducer
})

export default rootReducer
