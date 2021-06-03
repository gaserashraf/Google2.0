import React,{useState,useContext} from 'react'
import GoogleContext from "../../contexts/google/googleContext";

const Pagination = () => {

  let googleContext = useContext(GoogleContext);
  let postsPerPage= googleContext.postsPerPage;
  let totalPosts=googleContext.searchResults.length; 
  const pageNumbers = [];

  for (let i = 1; i <= Math.ceil(totalPosts / postsPerPage); i++) {
    pageNumbers.push(i);
  }
  const changePage = (e)=>{
    googleContext.setCurrentPage(e.target.textContent);

  }
  return (
    <nav>
      <ul className='pagination'>
        {pageNumbers.map(number => (
          <li key={number} className={googleContext.currentPage==number ? 'active page-item' :'page-item'}>
            <a onClick={changePage}  href='!#' className='page-link'>
              {number}
            </a>
          </li>
        ))}
      </ul>
    </nav>
  );
};

export default Pagination;