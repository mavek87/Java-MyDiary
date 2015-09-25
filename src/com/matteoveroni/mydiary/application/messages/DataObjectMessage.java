package com.matteoveroni.mydiary.application.messages;

/**
 *
 * @author Matteo Veroni
 */
public class DataObjectMessage {

    private Object objectToPush;
    private Class senderClass;

    public Object getData() {
        return objectToPush;
    }

    public void setData(Object objectToPush) {
        this.objectToPush = objectToPush;
    }

    public Class getSenderClass() {
        return senderClass;
    }

    public void setSenderClass(Class senderClass) {
        this.senderClass = senderClass;
    }
}
