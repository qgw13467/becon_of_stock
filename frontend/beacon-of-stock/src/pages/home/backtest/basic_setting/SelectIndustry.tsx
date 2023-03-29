import React from "react";

interface Props {
  setShowIndustry: React.Dispatch<React.SetStateAction<boolean>>;
}

const SelectIndustry = (props: Props) => {
  return (
    <div className="fixed top-[20%] left-[35%] w-[30%] z-100 overflow-hidden bg-[#FEFEFE] rounded-lg z-10">
      <div className="flex justify-between">
        <span className="text-xl">산업 선택</span>
      </div>
      <div>
        <p>API로 받아올 항목</p>
      </div>
    </div>
  );
};

export default SelectIndustry;
