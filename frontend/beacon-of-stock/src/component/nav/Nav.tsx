import { FC, useEffect, useRef, useState  } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useLoginStore } from "../../store/store";
import { MyProfile } from "../../pages/profile/MyProfile";

const Nav: FC = () => {
  // const navigate = useNavigate()
  const logo = require("../../assets/img/bos-logo.png")
  const emptyImg = require("../../assets/img/empty-img.jpg")
  // 이 부분 주스탠드로 관리.
  const { isLogin } = useLoginStore()
  const ref = useRef<HTMLDivElement>(null);
  // 모달 열리고 닫히고 하는 부분
  const [isOpen, setIsOpen] = useState<boolean>(false)
  // 모달 boolean 업데이트 시 실행
  useEffect(() => {
    const modalCloseHandler: EventListener = (event: Event) => {
      if (isOpen && ref.current && !ref.current.contains(event.target as Node)) {
        setIsOpen(false);
        // console.log('===')
      }
    };
    if (isOpen) { 
      window.addEventListener('click', modalCloseHandler);
      // console.log('window 클릭 됨.')
    }
    return () => {
      window.removeEventListener('click', modalCloseHandler);
    };
  }, [isOpen]);

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
        <div className="m-auto" onClick={() => setIsOpen(!isOpen)} ref={ref}>
          <img
            src={emptyImg}
            alt="emptyImg"
            className="w-[40px] h-[40px] rounded-full"
          />
        {isOpen ?
          <div className="absolute border-2 border-black w-[150px] h-[180px] right-6 top-16 grid content-around bg-[#fefefe] rounded-lg z-50"
          // onClick={modalCloseHandler}
          >
            <Link to='/myProfile' className="text-center">내 정보</Link>
            <Link to='/strategy' className="text-center">내 전략조회</Link>
            <p className="text-center">북마크</p>
            <p className="text-center text-[#DE2727]">로그아웃</p>
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
