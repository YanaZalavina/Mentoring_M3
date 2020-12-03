package Planes;

import lombok.Getter;
import lombok.Setter;
import models.ClassificationLevel;
import models.ExperimentalTypes;
@Getter
@Setter
public class ExperimentalPlane extends Plane {

    private ExperimentalTypes experimentalType;
    private ClassificationLevel classificationLevel;

    public ExperimentalPlane(){}

    public ExperimentalPlane(String model, int maxSpeed, int maxFlightDistance, int maxLoadCapacity, ExperimentalTypes experimentalType, ClassificationLevel classificationLevel) {
        super(model, maxSpeed, maxFlightDistance, maxLoadCapacity);
        this.experimentalType = experimentalType;
        this.classificationLevel = classificationLevel;
    }
    public void setClassificationLevel(ClassificationLevel classificationLevel) {
        this.classificationLevel = classificationLevel;
    }
    public ClassificationLevel getClassificationLevel() {
        return classificationLevel;
    }

    public void setExperimentalType(ExperimentalTypes experimentalType) {
        this.experimentalType = experimentalType;
    }

    public ExperimentalTypes getExperimentalType() {
        return experimentalType;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString().replace("}",
                ", type=" + experimentalType +
                        ", Classification level=" + classificationLevel +
                        '}');
    }
}
