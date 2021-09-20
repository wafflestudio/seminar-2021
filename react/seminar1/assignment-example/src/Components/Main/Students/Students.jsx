import Student from "./Student";

const Students = ({
  students,
  selectedStudent,
  keyword,
  handleSelectStudent,
}) => {
  return (
    <section className="student-list">
      <header>
        <span className="name">이름</span>
        <span className="grade">학년</span>
      </header>
      {Array.isArray(students) &&
        students
          .filter((student) => student.name.includes(keyword))
          .map((student) => (
            <Student
              key={student.id}
              student={student}
              handleSelectStudent={() => handleSelectStudent(student)}
              isSelected={selectedStudent && student.id === selectedStudent.id}
            />
          ))}
    </section>
  );
};

export default Students;
