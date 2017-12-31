package pers.steve.Sensors;

import javafx.scene.chart.PieChart;

import java.util.concurrent.SynchronousQueue;

public class SensorAbstract<DataElementType> {
    private final java.util.concurrent.SynchronousQueue<DataElementType> data_queue = new SynchronousQueue<DataElementType>();
}
