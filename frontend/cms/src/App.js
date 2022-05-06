import './App.css';

import React, {useEffect, useState} from 'react'

import RegisterPage from './enterPage/RegisterPage'
import Container from "@mui/material/Container";
import ChairPage from "./chairPage/ChairPage";
import useLocalStorage from "./UseLocalStorage";

function App() {
    const { setItem: setToken, item: token } = useLocalStorage("token", undefined);
    const [ name, setName ] = useState("");
    const [ type, setType ] = useState("");

    const accountRequest = accountID => {
        if (accountID !== undefined && accountID !== 122) {
            fetch("http://localhost:8080/accounts/getUserData?accountID=" + accountID.toString())
                .then(response => response.json())
                .then(data => {
                    setName(data.name);
                    setType(data.type);
                })
                .catch(() => alert("Invalid account!"));
        } else {
            setName(accountID === 122 ? "testName" : "");
            setType(accountID === 122 ? "chair" : "");
        }
    }

    useEffect(() => accountRequest(token), []);

    const setAll = token => {
        setToken(token);
        accountRequest(token);
    }


    function renderSwitch(){
        switch (type){
            case "chair":
                return (<ChairPage name={name} token={token} setToken={setAll}/>);
            default:
                //alert(type);
        }
    }

    return(
        <Container component="main" id="main" disableGutters={true} maxWidth={false}>
            {!token ? <RegisterPage setToken={setAll}/> : renderSwitch()}
        </Container>
    )

}

export default App;
