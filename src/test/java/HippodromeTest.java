import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class HippodromeTest {

    //--Проверить, что при передаче в конструктор null, будет выброшено IllegalArgumentException
    @Test
    public void constructor_ThrowsIllegalArgumentException_WhenHorsesIsNull() {
        assertThrows(IllegalArgumentException.class,() -> new Hippodrome(null));
    }

    //--Проверить, что при передаче в конструктор null, выброшенное исключение будет содержать сообщение "Horses cannot be null."
    @Test
    public void constructor_ExceptionMessage_WhenHorsesIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }
    
    //--Проверить, что при передаче в конструктор пустого списка, будет выброшено IllegalArgumentException
    @Test
    public void constructor_ThrowsIllegalArgumentException_WhenHorsesIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(Collections.emptyList()));
    }

    //--Проверить, что при передаче в конструктор пустого списка, выброшенное исключение будет содержать сообщение "Horses cannot be empty."
    @Test
    public void constructor_ExceptionMessage_WhenHorsesIsEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(Collections.emptyList()));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    //--Проверить, что метод возвращает список, который содержит те же объекты и в той же последовательности, 
    // что и список который был передан в конструктор. 
    // При создании объекта Hippodrome передай в конструктор список из 30 разных лошадей
    @Test
    public void getHorsesTest() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("" + i, i, i));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    //--Проверить, что метод вызывает метод move у всех лошадей.
    // При создании объекта Hippodrome передай в конструктор список из 50 моков лошадей и воспользуйся методом verify
    @Test
    public void moveTest() {
        // Создаем список из 50 моков лошадей
        List<Horse> mockHorses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mockHorses.add(mock(Horse.class));
        }
        // Создаем Hippodrome с нашими моками
        Hippodrome hippodrome = new Hippodrome(mockHorses);

        // Вызываем тестируемый метод
        hippodrome.move();

        // Проверяем, что метод move() вызван у каждой лошади
        mockHorses.forEach(horse -> Mockito.verify(horse).move());
    }

    //--Проверить, что метод возвращает лошадь с самым большим значением distance
    @Test
    public void getWinnerTest() {
        // Создаем тестовых лошадей с разными дистанциями
        Horse horse1 = new Horse("Horse1", 4, 40);
        Horse horse2 = new Horse("Horse2", 7, 90);
        Horse horse3 = new Horse("Horse3", 8, 20);
        Horse horse4 = new Horse("Horse4", 2, 70);

        // Создаем Hippodrome с нашими лошадьми
        Hippodrome hippodrome = new Hippodrome(List.of(horse1,horse2,horse3,horse4));

        // Проверяем, что побеждает лошадь с максимальной дистанцией
        assertSame(horse2,hippodrome.getWinner());
    }
}
