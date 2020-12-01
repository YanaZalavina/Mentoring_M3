import java.io.IOException;

public class Runner {
    public static void main(String[] args) throws IOException {
        AirportService airportService = new AirportService();
        airportService.startAirportService();
    }
}
