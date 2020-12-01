import Planes.ExperimentalPlane;
import Planes.MilitaryPlane;
import Planes.PassengerPlane;
import Planes.Plane;
import models.ClassificationLevel;
import models.ExperimentalTypes;
import models.MilitaryType;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AirportService {

    public static List<Plane> planes = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(AirportService.class);

    public static String getDataFromConfigFile(String key) throws IOException {
        FileInputStream fileInputStream;
        Properties property = new Properties();
        fileInputStream = new FileInputStream("src/main/resources/readingFromFileConfig.properties");
        try {
            property.load(fileInputStream);
        } finally {
            fileInputStream.close();
        }
        return property.getProperty(key);
    }
    public  List<Plane> setPlanesFromFile() throws IOException {
        List<Plane> planes = new ArrayList<>();
        BufferedReader reader = null;
        String str = getDataFromConfigFile("filePathPlanes");
        reader = new BufferedReader(new FileReader(str));
        String currentLine = "";
        String[] subStr = new String[0];
        while (currentLine != null) {
            currentLine = reader.readLine();
            if (currentLine != null) {
                subStr = currentLine.split(getDataFromConfigFile("delimeterSymbol"));
                if (subStr[4].equalsIgnoreCase(MilitaryType.BOMBER.toString())
                        || subStr[4].equalsIgnoreCase(MilitaryType.FIGHTER.toString())
                        || subStr[4].equalsIgnoreCase(MilitaryType.TRANSPORT.toString())) {
                    planes.add(new MilitaryPlane(subStr[0].trim(), Integer.parseInt(subStr[1].trim()), Integer.parseInt(subStr[2].trim()), Integer.parseInt(subStr[3].trim()), MilitaryType.valueOf(subStr[4].trim())));
                } else if (subStr[4].equalsIgnoreCase(ExperimentalTypes.HIGH_ALTITUDE.toString())
                        || subStr[4].equalsIgnoreCase(ExperimentalTypes.VTOL.toString())
                        || subStr[4].equalsIgnoreCase(ExperimentalTypes.HYPERSONIC.toString())
                        || subStr[4].equalsIgnoreCase(ExperimentalTypes.LIFTING_BODY.toString())) {
                    planes.add(new ExperimentalPlane(subStr[0].trim(), Integer.parseInt(subStr[1].trim()), Integer.parseInt(subStr[2].trim()), Integer.parseInt(subStr[3].trim()), ExperimentalTypes.valueOf(subStr[4].trim()), ClassificationLevel.valueOf(subStr[5].trim())));
                } else
                    planes.add(new PassengerPlane(subStr[0].trim(), Integer.parseInt(subStr[1].trim()), Integer.parseInt(subStr[2].trim()), Integer.parseInt(subStr[3].trim()), Integer.parseInt(subStr[4].trim())));
            }
        }

        return planes;
    }


    public void startAirportService() throws IOException {
        planes = setPlanesFromFile();
        Airport airport = new Airport(planes);
        Airport militaryAirport = new Airport(airport.getMilitaryPlanes());
        Airport passengerAirport = new Airport(airport.getPassengerPlanes());
        Airport experimentalAirport = new Airport(airport.getExperimentalPlanes());

        logger.info("Passenger airport sorted by max speed: " + passengerAirport
                .sortByMaxSpeed()
                .toString());
        logger.info("Military airport sorted by max distance: " + militaryAirport
                .sortByMaxDistance()
                .toString());
        logger.info("Experimental airport sorted by max speed: " + experimentalAirport
               .sortByMaxSpeed()
                .toString());
        logger.info("Plane with max passenger capacity: " + passengerAirport.getPassengerPlaneWithMaxPassengersCapacity());
    }
}
