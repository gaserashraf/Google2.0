import React from 'react'
import './results.css'
const ResultItem = ({result}) => {
    
    return (
      <div className={"pb-3 w-75 box"}>
        <a href={result.link} className='LinkColor' target="_blank">
          {result.link}
          <h2>{result.title}</h2>
        </a>
        <p>{result.description}</p>
      </div>
    );
}
export default ResultItem
