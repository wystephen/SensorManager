package pers.steve.Sensors;

public class IMUDataElement extends SensorsDataElement {

    protected double[] acc = new double[3];
    protected double[] gyr = new double[3];
    protected double[] mag = new double[3];
    protected double[] angle = new double[3];
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


    public void IMUDataElement(int index) {
        dataname = "IMUData";
    }


    /**
     * Convert Data to String( with '\n' at the end).
     *
     * @return
     */
    @Override
    public String convertDatatoString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%015.04f",time_stamp));
        stringBuilder.append(",");
        stringBuilder.append(darray2String(acc, 3));
        stringBuilder.append(darray2String(gyr, 3));
        stringBuilder.append(darray2String(mag, 3));
        stringBuilder.append(darray2String(angle, 3));
        stringBuilder.append(darray2String(pressure, 1));
        stringBuilder.append(darray2String(Temp, 1));
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        stringBuilder.append("\n");


        return stringBuilder.toString();
    }


    private String darray2String(double[] a, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            sb.append(a[i]);
            sb.append(",");
        }

        return sb.toString();
    }
}
