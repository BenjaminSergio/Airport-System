import com.solvd.airport.systens.AirportSystem;

import static com.solvd.airport.Main.LOGGER;

public class Test {
    public static void main(String[] args) {
        AirportSystem.functionalityTest();
        LOGGER.warn("Compilation success!, view more in debug.log");
    }
}
