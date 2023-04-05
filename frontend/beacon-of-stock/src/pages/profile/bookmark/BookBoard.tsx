type BookItem = {
  nickname: string;
  title: string;
  createDate: string;
  boardId: number;
  commentNum: number;
  hit: number;
  likeNum: number;
  memberId: number;
};

type BookBoard = {
  item: BookItem;
};
export const BookBoard = ({ item }: BookBoard) => {
  return (
    <div>
      <div className='flex justify-between'>
        <div>{item.nickname}</div>
        <div className="flex justify-center">
          <div>{item.title}</div>
          <div>{item.commentNum}</div>
        </div>
        <div>{item.createDate.replace('T', ' ')}</div>
        <div>{item.hit}</div>
        <div>{item.likeNum}</div>
      </div>
    </div>
  );
};
