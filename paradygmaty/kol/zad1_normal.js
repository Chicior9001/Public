class NumbersAnalyzer {
  constructor(arr) {
    this.arr = arr;
  }
  
  count() {
    return this.arr.length;
  }

  sum() {
    let sum = 0;
    this.arr.forEach(numb => {sum += numb});
    return sum;
  }

  average() {
    return this.sum() / this.count();
  }

  min() {
    return Math.min(...this.arr);
  }

  max() {
    return Math.max(...this.arr);
  }

  anotherAverage() {
    let sum = 0;
    this.arr.forEach(numb => { sum += 1 / numb});
    return this.count() / sum;
  }
}

const numbers = new NumbersAnalyzer([1, 2, 3, 4, 5, 6, 7]);

console.log(numbers.count());
console.log(numbers.sum());
console.log(numbers.average());
console.log(numbers.max());
console.log(numbers.anotherAverage());