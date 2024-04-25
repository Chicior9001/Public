import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        /*HashMap<String, Integer> mapa = new HashMap<>();
        mapa.put("papierzowa", 2137);
        mapa.put("inna", 1234);
        mapa.put("jedna", 4);


        System.out.println(mapa.get("papierzowa"));
        System.out.println(mapa.getOrDefault("dupa", 0000));*/

        Main main = new Main();
        Computation computation;

        if (main.shouldMultiply()) {
            computation = new Multiplication(); // zaimplementuj brakującą klasę
        }
        else {
            computation = new Addition(); // zaimplementuj brakującą klasę
        }

        double argument1 = main.getArgument();
        double argument2 = main.getArgument();

        double result = computation.compute(argument1, argument2);
        System.out.println("Wynik: " + result);
    }

    private boolean shouldMultiply() {
        System.out.println("Jaka operacje chcesz wykonac? Napisz M jesli ma to byc mnozenie i nacisnij <Enter>. Mazdy inny wybor oznacza dodawanie.");
        return scanner.next().equals("M");
    }

    private double getArgument() {
        System.out.print("Podaj liczbe: ");
        return scanner.nextDouble();
    }
}