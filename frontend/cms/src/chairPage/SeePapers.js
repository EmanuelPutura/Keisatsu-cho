import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Divider from "@mui/material/Divider";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";
import {ListItemWithCollapsible} from "../formUtils";
import React from "react";
import ListItemText from "@mui/material/ListItemText";
import downloadFile from "../commons/FileDownload";

function PaperCollapsible({paper, papersRequest, conference, token}){

    const paperObj = JSON.parse(paper);

    //TODO: request changed -- added conferenceID representing the ID of the conference for which the paper is reviewed
    function sendResponse(response){
        fetch("http://localhost:8080/accounts/papers",
            {
                method: "PUT",
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem("jwt") 
                },
                body: JSON.stringify({
                    chairID: token,
                    paperID: paperObj.id,
                    conferenceID: paperObj.conferenceID,
                    response: response
                })
            }).then(response => response.json())
            .then(data => {
                if(data.executedWithoutErrors){
                    alert("Response sent!");
                    papersRequest();
                } else {
                    alert(data.errorMessage);
                }
            });
    }

    return (
        <Box component="div" className="chair-form">
            <Typography variant="h6" component="h3">
                Abstract:
            </Typography>
            <Typography variant="caption" component="h4">
                {paperObj.abstract}
            </Typography>
            <Typography variant="h6" component="h3">
                Conference:
            </Typography>
            <Typography variant="caption" component="h4">
                {conference}
            </Typography>
            <Box component="div">
                <Typography variant="h6" component="h3">
                    Authors:
                </Typography>
                <Box component="div">
                {
                    paperObj.authors && paperObj.authors.length > 0 && paperObj.authors.map((author) => (
                        <ListItemText key={author}>{author.name}</ListItemText>
                    ))
                }
                </Box>
                <Stack component="div"
                       direction="row"
                       justifyContent="space-evenly"
                >
                    <Button
                        fullWidth
                        variant="contained"
                        onClick={() => {downloadFile(paperObj);}}
                    >
                        DOWNLOAD
                    </Button>
                    <Button
                        fullWidth
                        variant="contained"
                        onClick={() => {sendResponse(true);}}
                    >
                        ACCEPT
                    </Button>
                    <Button
                        fullWidth
                        variant="contained"
                        onClick={() => {sendResponse(false);}}
                    >
                        REJECT
                    </Button>
                </Stack>
            </Box>
        </Box>
    )
}

function SeePapers({papers, papersRequest, conferences, token}){

    return (
        <Box component="div" className="chair_container">
            <Typography component="h2" variant="h5" align="center" my="5px">
                Papers
            </Typography>
            <Stack component="div"
                   direction="column"
                   spacing={0}
                   divider={<Divider orientation="horizontal" flexItem/>}
            >
                {
                    papers && papers.length > 0 && papers.map((paper) => (
                    <ListItemWithCollapsible value={paper.title} collapsible={
                        <PaperCollapsible
                            paper={JSON.stringify(paper)}
                            papersRequest={papersRequest}
                            conference={conferences.find((conference) => conference.id===paper.conferenceID).name}
                            token={token}
                        />
                    }/>
                    ))
                }
            </Stack>
        </Box>
    )
}

export default SeePapers