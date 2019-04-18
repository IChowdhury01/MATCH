import React from 'react';
import styled from 'styled-components';
import oc from 'open-color';
import { Link } from 'react-router-dom';

// centralize
const Positioner = styled.div`
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
`;

// width
const Box = styled.div`
    width: 500px;
`;

// logo
// https://css-tricks.com/snippets/css/a-guide-to-flexbox/
const LogoWrapper = styled.div`
    background: ${oc.gray[7]};
    height: 5rem;
    display: flex;
    align-items: center;
    justify-content: center;
`;

const Logo = styled(Link)`
    color: white;
    font-family: 'Rajdhani';
    font-size: 2.4rem;
    letter-spacing: 5px;
    text-decoration: none;
`;

// children
const Contents = styled.div`
    background: white;
    padding: 2rem;
    height: auto;
`;

const AuthWrapper = ({children}) => (
    <Positioner>
        <Box>
            <LogoWrapper>
                <Logo to="/">MATCH</Logo>
            </LogoWrapper>
            <Contents>
                {children}
            </Contents>
        </Box>
    </Positioner>
);

export default AuthWrapper;