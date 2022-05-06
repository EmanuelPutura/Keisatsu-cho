import React, {useState} from "react";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import AuthorDetails from "./AuthorDetails";
import List from "@mui/material/List";
import {ListItem} from "@mui/material";
import Stack from "@mui/material/Stack";

function AddPaperForm({token}){
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
                    interestTopic: interestTopic
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
                    {authors.map(
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
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    label="Topic of Interest"
                    name="interestTopic"
                    size="small"
                    type="text"
                    value={interestTopic}
                    onChange={e => setInterestTopic(e.target.value)}
                />
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