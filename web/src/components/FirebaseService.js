import firebase from "firebase/app";
import "firebase/firestore";
import "firebase/auth";

firebase.initializeApp({
    apiKey: "AIzaSyAoFn-ix8eTFTlbTfJX6zLmDbA_deQ9Li0",
    authDomain: "question-and-answer-df3c7.firebaseapp.com",
    projectId: "question-and-answer-df3c7",
    storageBucket: "question-and-answer-df3c7.appspot.com",
    messagingSenderId: "816410372904",
    appId: "1:816410372904:web:5c35e74b668b53491f7e1d"
  });

export const auth = firebase.auth(); 
export const GoogleProvider = () =>  new firebase.auth.GoogleAuthProvider();
