import java.io.*;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name;
    private LocalDate birthDate;
    private LocalDate deathDate;
    // private String motherName;
    // private String fatherName;


    public Person(String name, LocalDate birthDate, LocalDate deathDate) {
        this.name = name;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
    }

    public static Person fromCsvLine(String row) {
        String[] fields = row.split(",");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthDate = LocalDate.parse(fields[1], formatter);
        LocalDate deathDate = !fields[2].equals("") ? LocalDate.parse(fields[2], formatter) : null;

        return new Person(fields[0], birthDate, deathDate);
    }

    public static List<Person> fromCsv(String path) throws IOException {
        List<Person> persons = new ArrayList<>();
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            Person person = Person.fromCsvLine(line);
            persons.add(person);
        }

        return persons;
    }

    @Override
    public String toString() {
        return  "\n" + "Person{" +
                "name='" + name + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", deathDate='" + deathDate + '\'' +
                '}';
    }
}
