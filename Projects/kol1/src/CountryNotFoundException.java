public class CountryNotFoundException extends Exception{
    public CountryNotFoundException(String countryName) {
        super(countryName);
    }
}