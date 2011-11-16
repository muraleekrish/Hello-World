package com.datrics.scheduler.valueobjects;

import java.io.Serializable;

/*
 * Created on Dec 9, 2005
 */

/**
 * @author sduraisamy
 */
public class ExecuteWeeklyDetails implements Serializable 
{
    private String scheduleType = "W";//If daily schedule ,set this to W.
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;
    private int noOfExecutions;
    private int remainingExecutions;
    public ExecuteWeeklyDetails()
    {
        this.monday = false;
        this.tuesday = false;
        this.wednesday = false;
        this.thursday = false;
        this.friday = false;
        this.saturday = false;
        this.sunday = false;
        this.noOfExecutions = 0;
        
    }
    
    public String getScheduleType()
    {
        return scheduleType;
    }
    /**
     * @return Returns the friday.
     */
    public boolean isFriday() {
        return friday;
    }
    /**
     * @param friday The friday to set.
     */
    public void setFriday(boolean friday) {
        
        this.friday = friday;
        if(friday)
        {
            noOfExecutions++;
            remainingExecutions = noOfExecutions;
        }
    }
    /**
     * @return Returns the monday.
     */
    public boolean isMonday() {
        return monday;
    }
    /**
     * @param monday The monday to set.
     */
    public void setMonday(boolean monday) {
        this.monday = monday;
        if(monday)
        {
            noOfExecutions++;
            remainingExecutions = noOfExecutions;
        }
    }
    /**
     * @return Returns the saturday.
     */
    public boolean isSaturday() {
        return saturday;
    }
    /**
     * @param saturday The saturday to set.
     */
    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
        if(saturday)
        {
            noOfExecutions++;
            remainingExecutions = noOfExecutions;
        }
    }
    /**
     * @return Returns the sunday.
     */
    public boolean isSunday() {
        return sunday;
    }
    /**
     * @param sunday The sunday to set.
     */
    public void setSunday(boolean sunday) {
        this.sunday = sunday;
        if(sunday)
        {
            noOfExecutions++;
            remainingExecutions = noOfExecutions;
        }
    }
    /**
     * @return Returns the thursday.
     */
    public boolean isThursday() {
        return thursday;
    }
    /**
     * @param thursday The thursday to set.
     */
    public void setThursday(boolean thursday) {
        this.thursday = thursday;
        if(thursday)
        {
            noOfExecutions++;
            remainingExecutions = noOfExecutions;
        }
    }
    /**
     * @return Returns the tuesday.
     */
    public boolean isTuesday() {
        return tuesday;
    }
    /**
     * @param tuesday The tuesday to set.
     */
    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
        if(tuesday)
        {
            noOfExecutions++;
            remainingExecutions = noOfExecutions;
        }
    }
    /**
     * @return Returns the wednesday.
     */
    public boolean isWednesday() {
        return wednesday;
    }
    /**
     * @param wednesday The wednesday to set.
     */
    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
        if(wednesday)
        {
            noOfExecutions++;
            remainingExecutions = noOfExecutions;
        }
    }
    
    /**
     * @return Returns the remainingExecutions.
     */
    public int getRemainingExecutions() {
        return remainingExecutions;
    }
    /**
     * @param remainingExecutions The remainingExecutions to set.
     */
    public void setRemainingExecutions(int remainingExecutions) {
        this.remainingExecutions = remainingExecutions;
    }
    /**
     * @return Returns the noOfExecutions.
     */
    public int getNoOfExecutions() {
        return noOfExecutions;
    }
    
}
