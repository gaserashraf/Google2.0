import React, { useReducer } from "react";
import GoogleContext from "./authContext";
import GoogleReducer from "./authReducer";
import {
    SEARCH_NEW,
    CLEARSEARCH 
} from "../types";
const GoogleState = (props) =>{
    const initialState = {
        searchResults :[]
    }
    const [state,dispatch] = useReducer(GoogleState,initialState);

    // some methods

    return (
      <GoogleState.Provider value={{}}>{props.children}</GoogleState.Provider>
    );
}