import BOSLoading from '../../../../assets/img/BOSLoading.gif';
const animate1 = { animationDuration: '0.5s' };
const animate2 = { animationDuration: '1.5s' };
const Loading = () => {
  return (
    <div className='relative'>
      <img
        src={BOSLoading}
        alt='loading'
        className='absolute w-screen h-[91.5vh]'
      />
      <div className='absolute text-4xl right-[41%] top-[500px] text-[#fefefe] font-KJCthin flex justify-center'>
        <div className='animate-bounce' style={animate2}>
          N
        </div>
        <div className='animate-bounce' style={animate1}>
          o
        </div>
        <div className='animate-bounce mr-4'>w</div>
        <div className='animate-bounce' style={animate2}>
          L
        </div>
        <div className='animate-bounce' style={animate1}>
          o
        </div>
        <div className='animate-bounce' style={animate2}>
          a
        </div>
        <div className='animate-bounce'>d</div>
        <div className='animate-bounce' style={animate1}>
          i
        </div>
        <div className='animate-bounce'>n</div>
        <div className='animate-bounce' style={animate1}>
          g
        </div>
        <div className='animate-bounce'>. </div>
        <div className='animate-bounce' style={animate2}>
          .
        </div>
        <div className='animate-bounce'>. </div>
      </div>
    </div>
  );
};
export default Loading;
