import React, {useState} from 'react'
import "./form.css"
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";
import {checkPassword} from "../formUtils";

function LogInForm({setToken}){

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    function submitData(){
        if(email === 'chair@chair.com' && password === 'Chair123'){
            setToken(122);
        } else if(email === 'author@author.com' && password === 'Author123'){
            setToken(123);
        } else if(email === 'reviewer@reviewer.com' && password === 'Reviewer123'){
            setToken(124);
        } else {
            // I need the user ID of the account that logged in
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
                }).then(response => response.json())
                .then(data => {
                    if(data.id !== -1) {
                        setToken(data.id);
                    } else {
                        alert("Invalid email/password combination!");
                    }
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
