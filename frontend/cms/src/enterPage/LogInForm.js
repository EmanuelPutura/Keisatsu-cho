import React, {useState} from 'react'
import "./form.css"

function LogInForm(){

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    function handleSubmit(event) {
        alert('Submitted ' + email + " " + password);
        event.preventDefault();
    }

    return (
        <form onSubmit={handleSubmit} className="form">
            <h2>LOG IN</h2>
            <label>E-mail:
                <br/>
                <input name="email" type="text"
                       value={email}
                       onChange={e => setEmail(e.target.value)}/>
            </label>
            <label>Password:
                <br/>
                <input name="password" type="password"
                       value={password}
                       onChange={e => setPassword(e.target.value)}/>
            </label>
            <input type="submit" value="Log in"/>
        </form>
    )
}

export default LogInForm