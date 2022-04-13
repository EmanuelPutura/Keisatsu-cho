import React, {useState} from 'react'
import "./form.css"
import {Box, Button, FormControl, InputLabel, MenuItem, Select, Stack, TextField, Typography} from "@mui/material";
import {checkPassword} from "./formUtils";

function SignUpForm(){

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [username, setUsername] = useState("");
    const [userFName, setUserFName] = useState("");
    const [userLName, setUserLName] = useState("");
    const [passwordRepeat, setPasswordRepeat] = useState("");
    const [userType, setUserType] = useState("");
    const [birthDate, setBirthDate] = useState("");
    const [address, setAddress] = useState("");
    const [validPassword, setValidPassword] = useState(true);

    function checkPasswordValid(data){
        if(!checkPassword(data)){
            setValidPassword(false);
        }else{
            setValidPassword(true);
        }
    }

    function submitData(){
        fetch("http://localhost:8080/accounts/sign-up",
            {
                method: "POST",
                headers:{
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    email: email,
                    password: password,
                    username: username,
                    user_fname: userFName,
                    user_lname: userLName,
                    user_type: userType,
                    birth_date: birthDate,
                    address: address,
                })
            }).then(function(response){
            //TODO: implement for response
        })
    }

    function handleSubmit(event) {
        event.preventDefault();
        alert('Submitted ' + email + ' ' + username + ' ' + userFName + ' ' + userLName + ' ' + password + ' ' + passwordRepeat + ' ' + userType + ' ' + birthDate + ' ' + address);
        if(!validPassword || passwordRepeat !== password){
            alert("Invalid data!")
        }else{
            submitData()
        }
    }

    return (
        <Box component="form" onSubmit={handleSubmit} className="form">
            <Typography component="h2" variant="h5" sx={{fontWeight: 'bold'}}>SIGN UP</Typography>
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
                label="Username"
                name="username"
                autoComplete="username"
                size="small"
                type="text"
                value={username}
                onChange={e => setUsername(e.target.value)}
            />

            <Stack direction="row">
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    label="First name"
                    name="user_fname"
                    size="small"
                    type="text"
                    value={userFName}
                    onChange={e => setUserFName(e.target.value)}
                />
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    label="Last name"
                    name="user_lname"
                    size="small"
                    type="text"
                    value={userLName}
                    onChange={e => setUserLName(e.target.value)}
                />
            </Stack>
            <TextField
                margin="normal"
                required
                fullWidth
                label="Birthday"
                name="birth_date"
                size="small"
                type="date"
                value={birthDate}
                InputLabelProps={{ shrink: true }}
                onChange={e => setBirthDate(e.target.value)}
            />
            <TextField
                margin="normal"
                required
                fullWidth
                label="Home Address"
                name="address"
                size="small"
                type="text"
                value={address}
                onChange={e => setAddress(e.target.value)}
            />
            <FormControl  required fullWidth size="small" sx={{my: 1}}>
                <InputLabel id="type-select-label">User Type</InputLabel>
                <Select
                    labelId="type-select-label"
                    label="User Type"
                    name="user_type"
                    value={userType}
                    onChange = {e => setUserType(e.target.value)}
                >
                    <MenuItem value="chair">Chair</MenuItem>
                    <MenuItem value="reviewer">Reviewer</MenuItem>
                    <MenuItem value="author">Author</MenuItem>
                </Select>
            </FormControl>
            <TextField
                margin="normal"
                required
                fullWidth
                label="Password"
                name="password"
                size="small"
                type="password"
                value={password}
                error={!validPassword}
                helperText={!validPassword ? "The password needs minimum eight characters, at least one uppercase letter" +
                    ", one lowercase letter and one number:" : ""}
                onChange={e => {
                    setPassword(e.target.value);
                    checkPasswordValid(e.target.value);
                }}
            />
            <TextField
                margin="normal"
                required
                fullWidth
                label="Confirm Password"
                name="password_repeat"
                size="small"
                type="password"
                value={passwordRepeat}
                error={passwordRepeat !== "" && passwordRepeat !== password}
                helperText={passwordRepeat !== "" && passwordRepeat !== password ? "The passwords do not match!" : ""}
                onChange={e => setPasswordRepeat(e.target.value)}
            />
            <Button
                type="submit"
                fullWidth
                variant="contained"
            >
                Sign up
            </Button>
        </Box>
    )
}

export default SignUpForm