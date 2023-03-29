import React from "react";

interface Props {
  setShowIndustry: React.Dispatch<React.SetStateAction<boolean>>;
}

const SelectIndustryBackground = (props: Props) => {
  const closeSelectIndustry = () => {
    props.setShowIndustry(false);
  };

  return (
    <div
      className="fixed top-0 left-0 w-full h-screen z-10 bg-[#131313] bg-opacity-50"
      onClick={closeSelectIndustry}
    ></div>
  );
};

export default SelectIndustryBackground;
