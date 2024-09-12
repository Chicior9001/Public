public class Main {
    public static void main(String[] args) {
        Cat cat = new Cat("Kot");
        Cow cow = new Cow("Krowa");
        Snake snake = new Snake("Wąż");
        System.out.println(cat.sound());
        System.out.println(cow.sound());
        System.out.println(snake.sound());
    }
}