import logo from './logo.svg';
import './App.css';
import './enterPage/LogInForm'
import LogInForm from "./enterPage/LogInForm";
import SignUpForm  from "./enterPage/SignUpForm";

function App() {
  // return (
  //   <div className="App">
  //     <header className="App-header">
  //       <img src={logo} className="App-logo" alt="logo" />
  //       <p>
  //         Edit <code>src/App.js</code> and save to reload.
  //       </p>
  //       <a
  //         className="App-link"
  //         href="https://reactjs.org"
  //         target="_blank"
  //         rel="noopener noreferrer"
  //       >
  //         Learn React
  //       </a>
  //     </header>
  //   </div>
  // );
  return(
      <main>
          <h1 id="main-title">Conference management</h1>
          <div className="form_container">
              <div className="log-form_container">
                  <SignUpForm />
              </div>
              <div className="log-form_container">
                  <LogInForm />
              </div>
          </div>
      </main>
  )
}

export default App;
