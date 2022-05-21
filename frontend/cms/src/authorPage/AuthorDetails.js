import React from "react";
import Stack from "@mui/material/Stack";
import Divider from "@mui/material/Divider";
import TextField from "@mui/material/TextField";

function AuthorDetails({author, setAuthor}){

    return(
        <Stack component="div"
               direction="row"
               justifyContent="space-evenly"
               width="100%"
               divider={<Divider orientation="vertical" flexItem /> }
        >
            <TextField
                margin="normal"
                required
                fullWidth
                label="Author Name"
                name="name"
                size="small"
                type="text"
                value={author.name}
                onChange={e => setAuthor({name: e.target.value, email: author.email, address: author.address})}
            />
            <TextField
                margin="normal"
                required
                fullWidth
                label="Author's Email"
                name="email"
                size="small"
                type="email"
                value={author.email}
                onChange={e => setAuthor({name: author.name, email: e.target.value, address: author.address})}
            />
            <TextField
                margin="normal"
                required
                fullWidth
                label="Author's Home Address"
                name="address"
                size="small"
                type="text"
                value={author.address}
                onChange={e => setAuthor({name: author.name, email: author.email, address: e.target.value})}
            />
        </Stack>
    )
}

export default AuthorDetails