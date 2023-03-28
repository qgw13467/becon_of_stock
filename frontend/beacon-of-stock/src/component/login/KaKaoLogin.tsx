import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { setCookie } from '../../assets/config/Cookie';
import { onLogin } from '../../assets/config/SignIn';

function KaKaoLogin() {
  const navigate = useNavigate();
  const getUrlParameter = (name: any) => {
    console.log('getUrlParameter 실행됨');
    // 쿼리 파라미터에서 값을 추출해주는 함수
    let search = window.location.search;
    let params = new URLSearchParams(search);
    return params.get(name);
  };

  useEffect(() => {
    // console.log('useEffect 실행됨');
    const token = getUrlParameter('accessToken');
    // console.log(token);
    const accessToken = token;
    setCookie('accessToken', accessToken, {
      path: '/',
      secure: true,
      // httpOnly: true,
      sameSite: 'none',
    });
    if (accessToken !== undefined) {
      onLogin();
    }
  }, []);
}

export default KaKaoLogin;
