import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String csvLine = "Marek Kowalski,15.05.1899,25.06.1957,,";
        Person person = Person.fromCsvLine(csvLine);
        System.out.println(person);

        //System.out.println(person.fromCsv("E:\\Studia\\Semestr II\\Programowanie obiektowe\\Nauka\\Lab4\\Laboratoria4\\family.csv"));
    }
}