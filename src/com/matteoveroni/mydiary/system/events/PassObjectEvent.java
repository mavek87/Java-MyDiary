package com.matteoveroni.mydiary.system.events;

/**
 *
 * @author Matteo Veroni
 */
public class PassObjectEvent extends SystemEvent{
    
    private final Object objectPassed;

    public PassObjectEvent(Object objectPassed, long priority) {
        super(priority);
        this.objectPassed = objectPassed;
    }
    
   public Object getObjectPassed(){
       return objectPassed;
   }
}
