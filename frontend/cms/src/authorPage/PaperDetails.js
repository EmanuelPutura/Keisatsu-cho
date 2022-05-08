import React, {useState} from "react";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";

function PaperDetails({paper, token, url, buttonText}) {

    const [file, setFile] = useState();

    function handleFileSelected(e) {
        setFile(e.target.files[0]);
    }

    function uploadPaper(e) {
        let data = new FormData()
        data.append('file', file);
        data.append('paper', paper.id);
        data.append('token', token);

        e.preventDefault();
        fetch(url, {
            method: 'PUT',
            body: data
        })
            .then(response => response.json())
            .then(data => alert(data ? "File uploaded" : "File was not uploaded"));
    }

    return (
        <Stack component="div" alignSelf="center" width="80%" justifyContent="space-between" direction="row" >
            <Typography component="h3" variant="h6">
                {paper.title}
            </Typography>
            <Box component="div">
                <input
                    onChange={handleFileSelected}
                    type="file"
                    accept="application/pdf"
                />
                <Button
                    variant="contained"
                    onClick={uploadPaper}
                    disabled={file === undefined}
                >
                    {buttonText}
                </Button>
            </Box>
        </Stack>
    )
}

export default PaperDetails