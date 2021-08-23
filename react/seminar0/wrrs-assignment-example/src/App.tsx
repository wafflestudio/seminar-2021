// @ts-ignore
import React, {useState} from 'react';

import { AppWrapper, Header, LogoImg, GithubImg, Title, NameBox, NameDisplayList, NameDisplayWrapper, Button, Input } from "./App.style";

function App() {
  const [name, setName] = useState<string>('');
  const [names, setNames] = useState<string[]>(['김와플', '이와플', '박와플']);

  return (
    <AppWrapper onSubmit={(e) => e.preventDefault()}>
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
