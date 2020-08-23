package Objects.Car;

public class PoliceCar extends Car {

    public PoliceCar(){
        car_type = Math.random() < 0.5 ? 1 : 2;
        length = 4.8;
        width = 1.8;

        acceleration = 2;
        deceleration = 4.5;
        max_speed = 70;
    }
}
