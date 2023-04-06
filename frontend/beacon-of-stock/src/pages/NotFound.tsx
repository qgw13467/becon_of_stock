// import React from 'react';

const NotFound = () => {
  return (
    <div className='min-h-screen bg-gray-100 flex items-center justify-center'>
      <div className='bg-white p-8 rounded-lg shadow-md text-center'>
        <h1 className='text-4xl font-bold text-red-500'>404 Not Found</h1>
        <p className='text-gray-700 mt-4'>
          Sorry, the page you are looking for does not exist.
        </p>
      </div>
    </div>
  );
};

export default NotFound;
