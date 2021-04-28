import React from "react";
import PropTypes from "prop-types";
import logo from './web.png'
import {Link } from "react-router-dom";
const Navbar = ({title}) => {
  return (
    <div>
      <nav className="navbar">
        <h3>
          <div className="d-flex align-items-center">
            <div className='mr-2' style={{ width: "50px" }}>
              <img src={logo} className="w-100"></img>
            </div>
            {title}
          </div>
        </h3>
      </nav>
    </div>
  );
};
Navbar.defaultProps = {
  title: "Google 2.0",
};
Navbar.propTypes = {
  title: PropTypes.string.isRequired,
  icon: PropTypes.string.isRequired,
};
export default Navbar;
