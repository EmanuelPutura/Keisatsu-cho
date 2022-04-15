import {
    Box,
    Button,
    Divider,
    Stack,
    TextField,
    Typography
} from "@mui/material";
import React from "react";
import {ListItemWithCollapsible} from "../formUtils";

function InterestTopicsForm({conference}) {
    return (
        <Box component="form" className="chair-form">
            <TextField
                margin="normal"
                required
                fullWidth
                label="Topics of interest"
                name="topics"
                size="small"
                multiline
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
    return (
        <Box component="form" className="chair-form">
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

function SeeConferences() {
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
                    [1, 2, 3].map((number) => (
                        <ListItemWithCollapsible value={number} collapsible={
                            <ConferenceCollapsible conference={number}/>
                        }/>
                    ))
                }
            </Stack>
        </Box>
    )
}

export default SeeConferences;