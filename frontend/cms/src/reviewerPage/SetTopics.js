import {useEffect, useState} from "react";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import "./reviewerStyle.css";

function SetTopics({token, papersRequest}){
    const [topics, setTopics]=useState("");
    const testTopics = "Topic1\nTopic2\nTopic3";

    useEffect(() => {
        const topicsRequest = token => {
            if(token !== undefined && token !== 124){
                fetch("http://localhost:8080/accounts/topics?accountID=" + token.toString())
                    .then(response => response.json())
                    .then(data => {
                        setTopics(data.topics);
                        papersRequest();
                    })
                    .catch(() => alert("Invalid topics request!"))
            } else {
                setTopics(() => token === 124 ? testTopics : "");
            }
        }
        topicsRequest(token);
    }, [token, papersRequest]);


    function submitData(event){
        event.preventDefault();
        fetch("http://localhost:8080/accounts/topics",
            {
                method: "PUT",
                headers:{
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    token: token,
                    topics: topics
                })
            }).then(response => response.json())
            .then(() =>{
                alert("Topics updated!");
            })
    }


    return(
        <Box component="div" className="reviewer_container">
            <Typography variant="h5" component="h2" align="center" my="5px">
                Set up topics of interest:
            </Typography>
            <Box component="form" className="reviewer_form" onSubmit={submitData}>
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    multiline
                    label="Topics of Interest, one on each row"
                    name="topics"
                    value={topics}
                    onChange={e => setTopics(e.target.value)}
                />
                <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                >
                    UPDATE TOPICS
                </Button>
            </Box>
        </Box>
    )
}

export default SetTopics