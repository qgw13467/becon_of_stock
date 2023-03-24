import React from 'react';
import Factor from './Factor';
import FactorDetail from './FactorDetail';

interface Props {
  id: number;
  title: string;
  description: string;
  indicators: {
    id: number;
    title: string;
    count: number;
    description: string;
  }[];
}

const BasicFactor = (props: Props) => {
  return (
    <React.Fragment>
      <p>BasicFactors</p>
      <p>-----------------------------------</p>
      <p>{props.title}</p>
      <p>{props.description}</p>
      <ul>
        {props.indicators.map((indicator) => {
          return (
            <li key={indicator.id}>
              <FactorDetail
                id={indicator.id}
                title={indicator.title}
                count={indicator.count}
                description={indicator.description}
              />
            </li>
          );
        })}
      </ul>
    </React.Fragment>
  );
};

export default BasicFactor;
