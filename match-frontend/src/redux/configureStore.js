import { createStore } from 'redux';

import modules from './modules';

//https://redux.js.org/recipes/configuring-your-store
//need middleware
//sends store to Root.js props

const configureStore = (initialState) => {
    const store = createStore(modules, window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__());
    return store;
}

export default configureStore;