import React from 'react';
import './sidenav.css';

function SideNav() {
  return (
    <div id="mySidenav" class="sidenav">
      <a href="javascript:void(0)" class="closebtn" onClick={closeNav}>&times;</a>
      <a href="#">Cars</a>
      <a href="#">Countries</a>
      <a href="#">Manufacturers</a>
      <a href="#">Shops</a>
      <a href="#">Stocks</a>
    </div>
  );
}

function closeNav() {
  document.getElementById("mySidenav").style.width = "0";
}

export default SideNav;
