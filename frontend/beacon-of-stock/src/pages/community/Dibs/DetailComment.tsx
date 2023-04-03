import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios_api from '../../../assets/config/Axios';
import { getCookie } from '../../../assets/config/Cookie';
import chatImg from '../../../assets/img/chat.png';

interface Comment {
  id: number;
  userNickname: string;
  content: string;
}

interface DetailCommentProps {
  boardId: number;
}

const DetailComment: React.FC<DetailCommentProps> = ({ boardId }) => {
  const navigate = useNavigate();

  const [comments, setComments] = useState<Comment[]>([]);
  // console.log(comments);
  const [newComment, setNewComment] = useState({ content: '' });

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

  const handleSubmit = () => {
    axios_api
      .post(`/boards/${boardId}/comments`, newComment, {
        headers: { authentication: token },
      })
      .then((res) => {
        console.log(res);
        setComments([...comments, res.data]);
        setNewComment({ content: '' });
      })
      .catch((err) => {
        console.log(err);
      });
  };
  return (
    <div className='bg-gray-100 p-6 rounded-lg shadow-lg'>
      <div className='flex justify-between mb-4'>
        <div className='flex justify-center'>
          <img src={chatImg} alt='comment' className='w-5 h-5 self-center' />
          <div className='grid content-center justify-center grid-cols-2'>
            <h2 className='text-xl font-bold'>댓글</h2>
            <p> ({comments.length}) </p>
          </div>
        </div>
        <button
          onClick={() => navigate('/community/dibs')}
          className='border-2 border-[#3E7CBC] py-1 px-2 mr-2 rounded-sm bg-[#3E7CBC] text-[#fefefe]'
        >
          <p>목록</p>
        </button>
      </div>
      {comments.length === 0 ? (
        <p className='text-gray-500'>댓글이 없습니다.</p>
      ) : (
        <ul className='bg-[#fefefe] p-2 rounded'>
          {comments.map((comment, index) => (
            <div key={index}>
              {index > 0 && <hr />}
              <li className='my-2 flex justify-between'>
                <p className='text-gray-700'>
                  <span className='font-bold mr-2'>
                    {comment.userNickname}:
                  </span>
                  <span className='ml-4'>{comment.content}</span>
                </p>
                <p>
                  <span>수정</span>
                  <span className='mx-4'> | </span>
                  <span>삭제</span>
                </p>
              </li>
            </div>
          ))}
        </ul>
      )}
      <form onSubmit={handleSubmit}>
        <div className='mt-6'>
          <label
            htmlFor='content'
            className='block text-sm font-medium text-gray-700'
          >
            새 댓글 작성
          </label>
          <textarea
            id='content'
            name='content'
            rows={4}
            className='resize-none mt-1 focus:ring-blue-500 focus:border-blue-500 block w-full sm:text-sm border-gray-300 rounded-md'
            value={newComment.content}
            onChange={(e) =>
              setNewComment({ ...newComment, content: e.target.value })
            }
          ></textarea>
        </div>
        <div className='mt-6 flex justify-end'>
          <button
            type='submit'
            className='bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded'
            onClick={() => handleSubmit}
          >
            작성
          </button>
        </div>
      </form>
    </div>
  );
};

export default DetailComment;
