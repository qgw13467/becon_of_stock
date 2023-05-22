import React, { useState, useEffect } from 'react';

type Props = {
  initialTimeLeft: number;
};

function CountdownTimer({ initialTimeLeft }: Props) {
  const [timeLeft, setTimeLeft] = useState<number>(
    Number(localStorage.getItem('timeLeft')) || initialTimeLeft
  );

  useEffect(() => {
    const intervalId = setInterval(() => {
      setTimeLeft((prevTimeLeft) => prevTimeLeft - 1);
    }, 1000);
    return () => clearInterval(intervalId);
  }, []);

  useEffect(() => {
    localStorage.setItem('timeLeft', timeLeft.toString());
  }, [timeLeft]);

  useEffect(() => {
    if (timeLeft <= 0) {
      localStorage.removeItem('timeLeft');
      window.location.reload();
    }
  }, [timeLeft]);

  const minutes = Math.floor(timeLeft / 60)
    .toString()
    .padStart(2, '0');
  const seconds = (timeLeft % 60).toString().padStart(2, '0');

  return (
    <div>
      로그인 유지 시간 : {minutes}:{seconds}
    </div>
  );
}

export default CountdownTimer;
