import React,{useState,useContext} from 'react'
import { Container,Row,Col } from 'react-bootstrap';
import GoogleContext from "../../contexts/google/googleContext";
import logo from '../..//cover.png';
import Results from './Results'
import ResultItem from './ResultItem'
import Header from './Header'
import Pagination from './Pagination'
const ResultsSearch = () => {
  let googleContext = useContext(GoogleContext);

    return (
      <div>
        <Header />
        <div style={{ background: "#F9FAFB" }}>
          <div className={"container"}>
            <Results />
            <Pagination />
          </div>
        
        </div>
      
      </div>
    );
}

export default ResultsSearch
