// import React from 'react';

// const BacktestResult = () => {
//   return (
//     <React.Fragment>
//       <p>결과 페이지</p>
//     </React.Fragment>
//   );
// };

// export default BacktestResult;

import * as React from 'react';
import {
  XYPlot,
  XAxis,
  YAxis,
  VerticalGridLines,
  HorizontalGridLines,
  LineSeries,
  Crosshair,
} from 'react-vis';

interface DataPoint {
  x: number;
  y: number;
}

type Data = [DataPoint[], DataPoint[]];

interface Props {}

interface State {
  crosshairValues: DataPoint[];
}

export default class DynamicCrosshair extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      crosshairValues: [],
    };
  }

  /**
   * Event handler for onMouseLeave.
   * @private
   */
  private _onMouseLeave = () => {
    this.setState({ crosshairValues: [] });
  };

  /**
   * Event handler for onNearestX.
   * @param {Object} value Selected value.
   * @param {index} index Index of the value in the data array.
   * @private
   */
  private _onNearestX = (value: DataPoint, { index }: { index: number }) => {
    const { crosshairValues } = this.state;
    this.setState({
      crosshairValues: [...crosshairValues, ...this.DATA.map((d) => d[index])],
    });
  };

  private readonly DATA: Data = [
    [
      { x: 1, y: 10 },
      { x: 2, y: 7 },
      { x: 3, y: 15 },
    ],
    [
      { x: 1, y: 20 },
      { x: 2, y: 5 },
      { x: 3, y: 15 },
    ],
  ];

  public render() {
    const { crosshairValues } = this.state;

    return (
      <XYPlot onMouseLeave={this._onMouseLeave} width={300} height={300}>
        <VerticalGridLines />
        <HorizontalGridLines />
        <XAxis />
        <YAxis />
        <LineSeries onNearestX={this._onNearestX} data={this.DATA[0]} />
        <LineSeries data={this.DATA[1]} />
        <Crosshair values={crosshairValues} className={'test-class-name'} />
      </XYPlot>
    );
  }
}
