import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios_api from '../../../../assets/config/Axios';
import { getCookie } from '../../../../assets/config/Cookie';
import chatImg from '../../../../assets/img/chat.png';
import { CreateComment } from './CreateComment';
import { CommentReply } from './CommentReply';

interface Comment {
  id: number;
  userNickname: string;
  content: string;
  children: any[];
}

interface DetailCommentProps {
  boardId: number;
}

const DetailComment: React.FC<DetailCommentProps> = ({ boardId }) => {
  const navigate = useNavigate();
  const [comments, setComments] = useState<Comment[]>([]);
  const [newComment, setNewComment] = useState({ content: '' });
  const token = getCookie('accessToken');
  const changeNewComment = (e: any) => {
    setNewComment(e);
  };

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
    // e.preventDefault();
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
        <CommentReply comments={comments} />
      )}
      <CreateComment
        handleSubmit={handleSubmit}
        newComment={newComment}
        changeNewComment={changeNewComment}
      />
    </div>
  );
};

export default DetailComment;
