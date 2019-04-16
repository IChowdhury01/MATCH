import React from 'react';
import '../stylesheets/Main.css';

const Main = ({form, children}) => {
    return (
        <main className="main-page">
            <div className="title">
                MATCH
            </div>
            <section className="input">
                {form}
            </section>
            <section className="input2">
                { children }
            </section>
        </main>
    );
};

export default Main;