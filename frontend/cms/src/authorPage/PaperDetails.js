import React, {useState} from "react";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";

function PaperDetails({paper, token, url, buttonText, papersRequest}) {

    const [file, setFile] = useState();

    function handleFileSelected(e) {
        if((e.target.files[0].size/1024/1024).toFixed(4) <= 10) {
            setFile(e.target.files[0]);
        } else {
            alert("File is too big! Maximum 10 MB!");
            document.getElementById("file-input").value = "";
        }
    }

    function uploadPaper(e) {
        let data = new FormData()
        data.append('file', file);
        data.append('paper', paper.id);
        data.append('token', token);

        e.preventDefault();
        fetch(url, {
            method: 'PUT',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('jwt')
            },
            body: data
        })
            .then(response => response.json())
            .then(data => {
                if(data.executedWithoutErrors){
                    alert("File uploaded!");
                    papersRequest();
                } else {
                    alert(data.errorMessage);
                }
            });
    }

    return (
        <Stack component="div" alignSelf="center" width="80%" justifyContent="space-between" direction="row" >
            <Typography component="h3" variant="h6">
                {paper.title} at Conference {paper.conferenceName}
            </Typography>
            <Box component="div">
                <input
                    onChange={handleFileSelected}
                    type="file"
                    accept="application/pdf"
                    id="file-input"
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