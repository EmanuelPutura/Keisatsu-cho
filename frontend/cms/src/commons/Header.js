import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import React from "react";

function Header({name, setToken}){
    function logOut(){
        setToken(undefined)
        localStorage.removeItem("jwt")
    }

    return(
        <Stack component="div" direction="row" justifyContent="space-between" width="80%" mx="auto">
            <Typography variant="h4" component="h1" id="main-title">
                Hi {name}
            </Typography>
            <Button
                variant="contained"
                onClick={logOut}
            >
                LOG OUT
            </Button>
        </Stack>
    )
}

export default Header