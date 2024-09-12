public class Cow extends Animal {
    public Cow(String name) {
        super(name);
    }

    @Override
    public String sound() {
        return getName() + ": Moooooooooooooooooo";
    }
}
