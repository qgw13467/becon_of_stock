interface Props {
  id: number;
  industryName: string;
}

const SelectIndustry = (props: Props) => {
  return (
    <div>
      <div>{props.industryName}</div>
    </div>
  );
};

export default SelectIndustry;
