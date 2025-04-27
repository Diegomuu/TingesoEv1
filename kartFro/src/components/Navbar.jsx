import React from 'react';
import './Navbar.css'; 

const Navbar = () => {
  return (
    <nav className="navbar">
      <div className="navbar-logo">
        <a href="/">MiLogo</a>
      </div>
      <ul className="navbar-links">
        <li><a href="/">Inicio</a></li>
        <li><a href="/about">Acerca de</a></li>
        <li><a href="/services">Servicios</a></li>
        <li><a href="/contact">Contacto</a></li>
      </ul>
    </nav>
  );
};

export default Navbar;