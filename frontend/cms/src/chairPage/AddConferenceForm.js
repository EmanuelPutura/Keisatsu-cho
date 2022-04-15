import {Box, Button, Divider, Stack, TextField, Typography} from "@mui/material";
import React, {useState} from "react";

function AddConferenceForm(){

    const [name, setName] = useState("");
    const [url, setURL] = useState("");
    const [email, setEmail] = useState("")
    const [subtitles, setSubtitles] = useState("")
    return(
        <Box component="div" className="chair_container">
            <Typography variant="h5" component="h2" align="center" my="5px">
                Create a new conference:
            </Typography>
            <Box component="form" className="chair-form">
                <Stack component="div"
                       direction="row"
                       justifyContent="space-evenly"
                       divider={<Divider orientation="vertical" flexItem /> }>
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        label="Conference Name"
                        name="name"
                        size="small"
                        type="text"
                        value={name}
                        onChange={e => setName(e.target.value)}
                    />
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        label="Conference URL"
                        name="url"
                        size="small"
                        type="url"
                        value={url}
                        onChange={e => setURL(e.target.value)}
                    />
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        label="Main Organizer's Email"
                        name="email"
                        size="small"
                        type="email"
                        value={email}
                        onChange={e => setEmail(e.target.value)}
                    />
                </Stack>
                <TextField
                    margin="normal"
                    fullWidth
                    multiline
                    label="Subtitles, one on each row"
                    name="subtitles"
                    value={subtitles}
                    onChange={e => setSubtitles(e.target.value)}
                />
                <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                >
                    ADD
                </Button>
            </Box>
        </Box>
    )
}

export default AddConferenceForm