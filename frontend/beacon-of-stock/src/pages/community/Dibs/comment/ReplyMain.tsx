import React, { useState } from 'react';
import { getCookie } from '../../../../assets/config/Cookie';
import axios_api from '../../../../assets/config/Axios';

type ReplyProps = {
  comment: any;
  changeImeeey: () => void;
  setCreateState: any;
};
export const ReplyMain: React.FC<ReplyProps> = ({
  comment,
  changeImeeey,
  setCreateState,
}) => {
  const [editingCommentId, setEditingCommentId] = useState<number | null>(null);
  const [editedCommentContent, setEditedCommentContent] = useState('');
  const token = getCookie('accessToken');
  // 댓글 수정 저장
  const saveEditedComment = () => {
    axios_api
      .patch(
        `/boards/comments/${editingCommentId}`,
        { content: editedCommentContent },
        {
          headers: { authentication: token },
        }
      )
      .then((res) => {
        // console.log(res);
        changeImeeey();
        setEditingCommentId(null);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // 댓글 수정
  const commentCorrection = (content: string, commentId: number) => {
    setEditingCommentId(commentId);
    setEditedCommentContent(content);
  };

  // 댓글 삭제
  const commentDelete = (commentId: number, children: []) => {
    if (children.length === 0) {
      axios_api
        .delete(`/boards/comments/${commentId}`, {
          headers: { authentication: token },
        })
        .then((res) => {
          // console.log('삭제 실행');
          changeImeeey();
        })
        .catch((err) => {
          console.log(err);
        });
    } else {
      alert('대댓글이 있는 게시글은 삭제하실 수 없습니다.');
    }
  };
  // console.log(editingCommentId);
  const createReplyReply = () => {
    setCreateState(editingCommentId);
  };
  return (
    <div className='my-2 flex justify-between'>
      <p className='text-gray-700'>
        <span className='font-bold mr-2'>{comment.userNickname}:</span>
        {editingCommentId === comment.commentId ? (
          <input
            type='text'
            value={editedCommentContent}
            onChange={(e) => setEditedCommentContent(e.target.value)}
            className='w-[800px] border-2 indent-4 rounded-lg border-[#131313]'
          />
        ) : (
          <span>
            <span className='ml-4'>{comment.content}</span>
            <span
              className='ml-2 text-gray-500 text-sm cursor-pointer'
              onClick={() => createReplyReply()}
            >
              답글쓰기
            </span>
          </span>
        )}
      </p>
      {comment.isAuthor && (
        <p>
          {editingCommentId === comment.commentId ? (
            <>
              <span
                onClick={() => saveEditedComment()}
                className='cursor-pointer'
              >
                저장
              </span>
              <span className='mx-4'> | </span>
              <span
                onClick={() => setEditingCommentId(null)}
                className='cursor-pointer'
              >
                취소
              </span>
            </>
          ) : (
            <>
              <span
                onClick={() =>
                  commentCorrection(comment.content, comment.commentId)
                }
                className='cursor-pointer'
              >
                수정
              </span>
              <span className='mx-4'> | </span>
              <span
                onClick={() =>
                  commentDelete(comment.commentId, comment.children)
                }
                className='cursor-pointer'
              >
                삭제
              </span>
            </>
          )}
        </p>
      )}
    </div>
  );
};
