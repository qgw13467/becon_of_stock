import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Nav from './component/nav/Nav';
// import Home from './pages/home/Home';
import BacktestMain from './pages/home/backtest/BacktestMain';
import BacktestResult from './pages/home/backtest/result/BacktestResult';
import CommunityMain from './pages/community/CommunityMain';
import NotLoginHome from './pages/home/NotLoginHome';
import { WriteCommu } from './pages/community/Dibs/WriteCommu';
import Detail from './pages/community/Dibs/Detail';
import { LoginIndex } from './component/login/LoginIndex';
import { Contests } from './pages/community/contests/Contests';
import { CommunityDibs } from './pages/community/Dibs/CommunityDibs';
import Login from './component/login/Login';
import { Bookmark } from './pages/profile/bookmark/Bookmark';
import { MyProfile } from './pages/profile/MyProfile';
import { Strategy } from './pages/profile/strategy/Strategy';
import { useLoginStore } from './store/store';
import { useEffect } from 'react';

const App = () => {
  const { isLogin, setIsLogout } = useLoginStore();

  return (
    <>
      <BrowserRouter>
        <Nav />
        <hr className='bg-[#808080] h-[1px] border-0 opacity-25' />
        <Routes>
          {/* 로그인 여부에 따라 보이는 화면 다르게 구성 */}
          {isLogin ? (
            <>
              {/* 로그인 된 상태 */}
              <Route path='/' element={<BacktestMain />} />
              <Route path='/result' element={<BacktestResult />} />
              <Route path='/community' element={<CommunityMain />}>
                <Route path='write' element={<WriteCommu />} />
                <Route path='dibs' element={<CommunityDibs />} />
                <Route path='contests/:id' element={<Contests />} />
                <Route path='detail/:id' element={<Detail />} />
              </Route>
              <Route path='/myProfile' element={<MyProfile />} />
              <Route path='/strategy' element={<Strategy />} />
              <Route path='/bookmark' element={<Bookmark />} />
              <Route path='/index' element={<LoginIndex />} />
            </>
          ) : (
            <>
              <Route path='/' element={<NotLoginHome />} />
              <Route path='/login' element={<Login />} />
              <Route path='/index' element={<LoginIndex />} />
            </>
          )}
        </Routes>
      </BrowserRouter>
    </>
  );
};

export default App;
