import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Divider from "@mui/material/Divider";
import Stack from "@mui/material/Stack";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";
import React, {useState} from "react";
import {ListItemWithCollapsible} from "../formUtils";

function InterestTopicsForm({conference}) {

    conference = JSON.parse(conference)
    const [topics, setTopics] = useState(conference.topics);

    function updateTopics(event){
        event.preventDefault();
        fetch("http://localhost:8080/accounts/conferenceTopics/",
            {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    conferenceID: conference.id,
                    topics: topics
                })
            }).then(response => response.json())
            .then(() => {
                alert("response sent");
            })
    }

    return (
        <Box component="form" className="chair-form" onSubmit={updateTopics}>
            <TextField
                margin="normal"
                required
                fullWidth
                label="Topics of interest"
                name="topics"
                size="small"
                multiline
                value={topics}
                onChange={e => setTopics(e.target.value)}
            />
            <Button
                type="submit"
                fullWidth
                variant="contained"
            >
                UPDATE TOPICS OF INTEREST
            </Button>
        </Box>
    )
}

function DeadlineForm({conference}) {

    function updateDeadlines(event){
        event.preventDefault();
        fetch("http://localhost:8080/accounts/conferenceDeadlines/",
            {
                method: "PUT",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    conferenceID: conference.id,
                    submission: submission,
                    review: review,
                    acceptance: acceptance,
                    upload: upload
                })
            }).then(response => response.json())
            .then(() => {
                alert("response sent");
            })
    }


    conference = JSON.parse(conference);
    const [submission, setSubmission] = useState(conference.submission);
    const [review, setReview] = useState(conference.review);
    const [acceptance, setAcceptance] = useState(conference.acceptance);
    const [upload, setUpload] = useState(conference.upload);

    return (
        <Box component="form" className="chair-form" onSubmit={updateDeadlines}>
            <Stack component="div"
                   direction="row"
                   justifyContent="space-evenly"
                   divider={<Divider orientation="vertical" flexItem/>}
            >
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    label="Submission Deadline"
                    name="submission"
                    size="small"
                    type="date"
                    InputLabelProps={{shrink: true}}
                    value={submission}
                    onChange={e => setSubmission(e.target.value)}
                />
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    label="Review Deadline"
                    name="review"
                    size="small"
                    type="date"
                    InputLabelProps={{shrink: true}}
                    value={review}
                    onChange={e => setReview(e.target.value)}
                />
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    label="Acceptance Notification Deadline"
                    name="acceptance"
                    size="small"
                    type="date"
                    InputLabelProps={{shrink: true}}
                    value={acceptance}
                    onChange={e => setAcceptance(e.target.value)}
                />
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    label="Paper Upload Deadline"
                    name="upload"
                    size="small"
                    type="date"
                    InputLabelProps={{shrink: true}}
                    value={upload}
                    onChange={e => setUpload(e.target.value)}
                />
            </Stack>
            <Button
                type="submit"
                fullWidth
                variant="contained"

            >
                UPDATE DEADLINES
            </Button>
        </Box>
    )
}

function ConferenceCollapsible({conference}) {
    return (
        <Box component="div">
            <DeadlineForm conference={conference}/>
            <InterestTopicsForm conference={conference}/>
        </Box>
    )
}

function SeeConferences({conferences}) {
    return (
        <Box component="div" className="chair_container">
            <Typography component="h2" variant="h5" align="center" my="5px">
                Conferences
            </Typography>
            <Stack component="div"
                   direction="column"
                   spacing={0}
                   divider={<Divider orientation="horizontal" flexItem/>}
            >
                {
                    conferences.map((conference) => (
                        <ListItemWithCollapsible value={conference.name} collapsible={
                            <ConferenceCollapsible conference={JSON.stringify(conference)}/>
                        }/>
                    ))
                }
            </Stack>
        </Box>
    )
}

export default SeeConferences;