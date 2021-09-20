import Student from "./Student";

const Students = ({ students, selectedStudent, keyword }) => {
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
              handleSelectStudent={() => {}}
              isSelected={selectedStudent && student.id === selectedStudent.id}
            />
          ))}
    </section>
  );
};

export default Students;
