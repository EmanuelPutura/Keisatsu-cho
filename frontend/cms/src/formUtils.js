import React, {useState} from "react";
import {Collapse, IconButton, List, ListItem, ListItemText} from "@mui/material";
import {KeyboardArrowDown} from "@mui/icons-material";

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
                        <KeyboardArrowDown/>
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
