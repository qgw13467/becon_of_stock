import React from "react";
import FactorSettings from "./FactorSettings";
import SelectedItems from "./SelectedItems";

const Factor = () => {
  return (
    <main className="flex items-center place-content-between">
      <section className="relative inline-block w-[46.1%] h-full border">
        <FactorSettings />
      </section>
      <section className="relative inline-block w-[46.1%] h-full border">
        <SelectedItems />
      </section>
    </main>
  );
};

export default Factor;
