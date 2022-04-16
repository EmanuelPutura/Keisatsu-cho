import "./chairStyle.css"
import React from "react";
import {Container, Stack, Typography} from "@mui/material";
import AddConferenceForm from "./AddConferenceForm";
import SeeConferences from "./SeeConferences";
import SeePapers from "./SeePapers";

function ChairPage({name}){
    return(
        <Container component="div" disableGutters={true} maxWidth={false}>
            <Typography variant="h4" component="h1" id="main-title">
                Hi {name}
            </Typography>
            <Stack component="div" spacing={2}>
                <AddConferenceForm />
                <SeeConferences />
                <SeePapers />
            </Stack>
        </Container>
    )
}

export default ChairPage