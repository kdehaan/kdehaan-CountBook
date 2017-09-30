/*
 * Counter
 *
 * Version 1.0
 *
 * September 27, 2017
 *
 * Copyright © 2017 Kevin de Haan, CMPUT301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the COde of Student Behavior at the University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact kdehaan@ualberta.ca
 */
package dehaan.kdehaan_countbook;

import java.util.Date;

/**
 * Describes a counter.
 *
 * @author kdehaan
 * @version 1.0
 * @since 1.0
 */

public class Counter {
    private String name;
    private Date date;
    private Integer currentValue;
    private Integer initValue;
    private String comment;

    /**
     * Constructs a Counter object
     *
     * @param name Name of Counter
     * @param initValue Initial value
     */
    public Counter(String name, Integer initValue) {
        date = new Date();
        this.name = name;
        this.initValue = initValue;
        this.currentValue = this.initValue;
        this.comment = "";
    }

    /**
     * Constructs a Counter object
     *
     * @param name Name of Counter
     * @param initValue Initial value
     * @param comment Comment for Counter
     */
    public Counter(String name, Integer initValue, String comment) {
        date = new Date();
        this.name = name;
        this.initValue = initValue;
        this.currentValue = this.initValue;
        this.comment = comment;
    }

    /**
     * Increments currentValue by 1
     */
    public void increment() {
        this.currentValue += 1;
    }

    /**
     * decrements currentValue by 1
     */
    public void decrement() {
        this.currentValue -= 1;
    }


    /**
     * Get string representation of Counter
     *
     * @return string of Counter
     */
    @Override
    public String toString() {
        return name.toString() + " " + currentValue.toString() + " " + date.toString();

    }

    /**
     * Gets name of Counter
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of Counter
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets value of counter
     *
     * @return currentValue
     */
    public Integer getCurrentValue() {
        return currentValue;
    }

    /**
     * Sets current value of Counter
     *
     * @param currentValue
     */
    public void setCurrentValue(Integer currentValue) {
        this.currentValue = currentValue;
    }

    /**
     * Gets default value of Counter
     *
     * @return initValue
     */
    public Integer getInitValue() {
        return initValue;
    }

    /**
     * Sets the default value of Counter
     *
     * @param initValue
     */
    public void setInitValue(Integer initValue) {
        this.initValue = initValue;
    }

    /**
     * Gets the Counter comment
     *
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the Counter comment
     *
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Get the creation date of Counter
     *
     * @return date
     */
    public Date getDate() {
        return date;
    }
}
