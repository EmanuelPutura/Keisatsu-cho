import "./chairStyle.css"
import React, {useEffect, useState} from "react";
import Container from "@mui/material/Container";
import Stack from "@mui/material/Stack";
import AddConferenceForm from "./AddConferenceForm";
import SeeConferences from "./SeeConferences";
import SeePapers from "./SeePapers";
import Header from "../commons/Header";

function ChairPage({name, token, setToken}){

    const [conferences, setConferences] = useState([]);
    const [papers, setPapers] = useState([]);

    // Conferences format
    const testConferences = [{  id: 1,
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

    // Papers format
    const testPapers = [{ id: 1,
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

    useEffect(() => {
        const conferenceRequest = accountID => {
            if (accountID !== undefined && accountID !== 122) {
                fetch("http://localhost:8080/conferences/get?accountID=" + accountID.toString())
                    .then(response => response.json())
                    .then(data => {
                        setConferences(data);
                    })
                    .catch(() => alert("Invalid account!"));
            } else {
                setConferences(() => accountID === 122 ? [...testConferences] : []);
            }
        }
        conferenceRequest(token);
    },[token])

    useEffect(() => {
        const paperRequest = accountID => {
            if (accountID !== undefined && accountID !== 122) {
                fetch("http://localhost:8080/papers?accountID=" + accountID.toString())
                    .then(response => response.json())
                    .then(data => {
                        setPapers(data);
                    })
                    .catch(() => alert("Invalid account!"));
            } else {
                setPapers(accountID === 122 ? testPapers : []);
            }
        };
        paperRequest(token);
    }, [token])

    return(
        <Container component="div" disableGutters={true} maxWidth={false}>
            <Header name={name} setToken={setToken} />
            <Stack component="div" spacing={2}>
                <AddConferenceForm />
                <SeeConferences conferences={conferences}/>
                <SeePapers papers={papers} conferences={conferences} token={token}/>
            </Stack>
        </Container>
    )
}

export default ChairPage