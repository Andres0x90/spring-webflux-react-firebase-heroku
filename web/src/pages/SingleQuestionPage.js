import React, { useEffect } from 'react'
import { connect } from 'react-redux'

import { fetchQuestion, voteDown, voteUp } from '../actions/questionActions'

import { Question } from '../components/Question'
import { Answer } from '../components/Answer'
import { Link } from 'react-router-dom'
import { useForm } from 'react-hook-form'


const SingleQuestionPage = ({
  match,
  dispatch,
  question,
  hasErrors,
  loading,
  userId,
}) => {

  const { id } = match.params
  useEffect(() => {
    dispatch(fetchQuestion(id));
  }, [dispatch, id]);


 const {handleSubmit} = useForm();

  const renderQuestion = () => {
    if (loading.question) return <p>Loading question...</p>
    if (hasErrors.question) return <p>Unable to display question.</p>

    return <Question question={question} />
  }

  const onClickVoteUp = (answerId, userId, questionId)=> dispatch(voteUp(answerId, userId, questionId));
  const onClickVoteDown = (answerId, userId, questionId)=> dispatch(voteDown(answerId, userId, questionId));
 

  const renderAnswers = () => {
    return (question.answers && question.answers.length) ? question.answers.map(answer => (
      <div key={answer.id}>
        <Answer  answer={answer} />
       {userId?(<div>
        <button className="btn btn-success" disabled={answer.upVotes
        .find((userIdVoted) => userId === userIdVoted)} 
        onClick={handleSubmit(()=> onClickVoteUp(answer.id, userId, id))}>It's useful</button>
        <button className="btn btn-danger mx-3" disabled={answer.downVotes
        .find((userIdVoted) => userId === userIdVoted)} 
         onClick={handleSubmit(()=> 
          onClickVoteDown(answer.id, userId, id))}>It's useless</button>
       </div>):(<div></div>)}
        
  
      </div>
    )) : <p>Empty answer!</p>;
  }

  return (
    <section>
      {renderQuestion()}
      {userId && <Link to={"/answer/" + id} className="button right">
        Reply
      </Link>}

      <h2>Answers</h2>
      {renderAnswers()}
    </section>
  )
}

const mapStateToProps = state => ({
  question: state.question.question,
  loading: state.question.loading,
  hasErrors: state.question.hasErrors,
  userId: state.auth.uid
})

export default connect(mapStateToProps)(SingleQuestionPage)
