import React, {useState} from 'react'
import "./form.css"

function SignUpForm(){

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [username, setUsername] = useState("");
    const [userFName, setUserFName] = useState("");
    const [userLName, setUserLName] = useState("");
    const [passwordRepeat, setPasswordRepeat] = useState("");
    const [userType, setUserType] = useState("");
    const [birthDate, setBirthDate] = useState("");
    const [address, setAddress] = useState("");


    function handleSubmit(event) {
        alert('Submitted ' + email + ' ' + username + ' ' + userFName + ' ' + userLName + ' ' + password + ' ' + userType + ' ' + birthDate + ' ' + address);
        event.preventDefault();
    }

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
            <label>Username:
                <br/>
                <input name="username" type="text"
                       value={username}
                       onChange={e => setUsername(e.target.value)}/>
            </label>
            <div className="input_container">
                <label>First Name:
                    <br/>
                    <input name="user_fname" type="text"
                           value={userFName}
                           onChange={e => setUserFName(e.target.value)}/>
                </label>
                <label>Last Name:
                    <br/>
                    <input name="user_lname" type="text"
                           value={userLName}
                           onChange={e => setUserLName(e.target.value)}/>
                </label>
                </div>
            <label>
                Birthday:
                <br/>
                <input name="birth_date" type="date"
                       value={birthDate}
                       onChange={e => setBirthDate(e.target.value)}/>
            </label>
            <label>
                Home Address:
                <br/>
                <input name="address" type="text"
                       value={address}
                       onChange={e => setAddress(e.target.value)} />
            </label>
            <label>User Type:</label>
            <div className="input_container">
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
            <div className="input_container">
                <label>Password:
                <br/>
                <input name="password" type="password"
                       value={password}
                       onChange={e => setPassword(e.target.value)}/>
            </label>
            <label>Confirm Password:
                <br/>
                <input name="passwordRepeat" type="password"
                       value={passwordRepeat}
                       onChange={e => setPasswordRepeat(e.target.value)}/>
            </label>
            </div>
            <input type="submit" value="Sign up"/>
        </form>
    )
}

export default SignUpForm