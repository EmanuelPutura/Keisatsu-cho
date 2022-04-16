import './App.css';

import React from 'react'

import RegisterPage from './enterPage/RegisterPage'
import Container from "@mui/material/Container";
import ChairPage from "./chairPage/ChairPage";
import useLocalStorage from "./UseLocalStorage";

function App() {
    const { setItem: setToken, item: token } = useLocalStorage("token", undefined);
    const { setItem: setName, item: name } = useLocalStorage("name", "");
    const { setItem: setType, item: type } = useLocalStorage("type", "");

    if(!token){
        return(
            <Container component="main" id="main" disableGutters={true} maxWidth={false}>
                <RegisterPage setToken={setToken} setName={setName} setType={setType}/>
            </Container>
        )
    }

    if(type === "chair"){
        return(
            <Container component="main" id="main" disableGutters={true} maxWidth={false}>
                <ChairPage name={name} />
            </Container>
        )
    }

    return(
        <Container component="main" id="main" disableGutters={true} maxWidth={false}>
            {!token ? <RegisterPage setToken={setToken} setName={setName} setType={setType}/> :
                type === "chair" ? <ChairPage name={name} /> : null
            }
        </Container>
        )
}

export default App;
