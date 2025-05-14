class Book {
  users = [];
  
  addUser(name, age, phone) {
    this.users.push({name, age, phone});
  }
  
  showUsers() {
    console.log("Wszyscy użytkownicy w książce: ");
    this.users.forEach(user => console.log(user));
  }
  
  findByName(name) {
    const user = this.users.find(user => user.name === name);
    console.log(user ? user : false);
  }
  
  findByPhone(phone) {
    const user = this.users.find(user => user.phone == phone);
    console.log(user ? user : false);
  }
  
  getCount() {
    console.log(this.users.length);
  }
}

const book = new Book();
book.addUser("Anna", 21, 111222333);
book.addUser("Krzysztof", 22, 444555666);
book.showUsers();
book.findByName("Anna");
book.findByName("Nie Anna");
book.findByPhone(111222333);
book.findByPhone("444555666");
book.findByPhone("777888999");
book.getCount();