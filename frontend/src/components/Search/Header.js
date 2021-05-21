import React,{useState,useContext} from 'react'
import logo from '../..//cover.png';
import GoogleContext from "../../contexts/google/googleContext";
import Suggest from './Suggest';
const Header = () => {
    let googleContext = useContext(GoogleContext);
    //const [search,setSearch] = useState("");
   const search =googleContext.currSearch;
    const onChange = (e) => {
      // setSearch( e.target.value );
      googleContext.setCurrWord(e.target.value);
      if(e.target.value.length)
      {
        googleContext.filterSuggest(e.target.value);
      }
      else{
        googleContext.clearFilter();
      }
    };
    const onSubmit = (e)=>{
        e.preventDefault();
        console.log(search);
        googleContext.getResults(search);
        googleContext.addSuggest(search); 
    }
    return (
      <div>
        <form onSubmit={onSubmit} className="form">
          <div className={"container py-4"}>
            <div className={"row justify-content-start"}>
              <div
                className={
                  "col-2  d-flex align-items-center justify-content-center"
                }
              >
                <img src={logo} className={"w-75"}></img>
              </div>
              <div className={"col-8 text-center"}>

                <div>
                  <div className="input-group">
                    <div className="input-group-prepend">
                      <div className="input-group-text">
                        <i className="fas fa-search"></i>
                      </div>
                    </div>
                    <input
                      className={"form-control form-control-lg"}
                      placeholder="Enter something to search"
                      value={search}
                      onChange={onChange}
                    ></input>
                  </div>
                </div>
                <Suggest/>
              </div>
              <div className={"Col-2 justify-content-center"}>
                <button type="submit" className="btn btn-lg px-4">
                  Search
                </button>
              </div>
            </div>
          </div>
        </form>
      </div>
    );
}

export default Header
