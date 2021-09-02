import React, {useState} from 'react';
import './Counter.css';

const Counter = (props) => {
  const [isHiddenValue, setIsHiddenValue] = useState(false);

  return (
    <div>
      <h1>value: {!isHiddenValue && props.value}</h1>
      <button onClick={props.incrementValue}>+1</button>
      <span className="hideInputWrapper">
        값 숨기기
        <input
          type="checkbox"
          value={isHiddenValue}
          onChange={(e) => setIsHiddenValue(e.target.checked)}
        />
      </span>
    </div>
  );
}

export default Counter;