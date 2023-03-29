import { Link } from 'react-router-dom';

const Login = () => {
  const kakaoLogin = 'https://j8d207.p.ssafy.io/api/oauth2/authorization/kakao';
  const loginLogo = require('../../assets/img/bos-logo.png');
  return (
    <div className='grid justify-center mt-32'>
      <div className='border-2 border-black h-[360px] w-[400px] rounded-lg grid content-center justify-center'>
        <img
          src={loginLogo}
          alt='login-logo'
          className='w-[200px] h-[200px] '
        />
        <div className='grid content-center'>
          <Link to={kakaoLogin} className='text-lg bg-[#fae100] text-center'>
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
