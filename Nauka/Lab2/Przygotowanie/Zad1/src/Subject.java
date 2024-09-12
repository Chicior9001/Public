public class Subject {
    private String title;
    private Teacher teacher;
    private Student[] students;

    public Subject(String title, Teacher teacher, Student[] students) {
        this.title = title;
        this.teacher = teacher;
        this.students = students;
    }

    public String getNames() {
        String studentNames = "";
        for (int i = 0; i < students.length; i++) {
            studentNames += students[i].getName() + ", ";
        }
        return "Teacher: " + teacher.getName() + ", Students: " + studentNames;
    }
}