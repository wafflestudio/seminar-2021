import { useState } from "react";

import "./index.css";

const defaultInput = {
  name: "",
  grade: "",
  profileImg: "",
};

const Modal = ({ isModal, handleModal, handleAddStudent }) => {
  const [inputs, setInputs] = useState(defaultInput);

  const onSubmit = (e) => {
    e.preventDefault();
    handleAddStudent(inputs);
    setInputs(defaultInput);
  };

  const handleCloseModal = (e) => {
    e.preventDefault();

    handleModal(false);
    setInputs(defaultInput);
  };

  const handleChangeInputs = (e) => {
    setInputs({ ...inputs, [e.target.name]: e.target.value });
  };

  return (
    <>
      <div className={`dimmed ${isModal ? "on" : ""}`} />
      <div className={`modal ${isModal ? "on" : ""}`}>
        <form onSubmit={onSubmit}>
          <div className="inputs">
            <label>이름</label>
            <input
              value={inputs.name}
              name="name"
              onChange={handleChangeInputs}
            />
          </div>
          <div className="inputs">
            <label>학년</label>
            <input
              value={inputs.grade}
              name="grade"
              onChange={handleChangeInputs}
            />
          </div>
          <div className="inputs">
            <label>프로필</label>
            <input
              value={inputs.profileImg}
              name="profileImg"
              onChange={handleChangeInputs}
            />
          </div>
          <div className="buttons">
            <button type="button" onClick={handleCloseModal} className="danger">
              닫기
            </button>
            <button type="submit" className="submit">
              저장
            </button>
          </div>
        </form>
      </div>
    </>
  );
};

export default Modal;
