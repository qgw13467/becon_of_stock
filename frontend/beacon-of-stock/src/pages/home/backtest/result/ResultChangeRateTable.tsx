const ResultChangeRateTable = () => {
  return (
    <div className='flex flex-col justify-center'>
      <p className='text-xl font-KJCbold'>ResultChangeRateTable</p>
      {/* 표를 나타내는 태그 */}
      <table className='border'>
        {/* 제목 행을 그룹화하는 태그 */}
        <thead className='border'>
          <tr className='border'>
            <th>Month</th>
            <th>Savings</th>
            <th>Savings</th>
          </tr>
        </thead>
        {/* 본문 행을 그룹화하는 태그 */}
        <tbody className='border'>
          <tr>
            <td>January</td>
            <td>$100</td>
            <td>$100</td>
          </tr>
        </tbody>
        <tbody className='border'>
          <tr>
            <td>January</td>
            <td>$100</td>
            <td>$100</td>
          </tr>
        </tbody>
        <tbody className='border'>
          <tr>
            <td>January</td>
            <td>$100</td>
            <td>$100</td>
          </tr>
        </tbody>
        {/* 바닥 행을 그룹화하는 태그 */}
        <tfoot className='border'>
          <tr>
            <td>Sum</td>
            <td>$180</td>
            <td>$180</td>
          </tr>
        </tfoot>
        <tfoot className='border'>
          <tr>
            <td>Sum</td>
            <td>$180</td>
            <td>$180</td>
          </tr>
        </tfoot>
      </table>
    </div>
  );
};

export default ResultChangeRateTable;
