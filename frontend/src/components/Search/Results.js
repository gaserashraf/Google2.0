import React,{useContext,useEffect} from 'react'
import ResultItem from './ResultItem'
import './results.css';
import GoogleContext from "../../contexts/google/googleContext";
const Results = () => {
    let googleContext = useContext(GoogleContext);
    const resultsArr=googleContext.searchResults;
    console.log(googleContext.pageSearchResult);
    return (
        <div className='results'>
             <p>Found {resultsArr.length} results</p>
             <hr></hr>
             {googleContext.pageSearchResult.map(result =>(
                <ResultItem result={result} />
             ))} 
        </div>
    )
}

export default Results
