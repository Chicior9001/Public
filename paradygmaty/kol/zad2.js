function guessNumber(guesses) {
  let randomNumber = Math.floor(Math.random() * 100) + 1;
  return guesses.includes(randomNumber)
    ? {isCorrect: true, attempts: guesses.indexOf(randomNumber) + 1}
    : {isCorrect: false, attempts: guesses.length};
}
console.log(guessNumber([12, 45, 1, 75, 39, 28, 48, 23, 47, 86, 92]));
console.log(guessNumber([...Array(100).keys()]));
console.log(guessNumber(Array.from({ length: 100 }, (_, i) => i + 1)));