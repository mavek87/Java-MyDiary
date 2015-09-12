package com.matteoveroni.mydiary.system.events;

import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class SystemEventBus implements Disposable {

    private final static int MAX_EVENTS_IN_QUEUE = 100;

    private final ArrayList<SystemEventListener> listeners = new ArrayList<>(5);
    private final PriorityQueue<SystemEvent> events = new PriorityQueue<>(MAX_EVENTS_IN_QUEUE);

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

    @Override
    public void dispose() {
        listeners.clear();
        events.clear();
    }
}
