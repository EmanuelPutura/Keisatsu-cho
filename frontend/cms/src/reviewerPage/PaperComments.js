import React, {useEffect, useState} from "react"
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Stack from "@mui/material/Stack";
import Divider from "@mui/material/Divider";
import {ListItemWithCollapsible} from "../formUtils";
import Box from "@mui/material/Box";

export default function PaperComments({paperID, token}) {
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
            fetch("http://localhost:8080/reviewers/comments?paperId="+paperID, {
                method: 'GET',
                headers: {
                    'Authorization' : 'Bearer ' + localStorage.getItem('jwt')
                }
            })
                .then(response => response.json())
                .then(data => {
                    setComments(data);
                })
                .catch(() => alert("Invalid comments request!"))
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
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem('jwt')
                },
                body: JSON.stringify({
                    token: token,
                    paperID: paperID,
                    comment: comment
                })
            }).then(response => response.json())
            .then(data => {
                if(data.executedWithoutErrors){
                    alert("Comment uploaded!");
                    getComments();
                } else {
                    alert(data.errorMessage);
                }
            })
    }


    return (
        <Box component="div">
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
                   divider={<Divider orientation="horizontal" flexItem/>}
            >
                {
                    comments && comments.length > 0 ? (comments.map((comment) => (
                        <ListItemWithCollapsible value={comment.name} collapsible={
                            <Typography variant="body1" component="p">{comment.comment}</Typography>
                        }/>
                    ))) : "There are no comments."
                }
            </Stack>
        </Box>
    )
}