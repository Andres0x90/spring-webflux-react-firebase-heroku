import  * as userActions from '../actions/userActions'

export const initialState = 
{
    loading: true,
    user:null,
    error: null
}

export default function userReducer(state = initialState, action)
{
    switch(action.type)
    {
        case userActions.USER_LOADING:
            return {...state, loading: true};
        case userActions.USER_LOADED_SUCCESS:
            return {...state, user: action.payload.user ,loading: false};
        case userActions.USER_LOADED_FAILURE:
            return {...state,  error: action.payload.error, loading : false, user: null};
        default:
            return state;

    }

} 