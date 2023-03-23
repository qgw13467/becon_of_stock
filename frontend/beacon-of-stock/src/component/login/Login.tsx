import axios from 'axios';

const Login = () => {
  // const kakaoLogin = () => {

  //   axios.get('http://192.168.100.119:8888/oauth2/authorization/kakao')
  //     .then(response => {
  //       console.log(response);
  //       console.log('성공')}
  //     )
  //     .catch(err => {
  //       console.log(err)
  //       console.log('실패')
  //     })
  // }

  const loginLogo = require('../../assets/img/bos-logo.png');
  return (
    <div className='grid justify-center'>
      <div className='border-2 border-black h-[360px] w-[400px] rounded-lg grid content-center justify-center'>
        <img
          src={loginLogo}
          alt='login-logo'
          className='w-[200px] h-[200px] '
        />
        <div className='grid content-center'>
          <a
            href='http://192.168.100.119:80/oauth2/authorization/kakao'
            className='text-lg bg-[#fae100] text-center'
          >
            kakao 로그인
          </a>
          <button className='text-slate-500/75 text-center'>
            kakao로 회원가입하기
          </button>
        </div>
      </div>
    </div>
  );
};

export default Login;
