package com.guisorting.app;

/**
 * Created by Walter on 5/31/2014.
 */
public class TerminationException extends RuntimeException {
    public TerminationException(){
        super();
    }

    public TerminationException(String msg){
        super(msg);
    }
}
