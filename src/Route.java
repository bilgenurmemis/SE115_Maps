public class Route {

        private City startCity;
        private City endCity;
        private int distance;

        public Route(City startCity, City endCity, int distance) {
            this.startCity = startCity;
            this.endCity = endCity;
            this.distance = distance;
        }

        public City getStartCity() {
            return startCity;
        }

        public City getEndCity() {
            return endCity;
        }

        public int getDistance() {
            return distance;
        }
}



