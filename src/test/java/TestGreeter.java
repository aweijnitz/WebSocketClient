import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestGreeter {

    @Test
    public void greet() {
        String greeting = "Hello World!";
        Greeter g = new Greeter(greeting);
        assertEquals("Should return greeting from constructor as is.",greeting, g.greet());
    }
    
} 
