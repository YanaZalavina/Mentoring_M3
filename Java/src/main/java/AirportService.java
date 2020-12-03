import Planes.ExperimentalPlane;
import Planes.MilitaryPlane;
import Planes.PassengerPlane;
import Planes.Plane;
import models.ClassificationLevel;
import models.ExperimentalTypes;
import models.MilitaryType;

import java.io.*;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AirportService {

    public static List<Plane> planes = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(AirportService.class);
    private final static String pathToConfigFile = "src/main/resources/readingFromFileConfig.properties";

    public static String getDataFromConfigFile(String key) {
        FileInputStream fileInputStream;
        Properties property = new Properties();
        try {
            fileInputStream = new FileInputStream(pathToConfigFile);
            try {
                property.load(fileInputStream);
            } finally {
                fileInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("IOException while loading the config file");
        }
        return property.getProperty(key);
    }

    public static  List<Map> setPlanesFromFile(){
        List<Map> mapList = new ArrayList<>();

        BufferedReader reader = null;
        String str = getDataFromConfigFile("filePathPlanes");
        try {
            reader = new BufferedReader(new FileReader(str));

            String currentLine = "";
            String[] subStr = new String[0];
            String[] subStrKeyValue = new String[0];
            while (currentLine != null) {
                HashMap<String, String> keyValue = new HashMap<>();
                currentLine = reader.readLine();
                if (currentLine != null) {
                    subStr = currentLine.split(getDataFromConfigFile("delimeterSymbolComma"));
                    for (String subStrKV:subStr) {
                        subStrKeyValue = subStrKV.split(getDataFromConfigFile("delimeterSymbolEqually"));
                        if(subStrKeyValue.length==2){
                            keyValue.put(subStrKeyValue[0], subStrKeyValue[1]);
                        }
                    }
                }
                if(!keyValue.isEmpty()){
                    mapList.add(keyValue);}
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("IOException while loading the " + getDataFromConfigFile("filePathPlanes") + " file");
        }
        return mapList;
    }

    public static List<Plane> definePlanesFromList(List<Map> mapList){
        List<Plane> planes = new ArrayList<>();

        for (Map map:mapList) {
            if(map.containsKey("type")) {
                if (isDefinedTypeOfPlane(map.get("type").toString(), MilitaryType.BOMBER, MilitaryType.FIGHTER, MilitaryType.TRANSPORT)) {
                    MilitaryPlane militaryPlane = new MilitaryPlane();
                    militaryPlane.setPlaneModel(map.get("model").toString());
                    militaryPlane.setMaxSpeed(Integer.parseInt(map.get("maxSpeed").toString()));
                    militaryPlane.setMaxFlightDistance(Integer.parseInt(map.get("maxFlightDistance").toString()));
                    militaryPlane.setMaxLoadCapacity(Integer.parseInt(map.get("maxLoadCapacity").toString()));
                    militaryPlane.setMilitaryType(MilitaryType.valueOf(map.get("type").toString()));
                    planes.add(militaryPlane);
                } else if (isDefinedTypeOfPlane(map.get("type").toString(), ExperimentalTypes.VTOL, ExperimentalTypes.HYPERSONIC, ExperimentalTypes.LIFTING_BODY, ExperimentalTypes.HIGH_ALTITUDE)) {
                    ExperimentalPlane experimentalPlane = new ExperimentalPlane();
                    experimentalPlane.setPlaneModel(map.get("model").toString());
                    experimentalPlane.setMaxSpeed(Integer.parseInt(map.get("maxSpeed").toString()));
                    experimentalPlane.setMaxFlightDistance(Integer.parseInt(map.get("maxFlightDistance").toString()));
                    experimentalPlane.setMaxLoadCapacity(Integer.parseInt(map.get("maxLoadCapacity").toString()));
                    experimentalPlane.setExperimentalType(ExperimentalTypes.valueOf(map.get("type").toString()));
                    experimentalPlane.setClassificationLevel(ClassificationLevel.valueOf(map.get("classificationLevel").toString()));
                    planes.add(experimentalPlane);
                }
            }
            else{
                PassengerPlane passengerPlane = new PassengerPlane();
                passengerPlane.setPlaneModel(map.get("model").toString());
                passengerPlane.setMaxSpeed(Integer.parseInt(map.get("maxSpeed").toString()));
                passengerPlane.setMaxFlightDistance(Integer.parseInt(map.get("maxFlightDistance").toString()));
                passengerPlane.setMaxLoadCapacity(Integer.parseInt(map.get("maxLoadCapacity").toString()));
                passengerPlane.setPassengersCapacity(Integer.parseInt(map.get("passengersCapacity").toString()));
                planes.add(passengerPlane);
            }
        }
        return planes;
    }

    public static boolean isDefinedTypeOfPlane(String typeOfPlaneFromFileWithData, Enum... typesOfPlane) {
        return Arrays.stream(typesOfPlane).anyMatch(typeOfPlane -> typeOfPlane.toString().equalsIgnoreCase(typeOfPlaneFromFileWithData));
    }

    public void startAirportService() {
        planes = definePlanesFromList(setPlanesFromFile());
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
