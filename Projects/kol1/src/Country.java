import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public abstract class Country {
    static private String pathCases;
    static private String pathDeaths;

    private final String name;

    public Country(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static void setFiles(String pathCases, String pathDeaths) throws FileNotFoundException {
        File pathCasesFile = new File(pathCases);
        File pathDeathsFile = new File(pathDeaths);

        if(!pathCasesFile.exists() || pathCasesFile.isDirectory()) {
            throw new FileNotFoundException(pathCases);
        }

        if(!pathDeathsFile.exists() || pathDeathsFile.isDirectory()) {
            throw new FileNotFoundException(pathDeaths);
        }

        Country.pathCases = pathCases;
        Country.pathDeaths = pathDeaths;
    }

    public static Country fromCsv(String country) throws CountryNotFoundException {
        try(
            BufferedReader casesReader = new BufferedReader(new FileReader(pathCases));
            BufferedReader deathsReader = new BufferedReader(new FileReader(pathDeaths));
        ) {
            String currentCases;
            String currentDeaths;

            currentCases = casesReader.readLine();
            currentDeaths = deathsReader.readLine();

            CountryColumns casesColumns = getCountryColumns(currentCases, country);
            CountryColumns deathsColumns = getCountryColumns(currentDeaths, country);

            if(casesColumns.columnCount > 1) {
                CountryWithProvinces countryWithProvinces;
                deathsReader.readLine();

                String[] casesProvinces = casesReader.readLine().split(";");
                ArrayList<Country> provinces = new ArrayList<>();

                while((currentCases = casesReader.readLine()) != null && (currentDeaths = deathsReader.readLine()) != null) {
                    String[] casesData = currentCases.split(";");
                    String[] deathsData = currentDeaths.split(";");

                    for(int i = 0; i < casesColumns.columnCount; i++) {
                        LocalDate date = LocalDate.parse(casesData[0], DateTimeFormatter.ofPattern("M/d/yy"));

                        int index = casesColumns.firstColumnIndex + i;
                        int cases = Integer.parseInt(casesData[index]);
                        int deaths = Integer.parseInt(deathsData[index]);

                        CountryWithoutProvinces province = new CountryWithoutProvinces(casesProvinces[index]);
                        province.addDailyStatistics(date, cases, deaths);
                        provinces.add(province);
                    }
                }

                Country[] temp = new Country[casesColumns.columnCount];
                countryWithProvinces = new CountryWithProvinces(country, provinces.toArray(temp));
                return countryWithProvinces;
            } else {
                CountryWithoutProvinces countryWithoutProvinces = new CountryWithoutProvinces(country);
                casesReader.readLine();
                deathsReader.readLine();

                while((currentCases = casesReader.readLine()) != null && (currentDeaths = deathsReader.readLine()) != null) {
                    String[] casesData = currentCases.split(";");
                    String[] deathsData = currentDeaths.split(";");

                    countryWithoutProvinces.addDailyStatistics(
                            LocalDate.parse(casesData[0], DateTimeFormatter.ofPattern("M/d/yy")),
                            Integer.parseInt(casesData[casesColumns.firstColumnIndex]),
                            Integer.parseInt(deathsData[deathsColumns.firstColumnIndex])
                    );
                }
                return countryWithoutProvinces;
            }
        } catch (IOException | CountryNotFoundException e) {
        throw new CountryNotFoundException(country);
        }
        // chciałbym tak ale nie mogę bo musiał bym jeszcze dodać wyjątek w przeciążeniu czego nie ma w poleceniu
        /*catch (IOException e) {
            throw new IOException(e.getMessage());
        } catch (CountryNotFoundException e) {
            throw new CountryNotFoundException(country);
        }*/
    }

    // dobra nie wiem jak to zrobić
    /* public static Country[] fromCsv(String[] countries) {
        try(
                BufferedReader casesReader = new BufferedReader(new FileReader(pathCases));
                BufferedReader deathsReader = new BufferedReader(new FileReader(pathDeaths));
        ) {
            String currentCases;
            String currentDeaths;

            currentCases = casesReader.readLine();
            currentDeaths = deathsReader.readLine();

            CountryColumns casesColumns = getCountryColumns(currentCases, country);
            CountryColumns deathsColumns = getCountryColumns(currentDeaths, country);

            if(casesColumns.columnCount > 1) {
                CountryWithProvinces countryWithProvinces;
                deathsReader.readLine();

                String[] casesProvinces = casesReader.readLine().split(";");
                ArrayList<Country> provinces = new ArrayList<>();

                while((currentCases = casesReader.readLine()) != null && (currentDeaths = deathsReader.readLine()) != null) {
                    String[] casesData = currentCases.split(";");
                    String[] deathsData = currentDeaths.split(";");

                    for(int i = 0; i < casesColumns.columnCount; i++) {
                        LocalDate date = LocalDate.parse(casesData[0], DateTimeFormatter.ofPattern("M/d/yy"));

                        int index = casesColumns.firstColumnIndex + i;
                        int cases = Integer.parseInt(casesData[index]);
                        int deaths = Integer.parseInt(deathsData[index]);

                        CountryWithoutProvinces province = new CountryWithoutProvinces(casesProvinces[index]);
                        province.addDailyStatistics(date, cases, deaths);
                        provinces.add(province);
                    }
                }

                Country[] temp = new Country[casesColumns.columnCount];
                countryWithProvinces = new CountryWithProvinces(country, provinces.toArray(temp));
                return countryWithProvinces;
            } else {
                CountryWithoutProvinces countryWithoutProvinces = new CountryWithoutProvinces(country);
                casesReader.readLine();
                deathsReader.readLine();

                while((currentCases = casesReader.readLine()) != null && (currentDeaths = deathsReader.readLine()) != null) {
                    String[] casesData = currentCases.split(";");
                    String[] deathsData = currentDeaths.split(";");

                    countryWithoutProvinces.addDailyStatistics(
                            LocalDate.parse(casesData[0], DateTimeFormatter.ofPattern("M/d/yy")),
                            Integer.parseInt(casesData[casesColumns.firstColumnIndex]),
                            Integer.parseInt(deathsData[deathsColumns.firstColumnIndex])
                    );
                }
                return countryWithoutProvinces;
            }
        } catch (IOException | CountryNotFoundException e) {}
        return null;
    }*/
    // ale chatGPT wypluł coś takiego
    public static Country[] fromCsv(String[] countries) {
        ArrayList<Country> countryList = new ArrayList<>();

        for(String country : countries) {
            try {
                Country countryObiekt = fromCsv(country);
                countryList.add(countryObiekt);
            } catch (CountryNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }

        return countryList.toArray(new Country[0]);
    }

    /*private*/public static class CountryColumns {
        public final int firstColumnIndex;
        public final int columnCount;

        public CountryColumns(int firstColumnIndex, int columnCount) {
            this.firstColumnIndex = firstColumnIndex;
            this.columnCount = columnCount;
        }
    }

   /*private*/public static CountryColumns getCountryColumns(String firstRow, String country) throws CountryNotFoundException {
       String[] columns = firstRow.split(";");
       int startIndex = -1;
       for (int i = 0; i < columns.length; i++) {
           if (columns[i].equalsIgnoreCase(country)) {
               startIndex = i;
               break;
           }
       }

       if(startIndex == -1) {
           throw new CountryNotFoundException(country);
       }

       int columnCount = 1;
       for (int i = startIndex + 1; i < columns.length; i++) {
           if (columns[i].equalsIgnoreCase(country)) {
               columnCount++;
           }
       }

       return new CountryColumns(startIndex, columnCount);
   }

   public abstract int getConfirmedCases(LocalDate date);
   public abstract int getDeaths(LocalDate date);

   public static void sortByDeaths(List<Country> countries, LocalDate startDate, LocalDate endDate) {
       //for(LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
           //LocalDate finalDate = date;
           countries.sort(new Comparator<Country>() {
               @Override
               public int compare(Country o1, Country o2) {
                   return Integer.compare(o1.getDeaths(startDate), o2.getDeaths(endDate));
               }
           });
       //}
       for(Country country : countries) {
           System.out.println(country);
       }
   }

    public static void saveToDataFile(String path, ArrayList<Country> countries) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        for(Country country : countries) {
            if(country instanceof CountryWithoutProvinces countryWithoutProvinces) {
                for(CountryWithoutProvinces.DailyStatistics stats : countryWithoutProvinces.statistics) {
                    String date = stats.getDate().format(DateTimeFormatter.ofPattern("d.MM.yy"));
                    writer.write(date + "\t" + stats.getCases() + "\t" + stats.getDeaths() + "\n");
                }
            } else if(country instanceof CountryWithProvinces countryWithProvinces) {
                for(LocalDate date : countryWithProvinces.getDate()) {
                    String dateFormatted = date.format(DateTimeFormatter.ofPattern("d.MM.yy"));
                    writer.write(dateFormatted + "\t" + countryWithProvinces.getConfirmedCases(date) + "\t" + countryWithProvinces.getDeaths(date) + "\n");
                    System.out.println(Arrays.toString(countryWithProvinces.getDate()));
                }
            }
        }
        writer.close();
        /*int counter = 0;
        for (int i = 0; i < points.size(); i++) {
            Point pa = points.get(i);
            Point pb = points.get(i + 1);

        }*/
    }
}
