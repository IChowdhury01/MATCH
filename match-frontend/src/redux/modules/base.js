import { Map } from 'immutable';
import { handleActions, createAction } from 'redux-actions';
// AType
const SET_HEADER_VISIBILITY = 'base/SET_HEADER_VISIBILITY';

export const setHeaderVisibility = createAction(SET_HEADER_VISIBILITY); // Action created

const initialState = Map({
    header: Map({
        visible: true
    })
});

//handle action->reducer
export default handleActions({
    [SET_HEADER_VISIBILITY]: (state, action) => state.setIn(['header', 'visible'], action.payload)
},
    initialState
);