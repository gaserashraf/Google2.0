import React,{useState} from 'react'
import { Container,Row,Col } from 'react-bootstrap';
import logo from '../..//cover.png';
import Navbar from './Navbar'
const Home = () => {
    const [search,setSearch] = useState("");
    const onChange = (e) => {
       setSearch( e.target.value );
    };
    const onSubmit = (e)=>{
        e.preventDefault();
        console.log(search);
    }
    return (
      <div>
        <Navbar />
        <form onSubmit={onSubmit} className="form">
          <div className={"container"} style={{ paddingTop: "150px" }}>
            <div className={"row justify-content-center"}>
              <div className={"col-6 text-center"}>
                <img src={logo} className={"w-75"}></img>
              </div>
            </div>
            <div className={"row justify-content-center my-3"}>
              <div className={"col-6 text-center"}>
                <div className="input-group">
                  <div className="input-group-prepend">
                    <div className="input-group-text">
                      <i class="fas fa-search"></i>
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
            </div>
            <div className={"row justify-content-center my-4"}>
              <button type="submit" className="btn btn-lg px-4">
                Search
              </button>
            </div>
          </div>
        </form>
      </div>
    );
}

export default Home
