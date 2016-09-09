package com.ceylinco.ceylincocustomerapp.util;

/**
 * Created by Prishan Maduka on 9/8/2016.
 */
public class Validator {

    public boolean isValidNic(String nic){

        if(nic.length()==10){
            if(nic.substring(nic.length() - 1).equalsIgnoreCase("V"))
                return true;
            else return false;
        }else if(nic.length()==12){
            if(nic.matches("[0-9]+")){
                return true;
            }else return false;
        }else return false;

    }
}
