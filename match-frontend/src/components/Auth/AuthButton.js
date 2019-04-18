import React from 'react';
import styled from 'styled-components';
import oc from 'open-color';

//https://css-tricks.com/almanac/selectors/h/hover/
//reference used for hover and active, the cursor action
//added cursor->pointer change and user select none to avoid text selection

const Wrapper = styled.div`
    margin-top: 1rem;
    padding-top: 0.6rem;
    padding-bottom: 0.5rem;

    background: ${oc.gray[7]};
    color: white;

    text-align: center;
    font-size: 1.25rem;
    font-weight: 500;

    cursor: pointer;
    user-select: none;
    transition: .2s all;

    &:hover {
        background: ${oc.yellow[3]};
    }

    &:active {
        background: ${oc.red[7]};
    }

`;

const AuthButton = ({children, onClick}) => (
    <Wrapper onClick={onClick}>
        {children}
    </Wrapper>
);

export default AuthButton;