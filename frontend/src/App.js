import React from 'react';
import Header from './components/shared/header/header';
import SideNav from './components/shared/sidenav/sidenav';

import './App.css';

function App() {
  return (
    <div className="App">
     
      <Header></Header>
      <body>
      <SideNav></SideNav>
      </body>
    </div>
  );
}

export default App;
