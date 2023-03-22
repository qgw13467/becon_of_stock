import { BrowserRouter, Route, Routes } from "react-router-dom";
import Nav from "./component/nav/Nav";
// import Home from './pages/home/Home';
import BacktestMain from "./pages/home/backtest/BacktestMain";
import CommunityMain from "./pages/community/CommunityMain";
import NotLoginHome from "./pages/home/NotLoginHome";
import Login from "./component/login/Login";
import { MyProfile } from "./pages/profile/MyProfile";
import { useLoginStore } from "./store/store";

const App = () => {
  const { isLogin } = useLoginStore()
  // console.log(isLogin)
  return (
    <>
      <BrowserRouter>
        <Nav />
        <hr className="bg-[#808080] h-[1px] border-0 opacity-25" />
        <Routes>
          {/* 로그인 여부에 따라 보이는 화면 다르게 구성 */}
          {isLogin ?
            <>
              <Route path="/" element={<BacktestMain />} />
              <Route path="/community" element={<CommunityMain />} />
              <Route path="/myProfile" element={<MyProfile />} />
            </> :
            <>
              <Route path="/" element={<NotLoginHome />} />
              <Route path="/login" element={<Login />} />
            </>
          }
        </Routes>
      </BrowserRouter>
    </>
  );
};

export default App;
