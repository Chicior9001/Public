import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws CountryNotFoundException, IOException {
        /*Country.CountryColumns test = Country.getCountryColumns(
                "Country/Region;Afghanistan;Albania;Algeria;Andorra;Angola;Antigua and Barbuda;Argentina;Armenia;Australia;Australia;Australia;Australia;Australia;Australia;Australia;Australia;Austria;Azerbaijan;Bahamas;Bahrain;Bangladesh;Barbados;Belarus;Belgium;Belize;Benin;Bhutan;Bolivia;Bosnia and Herzegovina;Botswana;Brazil;Brunei;Bulgaria;Burkina Faso;Burma;Burundi;Cabo Verde;Cambodia;Cameroon;Canada;Canada;Canada;Canada;Canada;Canada;Canada;Canada;Canada;Canada;Canada;Canada;Canada;Canada;Canada;Canada;Central African Republic;Chad;Chile;China;China;China;China;China;China;China;China;China;China;China;China;China;China;China;China;China;China;China;China;China;China;China;China;China;China;China;China;China;China;China;China;China;China;Colombia;Comoros;Congo (Brazzaville);Congo (Kinshasa);Costa Rica;Cote d'Ivoire;Croatia;Cuba;Cyprus;Czechia;Denmark;Denmark;Denmark;Diamond Princess;Djibouti;Dominica;Dominican Republic;Ecuador;Egypt;El Salvador;Equatorial Guinea;Eritrea;Estonia;Eswatini;Ethiopia;Fiji;Finland;France;France;France;France;France;France;France;France;France;France;France;France;Gabon;Gambia;Georgia;Germany;Ghana;Greece;Grenada;Guatemala;Guinea;Guinea-Bissau;Guyana;Haiti;Holy See;Honduras;Hungary;Iceland;India;Indonesia;Iran;Iraq;Ireland;Israel;Italy;Jamaica;Japan;Jordan;Kazakhstan;Kenya;Korea, South;Kosovo;Kuwait;Kyrgyzstan;Laos;Latvia;Lebanon;Lesotho;Liberia;Libya;Liechtenstein;Lithuania;Luxembourg;MS Zaandam;Madagascar;Malawi;Malaysia;Maldives;Mali;Malta;Marshall Islands;Mauritania;Mauritius;Mexico;Micronesia;Moldova;Monaco;Mongolia;Montenegro;Morocco;Mozambique;Namibia;Nepal;Netherlands;Netherlands;Netherlands;Netherlands;Netherlands;New Zealand;Nicaragua;Niger;Nigeria;North Macedonia;Norway;Oman;Pakistan;Panama;Papua New Guinea;Paraguay;Peru;Philippines;Poland;Portugal;Qatar;Romania;Russia;Rwanda;Saint Kitts and Nevis;Saint Lucia;Saint Vincent and the Grenadines;Samoa;San Marino;Sao Tome and Principe;Saudi Arabia;Senegal;Serbia;Seychelles;Sierra Leone;Singapore;Slovakia;Slovenia;Solomon Islands;Somalia;South Africa;South Sudan;Spain;Sri Lanka;Sudan;Suriname;Sweden;Switzerland;Syria;Taiwan*;Tajikistan;Tanzania;Thailand;Timor-Leste;Togo;Trinidad and Tobago;Tunisia;Turkey;US;Uganda;Ukraine;United Arab Emirates;United Kingdom;United Kingdom;United Kingdom;United Kingdom;United Kingdom;United Kingdom;United Kingdom;United Kingdom;United Kingdom;United Kingdom;United Kingdom;United Kingdom;Uruguay;Uzbekistan;Vanuatu;Venezuela;Vietnam;West Bank and Gaza;Yemen;Zambia;Zimbabwe",
                ""
        );
        System.out.println(test.firstColumnIndex + " " + test.columnCount);*/


        Country.setFiles("D:\\Studia\\Semestr II\\Programowanie obiektowe\\Projects\\kol1\\confirmed_cases.csv", "D:\\Studia\\Semestr II\\Programowanie obiektowe\\Projects\\kol1\\deaths.csv");


        LocalDate startDate = LocalDate.parse("2020-01-23");
        LocalDate endDate = LocalDate.parse("2020-02-22");
        List<Country> countries = List.of(Country.fromCsv("Australia"));
        Country.sortByDeaths(countries, startDate, endDate);
        Country.saveToDataFile("./test.csv", new ArrayList<>(countries));

    }
}