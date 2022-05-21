import React, {useEffect, useState} from "react";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import AuthorDetails from "./AuthorDetails";
import List from "@mui/material/List";
import {ListItem, Select} from "@mui/material";
import Stack from "@mui/material/Stack";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";

function AddPaperForm({token}){

    //TODO: request changed -- added conference representing the conference ID
    function submitData(event){
        event.preventDefault();

        fetch("http://localhost:8080/papers",
            {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    token: token,
                    title: title,
                    abstract: abstract,
                    authors: authors,
                    keywords: keywords,
                    interestTopic: interestTopic,
                    conference: conference
                })
            }).then(response => response.json())
            .then(data => {
                alert(data ? "paper added" : "paper was not added");
            });
    }

    const [abstract, setAbstract] = useState("");
    const [title, setTitle] = useState("");
    const [authors, setAuthors] = useState([{name: "", email: "", address: ""}]);
    const [keywords, setKeywords] = useState("");
    const [interestTopic, setInterestTopic] = useState("");
    const [conferences, setConferences] = useState([]);
    const [conference, setConference] = useState("");

    const testConferences = [{  id: 1,
        name: "Conference name",
        url: "conference.com",
        subtitles: "Subtitle1\nSubtitle2",
        topics: "Topic1\n" +
            "Topic2\n" +
            "Topic3\n",
        submission:"2021-08-07",
        review:"2021-09-07",
        acceptance:"2021-10-07",
        upload:"2021-11-07"}];


    //TODO: new request
    const conferenceRequest = (accountID) => {
        if (accountID !== undefined && accountID !== 123) {
            fetch("http://localhost:8080/conferences/all")
                .then(response => response.json())
                .then(data => {
                    setConferences(data);
                })
                .catch(() => alert("Invalid conference request!"));
        } else {
            setConferences(() => accountID === 123 ? [...testConferences] : []);
        }
    }

    useEffect(() => conferenceRequest(token), [])

    const setAuthor = (author, key) => {
        let newArr = [...authors];
        newArr[key] = author;
        setAuthors(newArr);
    }

    function addAuthor() {
        setAuthors([...authors, {name: "", email: "", address: ""}]);
    }

    function removeAuthor(){
        let newArr = [...authors];
        newArr.pop();
        setAuthors(newArr);
    }

    return(
        <Box component="div" className="author_container">
            <Typography variant="h5" component="h2" align="center" my="5px">
                Add a new Paper:
            </Typography>
            <Box component="form" className="author_form" onSubmit={submitData}>
                <FormControl fullWidth>
                    <InputLabel id="select-conference-label">Conference</InputLabel>
                    <Select
                        labelId="select-conference-label"
                        id="select-conference"
                        value={conference}
                        label="Conference"
                        onChange={event => setConference(event.target.value)}
                    >
                        {conferences && conferences.length > 0  && conferences.map((conference) => (
                            <MenuItem value={conference.id}>{conference.name}</MenuItem>
                        ))}
                    </Select>
                </FormControl>
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    label="Paper Title"
                    name="title"
                    size="small"
                    type="text"
                    value={title}
                    onChange={e => setTitle(e.target.value)}
                />
                <TextField
                    margin="normal"
                    fullWidth
                    required
                    multiline
                    label="Abstract"
                    name="abstract"
                    value={abstract}
                    onChange={e => setAbstract(e.target.value)}
                />
                <Typography variant="h6" component="h3">
                    Authors:
                </Typography>
                <List>
                    {authors && authors.length && authors.map(
                        (author, key) =>
                            <ListItem key={key} >
                                <AuthorDetails author={author} setAuthor={newAuthor => setAuthor(newAuthor, key)} />
                            </ListItem>
                    )}
                </List>
                <Stack direction="row">
                    <Button
                        onClick={addAuthor}
                        fullWidth
                        variant="contained"
                    >
                        Add Author
                    </Button>
                    <Button
                        onClick={removeAuthor}
                        disabled={authors.length <= 1}
                        fullWidth
                        variant="contained"
                    >
                        Remove Last Author
                    </Button>
                </Stack>

                <TextField
                    margin="normal"
                    fullWidth
                    required
                    multiline
                    label="Keywords, one on each row"
                    name="keywords"
                    value={keywords}
                    onChange={e => setKeywords(e.target.value)}
                />
                <FormControl fullWidth>
                    <InputLabel id="select-topic-label">Topic of Interest</InputLabel>
                    <Select
                        labelId="select-topic-label"
                        id="select-topic"
                        value={interestTopic}
                        label="Topic of Interest"
                        onChange={event => setInterestTopic(event.target.value)}
                        disabled={conference === ""}
                    >
                        {conference !== "" && conferences.find((conference_) => conference_.id === conference).topics.trim().split("\n").map((topic) => (
                            <MenuItem value={topic}>{topic}</MenuItem>
                        ))}
                    </Select>
                </FormControl>
                <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                >
                    Submit Paper
                </Button>
            </Box>
        </Box>
    )
}

export default AddPaperForm;