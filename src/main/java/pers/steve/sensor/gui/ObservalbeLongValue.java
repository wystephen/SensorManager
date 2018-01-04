package pers.steve.sensor.gui;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableLongValue;

public class ObservalbeLongValue implements ObservableLongValue {

    long value = 0;

    /**
     * Returns the current value of this {@code ObservableLongValue}.
     *
     * @return The current value
     */
    @Override
    public long get() {
        return value;
    }

    /**
     * Returns the value of this {@code ObservableNumberValue} as an {@code int}
     * . If the value is not an {@code int}, a standard cast is performed.
     *
     * @return The value of this {@code ObservableNumberValue} as an {@code int}
     */
    @Override
    public int intValue() {
        return (int) value;
    }

    /**
     * Returns the value of this {@code ObservableNumberValue} as a {@code long}
     * . If the value is not a {@code long}, a standard cast is performed.
     *
     * @return The value of this {@code ObservableNumberValue} as a {@code long}
     */
    @Override
    public long longValue() {
        return value;
    }

    /**
     * Returns the value of this {@code ObservableNumberValue} as a
     * {@code float}. If the value is not a {@code float}, a standard cast is
     * performed.
     *
     * @return The value of this {@code ObservableNumberValue} as a
     * {@code float}
     */
    @Override
    public float floatValue() {
        return (float) value;
    }

    /**
     * Returns the value of this {@code ObservableNumberValue} as a
     * {@code double}. If the value is not a {@code double}, a standard cast is
     * performed.
     *
     * @return The value of this {@code ObservableNumberValue} as a
     * {@code double}
     */
    @Override
    public double doubleValue() {
        return (double) value;
    }


    @Override
    public void addListener(ChangeListener<? super Number> listener) {

    }

    /**
     * Removes the given listener from the list of listeners, that are notified
     * whenever the value of the {@code ObservableValue} changes.
     * <p>
     * If the given listener has not been previously registered (i.e. it was
     * never added) then this method call is a no-op. If it had been previously
     * added then it will be removed. If it had been added more than once, then
     * only the first occurrence will be removed.
     *
     * @param listener The listener to remove
     * @throws NullPointerException if the listener is null
     * @see #addListener(ChangeListener)
     */
    @Override
    public void removeListener(ChangeListener<? super Number> listener) {

    }

    /**
     * Returns the current value of this {@code ObservableValue}
     *
     * @return The current value
     */
    @Override
    public Number getValue() {
        return value;
    }


    @Override
    public void addListener(InvalidationListener listener) {

    }

    /**
     * Removes the given listener from the list of listeners, that are notified
     * whenever the value of the {@code Observable} becomes invalid.
     * <p>
     * If the given listener has not been previously registered (i.e. it was
     * never added) then this method call is a no-op. If it had been previously
     * added then it will be removed. If it had been added more than once, then
     * only the first occurrence will be removed.
     *
     * @param listener The listener to remove
     * @throws NullPointerException if the listener is null
     * @see #addListener(InvalidationListener)
     */
    @Override
    public void removeListener(InvalidationListener listener) {

    }
}
