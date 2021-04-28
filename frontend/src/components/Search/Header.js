import React,{useState} from 'react'
import logo from '../..//cover.png';
const Header = () => {
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
