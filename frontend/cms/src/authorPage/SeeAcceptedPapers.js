import React from "react";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import Divider from "@mui/material/Divider";
import PaperDetails from "./PaperDetails";

function SeeCameraReadyPapers({papers, token}) {
    return (
        <Box component="div" className="author_container">
            <Typography component="h2" variant="h5" align="center" my="5px">
                Papers that need Camera Ready Copies
            </Typography>
            <Stack component="div"
                   direction="column"
                   spacing={0}
                   divider={<Divider orientation="horizontal" flexItem/> }
            >
                {
                    papers && papers.length && papers.map((paper) => (
                        <PaperDetails paper={paper}
                                      token={token}
                                      url={"http://localhost:8080/papers/uploadCameraReady"}
                                      buttonText={"Upload Camera Ready Copy"}
                        />
                    ))
                }
            </Stack>
        </Box>
    )
}

export default SeeCameraReadyPapers;