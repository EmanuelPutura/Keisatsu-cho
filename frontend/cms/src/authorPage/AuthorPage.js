import React, {useEffect, useState} from "react";
import Container from "@mui/material/Container";
import Header from "../commons/Header";
import Stack from "@mui/material/Stack";
import AddPaperForm from "./AddPaperForm";
import "./authorStyle.css"
import SeePapers from "./SeePapers";

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
        conferenceName: "Conference 1"
    },
    {
        id: 2,
        title: "Paper 2",
        abstract: "lorem ipsum ...",
        authors: [{name: "Author1", email: "i@a.com", address: "idk"}],
        keywords: "...",
        topic: "...",
        conferenceName: "Conference 2"
    }];

    const testNotFullPapers = [{
        id: 3,
        title: "Paper 3",
        abstract: "lorem ipsum ...",
        authors: [{name: "Author1", email: "i@a.com", address: "idk"}],
        keywords: "...",
        topic: "...",
        conferenceName: "Conference 3"
    },
    {
        id: 4,
        title: "Paper 4",
        abstract: "lorem ipsum ...",
        authors: [{name: "Author1", email: "i@a.com", address: "idk"}],
        keywords: "...",
        topic: "...",
        conferenceName: "Conference 4"
    }];

    function requestAcceptedPapers() {
        const acceptedPapersRequest = token => {
            if (token !== undefined && token !== 123) {
                fetch("http://localhost:8080/papers?token=" + token.toString() + "&type=accepted",
                {
                    method: 'GET',
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem("jwt")
                    }
                })
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
    }

    useEffect(() => {
        requestAcceptedPapers();
    }, [token]);

    function requestNotUploadedPapers() {
        const notFullPapersRequest = token => {
            if (token !== undefined && token !== 123) {
                fetch("http://localhost:8080/papers?token=" + token.toString() + "&type=missingFull",
                {
                    method: 'GET',
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem("jwt")
                    }
                })
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
    }

    useEffect(() => {
        requestNotUploadedPapers()
    }, []);


    return (
        <Container component="div" disableGutters={true} maxWidth={false}>
            <Header name={name} setToken={setToken}/>
            <Stack component="div" spacing={2}>
                <AddPaperForm token={token} refreshList={requestNotUploadedPapers}/>
                <SeePapers papers={papersToBeUploaded}
                           token={token}
                           papersRequest={requestNotUploadedPapers}
                           title={"Papers to be uploaded"}
                           buttonText={"Upload Full Paper"}
                           url={"http://localhost:8080/papers/uploadPaper"}
                />
                <SeePapers papers={acceptedPapers}
                           token={token}
                           papersRequest={requestAcceptedPapers}
                           title={"Papers that need Camera Ready Copies"}
                           buttonText={"Upload Camera Ready Copy"}
                           url={"http://localhost:8080/papers/uploadCameraReady"}
                />
            </Stack>
        </Container>
    )

}

export default AuthorPage;