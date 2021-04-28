import React from 'react';
import { BrowserRouter, Route } from "react-router-dom";

import Home from './pages/Home';
import PersonList from './pages/Person/';
import PersonListCreate from './pages/Person/create';

const Routes = () => {
  return (
    <BrowserRouter>
      <Route component={Home} path="/" exact />
      <Route component={PersonList} exact path="/person/:key" />
      <Route component={PersonListCreate} exact path="/person/create/:key" />
      <Route component={PersonListCreate} exact path="/person/update/:id/:key" />
    </BrowserRouter>
  );
}

export default Routes;