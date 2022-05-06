import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Stack from "@mui/material/Stack";
import Divider from "@mui/material/Divider";
import PaperDetails from "./PaperDetails";

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
                        <PaperDetails paper={paper}
                                      token={token}
                                      url={"https://localhost:8080/papers/uploadPaper"}
                                      buttonText={"Upload Full Paper"}
                        />
                    ))
                }
            </Stack>
        </Box>
    )
}

export default SeeNotUploadedPapers;