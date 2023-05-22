import { Link, useNavigate } from 'react-router-dom';
import { useLoginStore } from '../../store/store';
import { removeCookie } from '../../assets/config/Cookie';

export const ProfileBox = () => {
  // =============로그아웃=================
  const navigate = useNavigate();
  const { setIsLogout } = useLoginStore();
  const handleLogout = () => {
    setIsLogout(false);
    removeCookie('accessToken');
    localStorage.removeItem('timeLeft');
    navigate('/');
  }; // 로그아웃 핸들러
  // ======================================

  // ======스타일========
  const profileStyle =
    'text-center inline cursor-pointer w-[106px] p-1 rounded-lg hover:bg-[#1B4978] hover:text-[#fefefe]';
  const plusColor = `text-center inline cursor-pointer w-[106px] p-1 rounded-lg hover:bg-[#BA4C85] hover:text-[#fefefe] text-[#DE2727]`;
  // ===================
  return (
    <div
      className='absolute border-2 border-black w-[150px] h-[180px] right-6 top-16 grid justify-center content-around bg-[#fefefe] rounded-lg z-50'
      // onClick={modalCloseHandler}
    >
      <Link to='/myProfile' className={profileStyle}>
        내 정보
      </Link>
      <Link to='/strategy' className={profileStyle}>
        내 전략조회
      </Link>
      <Link to='/bookmark' className={profileStyle}>
        북마크
      </Link>
      <p className={plusColor} onClick={() => handleLogout()}>
        로그아웃
      </p>
    </div>
  );
};
