import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Stack from "@mui/material/Stack";
import Divider from "@mui/material/Divider";
import Button from "@mui/material/Button";
import {useState} from "react";

function PaperDetails({paper, token}) {

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
        fetch("https://localhost:8080/papers/uploadPaper", {
            method: 'PUT',
            body: data
        })
            .then(response => response.json())
            .then(data => alert(data ? "File uploaded" : "File was not uploaded"));
    }

    return (
        <Stack component="div" justifyContent="space-between" direction="row">
            <Typography component="h3" variant="p">
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
                    Upload Full Paper
                </Button>
            </Box>
        </Stack>
    )
}

function SeeNotUploadedPapers({papers, token}) {
    return (
        <Box component="div" className="author_container">
            <Typography component="h2" variant="h5" align="center" my="5px">
                Papers to be uploaded
            </Typography>
            <Stack component="div"
                   direction="column"
                   spacing={0}
                   divider={<Divider orientation="horizontal" flexItem/> }
            >
                {
                    papers.map((paper) => (
                        <PaperDetails paper={paper} token={token} />
                    ))
                }
            </Stack>
        </Box>
    )
}

export default SeeNotUploadedPapers;