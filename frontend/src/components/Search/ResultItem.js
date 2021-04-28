import React from 'react'
import './results.css'
const ResultItem = ({result}) => {
    const onClick = ()=>{
        window.location.href = '/'+result.link;
    }
    return (
      <div className={"pb-3 w-75 box"}>
        <span onClick={onClick} className='LinkColor' target="_blank">
          {result.link}
          <h2>{result.title}</h2>
        </span>
        <p>{result.dis}</p>
      </div>
    );
}
export default ResultItem
