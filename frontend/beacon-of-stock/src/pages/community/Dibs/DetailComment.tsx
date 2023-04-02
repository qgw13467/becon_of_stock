import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios_api from '../../../assets/config/Axios';
import { getCookie } from '../../../assets/config/Cookie';

interface Comment {
  id: number;
  writer: string;
  content: string;
}

interface DetailCommentProps {
  boardId: number;
}

const DetailComment: React.FC<DetailCommentProps> = ({ boardId }) => {
  const navigate = useNavigate();

  const [comments, setComments] = useState<Comment[]>([]);
  const token = getCookie('accessToken');

  useEffect(() => {
    axios_api
      .get(`/boards/${boardId}/comments`, {
        headers: { authentication: token },
      })
      .then((res) => {
        console.log(res);
        setComments(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [boardId, token]);

  return (
    <div className='bg-gray-100 p-6 rounded-lg shadow-lg'>
      <div className='flex justify-between'>
        <div className='flex justify-center'>
          <img src='' alt='comment' />
          <h2 className='text-xl font-bold mb-4'>댓글</h2>
          <p> ({comments.length}) </p>
        </div>
        <button
          className='px-4 py-2 rounded-md bg-gray-200 hover:bg-gray-300'
          onClick={() => navigate(-1)}
        >
          목록
        </button>
      </div>
      {comments.length === 0 ? (
        <p className='text-gray-500'>댓글이 없습니다.</p>
      ) : (
        <ul>
          {comments.map((comment) => (
            <li key={comment.id} className='mb-4'>
              <p className='text-gray-700'>
                <span className='font-bold mr-2'>{comment.writer}:</span>
                {comment.content}
              </p>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default DetailComment;
