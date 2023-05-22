import { useState } from 'react';
import axios_api from '../../../assets/config/Axios';
import SelectModal from './SelectModal';
import { getCookie } from '../../../assets/config/Cookie';
import { WriteTileBoard } from './WriteTileBoard';
import {
  useNavigate,
  // useLocation
} from 'react-router-dom';
// type dataType = {
//   boardId: number;
//   commentNum: number;
//   hit: number;
//   likeNum: number;
//   memberId: number;
//   content: string;
//   createDate: string;
//   nickname: string;
//   title: string;
//   dibStatus: boolean;
//   followStatus: boolean;
//   isAuthor: boolean;
//   likeStatus: boolean;
// };
export const UpdateCommu = () => {
  const navigate = useNavigate();
  // const location = useLocation();
  // const prevData = location.state as { data: dataType };
  const [title, setTitle] = useState<string>('');
  const titleChange = (e: any) => {
    setTitle(e.target.value);
  }; // title state
  const [content, setContent] = useState<string>('');
  const contentChange = (e: any) => {
    setContent(e.target.value);
  }; // content state

  // 그 후 최종적으로 게시글 등록을 누르면 새 글이 써지고
  // '/community/dibs' 페이지로 redirect 해준다.
  const [thisId, setThisId] = useState({ id: -1, title: '' });
  const handleSetThisId = (id: number, title: string) => {
    setThisId({ id, title });
  };
  // console.log(typeof thisId.id);
  const [openModal, setOpenModal] = useState<boolean>(false);
  const [items, setItems] = useState([]);
  const token = getCookie('accessToken');
  const openModalClick = () => {
    setOpenModal(true);
    axios_api
      .get('/strategies', {
        headers: {
          authentication: token,
        },
      })
      .then((res) => {
        // console.log(res);
        setItems(res.data.content);
      })
      .catch((err) => {
        console.log(err);
      });
  };
  const closeModal = () => {
    setOpenModal(false);
  };

  const whiteBoard = () => {
    axios_api
      .post(
        '/boards/',
        {
          content: content,
          strategyId: String(thisId.id),
          title: title,
        },
        {
          headers: { authentication: token },
        }
      )
      .then((res) => {
        alert('글 작성이 완료 되었습니다.');
        navigate('/community/dibs');
      })
      .catch((err) => {
        alert('제목과 내용이 입력되지 않았거나 전략이 선택되지 않았습니다.');
      });
  };
  return (
    <section className='bg-white shadow-lg rounded-lg pb-8 mb-4'>
      <p className='m-9 text-3xl font-bold bg-cyan-600 text-[#fefefe] text-center w-[280px] h-12 grid content-center rounded-[4px]'>
        전략 공유 게시판
      </p>
      <p className='text-[#131313] font-bold mb-2 ml-[78px] mt-9'>
        게시글 쓰기
      </p>
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
            className='resize-none shadow appearance-none border h-80 rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline'
            placeholder='내용을 입력하세요.'
            value={content}
            onChange={contentChange}
          ></textarea>
        </div>
        <div className='mb-4'>
          <p className='text-[#131313] font-bold mb-2'>내 전략 등록</p>
          <div className='flex justify-start'>
            <button
              className='bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded'
              onClick={() => {
                openModalClick();
              }}
            >
              내 전략 선택
            </button>
            <p className='ml-10 my-auto'>
              선택된 전략 이름 :{' '}
              {thisId.title.length > 0
                ? thisId.title
                : '"선택된 전략이 없습니다."'}
            </p>
          </div>
        </div>
        {/* 전략 창 열리는 모달 컴포넌트 !!!  */}
        <SelectModal isOpen={openModal} onClose={closeModal}>
          <h2 className='text-xl font-bold mb-4'>내 전략 선택</h2>
          <article className='grid grid-cols-4 gap-4 content-evenly mx-32'>
            {items.map((item, index) => (
              <WriteTileBoard
                key={index}
                item={item}
                setThisId={handleSetThisId}
                closeModal={closeModal}
              />
            ))}
          </article>
        </SelectModal>
        <div className='flex justify-end'>
          <button
            className='bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded'
            onClick={whiteBoard}
          >
            게시글 등록
          </button>
        </div>
      </article>
    </section>
  );
};
