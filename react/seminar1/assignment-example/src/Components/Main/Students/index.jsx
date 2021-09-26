import { useState } from "react";
import Students from "./Students";

import "./index.css";

const StudentsSection = ({
  students,
  selectedStudent,
  handleModal,
  handleSelectStudent,
}) => {
  const [keyword, setKeyword] = useState("");

  return (
    <section className="student-list-section">
      <section className="search-bar">
        <input
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
          placeholder="검색"
        />
        <button onClick={() => handleModal(true)}>추가</button>
      </section>
      <Students
        students={students}
        selectedStudent={selectedStudent}
        handleSelectStudent={handleSelectStudent}
        keyword={keyword}
      />
    </section>
  );
};

export default StudentsSection;
