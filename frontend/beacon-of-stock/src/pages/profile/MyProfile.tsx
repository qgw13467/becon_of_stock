import { FC, useEffect, useState } from 'react';
import { useProfileStore } from '../../store/store';
import axios_api from '../../assets/config/Axios';
import { getCookie } from '../../assets/config/Cookie';
import FollowModal from './follow_modal/FollowModal';
import FollowerModalComponent from './follow_modal/FollowerModalComponent';
import { MyStrategy } from './MyStrategy';
import pen from '../../assets/img/edit.png';
import closeX from '../../assets/img/close.png';

export const MyProfile: FC = () => {
  const emptyProfile = require('../../assets/img/empty-img.jpg');
  const token = getCookie('accessToken');
  const { profile } = useProfileStore();
  const [isNickname, setIsNickname] = useState<string>(profile.nickname);
  const [isInput, setIsInput] = useState<boolean>(true);
  const nicknameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setIsNickname(e.target.value);
  };
  const nicknameValidation = () => {
    // 닉네임에 변화가 있을 때, 공백이 없을 때, 길이가 1이상, 8이하 일 때
    if (
      isNickname !== profile.nickname &&
      !isNickname.includes(' ') &&
      isNickname.length >= 1 &&
      8 >= isNickname.length
    ) {
      // 조건을 만족하는 닉네임을 백 서버로 보내는 코드 작성해야함.
      const body = {
        nickname: isNickname,
      };
      axios_api
        .put('/user', body, {
          headers: {
            authentication: token,
          },
        })
        .then((res) => {
          console.log(res, '닉네임 변경 요청');
        })
        .catch((err) => {
          console.log(err);
        });
      // 백으로 보낸 후
      setIsInput(true);
    } else {
      console.log('왜 안 되냐 이거?');
      alert('닉네임 양식이 맞지 않습니다.');
      setIsNickname(profile.nickname);
    }
  };
  // 닉네임 변경 취소 시 원래 닉네임 할당
  const cencelChange = () => {
    setIsNickname(profile.nickname);
    setIsInput(true);
  };
  // 혹시라도 로딩 시 profile 받아오는 부분이 한 박자 느리더라고
  // nickName에 nickname 제대로 할당하기 위한 hook
  useEffect(() => {
    setIsNickname(profile.nickname);
  }, [profile]);

  // ==============팔로우 팔로워 관련 모달 부분==================
  const [items, setItems] = useState<any[]>([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const followClick = () => {
    setIsModalOpen(true);
    axios_api
      .get('/followers', { headers: { authentication: token } })
      .then((res) => {
        console.log(res);
        setItems(res.data);
      })
      .catch((error) => {
        if (error.message !== 'Request failed with status code 404') {
          console.log('404 말고 다른 에러다 비사아ㅏㅇ');
        }
      });
  };
  const followCloseModal = () => {
    setIsModalOpen(false);
    axios_api
      .get('/follow', { headers: { authentication: token } })
      .then((res) => {
        console.log(res);
        setItems(res.data);
      })
      .catch((error) => {
        if (error.message !== 'Request failed with status code 404') {
          console.log('404 말고 다른 에러다 비사아ㅏㅇ');
        }
      });
  };
  const [isModalOpen2, setIsModalOpen2] = useState(false);
  const followClick2 = () => {
    setIsModalOpen2(true);
  };
  const followCloseModal2 = () => {
    setIsModalOpen2(false);
  };
  //===========================================================

  return (
    <div className='grid grid-cols-3'>
      <div className='col-span-1'>
        <p className='font-KJCbold text-2xl my-9 ml-60'>내 프로필</p>
        <div id='프로필-프로필' className='grid justify-start my-9 mx-48'>
          <img
            src={profile.profileImg ? profile.profileImg : emptyProfile}
            alt='empty-profile-img'
            className='rounded-full border-[#131313] border-[2px] w-[180px] h-[180px] m-9'
          />
          <div
            id='프로필-사진-옆-div'
            className='grid content-evenly w-[200px] relative ml-10'
          >
            <div className='flex justify-between'>
              <p>닉네임 : </p>
              <div>
                {isInput && (
                  <div className='flex justify-end'>
                    <p>{isNickname}</p>
                  </div>
                )}
                {!isInput && (
                  <div className='flex justify-end'>
                    <input
                      type='text'
                      value={isNickname}
                      onChange={nicknameChange}
                      className='border rounded-sm border-black w-32'
                      dir='rtl'
                    />
                  </div>
                )}
                {/* 닉네임을 변경할 때 양식이 맞지 않거나 공백으로 제출할 시 onClick 시행 안 되게 메서드 작성.*/}
              </div>
            </div>
            <div className='absolute -right-[40px]'>
              {isInput && (
                <img
                  src={pen}
                  alt='edit-pen'
                  className='border border-[#3E7CBC] rounded-md w-[24px] h-[24px]'
                  onClick={() => setIsInput(false)}
                />
              )}
              {!isInput && (
                <div className='absolute -right-[30px] flex justify-center'>
                  <img
                    src={pen}
                    alt='edit-pen'
                    className='border border-[#3E7CBC] rounded-md w-[24px] h-[24px]'
                    onClick={() => setIsInput(false)}
                  />
                  <img
                    src={closeX}
                    alt='edit-pen'
                    className='border border-[#F19999] rounded-md w-[24px] h-[24px] ml-4'
                    onClick={cencelChange}
                  />
                </div>
              )}
            </div>
            <div className='flex justify-between'>
              <p>작성한 게시글 수 : </p>
              <div className='flex justify-end'>
                <p>{profile.postNum}</p>
                <p className='ml-2'>개</p>
              </div>
            </div>
            <div className='flex justify-between'>
              <p>팔로우 : </p>
              <button
                className='flex justify-end'
                onClick={() => {
                  followClick();
                }}
              >
                <p>{profile.followNum}</p>
                <p className='ml-2'>명</p>
              </button>
            </div>
            <FollowModal isOpen={isModalOpen} onClose={followCloseModal}>
              <p className='font-bold'>팔로우 목록</p>
              {items.map((item, index) => (
                <div key={index} className='flex content-around my-2'>
                  <img
                    src={item.profileImg}
                    alt='followImg'
                    className='h-8 w-8 mr-4'
                  />
                  <p className='text-center mx-8'>{item.nickname}</p>
                </div>
              ))}
            </FollowModal>
            <div className='flex justify-between'>
              <p>팔로워 : </p>
              <button
                className='flex justify-end'
                onClick={() => {
                  followClick2();
                }}
              >
                <p>{profile.followerNum}</p>
                <p className='ml-2'>명</p>
              </button>
            </div>
            <FollowerModalComponent
              isOpen={isModalOpen2}
              onClose={followCloseModal2}
            >
              <p className='font-bold'>팔로워 목록</p>
              {items.map((item, index) => (
                <div key={index} className='flex content-around my-2'>
                  <img
                    src={item.profileImg}
                    alt='followImg'
                    className='h-8 w-8 mr-4'
                  />
                  <p className='text-center mx-8'>{item.nickname}</p>
                </div>
              ))}
            </FollowerModalComponent>
          </div>
        </div>
        {/* 내 전략은 전략 컴포넌트 만들어서 해당 컴포넌트에서 앞에서 3개가 어차피 대표전략일거니까
    그 부분 그냥 불러와서 쓰기 */}
      </div>
      <MyStrategy />
    </div>
  );
};
