interface Props {
  cumulativeReturnDataDto: {
    cumulativeReturnDesc: string;
    strategyCumulativeReturn: number;
    marketCumulativeReturn: number;
    cagrDesc: string;
    strategyCagr: number;
    marketCagr: number;
    sharpe: string;
    strategySharpe: number;
    marketSharpe: number;
    sortino: string;
    strategySortino: number;
    marketSortino: number;
    mdd: string;
    strategyMDD: number;
    marketMDD: number;
  };
}

const ResultCumulativeReturnTable = (props: Props) => {
  console.log(props);
  return <p>ResultCumulativeReturnTable</p>;
};

export default ResultCumulativeReturnTable;
