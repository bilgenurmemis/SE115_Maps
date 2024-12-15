public class WayFinder {
    private CountryMap map;
    private int[] distances;
    private boolean[] visited;
    private City[] previousCities;

    public WayFinder(CountryMap map) {
        this.map = map;
        this.distances = new int[map.getCityCount()];
        this.visited = new boolean[map.getCityCount()];
        this.previousCities = new City[map.getCityCount()];
    }

    public void findFastestRoute(City startCity, City endCity) {
        initializeDistances(startCity);

        for (int i = 0; i < map.getCityCount(); i++) {
            City currentCity = getNearestUnvisitedCity();
            if (currentCity == null) break;

            visited[map.getCityIndex(currentCity)] = true;
            updateNeighborDistances(currentCity);
        }

        printFastestRoute(startCity, endCity);
    }

    private void initializeDistances(City startCity) {
        for (int i = 0; i < distances.length; i++) {
            distances[i] = Integer.MAX_VALUE;
            previousCities[i] = null;
        }
        distances[map.getCityIndex(startCity)] = 0;
    }

    private City getNearestUnvisitedCity() {
        int minDistance = Integer.MAX_VALUE;
        City nearestCity = null;

        for (int i = 0; i < distances.length; i++) {
            if (!visited[i] && distances[i] < minDistance) {
                minDistance = distances[i];
                nearestCity = map.getCityByIndex(i);
            }
        }

        return nearestCity;
    }

    private void updateNeighborDistances(City currentCity) {
        int currentCityIndex = map.getCityIndex(currentCity);
        Route[] neighbors = map.getRoutesFromCity(currentCity);

        for (Route route : neighbors) {
            City neighborCity = route.getEndCity();
            int neighborIndex = map.getCityIndex(neighborCity);

            if (!visited[neighborIndex]) {
                int newDistance = distances[currentCityIndex] + route.getDistance();
                if (newDistance < distances[neighborIndex]) {
                    distances[neighborIndex] = newDistance;
                    previousCities[neighborIndex] = currentCity;
                }
            }
        }
    }

    private void printFastestRoute(City startCity, City endCity) {
        if (distances[map.getCityIndex(endCity)] == Integer.MAX_VALUE) {
            System.out.println("No route found from " + startCity.getName() + " to " + endCity.getName());
            return;
        }

        StringBuilder route = new StringBuilder();
        City currentCity = endCity;
        while (currentCity != null) {
            route.insert(0, currentCity.getName() + (!route.isEmpty() ? " -> " : ""));
            currentCity = previousCities[map.getCityIndex(currentCity)];
        }

        System.out.println("Fastest Way: " + route);
        System.out.println("Total Time: " + distances[map.getCityIndex(endCity)] + " min");
    }
}

