package com.matteoveroni.mydiary.system.events;

public class EventoCambiaSchermata extends SystemEvent {

    private final ScreenController controllerSchermo;

    public EventoCambiaSchermata(final ScreenController controllerSchermo, final long priority) {
        super(priority);
        this.controllerSchermo = controllerSchermo;
    }

    public ScreenController getControllerSchermo() {
        return controllerSchermo;
    }
}
