import styled from "styled-components";

export const AppWrapper = styled.form`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
`

export const Input = styled.input`
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

export const Button = styled.button`
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

export const NameDisplayWrapper = styled.div`
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
  
  -ms-overflow-style: none; /* IE and Edge */
  scrollbar-width: none; /* Firefox */

  &::-webkit-scrollbar {
    display: none; /* Chrome, Safari, Opera*/
  }
`

export const NameDisplayList = styled.ul`
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  padding: 0;
  margin: 0;
`

export const Title = styled.h1`
  position: fixed;
  left: calc(50% - 300px);
  width: 600px;
  top: 100px;
  text-align: center;
`

export const NameBox = styled.li`
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

export const Header = styled.header`
  position: fixed;
  top: 0;
  left: -50px;
  right: 20px;
  height: 50px;
  display: flex;
  justify-content: space-between;
`

export const LogoImg = styled.img`
  margin-top: 5px;
  height: 40px;
`

export const GithubImg = styled.img`
  margin-top: 10px;
  height: 30px;
`