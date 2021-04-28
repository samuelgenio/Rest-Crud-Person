import React from 'react';
import './App.css';

import Routes from './routes';

function App() {
  return (
    <div>
      <Routes />
    </div>
  );
}

export default App;


//debug com firefox: https://stackoverflow.com/questions/48059983/react-debug-using-vscode-and-firefox-instead-of-chrome
/*
cd C:\Program Files\Mozilla Firefox
firefox -start-debugger-server -no-remote
*/