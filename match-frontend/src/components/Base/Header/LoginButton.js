import React from 'react';
import styled from 'styled-components';
import oc from 'open-color';
import { Link } from 'react-router-dom';

//https://github.com/apex/apex-ui-slim/blob/master/styles/open-color.less  color reference
//https://yeun.github.io/open-color/

const BorderedButton = styled(Link)`
    font-weight: 600;
    color: ${oc.gray[9]};
    border: 1px solid ${oc.gray[9]};
    padding: 0.5rem;
    padding-bottom: 0.4rem;
    cursor: pointer;
    border-radius: 2px;
    text-decoration: none;
    transition: .2s all;

    &:hover {
        background: ${oc.gray[9]};
        color: white;
    }


`;

const LoginButton = () => (
    <BorderedButton to="/auth/login">
        Login/Register
    </BorderedButton>
);

export default LoginButton;