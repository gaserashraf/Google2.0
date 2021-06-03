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
    CLEAR_FILTER,
    SET_CURRENT_PAGE,
    SET_PAGE_SEARCH_RESULT
} from "../types";
const GoogleState = (props) =>{
    const initialState = {
        searchResults :[],
        pageSearchResult:[],
        suggestsArray :[], // acutal suggests array will change it once use type
        allSuggestsArray :[],// all suggest we store it in database
        currSearch:"",// the keyword the use type it
        currentPage:1,
        postsPerPage:10
    }
    const [state,dispatch] = useReducer(GoogleReducer,initialState);

    // some methods
    const goSearch = (keyWord)=>
    {
        getResults(keyWord);
        //addSuggest(keyWord);
    }
    const getResults = async(keyWord)=>
    {
        //logic:
        /*
        send axios request to the api and get the results
        */

        // TO DO send a request and get the results
       /* const arrRes=[
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
        ]*/
        
        try {
            const res = await axios.get(
                `http://localhost:5000/${keyWord}`
                );
            console.log(res.data);
            if(res.data!="Not Found")
            {
                dispatch({ type: SEARCH_NEW, payload: res.data });
                setCurrentPage(1);
            }

        } catch (error) {
            console.log(error);   
        }
       

    }
    const addSuggest = (keyWord)=>
    {
        //logic:
        //add to the database the keyword of this user he search on it
        
        dispatch({ type: ADD_SUGGEST, payload: keyWord });
    }
    const loadSuggest = async()=>
    {
        //logic:
        /*
        get from the database all suggests and put it on allSuggestsArray
        */
        try {
            const res = await axios.get(
                `http://localhost:5000`
                );
            console.log(res.data);
            let arr=[];
            res.data.forEach(t => {
                arr.push(t.word);
            });
            dispatch({ type: LOAD_SUGGEST, payload: arr });
        } catch (error) {
            console.log(error);   
        }
      // const sug = ["sug1","sug2","sug3"];
      

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
        filterSuggest(str);
    }
    const clearSearch = ()=>{
        dispatch({ type: CLEARS_SEARCH });
    }

    // pagination functions
    const setCurrentPage = (pageNum)=>{
        // the current page
        //console.log("ana hna 5 "+pageNum);
        dispatch({ type: SET_CURRENT_PAGE , payload:pageNum});
    }
    
    return (
      <GoogleContext.Provider value={{
        searchResults:state.searchResults,
        suggestsArray:state.suggestsArray,
        allSuggestsArray:state.allSuggestsArray,
        currSearch:state.currSearch,
        pageSearchResult:state.pageSearchResult,
        currentPage:state.currentPage,
        postsPerPage:state.postsPerPage,
        getResults,
        loadSuggest,
        addSuggest,
        setCurrWord,
        filterSuggest,
        clearFilter,
        clearSearch,
        setCurrentPage
      }}
      >
        {props.children}
      </GoogleContext.Provider>
    );
}
export default GoogleState;