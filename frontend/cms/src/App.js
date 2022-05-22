import './App.css';

import React, {useEffect, useState} from 'react'

import RegisterPage from './enterPage/RegisterPage'
import Container from "@mui/material/Container";
import ChairPage from "./chairPage/ChairPage";
import useLocalStorage from "./UseLocalStorage";
import AuthorPage from "./authorPage/AuthorPage";
import ReviewerPage from "./reviewerPage/ReviewerPage";
import Header from "./commons/Header";

function App() {
    const { setItem: setToken, item: token } = useLocalStorage("token", undefined);
    const [ name, setName ] = useState("");
    const [ type, setType ] = useState("");

    const accountRequest = accountID => {

        if (accountID !== undefined && accountID !== 122 && accountID !== 123 && accountID !== 124) {
            fetch("http://localhost:8080/accounts/getUserData?accountID=" + accountID.toString(),
            {
                method: "GET",
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem("jwt")
                }
            })
                .then(response => response.json())
                .then(data => {
                    setName(data.name);
                    setType(data.type);
                })
                .catch(() => alert("Invalid account!"));
        } else {
            setName(accountID === 122 || accountID === 123 || accountID === 124 ? "testName" : "");
            setType(accountID === 122 ? "chair" : accountID === 123 ? "author" : accountID === 124 ? "reviewer" : "" );
        }
    }

    useEffect(() => accountRequest(token), [token]);


    const setAll = token => {
        setToken(token);
        accountRequest(token);
    }


    function renderSwitch(){
        switch (type){
            case "chair":
                return (<ChairPage name={name} token={token} setToken={setAll}/>);
            case "author":
                return (<AuthorPage name={name} token={token} setToken={setAll} />);
            case "reviewer":
                return (<ReviewerPage name={name} token={token} setToken={setAll} />)
            default:
                return (<Header name={"Error account"} setToken={setAll} /> )
        }
    }

    return(
        <Container component="main" id="main" disableGutters={true} maxWidth={false}>
            {!token ? <RegisterPage setToken={setAll}/> : renderSwitch()}
        </Container>
    )

}

export default App;
