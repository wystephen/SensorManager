package pers.steve.sensor.gui;

/**
 * Bad design!!!!!!!!!!!!!!
 */
public abstract class SensorWriteFileInterface {

    /**
     * Set name of directory.
     *
     * @param dirName String directory name.
     * @return
     */
    public abstract boolean setDirectoryName(String dirName);

    /**
     * Start write to files.
     *
     * @return
     */
    public abstract boolean startWrite();

    /**
     * Stop write to files.
     *
     * @return
     */
    public abstract boolean stopWrite();
}
