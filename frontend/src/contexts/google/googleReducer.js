import {
    SEARCH_NEW,
    CLEARS_SEARCH,
    ADD_SUGGEST,
    LOAD_SUGGEST,
    SET_SEARCH,
    FILTER_SUGGEST,
    CLEAR_FILTER
} from "../types";

export default (state, action) => {
  switch (action.type) {
    case SEARCH_NEW:
      return {
        ...state,
        searchResults: action.payload
      };
    case ADD_SUGGEST:
        return{
            ...state,
            allSuggestsArray : [...state.allSuggestsArray,action.payload]
        };
    case LOAD_SUGGEST:
        return{
            ...state,
            allSuggestsArray : action.payload
        };
    case FILTER_SUGGEST:
        return{
            ...state,
            // TO DO filter it
            suggestsArray : state.allSuggestsArray.filter(str=>{
              const regex = new RegExp(`${action.payload}`,'gi'); 
              return  str.match(regex);
            })
        };
    case CLEAR_FILTER:
        return {
            ...state,
            suggestsArray:[]
        }
    case SET_SEARCH:
        return{
            ...state,
            currSearch: action.payload
        }
    default:
      return state;
  }
};
