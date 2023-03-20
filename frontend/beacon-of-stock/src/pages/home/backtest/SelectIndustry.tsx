import React from "react";

const SelectIndustry = () => {
  return (
    <div className="fixed top-0 left-0 w-full h-screen z-10 bg-[#131313] bg-opacity-50">
      <div className="fixed top-[20%] left-[35%] w-[30%] z-100 overflow-hidden bg-[#FEFEFE] rounded-lg">
        <div className="flex justify-between">
          <span className="text-xl">산업 선택</span>
        </div>
        <div>
          <p>API로 받아올 항목</p>
        </div>
      </div>
    </div>
  );
};

export default SelectIndustry;
