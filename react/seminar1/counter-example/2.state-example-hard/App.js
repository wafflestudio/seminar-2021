import React, { useState } from 'react';

const App = () => {
  const [value1, setValue1] = useState(0);
  const [value2, setValue2] = useState(0);

  return (
    <div>
      <div>
        <h1>value: {value1}</h1>
        <button onClick={() => setValue1(value1 + 1)}>+1</button>
      </div>
      <br />
      <div>
        <h1>value: {value2}</h1>
        <button onClick={() => setValue2(value2 + 1)}>+1</button>
      </div>
      <br /><br /><br />
      <p>
        value1 + value2 = {value1 + value2}
      </p>
    </div>
  );
}

export default App;

