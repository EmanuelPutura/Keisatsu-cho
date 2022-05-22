import Typography from "@mui/material/Typography";
import Stack from "@mui/material/Stack";
import Divider from "@mui/material/Divider";
import {ListItemWithCollapsible} from "../formUtils";
import Box from "@mui/material/Box";
import React from "react";
import Button from "@mui/material/Button";
import ListItemText from "@mui/material/ListItemText";
import PaperComments from "./PaperComments";
import downloadFile from "../commons/FileDownload";

function PaperBidCollapsible({token, paper, refreshList}){
    const paperObj = JSON.parse(paper);

    function bid(){
        fetch("http://localhost:8080/reviewers/bid",
            {
                method: "POST",
                headers:{
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    token: token,
                    paperID: paperObj.id
                })
            }).then(response => response.json())
            .then(() =>{
                alert("Paper has been bid on!");
                refreshList(paperObj.id);
            })
    }

    return(
        <Box component="div" width="80%" className="reviewer_form">
            <Stack component="div"
                      direction="column"
                      spacing={2}
            >
                <Typography variant="h6" component="h3">
                    Abstract:
                </Typography>
                <Typography variant="caption" component="h4">
                    {paperObj.abstract}
                </Typography>
                <Stack component="div"
                       direction="row"
                       spacing={0}
                       divider={<Divider orientation="vertical" flexItem/> }
                >
                    <Box component="div" width="100%">
                        <Typography variant="h6" component="h3">
                            Authors:
                        </Typography>
                        {
                            paperObj.authors &&  paperObj.authors.length > 0 && paperObj.authors.map((author) => (
                                <ListItemText key={author}>{author.name}</ListItemText>
                            ))
                        }
                    </Box>
                    <Box component="div" width="100%">
                        <Typography variant="h6" component="h3">
                            Topic of interest:
                        </Typography>
                        <Typography variant="body1" component="h4">
                            {paperObj.topic}
                        </Typography>
                    </Box>
                </Stack>
                <Stack component="div"
                    direction="row"
                    spacing={0}
                    divider={<Divider orientation="vertical" flexItem/> }
                    >
                    <Button fullWidth
                            onClick={() => downloadFile(paperObj)}
                            variant="contained">
                        DOWNLOAD
                    </Button>
                    <Button fullWidth
                            onClick={bid}
                            variant="contained">
                        BID
                    </Button>
                </Stack>

                <PaperComments paperID={paperObj.id} token={token}/>
            </Stack>
        </Box>
    )

}

function PaperBid({token, papers}){

    function deleteFromList(paperID){
        papers.filter((paper) => paper.id !== paperID)
    }

    return(<Box component="div" className="reviewer_container">
        <Typography component="h2" variant="h5" align="center" my="5px">
            Bid on Papers
        </Typography>
        <Stack component="div"
               direction="column"
               spacing={0}
               divider={<Divider orientation="horizontal" flexItem/>}
        >
            {
                papers && papers.length > 0 && papers.map((paper) => (
                    <ListItemWithCollapsible value={paper.title} collapsible={
                        <PaperBidCollapsible paper={JSON.stringify(paper)} token={token} refreshList={deleteFromList}/>
                    }/>
                ))
            }
        </Stack>
    </Box>)
}

export default PaperBid