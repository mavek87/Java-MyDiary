package com.matteoveroni.mydiary.system.events;

public class SystemEvent implements Comparable<SystemEvent> {

    private final long priority;

    public SystemEvent(final long priority) {
        this.priority = priority;
    }

    public long getPriority() {
        return priority;
    }

    @Override
    public int compareTo(SystemEvent o) {
        if (priority < o.getPriority()) {
            return -1;
        } else if (priority > o.getPriority()) {
            return 1;
        }
        return 0;
    }
}
