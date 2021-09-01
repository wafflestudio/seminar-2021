import React, { useState } from 'react';
import Counter from "./Counter";

const App = () => {
  const [value1, setValue1] = useState(0);
  const [value2, setValue2] = useState(0);

  return (
    <div>
      <Counter value={value1} incrementValue={() => setValue1(value1 + 1)} />
      <br />
      <Counter value={value2} incrementValue={() => setValue2(value2 + 1)} />
      <br /><br /><br />
      <p>
        value1 + value2 = {value1 + value2}
      </p>
    </div>
  );
}

export default App;
