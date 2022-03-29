import React from 'react'
class LogInForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            email: '',
            password:''
        };

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        this.setState({[name] : value});
    }


    handleSubmit(event){
        alert('Submitted');
        event.preventDefault();
    }

    render(){
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Email:
                    <input name="email" type="text" value={this.state.email} onChange={this.handleInputChange} />
                </label>
                <br />
                <label>
                    Password:
                    <input name="password" type="password" value={this.state.password} onChange={this.handleInputChange} />
                </label>
                <br />
                <input type="submit" value="Submit"/>
            </form>
        )
    }
}

export default LogInForm