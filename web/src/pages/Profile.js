import React, { useEffect, useState } from 'react';

import { useForm } from 'react-hook-form';
import { connect } from 'react-redux';
import { getUser, success, updateUser } from '../actions/userActions';


const Profile = ({dispatch, userId, email, error, isLoading, user}) => {

    const {register, handleSubmit, setValue} = useForm();
    const [updateSuccess, setUpdateSucess] = useState();

    useEffect(()=>
    {
        dispatch(getUser(userId));

    },[dispatch]);

    setValue("name",user?.name);
    setValue("lastName",user?.lastName);

    const onSubmit = (data) =>
    {
        data.id = userId;
        data.email = email;
        dispatch(updateUser(data))
    }
   
    return <section>
        <h1>Bienvenido a su perfil</h1>
        <form className="my-3" onSubmit={handleSubmit(onSubmit)}>
        <div class="mb-3">
            <label for="name" class="form-label">Name</label>
            <input type="text" class="form-control" {...register("name", {required:true})} 
            disabled={isLoading}/>
        </div>
        <div class="mb-3">
            <label for="last_name" class="form-label">Last Name</label>
            <input type="text" class="form-control" {...register("lastName", {required:true})} 
            disabled={isLoading}/>
        </div>
        <div class="mb-3">
            <label for="exampleInputEmail1" class="form-label">Email address</label>
            <input type="email" class="form-control" {...register("email", {required:true})}
             placeholder="name@example.com" aria-describedby="emailHelp" 
             value={email}  readOnly={true}/>
        </div>
        <button type="submit" class="btn btn-danger">{isLoading?'Loading...':'Update'}</button>
        </form>
    </section>
}

const mapToProps = state =>
({
    userId: state.auth.uid,
    email: state.auth.email,
    error: state.user.error,
    isLoading: state.user.loading,
    user: state.user.user
})

export default connect(mapToProps)(Profile);