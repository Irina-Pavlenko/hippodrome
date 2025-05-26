import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {

    @Test
    public void nullNameException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
    }

    @Test
    public void nullNameMessage() {
        try {
            new Horse(null,1,1);
            fail();
        }catch (IllegalArgumentException e){
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ","\t\t", "\n\n\n\n\n" })
    public void blancNameException(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 1));
    }
    @ParameterizedTest
    @ValueSource(strings = {"", "  ","\t\t", "\n\n\n\n\n" })
    public void blancNameMessage(String name){
        try {
            new Horse(name, 1, 1);
            fail();
        }catch (IllegalArgumentException e){
            assertEquals("Name cannot be blank.", e.getMessage());
        }
    }

    @Test
    public void negativeSpeedException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Cherry", -1, 1));
    }

    @Test
    public void negativeSpeedMessage() {
        try {
            new Horse("Cherry", -1, 1);
            fail();
        }catch (IllegalArgumentException e){
            assertEquals("Speed cannot be negative.", e.getMessage());
        }
    }

    @Test
    public void negativeDistanceException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Blaze", 2, -4));
    }

    @Test
    public void negativeDistanceMessage() {
        try {
            new Horse("Blaze", 2, -4);
            fail();
        }catch (IllegalArgumentException e){
            assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }
//--Проверить, что метод возвращает строку, которая была передана первым параметром в конструктор
    @Test
    public void getName() {
        Horse horse = new Horse("Lobster",1,1);
        assertEquals("Lobster", horse.getName());
    }
    //--Проверить, что метод возвращает число, которое было передано вторым параметром в конструктор

    @Test
    public void getSpeed() {
        double expectedSpeed = 324;
        Horse horse = new Horse("Lobster",expectedSpeed,1);
        assertEquals(expectedSpeed, horse.getSpeed());
    }
    //--Проверяет, что метод возвращает число, которое было передано третьим параметром в конструктор

    @Test
    public void getDistance() {
        double expectedDistance = 254;
        Horse horse = new Horse("Lobster", 1, expectedDistance);
        assertEquals(expectedDistance, horse.getDistance());
    }
    //--Проверяет, что метод возвращает ноль, если объект был создан с помощью конструктора с двумя параметрами

    @Test
    public void xeroDistanceByDefault() {
        Horse horse = new Horse("Lobster",1);
        assertEquals(0,horse.getDistance());
    }
//--Проверяет, что метод вызывает внутри метод getRandomDouble с параметрами 0.2 и 0.9.м
    @Test
    void moveUsesGetRandom() {
        try(MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            new Horse("Pegasus", 33,232).move();

            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }
//--Проверяет, что метод присваивает дистанции значение высчитанное по формуле: distance + speed * getRandomDouble(0.2, 0.9)
//--Для этого нужно замокать getRandomDouble, чтобы он возвращал определенные значения, которые нужно задать параметризовав тест.
    @ParameterizedTest
    @CsvSource({"0.2, 10.0, 0.0, 2.0",//0.0+10.0*0.2 = 2.0
                "0.5, 10.0, 5.0, 10.0",//5.0+10.0*0.5 = 10.0
                "0.9, 3.0, 7.0, 9.7"})//7.0+3.0*0.9 = 9.7
    public void moveCalculatesDistanceCorrectly(
            double randomValue, double speed, double initialDistance, double expectedDistance) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);

            Horse horse = new Horse("TestHorse", speed, initialDistance);
            horse.move();

            assertEquals(expectedDistance,horse.getDistance(),0.0001);
        }
    }
}
