import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { setCookie } from '../../assets/config/Cookie';
import { onLogin } from '../../assets/config/SignIn';
import { useLoginStore } from '../../store/store';

export const LoginIndex = () => {
  const navigate = useNavigate();
  const token = new URLSearchParams(window.location.search).get('token');
  const { setIsLogin, isLogin } = useLoginStore();
  // console.log(token);
  useEffect(() => {
    if (token) {
      setCookie('accessToken', token, {
        path: '/',
        secure: true,
        // httpOnly: true,
        sameSite: 'none',
      });
      onLogin();
      setIsLogin(true);
      navigate('/');
    }
  }, [token, navigate]);
  // 만약 로그인 후에 해당 페이지로 접근하려고 하면 '/' 경로로 리다이렉트
  useEffect(() => {
    if (isLogin) {
      navigate('/');
    }
  }, []);

  return <></>;
};
