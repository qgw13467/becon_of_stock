const DetailSkeleton = () => {
  return (
    <section className='m-9 px-9'>
      <p className='my-9 text-3xl font-bold bg-cyan-600 text-white text-center w-[280px] h-12 grid place-content-center rounded-md animate-pulse'>
        Loading...
      </p>
      <div className='flex justify-between items-center'>
        <div className='w-2/3 h-10 bg-gray-200 rounded-md animate-pulse'></div>
        <div className='w-1/4 h-10 bg-gray-200 rounded-md animate-pulse'></div>
      </div>
      <div className='mt-6'>
        <div className='flex  justify-between items-center'>
          <div className='flex justify-center'>
            <div className='w-8 h-8 mr-4 bg-gray-200 rounded-md animate-pulse'></div>
            <div className='w-32 h-8 bg-gray-200 rounded-md animate-pulse'></div>
          </div>
          <div className='w-1/4 h-8 bg-gray-200 rounded-md animate-pulse'></div>
          <div className='flex justify-center'>
            <div className='w-32 h-8 bg-gray-200 rounded-md animate-pulse'></div>
            <div className='flex justify-center ml-8'>
              <div className='w-24 h-8 bg-gray-200 rounded-md animate-pulse'></div>
              <div className='w-24 h-8 bg-gray-200 rounded-md animate-pulse'></div>
            </div>
          </div>
        </div>
      </div>
      <div className='flex justify-between mt-10'>
        <div className='w-10/12 h-40 bg-gray-200 rounded-md animate-pulse'></div>
        <div className='w-2/12 h-40 bg-gray-200 rounded-md animate-pulse'></div>
      </div>
      <div className='flex justify-center mt-10'>
        <div className='w-32 h-12 bg-gray-200 rounded-md animate-pulse'></div>
        <div className='w-32 h-12 bg-gray-200 rounded-md animate-pulse ml-8'></div>
      </div>
      <div className='flex justify-end my-6'>
        <button className='px-4 mr-2 bg-gray-200 rounded-md animate-pulse'></button>
        <p className='mx-10 text-[#808080]'>|</p>
        <button className='px-4 bg-gray-200 rounded-md animate-pulse'></button>
      </div>
      <div className='mb-6'>
        {[...Array(3)].map((_, index) => (
          <div key={index} className='flex items-center my-4'>
            <div className='w-10 h-10 bg-gray-200 rounded-full animate-pulse'></div>
            <div className='ml-4'>
              <div className='w-1/4 h-6 bg-gray-200 rounded-md animate-pulse'></div>
              <div className='mt-1 w-2/4 h-6 bg-gray-200 rounded-md animate-pulse'></div>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
};

export default DetailSkeleton;
