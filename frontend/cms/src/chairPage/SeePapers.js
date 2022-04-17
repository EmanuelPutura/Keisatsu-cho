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

    //TODO: request for accepting / rejecting paper (send user token as well)
    //TODO: request for assigning paper to conference (conferences have their id)
    paper = JSON.parse(paper);
    const [decided, setDecided] = useState(paper.decided);
    const [conference, setConference] = useState("");
    return (
        <Box component="div" className="chair-form">
            { !decided ?
            <Stack component="div"
                   direction="row"
                   justifyContent="space-evenly"
            >
                <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                >
                    ACCEPT
                </Button>
                <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                >
                    REJECT
                </Button>
            </Stack> :
            <Stack component="form"
                   direction="column"
                   justifyContent="space-evenly"
            >
                <FormControl  required fullWidth size="small" sx={{my: 1}}>
                    <InputLabel id="conference-select-label">Conference</InputLabel>
                    <Select
                        labelId="conference-select-label"
                        label="Conference"
                        name="conference"
                        value={conference}
                        onChange={e => setConference(e.target.value)}
                    >
                        {
                            conferences.map((conference) =>(
                                <MenuItem value={conference}>{conference}</MenuItem>
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
                        <PaperCollapsible paper={JSON.stringify(paper)} conferences={
                            conferences.map(conference => conference.name)
                        } token={token}/>
                    }/>
                    ))
                }
            </Stack>
        </Box>
    )
}

export default SeePapers