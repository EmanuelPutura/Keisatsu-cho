import './App.css';
import RegisterPage from './enterPage/RegisterPage'
import {useState} from "react";
import {Container} from "@mui/material";

function App() {
    const [token, setToken] = useState(null);
    const [name, setName] = useState("");
    const [type, setType] = useState("");

    return(
        <Container component="main" id="main" disableGutters={true} maxWidth={false}>
            {!token ? <RegisterPage setToken={setToken} setName={setName} setType={setType}/> : null}
        </Container>
        )
}

export default App;
