import './App.css';
import './enterPage/LogInForm'
import LogInForm from "./enterPage/LogInForm";
import SignUpForm  from "./enterPage/SignUpForm";
import {Box, Container, Typography} from "@mui/material";

function App() {
  return(
      <Container component="main" id="main" disableGutters={true} maxWidth={false}>
          <Typography variant="h4" component="h1" id="main-title">
              Conference management
          </Typography>
          <Box component="div" className="form_container">
              <Box component="div" className="log-form_container">
                  <SignUpForm />
              </Box>
              <Box component="div" className="log-form_container">
                  <LogInForm />
              </Box>
          </Box>
      </Container>
  )
}

export default App;
