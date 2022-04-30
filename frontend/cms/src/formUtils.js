import React, {useState} from "react";
import Collapse from "@mui/material/Collapse";
import IconButton from "@mui/material/IconButton";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import ExpandLess from "@mui/icons-material/ExpandLess";
import ExpandMore from "@mui/icons-material/ExpandMore";

export function checkPassword(password){
    return password.length >= 8 && /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/.test(password);
}

export function ListItemWithCollapsible({collapsible, value}) {
    const [open, setOpen] = useState(false);

    return (
        <List>
            <ListItem
                key={value}
                disableGutters
                secondaryAction={
                    <IconButton
                        onClick={() => setOpen(!open)}
                    >
                        {open ? <ExpandLess/> : <ExpandMore/>}
                    </IconButton>
                }
            >
                <ListItemText
                    primary={value}
                />
            </ListItem>
            <Collapse in={open} timeout="auto" unmountOnExit>
                {collapsible}
            </Collapse>
        </List>
    )
}
