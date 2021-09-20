import { useState } from "react";
import Divider from "Components/Common/Divider";
import StudentsSection from "Components/Main/Students";

import dummyData from "data";
import "./index.css";

const Main = ({ handleModal }) => {
  const [students, setStudents] = useState(dummyData);
  const [selectedStudent, setSelectedStudent] = useState(null);

  const handleSelectStudent = (student) => {
    setSelectedStudent(student);
  };

  return (
    <section className="main-box">
      <StudentsSection
        students={students}
        selectedStudent={selectedStudent}
        handleSelectStudent={handleSelectStudent}
      />
      <Divider />
      <div style={{ width: "200px" }}></div>
    </section>
  );
};

export default Main;
