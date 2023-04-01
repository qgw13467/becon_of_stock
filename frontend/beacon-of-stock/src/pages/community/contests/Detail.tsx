import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import axios_api from '../../../assets/config/Axios';
import { getCookie } from '../../../assets/config/Cookie';

const Detail = () => {
  const location = useLocation();
  const boardId = location.pathname.split('/').pop();
  const token = getCookie('accessToken');

  // boardId를 사용하여 해당 게시물 정보를 가져오는 로직
  useEffect(() => {
    axios_api
      .get(`/boards/${boardId}`, {
        headers: {
          authentication: token,
        },
      })
      .then((res) => {
        console.log(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return (
    <section className='m-9'>
      <h2>Board ID: {boardId}</h2>
      <div>안녕하세요</div>
      {/* 이하 자세한 내용 생략 */}
    </section>
  );
};

export default Detail;
