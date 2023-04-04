import React from 'react';

type ReplyProps = {
  comment: any;
  createState: number;
  setCreateState: any;
};
export const ReplyCreate: React.FC<ReplyProps> = ({
  comment,
  createState,
  setCreateState,
}) => {
  return <div className='bg-[#808080] ml-4 p-2 rounded text-[#fefefe]'></div>;
};
