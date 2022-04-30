import logo from './logo.svg';
import './App.css';
import './logIn/LogInForm'
import LogInForm from "./logIn/LogInForm";

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
      <main className="log-in">
          <h1 id="main-title">Conference management</h1>
          <div className="form-container">
              <LogInForm />
          </div>
      </main>
  )
}

export default App;
