import React from 'react';

const Counter = (props) => {
  return (
    <div>
      <h1>value: {props.value}</h1>
      <button onClick={props.incrementValue}>+1</button>
    </div>
  )
}

export default Counter;
