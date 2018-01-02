package pers.steve.Sensors;

public class IMUDataElement extends SensorsDataElement {

    protected double[] acc = new double[3];
    protected double[] gyr = new double[3];
    protected double[] mag = new double[3];
    protected double[] pressure = new double[1];
    protected double[] Temp = new double[1];
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
