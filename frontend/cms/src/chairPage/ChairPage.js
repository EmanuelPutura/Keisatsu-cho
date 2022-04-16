import "./chairStyle.css"
import React from "react";
import Container from "@mui/material/Container";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";
import AddConferenceForm from "./AddConferenceForm";
import SeeConferences from "./SeeConferences";
import SeePapers from "./SeePapers";

function ChairPage({name}){

    //TODO: request to get conferences
    //TODO: request to get papers

    const conferences = [{  name: "Conference name",
                            topics: "Topic1\n" +
                                "Topic2\n" +
                                "Topic3\n",
                            submission:"2021-08-07",
                            review:"2021-09-07",
                            acceptance:"2021-10-07",
                            upload:"2021-11-07"}];

    const papers = [{ name: "Paper 1",
                      decided: false
                    },
                    { name: "Paper 2",
                      decided: true
                    }];

    return(
        <Container component="div" disableGutters={true} maxWidth={false}>
            <Typography variant="h4" component="h1" id="main-title">
                Hi {name}
            </Typography>
            <Stack component="div" spacing={2}>
                <AddConferenceForm />
                <SeeConferences conferences={conferences}/>
                <SeePapers papers={papers} conferences={conferences}/>

            </Stack>
        </Container>
    )
}

export default ChairPage