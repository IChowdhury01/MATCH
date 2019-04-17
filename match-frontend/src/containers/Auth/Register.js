import React, { Component } from 'react';
import { AuthContent, InputWithLabel, AuthButton, RightAlignedLink } from 'components/Auth';
import { connect } from 'react-redux';
import {bindActionCreators} from 'redux';
import * as authActions from 'redux/modules/auth';

//added componentWillUnmount to erase stuff when page moved

class Register extends Component {
    handleChange = (e) => {
        const { AuthActions } = this.props;
        const { name, value } = e.target;

        AuthActions.changeInput({
            name,
            value,
            form: 'register'
        });
    }

    componentWillUnmount() {
        const { AuthActions } = this.props;
        AuthActions.initializeForm('register')
    }

    render() {
        const { username, userpassword, displayname, maxtravel, profile, etc} = this.props.form.toJS();
        const { handleChange } = this;

        return (
            <AuthContent title="Register">
                <InputWithLabel label="username" name="username" placeholder="type username" value={username} onChange={handleChange}/>
                <InputWithLabel label="userpassword" name="userpassword" placeholder="type userpassword" type="password" value={userpassword} onChange={handleChange}/>
                <InputWithLabel label="displayname" name="displayname" placeholder="type displayname" value={displayname} onChange={handleChange}/>
                <InputWithLabel label="maxtravel" name="maxtravel" placeholder="type maxtravel distance you are willing to travel" value={maxtravel} onChange={handleChange}/>
                <InputWithLabel label="profile" name="profile" placeholder="type profile" value={profile} onChange={handleChange}/>
                <InputWithLabel label="etc" name="etc" placeholder="type etc" value={etc} onChange={handleChange}/>
                <AuthButton>Register</AuthButton>
                <RightAlignedLink to="/auth/login">Login</RightAlignedLink>
            </AuthContent>
        );
    }
}

export default connect(
    (state) => ({
        form: state.auth.getIn(['register', 'form'])
    }),
    (dispatch) => ({
        AuthActions: bindActionCreators(authActions, dispatch)
    })
)(Register);