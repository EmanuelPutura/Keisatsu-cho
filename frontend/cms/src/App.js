import './App.css';

import React from 'react'

import RegisterPage from './enterPage/RegisterPage'
import {useState} from "react";
import Container from "@mui/material/Container";
import ChairPage from "./chairPage/ChairPage";

function App() {
    const [token, setToken] = useState(null);
    const [name, setName] = useState("");
    const [type, setType] = useState("");

    return(
        <Container component="main" id="main" disableGutters={true} maxWidth={false}>
            {!token ? <RegisterPage setToken={setToken} setName={setName} setType={setType}/> :
                type === "chair" ? <ChairPage name={name} /> : null
            }
        </Container>
        )
}

export default App;
