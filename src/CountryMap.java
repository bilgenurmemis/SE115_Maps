import java.io.File;
import java.util.Scanner;

public class CountryMap {
    private int cityCount;
    private City[] cities;
    private Route[] routes;
    private String startCityName;
    private String endCityName;

    public void readMap(String mapFile) {
        File file = new File(mapFile);
        int line = 0;
        Scanner sc = null;
        boolean hasErrors = false;

        try {
            if (!file.exists()) {
                System.out.println("Error: The file couldn't be found.");
                return;
            }

            sc = new Scanner(file);

            // number of cities check
            line++;
            if (!sc.hasNextLine()) {
                System.out.println("Error Line: " + line + " - Missing number of cities.");
                hasErrors = true;
            } else {
                String cityCountLine = sc.nextLine().trim();
                if (!cityCountLine.matches("\\d+")) {
                    System.out.println("Error Line: " + line + " - Invalid number of cities format.");
                    hasErrors = true;
                } else {
                    cityCount = Integer.parseInt(cityCountLine);
                }
            }

            // city names check
            line++;
            if (!sc.hasNextLine()) {
                System.out.println("Error Line: " + line + " - Missing city names.");
                hasErrors = true;
            } else {
                String cityLine = sc.nextLine().trim();
                String[] cityNames = cityLine.split(" ");
                if (cityNames.length != cityCount) {
                    System.out.println("Error Line: " + line + " - City count mismatch. Expected: " + cityCount + ", Found: " + cityNames.length);
                    hasErrors = true;
                } else {
                    cities = new City[cityNames.length];
                    for (int i = 0; i < cityNames.length; i++) {
                        if (!cityNames[i].matches("[A-Za-z]+")) {
                            System.out.println("Error Line: " + line + " - Invalid city name: " + cityNames[i]);
                            hasErrors = true;
                        } else {
                            cities[i] = new City(cityNames[i]);
                        }
                    }
                }
            }

            // route count check
            line++;
            int routeCount = 0;
            if (!sc.hasNextLine()) {
                System.out.println("Error Line: " + line + " - Missing number of routes.");
                hasErrors = true;
            } else {
                String routeCountLine = sc.nextLine().trim();
                if (!routeCountLine.matches("\\d+")) {
                    System.out.println("Error Line: " + line + " - Invalid route count format.");
                    hasErrors = true;
                } else {
                    routeCount = Integer.parseInt(routeCountLine);
                    routes = new Route[routeCount];
                }
            }

            // routes check
            for (int i = 0; i < routeCount; i++) {
                line++;
                if (!sc.hasNextLine()) {
                    System.out.println("Error Line: " + line + " - Missing route definition.");
                    hasErrors = true;
                } else {
                    String routeLine = sc.nextLine().trim();
                    String[] routeParts = routeLine.split(" ");
                    if (routeParts.length != 3) {
                        System.out.println("Error Line: " + line + " - Invalid route format. Expected: <from> <to> <distance>");
                        hasErrors = true;
                    } else {
                        City fromCity = findCity(routeParts[0]);
                        City toCity = findCity(routeParts[1]);
                        if (fromCity == null || toCity == null) {
                            System.out.println("Error Line: " + line + " - Invalid city name in route.");
                            hasErrors = true;
                        }
                        if (!routeParts[2].matches("\\d+")) {
                            System.out.println("Error Line: " + line + " - Invalid distance value.");
                            hasErrors = true;
                        } else {
                            int distance = Integer.parseInt(routeParts[2]);
                            routes[i] = new Route(fromCity, toCity, distance);
                        }
                    }
                }
            }

            // start&end cities check
            line++;
            if (!sc.hasNextLine()) {
                System.out.println("Error Line: " + line + " - Missing start and end cities.");
                hasErrors = true;
            } else {
                String startEndCities = sc.nextLine().trim();
                String[] citiesFromInput = startEndCities.split(" ");
                if (citiesFromInput.length != 2) {
                    System.out.println("Error Line: " + line + " - Invalid start and end city format.");
                    hasErrors = true;
                } else {
                    startCityName = citiesFromInput[0];
                    endCityName = citiesFromInput[1];
                    if (findCity(startCityName) == null || findCity(endCityName) == null) {
                        System.out.println("Error Line: " + line + " - Start or end city not found.");
                        hasErrors = true;
                    }
                }
            }

            if (!hasErrors) {
                System.out.println("File read is successful!");
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    }



    public City findCity(String name) {
        for (City city : cities) {
            if (city.getName().equalsIgnoreCase(name)) {
                return city;
            }
        }
        return null;
    }
    public int getCityCount() {
        return cities.length;
    }
    public int getCityIndex(City city) {
        for (int i = 0; i < cities.length; i++) {
            if (cities[i].equals(city)) {
                return i;
            }
        }
        return -1;
    }
    public City getCityByIndex(int index) {
        if (index >= 0 && index < cities.length) {
            return cities[index];
        }
        return null;
    }
    public Route[] getRoutesFromCity(City city) {
        int routeCount = 0;
        for (Route route : routes) {
            if (route.getStartCity().equals(city)) {
                routeCount++;
            }
        }
        Route[] cityRoutes = new Route[routeCount];
        int index = 0;

        for (Route route : routes) {
            if (route.getStartCity().equals(city)) {
                cityRoutes[index++] = route;
            }
        }

        return cityRoutes;
    }
    public String getStartCityName() {
        return startCityName;
    }

    public String getEndCityName() {
        return endCityName;
    }



}





