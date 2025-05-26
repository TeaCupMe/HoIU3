import org.apache.maven.surefire.shared.lang3.ObjectUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class UITest {

    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @org.junit.Test
    public void getIntInput() {
        InputStream inputStream = UITest.class.getResourceAsStream("./testData/UITest_getIntInput.txt");
        UI ui = new UI(PrintStream.nullOutputStream(), inputStream);

        int result = ui.getIntInput("testData", 0, 10);
        assertFalse(result >= 0 && result <= 10);

        result = ui.getIntInput("testData", 0, 10);
        assertTrue(result >= 0 && result <= 10);

        result = ui.getIntInput("testData", 0, 10);
        assertFalse(result >= 0 && result <= 10);

        result = ui.getIntInput("testData", 0, 10);
        assertFalse(result >= 0 && result <= 10);

        result = ui.getIntInput("testData", 0, 10);
        assertTrue(result >= 0 && result <= 10);

        result = ui.getIntInput("testData", 0, 10);
        assertTrue(result >= 0 && result <= 10);

        result = ui.getIntInput("testData", 0, 10);
        assertTrue(result >= 0 && result <= 10);

        result = ui.getIntInput("testData", 0, 10);
        assertFalse(result >= 0 && result <= 10);

        result = ui.getIntInput("testData", 0, 10);
        assertFalse(result >= 0 && result <= 10);



//        ui.ge
    }

    @org.junit.Test
    public void testGetIntInput() {
    }
}