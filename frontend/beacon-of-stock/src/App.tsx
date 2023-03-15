import React from 'react';
import { BrowserRouter, Route, Router, Navigate, Routes } from 'react-router-dom';
import Nav from './nav/Nav';
import BacktestMain from './component/backtest/BacktestMain';
import CommunityMain from './component/community/CommunityMain';

const App = () => {
  return (
    <>
      <BrowserRouter>
        <Nav />
        <Routes>
          <Route path='/' element={<BacktestMain />} />
          <Route path='/community' element={<CommunityMain />} />        
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
