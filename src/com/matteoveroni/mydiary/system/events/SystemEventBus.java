package com.matteoveroni.mydiary.system.events;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class SystemEventBus {

    private final static int MAX_EVENTS_IN_QUEUE = 100;

    private final ArrayList<SystemEventListener> listeners = new ArrayList<SystemEventListener>(5);
    private final PriorityQueue<SystemEvent> events = new PriorityQueue<SystemEvent>(MAX_EVENTS_IN_QUEUE);

    public synchronized void addEventListener(SystemEventListener busListener) {
        if (!listeners.contains(busListener)) {
            listeners.add(busListener);
        }
    }

    public synchronized void removeEventListener(SystemEventListener busListener) {
        if (listeners.contains(busListener)) {
            listeners.remove(busListener);
        }
    }

    public void sendEventOnBus(SystemEvent systemEvent) {
        events.add(systemEvent);
    }

    public void executeNextEventInQueue() {
        if (!events.isEmpty()) {
            SystemEvent event = events.poll();
            for (SystemEventListener listener : listeners) {
                listener.executeEvent(event);
            }
        }
    }

    public void clear() {
        listeners.clear();
        events.clear();
    }
}
