import { BrowserRouter, Route, Routes } from "react-router-dom";
import Nav from "./component/nav/Nav";
// import Home from './pages/home/Home';
import BacktestMain from "./pages/home/backtest/BacktestMain";
import CommunityMain from "./pages/community/CommunityMain";

const App = () => {
  return (
    <>
      <BrowserRouter>
        <Nav />
        <hr className="bg-[#808080] h-[1px] border-0 opacity-25" />
        <Routes>
          {/* 로그인 여부에 따라 보이는 화면 다르게 구성 */}
          {/* <Route path='/' element={<Home />} /> */}
          <Route path="/" element={<BacktestMain />} />
          <Route path="/community" element={<CommunityMain />} />
        </Routes>
      </BrowserRouter>
    </>
  );
};

export default App;
