const sayReact = () => {
    // arrow function
    console.log('react');
}

function alertMe() {
    console.log('그 윈도우 경고창 띄우는 그거')
    window.alert('test alert 입니다')
}

const addRToExampleBox = () => {
    const exampleBox = document.getElementById('example-box');
    const text = exampleBox.innerText;
    exampleBox.innerText = text + "R";
}

const alertInputValue = () => {
    const input = document.getElementById('example-input');
    const text = input.value;
    window.alert(text);
}

const addElem = () => {
    const ul = document.getElementById('example-ul');
    const newLi = document.createElement('li');
    newLi.innerText = `${Math.random()}`;
    ul.appendChild(newLi);
}

console.log('js 파일은 지금 연결돼요')

console.debug('디버그 모드도 있음')