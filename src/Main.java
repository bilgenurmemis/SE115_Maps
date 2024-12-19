public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter the map file name as a command-line argument.");
            return;
        }

        String fileName = args[0];
        CountryMap countryMap = new CountryMap();

        countryMap.readMap(fileName);

        String startCityName = countryMap.getStartCityName();
        String endCityName = countryMap.getEndCityName();

        City startCity = countryMap.findCity(startCityName);
        City endCity = countryMap.findCity(endCityName);

        if (startCity != null && endCity != null) {
            WayFinder wayFinder = new WayFinder(countryMap);
            wayFinder.findFastestRoute(startCity, endCity);
        } else {
            System.out.println("Error: Start or end city not found in the map.");
        }
    }
}
