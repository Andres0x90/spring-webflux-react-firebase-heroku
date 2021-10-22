const URL_BASE = ' http://localhost:8080';

export const USER_LOADING = 'USER_LOADING'
export const USER_LOADED_SUCCESS = 'USER_LOADED_SUCCESS'
export const USER_LOADED_FAILURE = 'USER_LOADED_FAILURE'

export const loading = () => ({ type: USER_LOADING })

export const success = payload => ({
    type: USER_LOADED_SUCCESS,
    payload
});


export const failure = payload => ({ type: USER_LOADED_FAILURE, payload })
export function getUser(userId)
{
    return async dispatch =>
    {
        dispatch(loading());
        try
        {
            const response = await fetch(`${URL_BASE}/users/get/${userId}`);
            const data = await response.json();
            dispatch(success({user: data}));
        }
        catch(onError)
        {
            dispatch(failure({error: "The user couldn't be loaded"}));
        }
    }
}
export function updateUser(user)
{
    return async dispatch =>
    {
        dispatch(loading());
        try
        {
            await fetch(`${URL_BASE}/users/update`,
            {
                method: 'PUT',
                mode: 'cors',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(user)
            });
            dispatch(success({user:user}));
        }
        catch(onError)
        {
            dispatch(failure({error: "The user couldn't be updated"}));
        }
    }
}