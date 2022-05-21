import React, {useEffect, useState} from "react";
import Container from "@mui/material/Container";
import Header from "../commons/Header";
import Stack from "@mui/material/Stack";
import AddPaperForm from "./AddPaperForm";
import "./authorStyle.css"
import SeeNotUploadedPapers from "./SeeNotUploadedPapers";
import SeeCameraReadyPapers from "./SeeAcceptedPapers";

function AuthorPage({name, token, setToken}) {

    const [acceptedPapers, setAcceptedPapers] = useState([]);
    const [papersToBeUploaded, setPapersToBeUploaded] = useState([]);

    const testAcceptedPapers = [{
        id: 1,
        title: "Paper 1",
        abstract: "lorem ipsum ...",
        authors: [{name: "Author1", email: "i@a.com", address: "idk"}],
        keywords: "...",
        topic: "...",
        decided: true
    },
    {
        id: 2,
        title: "Paper 2",
        abstract: "lorem ipsum ...",
        authors: [{name: "Author1", email: "i@a.com", address: "idk"}],
        keywords: "...",
        topic: "...",
        decided: true
    }];

    const testNotFullPapers = [{
        id: 3,
        title: "Paper 3",
        abstract: "lorem ipsum ...",
        authors: [{name: "Author1", email: "i@a.com", address: "idk"}],
        keywords: "...",
        topic: "...",
        decided: false
    },
    {
        id: 4,
        title: "Paper 4",
        abstract: "lorem ipsum ...",
        authors: [{name: "Author1", email: "i@a.com", address: "idk"}],
        keywords: "...",
        topic: "...",
        decided: false
    }];

    useEffect(() => {
        const acceptedPapersRequest = token => {
            if (token !== undefined && token !== 123) {
                fetch("http://localhost:8080/papers?token=" + token.toString() + "&type=accepted")
                    .then(response => response.json())
                    .then(data => {
                        setAcceptedPapers(data);
                    })
                    .catch(() => alert("Invalid paper request!"));
            } else {
                setAcceptedPapers(() => token === 123 ? [...testAcceptedPapers] : []);
            }
        }
        acceptedPapersRequest(token);
    }, [token]);

    useEffect(() => {
        const notFullPapersRequest = token => {
            if (token !== undefined && token !== 123) {
                fetch("http://localhost:8080/papers?token=" + token.toString() + "&type=missingFull")
                    .then(response => response.json())
                    .then(data => {
                        setPapersToBeUploaded(data);
                    })
                    .catch(() => alert("Invalid paper request!"));
            } else {
                setPapersToBeUploaded(() => token === 123 ? [...testNotFullPapers] : []);
            }
        }
        notFullPapersRequest(token);
    }, [token]);


    return (
        <Container component="div" disableGutters={true} maxWidth={false}>
            <Header name={name} setToken={setToken}/>
            <Stack component="div" spacing={2}>
                <AddPaperForm token={token}/>
                <SeeNotUploadedPapers papers={papersToBeUploaded} token={token}/>
                <SeeCameraReadyPapers papers={acceptedPapers} token={token}/>
            </Stack>
        </Container>
    )

}

export default AuthorPage;