import React, {useState} from 'react'
import "./form.css"

function SignUpForm(){

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [userName, setUserName] = useState("");
    const [passwordRepeat, setPasswordRepeat] = useState("");
    const [userType, setUserType] = useState("");

    return (
        // <form onSubmit={handleSubmit} className="form">
        // TODO: do not give absolute url?
        <form action="http://localhost:8080/accounts/sign-up" method="POST" className="form">
            <h2>SIGN UP</h2>
            <label>E-mail:
                <br/>
                <input name="email" type="text"
                       value={email}
                       onChange={e => setEmail(e.target.value)}/>
            </label>
            <label>Name:
                <br/>
                <input name="userName" type="text"
                       value={userName}
                       onChange={e => setUserName(e.target.value)}/>
            </label>
            <label>User Type:</label>
            <div className="type_container">
                <label>
                    <input value="chair" name="userType" type="radio"
                           checked={userType === "chair"}
                           onChange={e => setUserType(e.target.value)}/> Chair
                </label>
                <label>
                    <input value="reviewer" name="userType" type="radio"
                           checked={userType === "reviewer"}
                           onChange={e => setUserType(e.target.value)}/> Reviewer
                </label>
                <label>
                    <input value="author" name="userType" type="radio"
                           checked={userType === "author"}
                           onChange={e => setUserType(e.target.value)}/> Author
                </label>
            </div>
            <label>Password:
                <br/>
                <input name="password" type="password"
                       value={password}
                       onChange={e => setPassword(e.target.value)}/>
            </label>
            <label>Confirm password:
                <br/>
                <input name="passwordRepeat" type="password"
                       value={passwordRepeat}
                       onChange={e => setPasswordRepeat(e.target.value)}/>
            </label>

            <div>
                <button type="submit">Sign up</button>
            </div>
        </form>
    )
}

export default SignUpForm