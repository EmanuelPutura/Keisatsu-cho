import React from 'react'
import "./form.css"

class SignUpForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            email: '',
            user_name: '',
            password: '',
            password_repeat: '',
            user_type: '',
        };

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        this.setState({[name]: value});
    }


    handleSubmit(event) {
        alert('Submitted ' + this.state.email + ' ' + this.state.user_name + ' ' + this.state.password + ' ' + this.state.user_type);
        event.preventDefault();
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit} className="form">
                <h2>SIGN UP</h2>
                <label>E-mail:
                    <br/>
                    <input name="email" type="text"
                           value={this.state.email}
                           onChange={this.handleInputChange}/>
                </label>
                <label>Name:
                    <br/>
                    <input name="user_name" type="text"
                           value={this.state.user_name}
                           onChange={this.handleInputChange}/>
                </label>
                <label>User Type:</label>
                <div className="type_container">
                    <label>
                    <input value="chair" name="user_type" type="radio"
                           checked={this.state.user_type === "chair"}
                           onChange={this.handleInputChange}/> Chair
                    </label>
                    <label>
                    <input value="reviewer" name="user_type" type="radio"
                           checked={this.state.user_type === "reviewer"}
                           onChange={this.handleInputChange}/> Reviewer
                    </label>
                    <label>
                    <input value="author" name="user_type" type="radio"
                           checked={this.state.user_type === "author"}
                           onChange={this.handleInputChange}/> Author
                    </label>
                </div>
                <label>Password:
                    <br/>
                    <input name="password" type="password"
                           value={this.state.password}
                           onChange={this.handleInputChange}/>
                </label>
                <label>Confirm password:
                    <br/>
                    <input name="password_repeat" type="password"
                           value={this.state.password_repeat}
                           onChange={this.handleInputChange}/>
                </label>
                <input type="submit" value="Sign up"/>
            </form>
        )
    }
}

export default SignUpForm