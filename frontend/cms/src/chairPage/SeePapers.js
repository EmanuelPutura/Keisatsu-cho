import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Divider from "@mui/material/Divider";
import Stack from "@mui/material/Stack";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import Select from "@mui/material/Select";
import Typography from "@mui/material/Typography";
import {ListItemWithCollapsible} from "../formUtils";
import React, {useState} from "react";

function PaperCollapsible({paper, conferences, token}){

    const paperObj = JSON.parse(paper);
    const conferencesArray = JSON.parse(conferences)

    function sendResponse(response){
        fetch("http://localhost:8080/accounts/papers",
            {
                method: "PUT",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    chairID: token,
                    paperID: paperObj.id,
                    response: response
                })
            }).then(response => response.json())
            .then(() => {
                alert("response sent");
            })
    }

    function assignPaper(event){
        event.preventDefault();
        fetch("http://localhost:8080/accounts/papers/assign",
            {
                method: "PUT",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    chairID: token,
                    paperID: paperObj.id,
                    conferenceID: conferenceID
                })
            }).then(response => response.json())
            .then(() => {
                alert("response sent");
            })
    }


    paper = JSON.parse(paper);
    const [decided, setDecided] = useState(paper.decided);
    const [conferenceID, setConferenceID] = useState("");
    return (
        <Box component="div" className="chair-form">
            { !decided ?
            <Stack component="div"
                   direction="row"
                   justifyContent="space-evenly"
            >
                <Button
                    fullWidth
                    variant="contained"
                    onClick={() => {sendResponse(true); setDecided(true);}}
                >
                    ACCEPT
                </Button>
                <Button
                    fullWidth
                    variant="contained"
                    onClick={() => {sendResponse(false); setDecided(true);}}
                >
                    REJECT
                </Button>
            </Stack> :
            <Stack component="form"
                   direction="column"
                   justifyContent="space-evenly"
                   onSubmit={assignPaper}
            >
                <FormControl required fullWidth size="small" sx={{my: 1}}>
                    <InputLabel id="conference-select-label">Conference</InputLabel>
                    <Select
                        labelId="conference-select-label"
                        label="Conference"
                        name="conference"
                        value={conferenceID}
                        onChange={e => setConferenceID(e.target.value)}
                    >
                        {
                            conferencesArray.map((conference) =>(
                                <MenuItem value={conference.id}>{conference.name}</MenuItem>
                            ))
                        }
                    </Select>
                </FormControl>
                <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                >
                    ASSIGN TO CONFERENCE
                </Button>
            </Stack>
            }
        </Box>
    )
}

function SeePapers({papers, conferences, token}){

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
                    papers.map((paper) => (
                    <ListItemWithCollapsible value={paper.title} collapsible={
                        <PaperCollapsible paper={JSON.stringify(paper)} conferences={JSON.stringify(conferences)} token={token}/>
                    }/>
                    ))
                }
            </Stack>
        </Box>
    )
}

export default SeePapers