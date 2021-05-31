import React,{useState,useEffect,useContext} from 'react'
import GoogleContext from "../../contexts/google/googleContext";
const Suggest = () => {
    let googleContext = useContext(GoogleContext);
    const sugOnClick = (e)=>{
        console.log(e.target.textContent);
        //console.log(props);
       // clickSugg(e.target.textContent);
       googleContext.setCurrWord(e.target.textContent);
       googleContext.clearFilter();
      }
    useEffect(() => {
        googleContext.loadSuggest();
    }, [])
    if(googleContext.suggestsArray.length)
    {
        return (
            <div className='sugBox '>
                {googleContext.suggestsArray.map(sug =>(
                    <><p onClick={sugOnClick}>{sug}</p>
                    <hr></hr></>
                ))} 
            </div>
        )
    }
    else{
        return (
            <></>
        )
    }
}
export default Suggest
