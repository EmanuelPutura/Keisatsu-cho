import React, {useState} from 'react'
import "./form.css"

function SignUpForm(){

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [user_name, setUser_name] = useState("");
    const [password_repeat, setPassword_repeat] = useState("");
    const [user_type, setUser_type] = useState("");
    const [birth_date, setBirth_date] = useState("");
    const [address, setAddress] = useState("");


    function handleSubmit(event) {
        alert('Submitted ' + email + ' ' + user_name + ' ' + password + ' ' + user_type + ' ' + birth_date + ' ' + address);
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
            <label>Name:
                <br/>
                <input name="user_name" type="text"
                       value={user_name}
                       onChange={e => setUser_name(e.target.value)}/>
            </label>
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
            <div className="type_container">
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
            <input type="submit" value="Sign up"/>
        </form>
    )
}

export default SignUpForm