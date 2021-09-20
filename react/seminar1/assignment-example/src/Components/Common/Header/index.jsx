import waffleLogo from "Resources/images/waffle-logo.png";

import "./Header.css";

const Header = () => {
  return (
    <header>
      <img id="logo" src={waffleLogo} alt="waffle logo" />
      <h1 id="title">와플고등학교 명단 관리 프로그램</h1>
    </header>
  );
};

export default Header;
