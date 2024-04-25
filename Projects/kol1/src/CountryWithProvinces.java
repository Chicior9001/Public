import java.time.LocalDate;

public class CountryWithProvinces extends Country{
    private Country[] provinces;

    public CountryWithProvinces(String name, Country[] provinces) {
        super(name);
        this.provinces = provinces;
    }

    public LocalDate[] getDate() {
        if(provinces.length == 0) {
            return new LocalDate[0];
        } else {
            CountryWithoutProvinces temp = (CountryWithoutProvinces) provinces[0];
//            return temp.statistics.stream().map(CountryWithoutProvinces.DailyStatistics::getDate).toArray(LocalDate[]::new);
            return temp.stats.keySet().toArray(new LocalDate[0]);
        }
    }

    @Override
    public int getConfirmedCases(LocalDate date) {
        int totalCases = 0;
        for(Country province : provinces) {
            totalCases +=  province.getConfirmedCases(date);
        }
        return totalCases;
    }

    @Override
    public int getDeaths(LocalDate date) {
        int totalDeaths = 0;
        for(Country province : provinces) {
            totalDeaths += province.getDeaths(date);
        }
        return totalDeaths;
    }
}