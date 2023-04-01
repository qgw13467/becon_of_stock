import { useState, useEffect } from 'react';

export const WriteCommu = () => {
  const [title, setTitle] = useState<string>('');
  const titleChange = (e: any) => {
    setTitle(e.target.value);
  }; // title state
  const [content, setContent] = useState<string>('');
  const contentChange = (e: any) => {
    setContent(e.target.value);
  }; // content state

  // 내 전략 선택을 누르면 내 전략들이 보이고
  // 그 중 하나의 전략을 선택할 경우 해당 전략의 title이 선택되었음을 보여준다.
  // 그 후 최종적으로 게시글 등록을 누르면 새 글이 써지고
  // '/community/dibs' 페이지로 redirect 해준다.
  return (
    <section className='bg-white shadow-lg rounded-lg pb-8 mb-4'>
      <p className='m-9 text-3xl font-bold bg-cyan-600 text-[#fefefe] text-center w-[280px] h-12 grid content-center rounded-[4px]'>
        전략 공유 게시판
      </p>
      <p className='text-[#131313] font-bold mb-2'>게시글 쓰기</p>
      <article className='mx-20'>
        <div className='mb-4'>
          <p className='text-[#131313] font-bold mb-2'>제목</p>
          <input
            className='shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline'
            type='text'
            placeholder='제목을 입력하세요.'
            value={title}
            onChange={titleChange}
          />
        </div>
        <div className='mb-4'>
          <p className='text-[#131313] font-bold mb-2'>내용</p>
          <textarea
            className='shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline'
            placeholder='내용을 입력하세요.'
            value={content}
            onChange={contentChange}
          ></textarea>
        </div>
        <div className='mb-4'>
          <p className='text-[#131313] font-bold mb-2'>내 전략 등록</p>
          <button className='bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded'>
            내 전략 선택
          </button>
        </div>
        <div className='flex justify-end'>
          <button className='bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded'>
            게시글 등록
          </button>
        </div>
      </article>
    </section>
  );
};
