package co.unicauca.gestiontg.infra;

import java.util.ArrayList;

/**
 *
 * @author Nicolas
 */
public class Subject {
    ArrayList<Observer> observers;
    
    public Subject() {
        this.observers = new ArrayList<>();
    }
    
    public void addObserver(Observer obs) {
        if (observers == null) {
            observers = new ArrayList<>();
        }
        observers.add(obs);
    }
    
    public void notifyAllObserves() {
        System.out.println("Notificando a observadores...");
        for (Observer each : observers) {
            each.update(this);
        }
    }
}
