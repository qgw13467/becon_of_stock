type ForumBoard = {
  item: number;
};

export const ForumBoard = ({ item }: ForumBoard) => {
  return <div className='my-2'> 한 줄에 하나 씩 {item + 1}</div>;
};
