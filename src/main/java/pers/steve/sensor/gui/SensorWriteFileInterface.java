package pers.steve.sensor.gui;

import java.io.File;

/**
 * Bad design!!!!!!!!!!!!!!
 */
public abstract class SensorWriteFileInterface {

    /**
     * Set name of directory.
     *
     * @param dirFile File of directory name.
     * @return
     */
    public abstract boolean setDirectoryFile(File dirFile);

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
