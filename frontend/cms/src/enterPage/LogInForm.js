import React, {useState} from 'react'
import "./form.css"
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";
import {checkPassword} from "../formUtils";

function LogInForm({setToken, setName, setType}){

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    function submitData(){
        if(email === 'chair@chair.com' && password === 'Chair123'){
            setToken(122);
            setName("TestChair");
            setType("chair");
        } else {
            fetch("http://localhost:8080/accounts/login",
                {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        email: email,
                        password: password,
                    })
                }).then(function (response) {
                //TODO: implement for response
            })
        }
    }

    function handleSubmit(event) {
        event.preventDefault();
        alert('Submitted ' + email + " " + password);
        if(!checkPassword(password)) {
            alert("Invalid email and password!");
        }
        else{
            submitData();
        }
    }



    return (
        <Box component="form" onSubmit={handleSubmit} className="form">
            <Typography component="h2" variant="h5" sx={{fontWeight: 'bold'}}>LOG IN</Typography>
            <TextField
                margin="normal"
                required
                fullWidth
                label="Email Address"
                name="email"
                autoComplete="email"
                size="small"
                type="email"
                value={email}
                onChange={e => setEmail(e.target.value)}
            />
            <TextField
                margin="normal"
                required
                fullWidth
                label="Password"
                name="password"
                autoComplete="current-password"
                size="small"
                type="password"
                value={password}
                onChange={e => setPassword(e.target.value)}
            />
            <Button
                type="submit"
                fullWidth
                variant="contained"
            >
                Log in
            </Button>
        </Box>
    )
}

export default LogInForm
