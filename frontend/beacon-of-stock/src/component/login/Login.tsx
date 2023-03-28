import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';
import axios_api from '../../assets/config/Axios';
import { setCookie } from '../../assets/config/Cookie';
import { onLogin } from '../../assets/config/SignIn';

const Login = () => {
  const navigate = useNavigate();
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
          <a
            // href='https://j8d207.p.ssafy.io/api/oauth2/authorization/kakao'
            href='https://j8d207.p.ssafy.io/api/oauth2/authorization/kakao'
            className='text-lg bg-[#fae100] text-center'
            // onClick={() => kakaoLogin()}
            // onClick={() => {
            //   console.log('click 됨!');
            //   // window.open(kakaoLogin);
            // }}
          >
            kakao 로그인
          </a>
          <button
            className='text-slate-500/75 text-center'
            // onClick={() => kakaoLogin()}
            onClick={() => {
              window.open(kakaoLogin);
            }}
          >
            kakao로 회원가입하기
          </button>
        </div>
      </div>
    </div>
  );
};

export default Login;
