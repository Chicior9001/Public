public class Cat extends Animal {
    public Cat(String name) {
        super(name);
    }

    @Override
    public String sound() {
        return getName() + ": Meow meow meow meow meow meow meow";
    }
}
