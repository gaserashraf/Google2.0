import { act } from "react-dom/test-utils";
import {
    SEARCH_NEW,
    CLEARS_SEARCH,
    ADD_SUGGEST,
    LOAD_SUGGEST,
    SET_SEARCH,
    FILTER_SUGGEST,
    CLEAR_FILTER,
    SET_CURRENT_PAGE,
    SET_PAGE_SEARCH_RESULT
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
                let tmp = str;
                const regex = new RegExp(`${action.payload}`,'gi'); 
                return  tmp.match(regex);
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
    case CLEARS_SEARCH:
        return{
            ...state,
            currSearch:"",
            searchResults:[],
            suggestsArray:[],
            pageSearchResult:[],
            currentPage:1
        }
    case SET_CURRENT_PAGE:
        return{
            ...state,
            currentPage:action.payload,
            pageSearchResult:state.searchResults.slice(action.payload * state.postsPerPage -  state.postsPerPage, action.payload * state.postsPerPage)
        }
    default:
      return state;
  }
};
