import * as types from 'actions/ActionTypes';

const initialState = {
    login: {
        status: 'INIT'
    },
    register: {
        status: 'INIT',
        error: -1
    },
    status: {
        valid: false,
        isLoggedIn: false,
        currentUser: ''
    }
};

export default function authentication(state = initialState, action) {
    switch(action.type) {
        case types.AUTH_REGISTER:
            return {
                ...state,
                register: {
                    status: 'WAITING',
                    error: -1
                }
            }
        case types.AUTH_REGISTER_SUCCESS:
            return {
                ...state,
                register: {
                    ...state.register,
                    status: 'SUCCESS'
                }
            }
        case types.AUTH_REGISTER_FAILURE:
            return {
                ...state,
                register:{
                    status: 'FAILURE',
                    error: action.error
                }
            }
        default:
            return state;
    }
};
