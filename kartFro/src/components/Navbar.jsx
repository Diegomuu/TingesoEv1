import React from "react";
import { Link } from "react-router-dom";
import "../assets/styles.css";

const Navbar = () => {
  return (
    <nav className="navbar">
      <ul>
        <li><Link to="/">Inicio</Link></li>
        <li><Link to="/pagos">Pagos</Link></li>
        <li><Link to="/rack-semanal">Rack Semanal</Link></li>
      </ul>
    </nav>
  );
};

export default Navbar;