import "./index.css";

const Profile = ({
  isSelected,
  inputs,
  handleChangeInputs,
  handleDeleteStudent,
  handleEditStudent,
}) => {
  const onSubmit = (e) => {
    e.preventDefault();
    handleEditStudent(inputs);
  };

  return (
    <section className="profile">
      {isSelected ? (
        <form onSubmit={onSubmit}>
          <div>
            <button type="submit">저장</button>
            <button onClick={handleDeleteStudent}>삭제</button>
          </div>
          <label>이름</label>
          <input
            value={inputs.name}
            name="name"
            onChange={handleChangeInputs}
          />
          <label>학년</label>
          <input
            value={inputs.grade}
            name="grade"
            onChange={handleChangeInputs}
          />
          <label>프로필</label>
          <input
            value={inputs.profileImg}
            name="profileImg"
            onChange={handleChangeInputs}
          />
        </form>
      ) : (
        <p>
          왼쪽 표에서 <br />
          학생을 선택해 주세요
        </p>
      )}
    </section>
  );
};

export default Profile;
