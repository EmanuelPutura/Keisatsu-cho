import React, {useState} from 'react'
import "./form.css"
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import Select from "@mui/material/Select";
import Stack from "@mui/material/Stack";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";
import {checkPassword} from "../formUtils";

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
                    userFName: userFName,
                    userLName: userLName,
                    userType: userType,
                    birthDate: birthDate,
                    address: address,
                })
            }).then(response => response.json())
            .then(data =>{
                switch (data){
                    case 1: // invalid account due to random data
                        alert("Invalid account!");
                        break;
                    case 2: // email already in use
                        alert("An account with that email already exists!");
                        break;
                    default:
                        alert("Account created! Please log in!");
                        break;
                }
            })
    }

    function handleSubmit(event) {
        event.preventDefault();
        if(!validPassword || passwordRepeat !== password){
            alert("Invalid data!")
        }else{
            submitData();
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
                    name="userFName"
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
                    name="userLName"
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
                name="birthDate"
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
                    name="userType"
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
                name="passwordRepeat"
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