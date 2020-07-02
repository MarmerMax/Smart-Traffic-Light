package Objects.Car;

public class CarFactory {

    public static Car createCar(CarTypes type) {
        Car toReturn = null;
        switch (type) {
            case Police:
                toReturn = new PoliceCar();
                break;
            case Ambulance:
                toReturn = new AmbulanceCar();
                break;
            case Taxi:
                toReturn = new TaxiCar();
                break;
            case Usual:
                toReturn = new UsualCar();
                break;
            case Track:
                toReturn = new TrackCar();
                break;
            default:
                throw new IllegalArgumentException("Wrong doughnut type:" + type);
        }
        return toReturn;
    }
}
