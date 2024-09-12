public class Snake extends Animal {
    public Snake(String name) {
        super(name);
    }

    @Override
    public String sound() {
        return getName() + ": Ssssssssssssssssssssssssssss";
    }
}
