import { Link } from 'react-router-dom';

const NotLoginHome = () => {
  const beacon = require('../../assets/img/beacon.png');
  return (
    <div className='bg-gradient-to-t from-indigo-500 via-[#FFE5B2] to-[#FFFFFF] h-screen flex justify-center'>
      <div
        id='nlh-div1'
        className='grid content-center lg:ml-48 md:ml-36 sm:ml-24 ml-12'
      >
        <p className='text-center lg:text-5xl md:text-3xl'>
          주식을 마주하는 등대
        </p>
        <p className='text-center lg:text-5xl md:text-3xl'>
          투자의 지향점, 주마등
        </p>
        <br />
        <div className='flex justify-center'>
          <Link
            to='/login'
            className='lg:w-20 lg:h-10 md:w-16 md:h-8 w-12 h-6 border-black border-2 bg-[#FEFEFE] rounded-lg hover:bg-gray-100 grid content-center'
          >
            <p className='md:text-[16px] sm:text-[12px] text-[8px] text-center'>
              시작하기
            </p>
          </Link>
        </div>
      </div>
      <div
        id='nlh-div2'
        className='flex items-center justify-center lg:pl-16 md:pl-12 sm:pl-8 pl-4'
      >
        <img
          src={beacon}
          alt='main-beacon'
          className='lg:w-[120px] lg:h-[400px] md:w-[90px] md:h-[300px] sm:w-[60px] sm:h-[200px] w-[30px] h-[100px]'
        />
      </div>
    </div>
  );
};

export default NotLoginHome;
