import './App.css';

import React, {useState} from 'react'

import RegisterPage from './enterPage/RegisterPage'
import Container from "@mui/material/Container";
import ChairPage from "./chairPage/ChairPage";
import useLocalStorage from "./UseLocalStorage";

function App() {
    const { setItem: setToken, item: token } = useLocalStorage("token", undefined);

    //TODO: Request for account with id = token if token is not undefined
    const [ name, setName ] = useState(token === 122 ? "testName" : "");
    const [ type, setType ] = useState(token === 122 ? "chair" : "");

    const setAll = token => {
        //TODO: Request for account with id = token if token is not undefined
        setToken(token);
        setName(token === 122 ? "testName" : "");
        setType(token === 122 ? "chair" : "");
    }


    function renderSwitch(){
        switch (type){
            case "chair":
                return (<ChairPage name={name} token={token} setToken={setAll}/>);
            default:
                alert(type);
        }
    }

    return(
        <Container component="main" id="main" disableGutters={true} maxWidth={false}>
            {!token ? <RegisterPage setToken={setAll}/> : renderSwitch()}
        </Container>
    )

}

export default App;
