import React, { Component } from 'react';
import { AuthContent, InputWithLabel, AuthButton, RightAlignedLink } from 'components/Auth';
import { connect } from 'react-redux';
import {bindActionCreators} from 'redux';
import * as authActions from 'redux/modules/auth';

//type password used to mark the character
//handlechange made to relate the event target's name and value with changeinput
//in render tried to get usr and pwd value from the form which goes into input value below

class Login extends Component {

    handleChange = (e) => {
        const { AuthActions } = this.props;
        const { name, value } = e.target;

        AuthActions.changeInput({
            name,
            value,
            form: 'login'
        });
    }

    componentWillUnmount() {
        const { AuthActions } = this.props;
        AuthActions.initializeForm('login')
    }

    render() {
        const { username, userpassword } = this.props.form.toJS();
        const { handleChange } = this;

        return (
            <AuthContent title="Login">
                <InputWithLabel label="username" name="username" placeholder="type username" value={username} onChange={handleChange}/>
                <InputWithLabel label="userpassword" name="userpassword" placeholder="type userpassword" type="password" value={userpassword} onChange={handleChange}/>
                <AuthButton>Login</AuthButton>
                <RightAlignedLink to="/auth/register">Register</RightAlignedLink>
            </AuthContent>
        );
    }
}

export default connect(
    (state) => ({
        form: state.auth.getIn(['login', 'form'])
    }),
    (dispatch) => ({
        AuthActions: bindActionCreators(authActions, dispatch)
    })
)(Login);