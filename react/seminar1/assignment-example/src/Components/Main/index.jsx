import { useState } from "react";
import Divider from "Components/Common/Divider";
import StudentsSection from "Components/Main/Students";
import { isAppropriateName, isAppropriateGrade } from "Utils/regex";

import dummyData from "data";
import "./index.css";

const Main = ({ handleModal }) => {
  const [students, setStudents] = useState(dummyData);
  const [selectedStudent, setSelectedStudent] = useState(null);

  return (
    <section className="main-box">
      <StudentsSection students={students} selectedStudent={selectedStudent} />
      <Divider />
      <div style={{ width: "200px" }}></div>
    </section>
  );
};

export default Main;
