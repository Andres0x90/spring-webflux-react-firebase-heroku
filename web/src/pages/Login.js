import React, { useEffect } from 'react';

import { useForm } from 'react-hook-form';
import { auth } from '../components/FirebaseService';
import { connect } from 'react-redux';
import { authFailure } from '../actions/authActions';

const Login = ({dispatch, error, isLoading}) => {

    const {register, handleSubmit} = useForm();

    const onSubmit = (data) =>
    {
        auth.signInWithEmailAndPassword(data.email, data.password)
        .catch((onError) => dispatch(authFailure(onError.message)));
    }
   
    return <section>
        <h1>Formulario de login</h1>
        <form className="my-3" onSubmit={handleSubmit(onSubmit)}>
        <div class="mb-3">
            <label for="exampleInputEmail1" class="form-label">Email address</label>
            <input type="email" class="form-control" {...register("email", {required:true})}
             placeholder="name@example.com" aria-describedby="emailHelp" />
            <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>
        </div>
        <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label">Password</label>
            <input type="password" class="form-control" {...register("password", {required:true})} />
        </div>
        <p>{error}</p>
        <button type="submit" class="btn btn-danger">Login</button>
        </form>
    </section>
}

const mapToProps = state =>
({
    error: state.auth.error,
})

export default connect(mapToProps)(Login);