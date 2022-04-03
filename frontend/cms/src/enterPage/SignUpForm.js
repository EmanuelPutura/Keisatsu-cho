import React, {useState} from 'react'
import "./form.css"

function SignUpForm(){

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [username, setUsername] = useState("");
    const [user_fname, setUser_fname] = useState("");
    const [user_lname, setUser_lname] = useState("");
    const [password_repeat, setPassword_repeat] = useState("");
    const [user_type, setUser_type] = useState("");
    const [birth_date, setBirth_date] = useState("");
    const [address, setAddress] = useState("");


    function handleSubmit(event) {
        alert('Submitted ' + email + ' ' + username + ' ' + user_fname + ' ' + user_lname + ' ' + password + ' ' + user_type + ' ' + birth_date + ' ' + address);
        event.preventDefault();
    }

    return (
        <form onSubmit={handleSubmit} className="form">
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
                           value={user_fname}
                           onChange={e => setUser_fname(e.target.value)}/>
                </label>
                <label>Last Name:
                    <br/>
                    <input name="user_lname" type="text"
                           value={user_lname}
                           onChange={e => setUser_lname(e.target.value)}/>
                </label>
                </div>
            <label>
                Birthday:
                <br/>
                <input name="birth_date" type="date"
                       value={birth_date}
                       onChange={e => setBirth_date(e.target.value)}/>
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
                    <input value="chair" name="user_type" type="radio"
                           checked={user_type === "chair"}
                           onChange={e => setUser_type(e.target.value)}/> Chair
                </label>
                <label>
                    <input value="reviewer" name="user_type" type="radio"
                           checked={user_type === "reviewer"}
                           onChange={e => setUser_type(e.target.value)}/> Reviewer
                </label>
                <label>
                    <input value="author" name="user_type" type="radio"
                           checked={user_type === "author"}
                           onChange={e => setUser_type(e.target.value)}/> Author
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
                <input name="password_repeat" type="password"
                       value={password_repeat}
                       onChange={e => setPassword_repeat(e.target.value)}/>
            </label>
            </div>
            <input type="submit" value="Sign up"/>
        </form>
    )
}

export default SignUpForm