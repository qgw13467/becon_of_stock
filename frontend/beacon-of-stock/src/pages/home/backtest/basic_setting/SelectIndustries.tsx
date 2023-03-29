import React, { useState, useEffect } from 'react';
import SelectIndustry from './SelectIndustry';
import axios_api from '../../../../assets/config/Axios';
import { getCookie } from '../../../../assets/config/Cookie';

interface Props {
  setShowIndustry: React.Dispatch<React.SetStateAction<boolean>>;
}

const SelectIndustries = (props: Props) => {
  type industryType = {
    industries: {
      id: number;
      industryName: string;
    }[];
  };

  const [industries, setIndustries] = useState<industryType>();
  const token = getCookie('accessToken');

  useEffect(() => {
    axios_api
      .get('/industries', {
        headers: {
          authentication: token,
        },
      })
      .then((response) => {
        console.log(response);
        console.log(response.data);
        setIndustries(response.data);
      })
      .catch((error) => console.log(error));
  }, [token]);

  console.log(industries);

  return (
    <div className='fixed top-[20%] left-[35%] w-[30%] z-100 overflow-hidden bg-[#FEFEFE] rounded-lg z-10'>
      <div className='flex justify-between'>
        <span className='text-xl'>산업 선택</span>
      </div>
      <ul>
        {industries?.industries.map((industry) => {
          return (
            <li key={industry.id}>
              <SelectIndustry
                id={industry.id}
                industryName={industry.industryName}
              />
            </li>
          );
        })}
      </ul>
    </div>
  );
};

export default SelectIndustries;
