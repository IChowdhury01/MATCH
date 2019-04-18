import { createAction, handleActions } from 'redux-actions';

import { Map } from 'immutable';


//https://github.com/react-boilerplate/react-boilerplate/issues/1620
//https://www.robinwieruch.de/react-state-array-add-update-remove/
//deals form change using map method to deal it as an array
//https://stackoverflow.com/questions/49725708/why-action-payload-use-in-reactjs

//Action type
const CHANGE_INPUT = 'auth/CHANGE_INPUT';
const INITIALIZE_FORM = 'auth/INITIALIZE_FORM';

//create Action
export const changeInput = createAction(CHANGE_INPUT); //  { form, name, value }
export const initializeForm = createAction(INITIALIZE_FORM); // form

//set initial state with form for each register and login, Map method used, reference added above
const initialState = Map({
    register: Map({
        form: Map({
            email: '',
            username: '',
            userpassword: '',
            displayname: '',
            maxtravel: '',
            profile: '',
            etc: '',
        })
    }),
    login: Map({
        form: Map({
            username: '',
            userpassword: ''
        })
    })
});

//reducer handles action, handle input
//https://immutable-js.github.io/immutable-js/
export default handleActions({
    [CHANGE_INPUT]: (state, action) => {
        const { form, name, value } = action.payload;
        return state.setIn([form, 'form', name], value);
    },
    [INITIALIZE_FORM]: (state, action) => {
        const initialForm = initialState.get(action.payload);
        return state.set(action.payload, initialForm);
    }
}, initialState);