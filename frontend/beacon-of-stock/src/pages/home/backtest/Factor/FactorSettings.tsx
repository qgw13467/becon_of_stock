import React, { useState, useEffect } from 'react';
import FactorBasic from './FactorBasic';
import axios from 'axios';

const FactorSettings = () => {
  type dataType = {
    factors: {
      description: string;
      id: number;
      title: string;
      indicators: {
        id: number;
        title: string;
        count: number;
        description: string;
      }[];
    }[];
  };
  const [data, setData] = useState<dataType>();

  useEffect(() => {
    const getFactors = axios
      .get('/api/indicators', {
        headers: {
          authentication: '',
        },
      })
      .then((response) => {
        // console.log(response);
        // console.log(response.data);
        setData(response.data);
      })
      .catch((error) => console.log(error));
  }, []);

  // console.log(data);

  return (
    <React.Fragment>
      <p className='text-xl font-KJCbold'>팩터 설정</p>
      <ul>
        {data?.factors.map((factor) => {
          return (
            <li key={factor.id}>
              <FactorBasic
                id={factor.id}
                title={factor.title}
                description={factor.description}
                indicators={factor.indicators}
              />
            </li>
          );
        })}
      </ul>
    </React.Fragment>
  );
};

export default FactorSettings;
