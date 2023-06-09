import React, { useEffect, useState } from 'react';
import axios_api from '../../../assets/config/Axios';
import { getCookie } from '../../../assets/config/Cookie';
import { useLocation, useNavigate } from 'react-router-dom';
import SelectModal from '../Dibs/SelectModal';
import { WriteTileBoard } from '../Dibs/WriteTileBoard';
import { AttendList } from './AttendList';
import { useContestStateStore } from '../../../store/store';
// import { useContestState } from '../../../store/store';
import Swal from 'sweetalert2';

interface CommunityContest {
  typ: number;
  title: string;
  content: string;
  description: string;
}

export const CommunityContest: React.FC = () => {
  const navigate = useNavigate();
  const { falseState } = useContestStateStore();
  const location = useLocation();
  const contestId = Number(location.pathname.split('/').pop());
  const token = getCookie('accessToken');
  const [data, setData] = useState<CommunityContest>();
  const [reload, setReload] = useState<boolean>(false);
  const changeReload = () => {
    setReload(!reload);
  };
  useEffect(() => {
    axios_api
      .get(`/contests/${contestId}`, {
        headers: { authentication: token },
      })
      .then((res) => {
        // console.log('여기까진 오케이');
        // const thisDate = res.data;
        setData(res.data);
        axios_api
          .put(
            `/contests/rank/${contestId}`,
            { contestId },
            {
              headers: { authentication: token },
            }
          )
          .then((res) => {
            // console.log(res);
          })
          .catch((err) => {
            console.log(err);
          });
      })
      .catch((err) => {
        console.log(err);
      });
  }, [reload]);
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
        setThisId({ id: -1, title: '' });
        // console.log('대회 참가하면 put rank 보냄');
        axios_api
          .put(
            `/contests/rank/${contestId}`,
            { contestId },
            {
              headers: {
                authentication: token,
              },
            }
          )
          .then((res) => {
            // console.log(res);
            changeReload();
          })
          .catch((err) => {
            if (err.request.status === 404) {
              Swal.fire({
                title: '오류 발생',
                text: '같은 전략으로는 참가하실 수 없습니다!',
                icon: 'warning',
                confirmButtonText: '돌아가기',
              }).then(() => {
                navigate(`/community/contests/${contestId}`);
              });
            }
            console.log(err);
          });
      })
      .catch((err) => {
        if (err.request.status === 404) {
          Swal.fire({
            title: '오류 발생',
            text: '같은 전략으로는 참가하실 수 없습니다!',
            icon: 'warning',
            confirmButtonText: '돌아가기',
          }).then(() => {
            navigate(`/community/contests/${contestId}`);
          });
        }
        console.log(err);
      });
  };

  if (!data) return <div></div>;
  return (
    <div className='m-9'>
      {falseState ? (
        <div className='my-4 flex justify-between px-9 py-5 border border-emerald-700 rounded w-[670px]'>
          <div className='grid content-center'>
            <p className='bg-emerald-700 rounded text-center w-80 text-[#fefefe] mb-2'>
              대회명 : {data.title}
            </p>
            <div className='grid grid-cols-10 border border-emerald-700 rounded text-center w-[600px] p-4 text-emerald-700 mt-2'>
              <p className='col-span-1'>목표 :</p>
              <p className='col-span-9'>{data.description}</p>
            </div>
          </div>
        </div>
      ) : (
        <div className='my-4 flex justify-between px-9 py-5 border border-emerald-700 rounded'>
          <div className='grid content-center'>
            <p className='bg-emerald-700 rounded text-center w-80 text-[#fefefe] mb-2'>
              대회명 : {data.title}
            </p>
            <div className='grid grid-cols-10 border border-emerald-700 rounded text-center w-[800px] p-4 text-emerald-700 mt-2'>
              <p className='col-span-1'>목표 :</p>
              <p className='col-span-9'>{data.description}</p>
            </div>
          </div>
          {thisId.title && (
            <div className='grid content-center'>
              <p className='my-auto text-start'>선택된 전략명 :</p>
              <p className='my-auto text-center'> {thisId.title} </p>
            </div>
          )}
          <SelectModal isOpen={openModal} onClose={closeModal}>
            <h2 className='text-xl font-bold mb-4'>내 전략 선택</h2>
            <article className='grid grid-cols-3 gap-4 content-evenly mx-2'>
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
                  className='w-[99px] h-[99px] border-2 bg-emerald-700 rounded text-[#fefefe]'
                  onClick={() => {
                    joinContest();
                  }}
                >
                  대회참가
                </button>
              ) : (
                <button
                  className='w-[99px] h-[99px] border-2 border-emerald-700 rounded'
                  onClick={() => {
                    openModalClick();
                  }}
                >
                  전략선택
                </button>
              )}
            </>
          )}
        </div>
      )}
      <AttendList contestId={contestId} />
    </div>
  );
};
