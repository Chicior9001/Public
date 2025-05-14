class NumbersAnalyzer {
  static count(arr) {
    return arr.length;
  }

  static sum(arr) {
    let sum = 0;
    arr.forEach(numb => {sum += numb});
    return sum;
  }

  static average(arr) {
    return NumbersAnalyzer.sum(arr) / arr.length;
  }

  static min(arr) {
    return Math.min(...arr);
  }

  static max(arr) {
    return Math.max(...arr);
  }

  static anotherAverage(arr) {
    let sum = 0;
    arr.forEach(numb => { sum += 1 / numb});
    return arr.length / sum;
  }
}

console.log(NumbersAnalyzer.count([1, 2, 3, 4, 1, 2, 1]));
console.log(NumbersAnalyzer.sum([1, 2, 3, 4, 5, 6, 7]));
console.log(NumbersAnalyzer.average([1, 2, 3, 4, 5, 6, 7]));
console.log(NumbersAnalyzer.average([1, 2, 3, 4, 5, 6, 7]));
console.log(NumbersAnalyzer.max([1, 2, 3, 4, 5, 6, 7]));
console.log(NumbersAnalyzer.anotherAverage([1, 2, 3, 4, 5, 6, 7]));
console.log(NumbersAnalyzer.anotherAverage([2, 2, 5, 7]));