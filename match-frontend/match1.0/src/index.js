import React from 'react';
import ReactDOM from 'react-dom';

const title = 'Hello Match';

ReactDOM.render(
    <div>{title}</div>,
    document.getElementById('root')
);

module.hot.accept();