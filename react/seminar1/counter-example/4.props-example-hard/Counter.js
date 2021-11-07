import React from 'react';

const Counter = ({ value, incrementValue }) => {
  return (
    <div>
      <h1>value: {value}</h1>
      <button onClick={incrementValue}>+1</button>
    </div>
  )
}

export default Counter;
