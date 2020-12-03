package Planes;

import java.util.Objects;

abstract public class Plane {
    private String planeModel;
    private int maxSpeed;
    private int maxFlightDistance;
    private int maxLoadCapacity;

    public Plane(String planeModel, int maxSpeed, int maxFlightDistance, int maxLoadCapacity) {
        this.planeModel = planeModel;
        this.maxSpeed = maxSpeed;
        this.maxFlightDistance = maxFlightDistance;
        this.maxLoadCapacity = maxLoadCapacity;
    }

    public Plane(){}

    public void setPlaneModel(String planeModel) {
        this.planeModel = planeModel;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setMaxLoadCapacity(int maxLoadCapacity) {
        this.maxLoadCapacity = maxLoadCapacity;
    }

    public void setMaxFlightDistance(int maxFlightDistance) {
        this.maxFlightDistance = maxFlightDistance;
    }

    public String getPlaneModel() {
        return planeModel;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public int getMaxFlightDistance() {
        return maxFlightDistance;
    }

    public int getMaxLoadCapacity() {
        return this.maxLoadCapacity;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "model='" + planeModel + '\'' +
                ", maxSpeed=" + maxSpeed +
                ", maxFlightDistance=" + maxFlightDistance +
                ", maxLoadCapacity=" + maxLoadCapacity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plane)) return false;
        Plane plane = (Plane) o;
        return maxSpeed == plane.maxSpeed &&
                maxFlightDistance == plane.maxFlightDistance &&
                maxLoadCapacity == plane.maxLoadCapacity &&
                Objects.equals(planeModel, plane.planeModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planeModel, maxSpeed, maxFlightDistance, maxLoadCapacity);
    }
}
