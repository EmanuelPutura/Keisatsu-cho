import React from 'react'
import "./form.css"

class LogInForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            email: '',
            password: ''
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
        alert('Submitted ' + this.state.email + " " + this.state.password);
        event.preventDefault();
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit} className="form">
                <h2>LOG IN</h2>
                <label>E-mail:
                    <br/>
                <input name="email" type="text"
                       value={this.state.email}
                       onChange={this.handleInputChange}/>
                </label>
                <label>Password:
                    <br/>
                <input name="password" type="password"
                       value={this.state.password}
                       onChange={this.handleInputChange}/>
                </label>
                <input type="submit" value="Log in"/>
            </form>
        )
    }
}

export default LogInForm