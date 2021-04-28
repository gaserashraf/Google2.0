import React from 'react'
import ResultItem from './ResultItem'
import './results.css';
const Results = () => {
    const resultsArr=[
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
    return (
        <div className='results'>
             <p>Found {resultsArr.length} results</p>
             <hr></hr>
             {resultsArr.map(result =>(
                <ResultItem key={result.id} result={result} />
             ))} 
        </div>
    )
}

export default Results
