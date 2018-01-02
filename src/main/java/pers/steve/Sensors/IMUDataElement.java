package pers.steve.Sensors;

public class IMUDataElement extends SensorsDataElement {

    protected double[] acc = new double[3];
    protected double[] gyr = new double[3];
    protected double[] mag = new double[3];
    protected double[] pressure = new double[1];
    protected double[] Temp = new double[1];

    public double[] getAcc() {
        return acc;
    }

    public void setAcc(double[] acc) {
        this.acc = acc;
    }

    public double[] getGyr() {
        return gyr;
    }

    public void setGyr(double[] gyr) {
        this.gyr = gyr;
    }

    public double[] getMag() {
        return mag;
    }

    public void setMag(double[] mag) {
        this.mag = mag;
    }

    public double[] getPressure() {
        return pressure;
    }

    public void setPressure(double[] pressure) {
        this.pressure = pressure;
    }

    public double[] getTemp() {
        return Temp;
    }

    public void setTemp(double[] temp) {
        Temp = temp;
    }

    public double[] getAngle() {
        return angle;
    }

    public void setAngle(double[] angle) {
        this.angle = angle;
    }

    protected double[] angle = new double[3];

    public void IMUDataElement(int index){
        dataname="IMUData";
    }



    /**
     * Convert Data to String( with '\n' at the end).
     *
     * @return
     */
    @Override
    public String convertDatatoString() {
        return null;
    }
}
