package pers.steve.Sensors;

public abstract class HardwareInteface <OutPutData>{
    /**
     * Read data as more as possible
     * @return
     */
    public abstract OutPutData ReadData();

    /**
     * Read data according to param n.
     * @param n
     * @return
     */
    public abstract OutPutData ReadData(int n);


}
