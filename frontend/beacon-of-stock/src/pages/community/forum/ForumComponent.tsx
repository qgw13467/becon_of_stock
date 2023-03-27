export const ForumComponent = () => {
  const PAGE_SIZE = 30; // 한 페이지에 보여질 게시글 수
  const 게시물수 = 10000; // back에서 총 게시물을 받아오게 할 것인가? x
  // 총 페이지 수
  const pageEA = Math.floor(게시물수 / PAGE_SIZE);
  // 더미
  const items: number[] = Array(PAGE_SIZE).fill(0);
  // console.log(pageEA);

  return <article>게시판 리스트 입니다.</article>;
};
