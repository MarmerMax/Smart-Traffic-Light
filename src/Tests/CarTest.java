package Tests;

import Objects.Car.*;
import Tools.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    private Car car;

    @BeforeEach
    void init() {
        car = CarFactory.createCar(Utils.createRandomCarType());
    }

    @Test
    void getLength() {
        double length;
        if (car instanceof PoliceCar) {
            length = 4.8;
        } else if (car instanceof AmbulanceCar) {
            length = 5.2;
        } else if (car instanceof TaxiCar) {
            length = 4.8;
        } else if (car instanceof UsualCar) {
            length = 4.7;
        } else {
            length = 5.4;
        }

        assertEquals(length, car.getLength());
    }
}