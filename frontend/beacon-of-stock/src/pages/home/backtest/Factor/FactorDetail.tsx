import React from 'react';

interface Props {
  id: number;
  title: string;
  count: number;
  description: string;
}

const FactorDetail = (props: Props) => {
  return (
    <React.Fragment>
      <p>===============================</p>
      <p>FactorDetail</p>
      <p>{props.title}</p>
      <p>{props.description}</p>
      <p>{props.count}</p>
    </React.Fragment>
  );
};

export default FactorDetail;
