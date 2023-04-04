import React, { useEffect, useState } from 'react';
import axios_api from '../../../assets/config/Axios';
import { getCookie } from '../../../assets/config/Cookie';
import { useLocation } from 'react-router-dom';
import SelectModal from '../Dibs/SelectModal';
import { WriteTileBoard } from '../Dibs/WriteTileBoard';
import { AttendList } from './AttendList';
import { useContestStateStore } from '../../../store/store';
// import { useContestState } from '../../../store/store';

interface CommunityContest {
  typ: number;
  title: string;
  content: string;
  description: string;
}

export const CommunityContest: React.FC = () => {
  const { falseState } = useContestStateStore();
  const location = useLocation();
  const contestId = Number(location.pathname.split('/').pop());
  const token = getCookie('accessToken');
  const [data, setData] = useState<CommunityContest>();

  useEffect(() => {
    axios_api
      .get(`/contests/${contestId}`, {
        headers: { authentication: token },
      })
      .then((res) => {
        setData(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);
  const [thisId, setThisId] = useState({ id: -1, title: '' });
  const handleSetThisId = (id: number, title: string) => {
    setThisId({ id, title });
  };
  const [items, setItems] = useState([]);
  const [openModal, setOpenModal] = useState<boolean>(false);
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
  // console.log(contestId, thisId.id);
  const closeModal = () => {
    setOpenModal(false);
  };
  const joinContest = () => {
    axios_api
      .post(
        '/contests/join',
        {
          contestId: contestId,
          strategyId: thisId.id,
        },
        {
          headers: {
            authentication: token,
          },
        }
      )
      .then((res) => {
        // console.log(res);
        setThisId({ id: -1, title: '' });
      })
      .catch((err) => {
        console.log(err);
      });
  };

  if (!data) return <div></div>;
  return (
    <div className='m-9'>
      <div className='my-4 flex justify-between px-9 py-5 border border-emerald-700 rounded'>
        <div className='grid content-center'>
          <p className='bg-emerald-700 rounded text-center w-80 text-[#fefefe] mb-2'>
            대회명 : {data.title}
          </p>
          <div className='grid grid-cols-10 border border-emerald-700 rounded text-center w-[900px] p-4 text-emerald-700 mt-2'>
            <p className='col-span-1'>목표 :</p>
            <p className=''>{data.description}</p>
          </div>
        </div>
        {thisId.title && (
          <p className='ml-10 my-auto'>선택된 전략 이름 : {thisId.title}</p>
        )}
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
        {!falseState && (
          <>
            {thisId.title ? (
              <button
                className='w-[104px] h-[104px] border-2 bg-emerald-700 rounded text-[#fefefe]'
                onClick={() => {
                  joinContest();
                }}
              >
                대회참가
              </button>
            ) : (
              <button
                className='w-[104px] h-[104px] border-2 border-emerald-700 rounded'
                onClick={() => {
                  openModalClick();
                }}
              >
                대회참가
              </button>
            )}
          </>
        )}
      </div>
      <AttendList contestId={contestId} />
    </div>
  );
};
