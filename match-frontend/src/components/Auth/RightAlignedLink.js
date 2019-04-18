import React from 'react';
import styled from 'styled-components';
import oc from 'open-color';
import { Link } from 'react-router-dom';

const Aligner = styled.div`
    margin-top: 1rem;
    text-align: right;
`;

const SLink = styled(Link)`
    color: ${oc.gray[7]};
    &:hover {
        color: ${oc.red[7]};
    }
`

const RightAlignedLink = ({to, children}) => (
    <Aligner>
        <SLink to={to}>{children}</SLink>
    </Aligner>
);

export default RightAlignedLink;