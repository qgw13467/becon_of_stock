import { FC, useState } from "react"

export const MyProfile: FC = () => {
  const emptyProfile = require('../../assets/img/empty-img.jpg')
  const [originalNickname, setOriginalNickname] = useState<string>('모시깽이') // back에서 받아오는 닉네임 정보
  const [isNickname, setIsNickname] = useState<string>(originalNickname)
  const [isInput, setIsInput] = useState<boolean>(true)
  const nicknameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setIsNickname(e.target.value)
  }
  const nicknameValidation = () => {
    // console.log('실행 중')
    // 닉네임에 변화가 있을 때, 공백이 없을 때, 길이가 1이상, 8이하 일 때
    if (isNickname !== originalNickname && !isNickname.includes(' ') && isNickname.length >= 1 && 8 >= isNickname.length) {
      // 조건을 만족하는 닉네임을 백 서버로 보내는 코드 작성해야함.
      // 백으로 보낸 후
      setIsInput(true)
      setOriginalNickname(isNickname)
    } else {
      console.log('왜 안 되냐 이거?')
      alert('닉네임 양식이 맞지 않습니다.')
      setIsNickname('')
    }
  }
  // 닉네임 변경 취소 시 원래 닉네임 할당
  const cencelChange = () => {
    setIsNickname(originalNickname)
    setIsInput(true)
  }
  
  return <div>
    <p className="font-KJCbold text-5xl m-9">내 정보</p>
    <div id='프로필-프로필' className="flex justify-start m-9">
      <img src={emptyProfile} alt="empty-profile-img" className="rounded-full border-[#131313] border-[2px] w-[200px] h-[200px] m-9"/>
      <div id='프로필-사진-옆-div' className="grid content-evenly w-[400px] relative">
        <div className="flex justify-between">
          <p>닉네임 : </p>
          <div>
            {isInput && <div className="flex justify-end">
              <p>{isNickname}</p>
            </div>}
            {!isInput &&
              <div className="flex justify-end">
                <input type="text" value={isNickname} onChange={nicknameChange} className='border rounded-sm border-black' dir="rtl"/> 
              </div>
            }
            {/* 닉네임을 변경할 때 양식이 맞지 않거나 공백으로 제출할 시 onClick 시행 안 되게 메서드 작성.*/}
          </div>
        </div>
        <div className="absolute -right-[120px] top-[28px]">
          {isInput && <button className="bg-[#802A57] text-[#fefefe] rounded-md w-[100px] h-[40px]" onClick={() => setIsInput(false)}> 닉네임 변경 </button>}
          {!isInput && <div className="absolute -right-[76px] flex justify-center">
            <button className="bg-[#2a3580] text-[#fefefe] rounded-md w-[80px] h-[40px]" onClick={nicknameValidation}> 변경 </button>  
            <button className="bg-[#802A57] text-[#fefefe] rounded-md w-[80px] h-[40px] ml-4" onClick={cencelChange}> 취소 </button> 
          </div>}
        </div>
        <div className="flex justify-between">
          <p>작성한 게시글 수 : </p>
          <div className="flex justify-end">
            <p>36</p>
            <p>개</p>
          </div>
        </div>
        <div className="flex justify-between">
          <p>팔로우 : </p>
          <div className="flex justify-end">
            <p>18</p>
            <p>명</p>
          </div>
        </div>
        <div className="flex justify-between">
          <p>팔로워 : </p>
          <div className="flex justify-end">
            <p>367</p>
            <p>명</p>
          </div>
        </div>
      </div>
    </div>
    {/* 내 전략은 전략 컴포넌트 만들어서 해당 컴포넌트에서 앞에서 3개가 어차피 대표전략일거니까
    그 부분 그냥 불러와서 쓰기 */}
  </div>
}