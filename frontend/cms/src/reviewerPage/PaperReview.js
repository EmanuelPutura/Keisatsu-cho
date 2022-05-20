import React, {useEffect, useState} from "react";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Stack from "@mui/material/Stack";
import Divider from "@mui/material/Divider";
import {ListItemWithCollapsible} from "../formUtils";
import ListItemText from "@mui/material/ListItemText";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";

function PaperReviewCollapsible({paper, token, paperRequest}) {
    const paperObj = JSON.parse(paper);
    const [comments, setComments] = useState([]);
    const [comment, setComment]=useState("");
    const testComments=[{
        name: "testName",
        comment: "very good 10/10"
    },
    {
        name: "testName1",
        comment: "very bad"
    }];

    //TODO: new request
    function getComments(){
        if(token !== undefined && token !== 124){
            fetch("http://localhost:8080/reviewers/comments?paperId="+paperObj.id)
                .then(response => response.json())
                .then(data => {
                    setComments(data);
                })
                .catch(() => alert("Invalid papers request!"))
        } else {
            setComments(token === 124 ? testComments : "");
        }
    }

    useEffect(getComments, []);

    //TODO: new request
    function sendComment(){
        fetch("http://localhost:8080/reviewers/comment",
            {
                method: "POST",
                headers:{
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    token: token,
                    paperID: paperObj.id,
                    comment: comment
                })
            }).then(response => response.json())
            .then(() =>{
                alert("Comment has been sent!");
            })
    }

    //TODO: new request
    function signalConflict(){
        fetch("http://localhost:8080/reviewers/conflict",
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
                paperRequest();
                alert("Conflict has been signaled!");
            })
    }

    //TODO: new request
    function acceptPaper(){
        fetch("http://localhost:8080/reviewers/acceptPaper",
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
                paperRequest();
                alert("Paper has been accepted!");
            })
    }

    //TODO: new request
    function rejectPaper(){
        fetch("http://localhost:8080/reviewers/rejectPaper",
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
                paperRequest();
                alert("Paper has been rejected!");
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
                            paperObj.authors && paperObj.authors.length > 0 && paperObj.authors.map((author) => (
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
                            onClick={signalConflict}
                            variant="contained">
                        SIGNAL CONFLICT
                    </Button>
                    <Button fullWidth
                            onClick={acceptPaper}
                            variant="contained">
                        ACCEPT
                    </Button>
                    <Button fullWidth
                            onClick={rejectPaper}
                            variant="contained">
                        REJECT
                    </Button>
                </Stack>
                <Typography variant="h6" component="p">Comments</Typography>
                <TextField
                    margin="normal"
                    fullWidth
                    required
                    multiline
                    label="Comment"
                    name="comment"
                    value={comment}
                    onChange={e => setComment(e.target.value)}
                />
                <Button fullWidth onClick={sendComment} variant="contained">SEND COMMENT</Button>
                <Stack component="div"
                       direction="column"
                       spacing={0}
                       divider={<Divider orientation="horizontal" flexItem/> }
                >
                    {
                        comments && comments.length > 0 ? (comments.map((comment) => (
                            <ListItemWithCollapsible value={comment.name} collapsible={
                                <Typography variant="body1" component="p">{comment.comment}</Typography>
                            }/>
                        ))) : "There are no comments."
                    }
                </Stack>
            </Stack>
        </Box>
    )
}

export default function PaperReview({token, papers, paperRequest}){
    return(<Box component="div" className="reviewer_container">
        <Typography component="h2" variant="h5" align="center" my="5px">
            Review Papers
        </Typography>
        <Stack component="div"
               direction="column"
               spacing={0}
               divider={<Divider orientation="horizontal" flexItem/>}
        >
            {
                papers && papers.length > 0 && papers.map((paper) => (
                    <ListItemWithCollapsible value={paper.title} collapsible={
                        <PaperReviewCollapsible paper={JSON.stringify(paper)} token={token} paperRequest={paperRequest}/>
                    }/>
                ))
            }
        </Stack>
    </Box>)

}