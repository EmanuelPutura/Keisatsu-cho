import "./chairStyle.css"
import React from "react";
import Container from "@mui/material/Container";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";
import AddConferenceForm from "./AddConferenceForm";
import SeeConferences from "./SeeConferences";
import SeePapers from "./SeePapers";
import Button from "@mui/material/Button";

function ChairPage({name, token, setToken}){

    function logOut(){
        setToken(undefined)
    }

    //TODO: request to get conferences (see format below for example)
    //TODO: request to get papers

    const conferences = [{  id: 1,
                            name: "Conference name",
                            url: "conference.com",
                            subtitles: "Subtitle1\nSubtitle2",
                            topics: "Topic1\n" +
                                "Topic2\n" +
                                "Topic3\n",
                            submission:"2021-08-07",
                            review:"2021-09-07",
                            acceptance:"2021-10-07",
                            upload:"2021-11-07"}];

    const papers = [{ id: 1,
                      title: "Paper 1",
                      keywords: "...",
                      topic: "...",
                      decided: false
                    },
                    { id: 2,
                      title: "Paper 2",
                      keywords: "...",
                      topic: "...",
                      decided: true
                    }];

    return(
        <Container component="div" disableGutters={true} maxWidth={false}>
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
            <Stack component="div" spacing={2}>
                <AddConferenceForm />
                <SeeConferences conferences={conferences}/>
                <SeePapers papers={papers} conferences={conferences} token={token}/>

            </Stack>
        </Container>
    )
}

export default ChairPage