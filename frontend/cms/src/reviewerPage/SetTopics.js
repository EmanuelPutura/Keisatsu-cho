import React, {useEffect, useState} from "react";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import "./reviewerStyle.css";
import InputLabel from "@mui/material/InputLabel";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import TopicsSelect from "./TopicsSelect";

function SetTopics({token, papersRequest}){
    const [topics, setTopics]=useState([]);
    const [selectedTopics, setSelectedTopics] = useState([]);
    const [conferences, setConferences] = useState([]);
    const [selectedConference, setSelectedConference] = useState('');
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

    function setSelectedAndUpdatePapers(topics){
        setSelectedTopics(topics)
        papersRequest(selectedConference.id, topics.join("\n"));
    }

    useEffect(() => {
        const conferenceRequest = accountID => {
            if (accountID !== undefined && accountID !== 124) {
                fetch("http://localhost:8080/conferences/get")
                    .then(response => response.json())
                    .then(data => {
                        setConferences(data);
                    })
                    .catch(() => alert("Invalid conference request!"));
            } else {
                setConferences(() => accountID === 124 ? [...testConferences] : []);
            }
        }
        conferenceRequest(token);
    },[token])

    function handleChange(event){
        setSelectedConference(event.target.value);
        setTopics(conferences.find(conference => conference.id === event.target.value).topics.trim().split("\n"));
        setSelectedTopics([]);
    }

    return(
        <Box component="div" className="reviewer_container">
            <Typography variant="h5" component="h2" align="center" my="5px">
                Set up topics of interest:
            </Typography>
            <FormControl fullWidth>
                <InputLabel id="select-conference-label">Conference</InputLabel>
                <Select
                    labelId="select-conference-label"
                    id="select-conference"
                    value={selectedConference}
                    label="Conference"
                    onChange={handleChange}
                >
                    {conferences && conferences.length > 0  && conferences.map((conference) => (
                        <MenuItem value={conference.id}>{conference.name}</MenuItem>
                    ))}
                </Select>
            </FormControl>
            <TopicsSelect
                topics={topics}
                setTopics={setTopics}
                selectedTopics={selectedTopics}
                setSelectedTopics={setSelectedAndUpdatePapers}
            />
        </Box>
    )
}

export default SetTopics