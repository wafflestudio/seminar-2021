import leftArrow from "Resources/images/left-arrow.png";
import rightArrow from "Resources/images/right-arrow.png";

import "./index.css";

const Student = ({ student, handleSelectStudent, isSelected }) => {
  const { name, grade } = student;

  return (
    <article className={`student ${isSelected ? "selected" : ""}`}>
      <span className="name">{name}</span>
      <span className="grade">{grade}</span>
      <button onClick={handleSelectStudent} tabIndex="-1">
        <img src={isSelected ? leftArrow : rightArrow} alt="arrow" id="arrow" />
      </button>
    </article>
  );
};

export default Student;
