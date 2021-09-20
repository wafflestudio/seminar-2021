import { useState } from "react";
import Divider from "Components/Common/Divider";
import StudentsSection from "Components/Main/Students";
import Profile from "Components/Main/Profile";

import dummyData from "data";
import "./index.css";

const defaultInput = {
  name: "",
  grade: "",
  profileImg: "",
};

const Main = () => {
  const [students, setStudents] = useState(dummyData);
  const [selectedStudent, setSelectedStudent] = useState(null);
  const [inputs, setInputs] = useState(defaultInput);

  const handleSelectStudent = (student) => {
    const isSelectedStudent =
      selectedStudent && selectedStudent.id === student.id;

    setSelectedStudent(isSelectedStudent ? null : student);
    setInputs(student);
  };

  const handleEditStudent = (student) => {
    const targetStudent = { ...selectedStudent, ...student };
    setStudents(
      students.map((student) =>
        student.id === targetStudent.id ? targetStudent : student
      )
    );
    setSelectedStudent(targetStudent);
    setInputs(targetStudent);
  };

  const handleDeleteStudent = () => {
    if (!students.find((student) => student.id === selectedStudent.id)) return;

    setStudents(
      students.filter((student) => student.id !== selectedStudent.id)
    );
    setInputs(defaultInput);
    setSelectedStudent(null);
  };

  const handleChagneInputs = (e) => {
    setInputs({ ...inputs, [e.target.name]: e.target.value });
  };

  return (
    <section className="main-box">
      <StudentsSection
        students={students}
        selectedStudent={selectedStudent}
        handleSelectStudent={handleSelectStudent}
      />
      <Divider />
      <Profile
        isSelected={!!selectedStudent}
        inputs={inputs}
        handleChangeInputs={handleChagneInputs}
        handleEditStudent={handleEditStudent}
        handleDeleteStudent={handleDeleteStudent}
      />
    </section>
  );
};

export default Main;
