import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {
    //--Проверить, что метод выполняется не дольше 22 секунд
    @Test
    @Timeout(value = 22)
    @Disabled
    public void main_ExecutionTimeLessThan22Seconds() throws Exception {
        // Запускаем метод main
        Main.main(null);
    }
}
