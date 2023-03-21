import { FC, useState } from "react";
import { Link } from "react-router-dom";
import { useLoginStore } from "../../store/store";
import Profile from "./Profile";

interface ProfileProps {
  cProfile: () => void
}
const Nav: FC<ProfileProps> = () => {
  const logo = require("../../assets/img/bos-logo.png");
  const emptyImg = require("../../assets/img/empty-img.jpg");
  // 이 부분 주스탠드로 관리.
  const { isLogin } = useLoginStore()
  const [isOpen, setIsOpen] = useState(false);
  const openProfile = () => {
    setIsOpen(true)
  }
  const closeProfile = () => {
    setIsOpen(false)
  }
  

  return (
    <nav className="flex justify-between mx-10">
      <div>
        <Link to="/">
          <img src={logo} alt="logo" className="w-[60px] h-[60px]" />
        </Link>
      </div>
      <div className="flex space-x-20">
        { isLogin ? <><div className="m-auto">
          <Link to="/">
            <p className="text-lg font-KJCbold">튜토리얼</p>
          </Link>
        </div>
        <div className="m-auto">
          <Link to="/">
            <p className="text-lg font-KJCbold">백테스트</p>
          </Link>
        </div>
        <div className="m-auto">
          <Link to="/community">
            <p className="text-lg font-KJCbold">커뮤니티</p>
          </Link>
        </div>
        {/* 로그인 상태에서 프로필 이미지 들어오는 곳 */}
        <div className="m-auto">
          <img
            src={emptyImg}
            alt="emptyImg"
            className="w-[40px] h-[40px] rounded-full"
            onClick={openProfile}
          />
          {isOpen ? <div>
              <Profile />
          </div> : null}
        </div></> : <>
        <div className="m-auto">
          <Link to="/login">
            <p className="text-lg font-KJCbold">회원가입</p>
          </Link>
        </div>
        <div className="m-auto">
          <Link to="/login">
            <p className="text-lg font-KJCbold">로그인</p>
          </Link>
        </div>
      </>}
        
      </div>
    </nav>
  );
};

export default Nav;
