import React, { useState } from 'react';

const App = () => {
  const value1State = useState(0);
  const value2State = useState(0);

  const value1 = value1State[0];
  const setValue1 = value1State[1];

  const value2 = value2State[0];
  const setValue2 = value2State[1];

  const incrementValue1 = () => {
    setValue1(value1 + 1);
  }

  const incrementValue2 = () => {
    setValue2(value2 + 1);
  }

  return (
    <div>
      <div>
        <h1>value: {value1}</h1>
        <button onClick={incrementValue1}>+1</button>
      </div>
      <br />
      <div>
        <h1>value: {value2}</h1>
        <button onClick={incrementValue2}>+1</button>
      </div>
      <br /><br /><br />
      <p>
        value1 + value2 = {value1 + value2}
      </p>
    </div>
  );
}

export default App;

