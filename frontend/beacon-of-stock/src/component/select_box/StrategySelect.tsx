import React, { useState } from 'react';

const StrategySelect: React.FC = () => {
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
      className='border-2 rounded-sm border-black ml-9 mb-3 cursor-pointer'
    >
      <option value='Element 1'>최신순</option>
      <option value='Element 2'>조회순</option>
      <option value='Element 3'>추천순</option>
    </select>
  );
};

export default StrategySelect;
