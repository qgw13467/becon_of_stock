import { Link } from 'react-router-dom';
import loginLogo from '../../assets/img/bos-logo.png';
// import beacon from '../../assets/img/beacon.png'

const Login = () => {
  const kakaoLogin = 'https://j8d207.p.ssafy.io/api/oauth2/authorization/kakao';
  return (
    <div className='grid justify-center mt-32 bg-gradient-to-t from-indigo-500/75 via-[#ffecc7b4] to-[#FFFFFF] h-screen'>
      <div className='border-2 border-black h-[360px] w-[400px] rounded-lg grid content-center justify-center bg-[#fefefe]'>
        <img
          src={loginLogo}
          alt='login-logo'
          className='w-[200px] h-[200px] '
        />
        <div className='grid content-center'>
          <Link
            to={kakaoLogin}
            className='text-lg bg-[#fae100] text-center rounded-md'
          >
            kakao 로그인
          </Link>
          <Link to={kakaoLogin} className='text-slate-500/75 text-center'>
            kakao로 회원가입하기
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Login;
