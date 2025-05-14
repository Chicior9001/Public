class Product {
  constructor(name, price) {
    this.name = name;
    this.price = price;
  }
};

class ProductInCart {
  constructor(product, quantity) {
    this.product = product;
    this.quantity = quantity;
  }
};

function calculatePrice(cart) {
  let sum = 0;
  cart.forEach(product => sum += product.product.price * product.quantity);
  return sum;
}

let product1 = new ProductInCart(new Product("sałata", 8), 1);
let product2 = new ProductInCart(new Product("bułka", 0.50), 10);
let product3 = new ProductInCart(new Product("snickers", 2.25), 4);

let cart = [product1, product2, product3];

console.log(calculatePrice(cart));