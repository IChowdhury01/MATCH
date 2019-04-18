import React from 'react';
import styled from 'styled-components';
import oc from 'open-color';

// fixed on top
const Positioner = styled.div`
    display: flex;
    flex-direction: column;
    position: fixed;
    top: 0px;
    width: 100%;
`;

// background, lined up in center
const WhiteBackground = styled.div`
    background: gray;
    display: flex;
    justify-content: center;
    height: auto;
`;

// Header
const HeaderContents = styled.div`
    width: 1200px;
    height: 55px;
    display: flex;
    flex-direction: row;
    align-items: center;

    padding-right: 1rem;
    padding-left: 1rem;
`;

// logo
const Logo = styled.div`
    font-size: 1.4rem;
    letter-spacing: 2px;
    color: ${oc.gray[9]};
    font-family: 'Rajdhani';
`;

// middle space
const Spacer = styled.div`
    flex-grow: 1;
`;

// bottom border
const GradientBorder = styled.div`
    height: 3px;
    background: linear-gradient(to right, ${oc.yellow[5]}, ${oc.red[9]});
`;

// to later indicate whether logged in or out generated children,
const Header = ({children}) => {
    return (
        <Positioner>
            <WhiteBackground>
                <HeaderContents>
                    <Logo>MATCH</Logo>
                    <Spacer/>
                    {children}
                </HeaderContents>
            </WhiteBackground>
            <GradientBorder/>
        </Positioner>
    );
};

export default Header;