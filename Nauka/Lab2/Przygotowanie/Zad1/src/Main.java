public class Main {
    public static void main(String[] args) {
        Person p1 = new Person("Jan", "Kowalski");
        Teacher p2 = new Teacher("Anna", "Kowalska");
        Student p3 = new Student("Piotr", "Nowak");
        Student p4 = new Student("Stachu", "Bożydar");

        Student[] students = new Student[]{p3, p4};

        Subject subject = new Subject("Math", p2, students);

        System.out.println(subject.getNames());
    }
}