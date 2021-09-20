import { useState } from "react";
import Divider from "Components/Common/Divider";
import StudentsSection from "Components/Main/Students";
import Profile from "Components/Main/Profile";
import Modal from "Components/Common/Modal";
import { isAppropriateGrade, isAppropriateName } from "Utils/regex";

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
  const [inputs, setInputs] = useState(defaultInput); //여기 있어서는 안 되는 친구이지만, 진도 상 useEffect의 부재로 인해 여기에 있습니다. 원래는 Profile 안에 있는 게 맞아요
  const [isModal, setIsModal] = useState(false);

  const isAppropriateStudent = (targetStudent) => {
    if (!isAppropriateName(targetStudent.name)) {
      window.alert("2~3자의 한국 이름을 입력해주세요");
      return false;
    }

    if (!isAppropriateGrade(targetStudent.grade)) {
      window.alert("1 이상, 3이하의 숫자를 입력해주세요");
      return false;
    }

    if (
      students.find(
        (student) =>
          student.name === targetStudent.name &&
          student.grade === Number(targetStudent.grade) &&
          student.id !== targetStudent.id
      )
    ) {
      window.alert("같은 학년에 동명이인이 존재합니다.");
      return false;
    }

    return true;
  };

  const handleSelectStudent = (student) => {
    const isSelectedStudent =
      selectedStudent && selectedStudent.id === student.id;

    setSelectedStudent(isSelectedStudent ? null : student);
    setInputs(student);
  };

  const handleAddStudent = (student) => {
    if (!isAppropriateStudent(student)) return;

    const id =
      students.reduce((maxId, { id }) => (id > maxId ? id : maxId), 0) + 1;

    const newStudent = { ...student, id, grade: Number(student.grade) };

    setStudents([...students, newStudent]);
    setSelectedStudent(newStudent);
    setInputs(newStudent);
    setIsModal(false);
  };

  const handleEditStudent = (student) => {
    const targetStudent = { ...selectedStudent, ...student };

    if (!isAppropriateStudent(targetStudent)) return;

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

  const handleChangeInputs = (e) => {
    setInputs({ ...inputs, [e.target.name]: e.target.value });
  };

  const handleModal = (isModal) => {
    setIsModal(isModal);
  };

  return (
    <>
      <section className="main-box">
        <StudentsSection
          students={students}
          selectedStudent={selectedStudent}
          handleSelectStudent={handleSelectStudent}
          handleModal={handleModal}
        />
        <Divider />
        <Profile
          isSelected={!!selectedStudent}
          inputs={inputs}
          handleChangeInputs={handleChangeInputs}
          handleEditStudent={handleEditStudent}
          handleDeleteStudent={handleDeleteStudent}
        />
      </section>
      <Modal
        isModal={isModal}
        handleModal={handleModal}
        handleAddStudent={handleAddStudent}
      />
    </>
  );
};

export default Main;
