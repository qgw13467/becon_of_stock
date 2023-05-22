import React, { useState } from 'react';

type DibsSelectProps = {
  onSelect: (selectedValue: string | null) => void;
};

const DibsSelect: React.FC<DibsSelectProps> = ({ onSelect }) => {
  const [selectedElement, setSelectedElement] = useState<string | null>(null);

  const handleSelectionChange = (
    event: React.ChangeEvent<HTMLSelectElement>
  ) => {
    const selectedValue = event.target.value;
    setSelectedElement(selectedValue);
    onSelect(selectedValue);
  };

  return (
    <select
      value={selectedElement ?? 'id'}
      onChange={handleSelectionChange}
      className='focus:outline-none cursor-pointer'
    >
      <option value='id'>기본</option>
      <option value='commentNum'>댓글수</option>
      <option value='hit'>조회수</option>
      <option value='likeNum'>추천수</option>
    </select>
  );
};

export default DibsSelect;
