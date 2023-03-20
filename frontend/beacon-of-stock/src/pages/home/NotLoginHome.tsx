const NotLoginHome = () => {
  const beacon = require('../../assets/img/beacon.png')
  return <div className="bg-gradient-to-t from-indigo-500 via-[#FFE5B2] to-[#FFFFFF] h-[660px] flex justify-center">
    <div id='nlh-div1' className="grid content-center ml-48">
      <p className="text-center text-5xl">주식을 마주하는 등대</p>
      <p className="text-center text-5xl">투자의 지향점, 주마등</p>
      <br />
      <div className="flex justify-center">
        <button className="w-20 h-10 border border-gray-300 bg-[#FEFEFE] rounded-lg hover:bg-gray-100"> 시작하기 </button>
      </div>
    </div>
    <div id='nlh-div2' className="mt-20 pl-16">
      <img src={beacon} alt="main-beacon" className="w-[120px] h-[400px]"/>
    </div>
  </div>;
};

export default NotLoginHome;
