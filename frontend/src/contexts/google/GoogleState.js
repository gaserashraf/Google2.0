import React, { useReducer } from "react";

import axios from "axios";
import GoogleContext from "./googleContext";
import GoogleReducer from "./googleReducer";
import {
    SEARCH_NEW,
    CLEARS_SEARCH,
    ADD_SUGGEST,
    LOAD_SUGGEST,
    SET_SEARCH,
    FILTER_SUGGEST,
    CLEAR_FILTER
} from "../types";
const GoogleState = (props) =>{
    const initialState = {
        searchResults :[],
        suggestsArray :[], // acutal suggests array will change it once use type
        allSuggestsArray :[],// all suggest we store it in database
        currSearch:"" // the keyword the use type it
    }
    const [state,dispatch] = useReducer(GoogleReducer,initialState);

    // some methods
    const goSearch = (keyWord)=>
    {
        getResults(keyWord);
        addSuggest(keyWord);
    }
    const getResults = (keyWord)=>
    {
        //logic:
        /*
        send axios request to the api and get the results
        */

        // TO DO send a request and get the results
        const arrRes=[
            {
                id :'1',
                link:'www.google.com',
                title:'Google',
                dis:'A, or a, is the first letter and the first vowel letter of the modern English alphabet and the ISO basic Latin alphabet. ... Its name in English is a (pronounced /ˈeɪ/), ...'
            },
            {
                id :'2',
                link:'www.google.com',
                title:'Google',
                dis:'A, or a, is the first letter and the first vowel letter of the modern English alphabet and the ISO basic Latin alphabet. ... Its name in English is a (pronounced /ˈeɪ/), ...'
            },
            {
                id :'3',
                link:'www.google.com',
                title:'Google',
                dis:'A, or a, is the first letter and the first vowel letter of the modern English alphabet and the ISO basic Latin alphabet. ... Its name in English is a (pronounced /ˈeɪ/), ...'
            }
        ]
        dispatch({ type: SEARCH_NEW, payload: arrRes });

    }
    const addSuggest = (keyWord)=>
    {
        //logic:
        /*
        add to the database the keyword of this user he search on it
        */
        dispatch({ type: ADD_SUGGEST, payload: keyWord });
    }
    const loadSuggest = ()=>
    {
        //logic:
        /*
        get from the database all suggests and put it on allSuggestsArray
        */
       const sug = ["sug1","sug2","sug3"];
       dispatch({ type: LOAD_SUGGEST, payload: sug });

    }
    const filterSuggest = (keyWord)=>
    {
        //logic:
        /*
       when the user typing we get to him a suggests result
        */
       dispatch({ type: FILTER_SUGGEST, payload: keyWord });

    }
    const clearFilter =()=>{
        dispatch({ type: CLEAR_FILTER });
    }
    const setCurrWord=(str)=>{
        dispatch({ type: SET_SEARCH, payload: str });
    }
    return (
      <GoogleContext.Provider value={{
        searchResults:state.searchResults,
        suggestsArray:state.suggestsArray,
        allSuggestsArray:state.allSuggestsArray,
        currSearch:state.currSearch,
        getResults,
        loadSuggest,
        addSuggest,
        setCurrWord,
        filterSuggest,
        clearFilter
      }}
      >
        {props.children}
      </GoogleContext.Provider>
    );
}
export default GoogleState;