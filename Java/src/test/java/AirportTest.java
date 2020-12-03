import Planes.ExperimentalPlane;
import models.ClassificationLevel;
import models.MilitaryType;
import org.testng.Assert;
import org.testng.annotations.Test;
import Planes.MilitaryPlane;
import Planes.PassengerPlane;
import Planes.Plane;
import java.util.*;

public class AirportTest {
    private static PassengerPlane planeWithMaxPassengerCapacity = new PassengerPlane("Boeing-747", 980, 16100, 70500, 242);
    private static List<Plane> planes = new AirportService().definePlanesFromList(AirportService.setPlanesFromFile());

    @Test
    public void testGetTransportMilitaryPlanes() {
        Airport airport = new Airport(planes);
        List<MilitaryPlane> transportMilitaryPlanes = airport.getTransportMilitaryPlanes();
        boolean flag = false;

        for (MilitaryPlane militaryPlane : transportMilitaryPlanes) {
            if ((militaryPlane.getMilitaryType() == MilitaryType.TRANSPORT)) {
                flag = true;
                break;
            }
        }
        Assert.assertTrue(flag);
    }

    @Test
    public void testGetPassengerPlaneWithMaxCapacity() {
        Airport airport = new Airport(planes);
        PassengerPlane expectedPlaneWithMaxPassengersCapacity = airport.getPassengerPlaneWithMaxPassengersCapacity();
        Assert.assertEquals(planeWithMaxPassengerCapacity, expectedPlaneWithMaxPassengersCapacity);
    }

    @Test
    public void testSortByMaxLoadCapacity() {
        Airport airport = new Airport(planes);
        airport.sortByMaxLoadCapacity();
        List<? extends Plane> planesSortedByMaxLoadCapacity = airport.getPlanes();

        boolean nextPlaneMaxLoadCapacityIsHigherThanCurrent = true;
        for (int i = 0; i < planesSortedByMaxLoadCapacity.size() - 1; i++) {
            if (planesSortedByMaxLoadCapacity.get(i).getMaxLoadCapacity() > planesSortedByMaxLoadCapacity.get(i + 1).getMaxLoadCapacity()) {
                nextPlaneMaxLoadCapacityIsHigherThanCurrent = false;
                break;
            }
        }
        Assert.assertTrue(nextPlaneMaxLoadCapacityIsHigherThanCurrent);
    }

    @Test
    public void testHasAtLeastOneBomberInMilitaryPlanes() {
        Airport airport = new Airport(planes);
        List<MilitaryPlane> bomberMilitaryPlanes = airport.getBomberMilitaryPlanes();
        boolean hasAtLeastOneBomberInMilitaryPlanes = false;
        for (MilitaryPlane militaryPlane : bomberMilitaryPlanes) {
            if ((militaryPlane.getMilitaryType() == MilitaryType.BOMBER)) {
                hasAtLeastOneBomberInMilitaryPlanes = true;
                break;
            }
        }
        Assert.assertTrue(hasAtLeastOneBomberInMilitaryPlanes);
    }

    @Test
    public void testExperimentalPlanesHasClassificationLevelHigherThanUnclassified(){
        Airport airport = new Airport(planes);
        List<ExperimentalPlane> experimentalPlanes = airport.getExperimentalPlanes();
        boolean hasUnclassifiedPlanes = false;
        for(ExperimentalPlane experimentalPlane : experimentalPlanes){
            if(experimentalPlane.getClassificationLevel() == ClassificationLevel.UNCLASSIFIED){
                hasUnclassifiedPlanes = true;
                break;
            }
        }
        Assert.assertFalse(hasUnclassifiedPlanes);
    }
}
