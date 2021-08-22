import React, {useState} from 'react';
import styled from "styled-components";

const AppWrapper = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
`

const Input = styled.input`
  position: fixed;
  left: calc(50% - 100px);
  width: 120px;
  bottom: 100px;
  height: 40px;
  font-size: 20px;
  line-height: 40px;
  padding-inline: 10px;
  box-sizing: border-box;
`

const Button = styled.button`
  position: fixed;
  left: calc(50% + 30px);
  width: 70px;
  bottom: 100px;
  height: 40px;
  font-size: 20px;
  line-height: 34px;
  padding-inline: 10px;
  box-sizing: border-box;
  cursor: pointer;
`

const NameDisplayWrapper = styled.div`
  position: fixed;
  left: calc(50% - 300px);
  width: 600px;
  top: 200px;
  bottom: 200px;
  border: 1px solid black;
  padding: 16px;
  border-radius: 8px;
  box-shadow: 0 2px 4px #888;
  overflow-y: scroll;
  line-height: 100px;
  align-items: start;
`

const NameDisplayList = styled.ul`
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  padding: 0;
  margin: 0;
`

const Title = styled.h1`
  position: fixed;
  left: calc(50% - 300px);
  width: 600px;
  top: 100px;
  text-align: center;
`

const NameBox = styled.li`
  width: 110px !important;
  height: 80px !important;
  line-height: 70px;
  padding: 5px;
  border: 1px dashed black;
  box-sizing: border-box;
  text-align: center;
  margin: 5px;
  border-radius: 8px;
  
  &:hover {
    background-color: #eee;
  }
`

const Header = styled.header`
  position: fixed;
  top: 0;
  left: -50px;
  right: 20px;
  height: 50px;
  display: flex;
  justify-content: space-between;
`

const LogoImg = styled.img`
  margin-top: 5px;
  height: 40px;
`

const GithubImg = styled.img`
  margin-top: 10px;
  height: 30px;
`

function App() {
  const [name, setName] = useState<string>('');
  const [names, setNames] = useState<string[]>(['김와플', '이와플', '박와플']);

  return (
    <AppWrapper>
      <Header>
        <a href={'https://wafflestudio.com'} target={'_blank'}>
          <LogoImg src={'https://github.com/wafflestudio/19.5-rookies/raw/master/wafflestudio_logo.png'} alt={'wafflestudio'} />
        </a>
        <a href={'https://github.com/wafflestudio/19.5-rookies'} target={'_blank'}>
          <GithubImg src={'https://image.flaticon.com/icons/png/512/25/25231.png'} alt={'github'} />
        </a>
      </Header>
      <Title>와플고등학교 명단 관리 프로그램</Title>
      <NameDisplayWrapper>
        <NameDisplayList>
          {names.map((item, i) => (
            <NameBox>{item}</NameBox>
          ))}
        </NameDisplayList>
      </NameDisplayWrapper>
      <Input value={name} onChange={e => setName(e.target.value)} />
      <Button onClick={() => {
        if (name) {
          setNames([...names, name]);
          setName('');
        } else {
          window.alert('이름을 입력해 주세요.');
        }
      }}>추가</Button>
    </AppWrapper>
  );
}

export default App;
