import React, { useState } from "react";
import SelectIndustry from "./SelectIndustry";
import SelectIndustryBackground from "./SelectIndustryBackground";

const BasicSettings = () => {
  const [industry, setIndustry] = useState<string>("선택");
  const [showIndustry, setShowIndustry] = useState<boolean>(false);
  const [tradeCost, setTradeCost] = useState<number>(0);
  const [maxNum, setMaxNum] = useState<number>(0);

  // 산업 선택 모달 활성화 여부 설정
  const showIndustryHandler = () => {
    setShowIndustry(true);
  };

  // 리밸런싱 주기 선택값 가져오기
  const rebalanceChangeHandler = (
    event: React.ChangeEvent<HTMLSelectElement>
  ) => {
    // text 값
    console.log(event.target.options[event.target.selectedIndex].text);
    // value 값
    console.log(event.target.value);
  };

  return (
    <React.Fragment>
      <p className="text-xl font-KJCbold">기본 설정</p>
      <div>
        <label htmlFor="industry">산업</label>
        <div className="border rounded-xl">
          <input
            type="button"
            id="industry"
            value={industry}
            onClick={showIndustryHandler}
          />
        </div>
        {showIndustry && (
          <div>
            <SelectIndustryBackground setShowIndustry={setShowIndustry} />
            <SelectIndustry setShowIndustry={setShowIndustry} />
          </div>
        )}
        <label htmlFor="tradeCost">거래비용</label>
        <div className="border rounded-xl">
          <input type="number" id="tradeCost" value={tradeCost} />
        </div>
      </div>
      <div>
        <label htmlFor="maxNum">최대 종목 수 (max 30)</label>
        <div className="border rounded-xl">
          <input type="number" id="maxNum" value={maxNum} />
        </div>
      </div>
      <div>
        <label htmlFor="rebalance">리밸런싱 주기</label>
        <div className="border rounded-xl">
          <select
            name="rebalance"
            id="rebalance"
            onChange={rebalanceChangeHandler}
          >
            <option value="3m">3개월</option>
            <option value="6m">6개월</option>
            <option value="12m">12개월</option>
          </select>
        </div>
      </div>
      <div>
        <label htmlFor="sort">정렬 기준</label>
        <div className="border rounded-xl">
          <select name="sort" id="sort">
            <option value="시가총액 높은 순">시가총액 높은 순</option>
            <option value="시가총액 낮은 순">시가총액 낮은 순</option>
            <option value="영업이익률 우선">영업이익률 우선</option>
            <option value="자본수익률 우선">자본수익률 우선</option>
            <option value="F-score 높은 순">F-score 높은 순</option>
          </select>
        </div>
      </div>
      <div>
        <label htmlFor="start">시작시점</label>
        <div className="border rounded-xl">
          <input type="date" id="start" />
        </div>
      </div>
      <div>
        <label htmlFor="end">종료시점</label>
        <div className="border rounded-xl">
          <input type="date" id="end" />
        </div>
      </div>
    </React.Fragment>
  );
};

export default BasicSettings;
