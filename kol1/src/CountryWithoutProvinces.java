import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class CountryWithoutProvinces extends Country{
    public CountryWithoutProvinces(String name) {
        super(name);
    }


    public HashMap<LocalDate, DailyDeathsAndCases> stats = new HashMap<>();


    public record DailyDeathsAndCases(int cases, int deaths) {}

    public ArrayList<DailyStatistics> statistics = new ArrayList<>();

    public void addDailyStatistics(LocalDate date, int cases, int deaths) {
        statistics.add(new DailyStatistics(date, cases, deaths));
        stats.put(date, new DailyDeathsAndCases(cases, deaths));
    }

    public class DailyStatistics{
        private LocalDate date;
        private int cases;
        private int deaths;

        public DailyStatistics(LocalDate date, int cases, int deaths) {
            this.date = date;
            this.cases = cases;
            this.deaths = deaths;
        }

        public LocalDate getDate() {
            return date;
        }

        public int getCases() {
            return cases;
        }
        public int getDeaths() {
            return deaths;
        }
    }

    @Override
    public int getConfirmedCases(LocalDate date) {
        for(DailyStatistics stats : statistics) {
            if(stats.getDate() == date) {
                return stats.getCases();
            }
        }
        return this.stats.get(date).cases;
    }

    @Override
    public int getDeaths(LocalDate date) {
        for(DailyStatistics stats : statistics) {
            if(stats.getDate() == date) {
                return stats.getDeaths();
            }
        }
        return 0;
    }
}
