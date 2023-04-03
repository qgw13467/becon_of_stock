import React from 'react';

type Props = {
  handleSubmit: () => void;
  changeNewComment: (e: any) => void;
  newComment: any;
};

export const CreateComment: React.FC<Props> = ({
  handleSubmit,
  newComment,
  changeNewComment,
}) => {
  return (
    <form onSubmit={handleSubmit}>
      <div className='mt-6'>
        <label
          htmlFor='content'
          className='block text-sm font-medium text-gray-700'
        >
          새 댓글 작성
        </label>
        <div className='flex justify-center'>
          <textarea
            id='content'
            name='content'
            rows={4}
            className='resize-none mt-1 focus:ring-blue-500 focus:border-blue-500 block w-full sm:text-sm border-gray-300 rounded-md'
            value={newComment.content}
            onChange={(e) =>
              changeNewComment({ ...newComment, content: e.target.value })
            }
          ></textarea>
          <button
            type='submit'
            className='border border-blue-500 hover:bg-blue-500 text-blue-500 hover:text-white font-bold py-2 w-24 rounded'
            onClick={() => handleSubmit}
          >
            작성
          </button>
        </div>
      </div>
    </form>
  );
};
