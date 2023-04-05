import { useEffect, useState, useCallback, memo } from 'react';
import { Pagenation } from '../../../component/Pagenation';
import { SearchbarNone } from '../../../component/search/SearchbarNone';
import { ForumBoard } from '../../../component/ForumBoard';
import DibsSelect from '../../../component/select_box/DibsSelect';
import { Link } from 'react-router-dom';
import axios_api from '../../../assets/config/Axios';
import { getCookie } from '../../../assets/config/Cookie';
import { usePageStore } from '../../../store/store';

export const ForumComponent = () => {
  const { page } = usePageStore();
  // 총 페이지 수
  const [totalPage, setTotalPage] = useState(0);
  // 게시글 목록
  const [content, setContent] = useState([]);
  // console.log(content);
  // 현재 페이지
  const token = getCookie('accessToken');
  const [selectedValue, setSelectedValue] = useState<string | null>(null);
  // console.log(selectedValue);
  const handleSelect = (value: string | null) => {
    setSelectedValue(value);
  };
  const fetchData = useCallback(() => {
    axios_api
      .get('/boards/', {
        headers: {
          authentication: token,
        },
        params: {
          page: page - 1,
          property: selectedValue,
        },
      })
      .then(({ data }) => {
        // console.log(data);
        setContent(data.content);
        setTotalPage(data.totalPages);
      })
      .catch(({ error }) => {
        console.log(error);
      });
  }, [page, token, totalPage, selectedValue]);

  useEffect(() => {
    fetchData();
  }, [page, selectedValue]);

  const scrollToTop = () => {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  return (
    <section className='grid content-between m-9'>
      {content.length > 0 ? (
        <>
          <article className='flex justify-between mb-8 mx-10'>
            <div className='border border-cyan-600 p-1 rounded-sm grid content-center'>
              <DibsSelect onSelect={handleSelect} />
            </div>
            <Link
              to='/community/write'
              className='border-2 p-2 rounded-sm border-cyan-600 bg-cyan-600 text-[#fefefe]'
            >
              <p>글쓰기</p>
            </Link>
          </article>
          <article className='grid justify-between grid-cols-11 my-4'>
            <p className='text-center'>번호</p>
            <p className='text-center col-span-4'>제목</p>
            <p className='text-center col-span-2'>글쓴이</p>
            <p className='text-center col-span-2'>등록일</p>
            <p className='text-center'>조회</p>
            <p className='text-center'>추천</p>
          </article>
          <hr className='border border-[#808080/75] mx-12' />
          <article className='text-sm'>
            {content.length > 0 ? (
              content.map((item, index) => (
                <ForumBoard key={index} item={item} />
              ))
            ) : (
              // 로딩 중임을 나타내는 스켈레톤 코드
              <div className='animate-pulse grid grid-cols-1 gap-2 my-2'>
                {Array.from({ length: 30 }, (_, i) => (
                  <div key={i} className='h-6 bg-gray-300 rounded mx-12'></div>
                ))}
              </div>
            )}
          </article>
          <article className='flex justify-end mb-8 mx-10'>
            <button
              onClick={scrollToTop}
              className='border-2 border-cyan-600 p-2 mr-2 rounded-sm bg-cyan-600 text-[#fefefe]'
            >
              <p>목록</p>
            </button>
            <Link
              to='/community/write'
              className='border-2 p-2 rounded-sm border-cyan-600 bg-cyan-600 text-[#fefefe]'
            >
              <p>글쓰기</p>
            </Link>
          </article>
          <article>
            <article className='my-8'>
              <Pagenation totalPage={totalPage} />
            </article>
            <article className='flex justify-center ml-32 my-8'>
              <SearchbarNone />
            </article>
          </article>
        </>
      ) : (
        <div>
          <p className='m-9 text-center'>아직 작성된 게시글이 없습니다.</p>
          <article className='flex justify-center mb-8 mx-10'>
            <Link
              to='/community/write'
              className='border-2 p-2 rounded-sm border-cyan-600 bg-cyan-600 text-[#fefefe]'
            >
              <p>글쓰기</p>
            </Link>
          </article>
        </div>
      )}
    </section>
  );
};
