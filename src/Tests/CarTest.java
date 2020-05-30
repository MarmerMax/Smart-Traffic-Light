package Tests;

import Objects.Car.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    private Car car;

    @BeforeEach
    void init() {
        car = new Car();
    }

    @Test
    void getLength() {
        int type = car.getType();
        double length;
        switch (type) {
            case 1: {
                length = 4.7;
                break;
            }
            case 2: {
                length = 4.7;
                break;
            }
            case 3: {
                length = 5.2;
                break;
            }
            case 4: {
                length = 4.6;
                break;
            }
            case 5: {
                length = 4.8;
                break;
            }
            case 6: {
                length = 5.4;
                break;
            }
            default: {
                length = 0;
            }

        }
        assertEquals(length, car.getLength());
    }
}