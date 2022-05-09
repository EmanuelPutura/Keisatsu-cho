import React, {useCallback, useEffect, useState} from "react";
import Header from "../commons/Header";
import Container from "@mui/material/Container";
import Stack from "@mui/material/Stack";
import SetTopics from "./SetTopics";
import PaperBid from "./PaperBid";
import Typography from "@mui/material/Typography";

function ReviewerPage({name, token, setToken}){

    const [papersToBid, setPapersToBid]=useState([]);
    const [papersToReview, setPapersToReview]=useState([]);

    const testPapersToBid = [{ id: 1,
        title: "Paper 1",
        abstract: "lorem ipsum ...",
        authors: [{name: "Author1", email: "i@a.com", address: "idk"}],
        keywords: "...",
        topic: "...",
    },
    { id: 2,
        title: "Paper 2",
        abstract: "lorem ipsum 2 ...",
        authors: [{name: "Author1", email: "i@a.com", address: "idk"},
            {name: "Author2", email: "a@i.com", address: "idk"}],
        keywords: "...",
        topic: "...",
    }];

    const testPapersToReview = [{ id: 1,
        title: "Paper 1",
        abstract: "lorem ipsum ...",
        authors: [{name: "Author1", email: "i@a.com", address: "idk"}],
        keywords: "...",
        topic: "...",
        comments: [{name: "Andrew", comment: "Great paper"}]
    },
    { id: 2,
        title: "Paper 2",
        abstract: "lorem ipsum 2 ...",
        authors: [{name: "Author1", email: "i@a.com", address: "idk"},
            {name: "Author2", email: "a@i.com", address: "idk"}],
        keywords: "...",
        topic: "...",
        comments: []
    }];

    const papersToBidRequest = useCallback( () => {
        if(token !== undefined && token !== 124){
            fetch("http://localhost:8080/papers/to_bid?accountID=" + token.toString())
                .then(response => response.json())
                .then(data => {
                    setPapersToBid(data);
                })
                .catch(() => alert("Invalid papers request!"))
        } else {
            setPapersToBid(() => token === 124 ? testPapersToBid : "");
        }
    }, [token]);

    /*
    useEffect(() => {
        papersToBidRequest();
    }, [papersToBidRequest]);


    const papersToReviewRequest = useCallback( () => {
        if(token !== undefined && token !== 124){
            fetch("http://localhost:8080/papers/to_review?accountID=" + token.toString())
                .then(response => response.json())
                .then(data => {
                    setPapersToBid(data);
                })
                .catch(() => alert("Invalid papers request!"))
        } else {
            setPapersToReview(() => token === 124 ? testPapersToReview : "");
        }
    }, [token]);


    useEffect(() => {
        papersToReviewRequest();
    }, [papersToReviewRequest]);
*/
    return(
        <Container component="div" disableGutters={true} maxWidth={false}>
            <Header name={name} setToken={setToken}/>
            <Stack component="div" spacing={2}>
                <SetTopics token={token} papersRequest={papersToBidRequest}/>
                <PaperBid token={token} papers={papersToBid}/>
            </Stack>
        </Container>
    )

}

export default ReviewerPage;