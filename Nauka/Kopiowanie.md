### 1. Konstruktor kopiujący (Copy Constructor)
Jest to podejście, które polega na stworzeniu specjalnego konstruktora w klasie, który przyjmuje inny obiekt tej samej klasy i kopiuje jego pola. To jest jeden z najprostszych sposobów na kopiowanie obiektów.

Przykład:
```java
public class City {
    private String name;

    // Zwykły konstruktor
    public City(String name) {
        this.name = name;
    }

    // Konstruktor kopiujący
    public City(City otherCity) {
        this.name = otherCity.name;
    }

    public String getName() {
        return name;
    }
}
```
Teraz możesz tworzyć kopię obiektu City w ten sposób:
```java
City originalCity = new City("Warsaw");
City copiedCity = new City(originalCity);

System.out.println(copiedCity.getName()); // "Warsaw"
```
Ten sposób pozwala na pełną kontrolę nad tym, jakie pola chcesz kopiować, a jakie nie, bez konieczności używania Cloneable.

### 2. Metoda kopiująca (Factory Method)
Zamiast tworzyć konstruktor kopiujący, możesz stworzyć metodę, która zwróci kopię obiektu. To często stosowane podejście w sytuacjach, gdy nie chcesz przeciążać konstruktora.

Przykład:
```java
public class City {
    private String name;

    public City(String name) {
        this.name = name;
    }

    // Metoda kopiująca
    public City copy() {
        return new City(this.name);
    }

    public String getName() {
        return name;
    }
}
```
Teraz kopiowanie wygląda tak:
```java
City originalCity = new City("Warsaw");
City copiedCity = originalCity.copy();

System.out.println(copiedCity.getName()); // "Warsaw"
```
To rozwiązanie daje większą elastyczność, jeśli na przykład chcesz w przyszłości dodać inne opcje kopiowania (np. głęboka kopia, zmodyfikowana kopia, itp.).

### 4. Kopiowanie "ręczne" (Manual Field Copying)
Jest to technika polegająca na ręcznym przypisywaniu każdego pola nowego obiektu, na wzór podejścia z konstruktorem kopiującym. Może być stosowane ad-hoc, np. w klasach, które nie mają wielu pól, i gdy nie chcesz implementować specjalnego konstruktora lub metody kopiującej.

Przykład:
```java
public class City {
    private String name;

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static City manualCopy(City original) {
        City copy = new City(original.name);
        return copy;
    }
}
```
W tym podejściu ręcznie tworzysz nowy obiekt i kopiujesz do niego pola. Jest to użyteczne przy prostych obiektach, ale dla bardziej złożonych klas może być trudne do utrzymania.

### 5. Kopiowanie głębokie (Deep Copy) za pomocą rekurencji
Jeśli obiekt zawiera inne obiekty jako pola (kompozycja), i zależy ci na głębokim kopiowaniu (czyli kopiowaniu również obiektów zależnych), możesz napisać metodę, która rekurencyjnie tworzy nowe kopie obiektów zależnych.

Przykład:
Załóżmy, że klasa Clock ma pole City, które także chcemy skopiować:
```java
public class Clock {
    private City city;

    public Clock(City city) {
        this.city = city;
    }

    public Clock deepCopy() {
        // Tworzymy nową instancję City, aby uniknąć dzielenia referencji
        City cityCopy = new City(this.city.getName());
        return new Clock(cityCopy);
    }
}
```
W tym przykładzie pole City jest również kopiowane (tworzymy nową instancję City), więc zmiany w skopiowanym obiekcie Clock nie wpłyną na oryginalny obiekt.
