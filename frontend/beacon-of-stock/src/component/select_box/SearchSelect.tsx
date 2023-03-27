import React, { useState } from 'react';

const SearchSelect: React.FC = () => {
  const [selectedElement, setSelectedElement] = useState<string | null>(null);

  const handleSelectionChange = (
    event: React.ChangeEvent<HTMLSelectElement>
  ) => {
    setSelectedElement(event.target.value);
  };

  return (
    <select
      value={selectedElement ?? 'Element 1'}
      onChange={handleSelectionChange}
      className='focus:outline-none cursor-pointer'
    >
      <option value='Element 1'>전체</option>
      <option value='Element 2'>제목</option>
      <option value='Element 3'>내용</option>
    </select>
  );
};

export default SearchSelect;
