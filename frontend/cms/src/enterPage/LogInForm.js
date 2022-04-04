import React, {useState} from 'react'
import "./form.css"

function LogInForm(){

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    return (
        // TODO: do not give absolute url?
        <form action="http://localhost:8080/accounts/login" method="POST" className="form">
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

            <div>
                <input type="submit" value="Log in" />
            </div>
        </form>
    )
}

export default LogInForm
