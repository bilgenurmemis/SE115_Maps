import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CountryMap {
    private int cityCount;
    private City[] cities;
    private Route[] routes;


    public void readMap(String mapFile) throws FileNotFoundException {
        File file = new File(mapFile);
        int line = 0;

        if (!file.exists()) {
            System.out.println("Error:The file couldn't be found.");
            return;
        }
        Scanner sc = new Scanner(file);

        line++; //number of cities check
        if (sc.hasNextLine()) {
            String cityCountLine = sc.nextLine().trim();

            if (cityCountLine.matches("\\d+")) {
                cityCount = Integer.parseInt(cityCountLine);
            } else {
                System.out.println("Error Line: " + line + " - Invalid number of cities.");
                return;
            }
        }


        line++; // city names check
        if (sc.hasNextLine()) {
            String cityLine = sc.nextLine().trim();
            String[] cityNames = cityLine.split(" ");

            cities = new City[cityNames.length];
            for (int i = 0; i < cityNames.length; i++) {

                if (!cityNames[i].matches("[A-Za-z]+")) {
                    System.out.println("Error Line: " + line + " - Invalid city name.");
                    return;
                }
                cities[i] = new City(cityNames[i]);
            }
        } else {
            System.out.println("Error Line: " + line + " - Cities are missing.");
            return;

        }

        line++; //routes check
        if (sc.hasNextLine()) {
            String routeCountLine = sc.nextLine().trim();
            int routeCount;
            if (routeCountLine.matches("\\d+")) {
                routeCount = Integer.parseInt(routeCountLine);
            } else {
                System.out.println("Error Line: " + line + " - Invalid route count.");
                return;
            }
            routes = new Route[routeCount];

            for (int i = 0; i < routeCount; i++) {
                line++;
                if (sc.hasNextLine()) {
                    String routeLine = sc.nextLine().trim();
                    String[] routeParts = routeLine.split(" ");
                    if (routeParts.length != 3) {
                        System.out.println("Error Line: " + line + " - Invalid route format.");
                        return;
                    }

                    City fromCity = findCity(routeParts[0]);
                    City toCity = findCity(routeParts[1]);
                    if (fromCity == null || toCity == null) {
                        System.out.println("Error Line: " + line + " - Invalid city name in route.");
                        return;
                    }

                    if (!routeParts[2].matches("\\d+")) {
                        System.out.println("Error Line: " + line + " - Invalid distance value.");
                        return;
                    }
                    int distance = Integer.parseInt(routeParts[2]);

                    routes[i] = new Route(fromCity, toCity, distance);
                } else {
                    System.out.println("Error Line: " + line + " - Route is missing.");
                    return;
                }
            }
        } else {
            System.out.println("Error Line: " + line + " - Routes are missing.");
            return;
        }



        line++; //start&end cities check
            if (sc.hasNextLine()) {
                String startEndCities = sc.nextLine().trim();
                String[] citiesFromInput = startEndCities.split(" ");

                if (citiesFromInput.length != 2) {
                    System.out.println("Error Line: " + line + " - Invalid start and end city format.");
                    return;
                }
            } else {
                System.out.println("Error Line: " + line + " - Start and end cities are missing.");
                return;
            }


            System.out.println("File read is successful!");

        }
    public City findCity(String name) {
        for (City city : cities) {
            if (city.getName().equalsIgnoreCase(name)) {
                return city;
            }
        }
        return null;
    }


}



