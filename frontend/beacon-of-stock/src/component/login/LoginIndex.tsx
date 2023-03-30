import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { setCookie } from '../../assets/config/Cookie';
import { onLogin } from '../../assets/config/SignIn';
import { useLoginStore } from '../../store/store';

export const LoginIndex = () => {
  const navigate = useNavigate();
  const token = new URLSearchParams(window.location.search).get('token');
  const { setIsLogin } = useLoginStore();
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

  return <></>;
};
