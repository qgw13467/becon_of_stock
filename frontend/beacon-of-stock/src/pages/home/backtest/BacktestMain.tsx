import React from "react";

const BacktestMain = () => {
  return (
    <React.Fragment>
      <h1 className="font-KJCbold">BackTest</h1>
      <main className="h-[80vh] flex place-content-around align-items: center mt-50">
        <section className="relative inline-block w-[29%] h-full">
          <p className="text-[30]">기본 설정</p>
        </section>
        <section className="relative inline-block w-[29%] h-full">
          <p>팩터 설정</p>
        </section>
        <section className="relative inline-block w-[29%] h-full">
          <p>선택 항목 보기</p>
        </section>
      </main>
    </React.Fragment>
  );
};

export default BacktestMain;
