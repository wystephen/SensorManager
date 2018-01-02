package pers.steve.Sensors;

public abstract class SensorsDataElement {
    protected String dataname = "SensorsDataElement"; // name of data
    protected int data_index = -1; // index of data
    protected double time_stamp = 0.0; //time stamp

    /**
     * Convert Data to String( with '\n' at the end).
     * @return
     */
    public abstract String convertDatatoString();

    public String getDataname() {
        return dataname;
    }

    public void setDataname(String dataname) {
        this.dataname = dataname;
    }

    public int getData_index() {
        return data_index;
    }

    public void setData_index(int data_index) {
        this.data_index = data_index;
    }

    public double getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(double time_stamp) {
        this.time_stamp = time_stamp;
    }
}
