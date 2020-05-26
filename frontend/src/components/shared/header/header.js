import React from 'react';
import './header.css';

function Header() {
  return (
    <header className="App-header">
      <span className="menu-icon" onClick={openNav}>&#9776;</span>
      <h3>
        Test Automation Framework FrontEnd
      </h3>
    </header>
  );
}

function openNav() {
  document.getElementById("mySidenav").style.width = "250px";
}
export default Header;
