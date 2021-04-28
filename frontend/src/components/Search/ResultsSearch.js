import React,{useState} from 'react'
import { Container,Row,Col } from 'react-bootstrap';
import logo from '../..//cover.png';
import Results from './Results'
import ResultItem from './ResultItem'
import Header from './Header'
const ResultsSearch = () => {
    return (
      <div>
        <Header />
        <div style={{ background: "#F9FAFB" }}>
          <div className={"container"}>
            <Results />
          </div>
        </div>
      </div>
    );
}

export default ResultsSearch
