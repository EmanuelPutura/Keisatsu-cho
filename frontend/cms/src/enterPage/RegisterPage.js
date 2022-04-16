import React from 'react'
import {Box, Container, Typography} from "@mui/material";
import SignUpForm from "./SignUpForm";
import LogInForm from "./LogInForm";

function RegisterPage({setToken, setName, setType}){
    return(
        <Container component="div" disableGutters={true} maxWidth={false}>
            <Typography variant="h4" component="h1" id="main-title">
                Conference management
            </Typography>
            <Box component="div" className="form_container">
                <Box component="div" className="log-form_container">
                    <SignUpForm />
                </Box>
                <Box component="div" className="log-form_container">
                    <LogInForm setToken={setToken} setName={setName} setType={setType}/>
                </Box>
            </Box>
        </Container>
    )
}

export default RegisterPage;