import React, { useEffect, useState } from 'react';

import { useForm } from 'react-hook-form';
import { connect } from 'react-redux';
import { authFailure } from '../actions/authActions';
import { failure, loading, success } from '../actions/questionActions';
import { db } from '../components/FirebaseService';


const Profile = ({dispatch, userId, email, error, isLoading}) => {

    const {register, handleSubmit, setValue} = useForm();
    const userRef = db.collection("user");
    const [updateSuccess, setUpdateSucess] = useState();

    useEffect(()=>
    {
        dispatch(loading())
        userRef.doc(userId).get().then((doc)=>
        {
            if (doc.exists)
            {
                const data = doc.data();
                setValue("name", data.name);
                setValue("last_name", data.last_name);
            }
            dispatch(success());
        });
    },[]);

    const onSubmit = (data) =>
    {
        dispatch(loading());
        userRef.doc(userId).set({
           "name": data.name,
            "last_name": data.last_name
        })
        .then(()=> {
            setUpdateSucess('Data updated successfully');
            dispatch(success());
        })
        .catch(() => {
            dispatch(authFailure("Error! the information couldn't be updated"));
            dispatch(failure());
        })
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
            <input type="text" class="form-control" {...register("last_name", {required:true})} 
            disabled={isLoading}/>
        </div>
        <div class="mb-3">
            <label for="exampleInputEmail1" class="form-label">Email address</label>
            <input type="email" class="form-control" {...register("email", {required:true})}
             placeholder="name@example.com" aria-describedby="emailHelp" 
             value={email}  readOnly={true}/>
        </div>
        <p>{error?error: updateSuccess}</p>
        <button type="submit" class="btn btn-danger">{isLoading?'Loading...':'Update'}</button>
        </form>
    </section>
}

const mapToProps = state =>
({
    userId: state.auth.uid,
    email: state.auth.email,
    error: state.auth.error,
    isLoading: state.question.loading
})

export default connect(mapToProps)(Profile);