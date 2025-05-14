const text = {
  check(txt, word) {
    return txt.includes(word) ? true : false;
  },
  
  getCount(txt) {
    return txt.length;
  },
  
  getWordsCount(txt) {
    return txt.trim().split(" ").length;
  },
  
  setCapitalize(txt) {
    return txt.trim().split(" ").map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(' ');
  },
  
  setMix(txt) {
    return txt.split('').map((char, index) =>
      (index % 2 !== 0) ? char.toUpperCase() : char.toLowerCase())
    .join('');
  },
  
  generateRandom(lng) {
    return Array.from({length: lng}, _ =>
      String.fromCharCode((Math.floor(Math.random() * 25) + 97)))
    .join('');
  }
}

console.log(text.check("ala ma kota", "kota"));
console.log(text.getCount("ala ma kota"));
console.log(text.getWordsCount("Ala ma kota"));
console.log(text.setCapitalize("ala ma kota"));
console.log(text.setMix("ala ma kota"));
console.log(text.generateRandom(10));