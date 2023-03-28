import { Link } from 'react-router-dom';
import { useLoginStore } from '../../store/store';

export const ProfileBox = () => {
  const { isLogin, setIsLogin, setIsLogout } = useLoginStore();
  return (
    <div
      className='absolute border-2 border-black w-[150px] h-[180px] right-6 top-16 grid content-around bg-[#fefefe] rounded-lg z-50'
      // onClick={modalCloseHandler}
    >
      <Link to='/myProfile' className='text-center inline cursor-pointer'>
        내 정보
      </Link>
      <Link to='/strategy' className='text-center inline cursor-pointer'>
        내 전략조회
      </Link>
      <p className='text-center cursor-pointer'>북마크</p>
      <p
        className='text-center text-[#DE2727] inline cursor-pointer'
        onClick={() => setIsLogout(false)}
      >
        로그아웃
      </p>
    </div>
  );
};
