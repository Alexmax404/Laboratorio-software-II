package co.unicauca.gestiontg.infra;

import co.unicauca.gestiontg.events.DomainEvent;
import java.util.ArrayList;

/**
 *
 * @author Nicolas
 */
public class Subject implements ISubject {

    ArrayList<Observer> observers;

    public Subject() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void addObserver(Observer obs) {
        if (observers == null) {
            observers = new ArrayList<>();
        }
        observers.add(obs);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(DomainEvent event) {
        for (Observer o : observers) {
            o.update(event);
        }
    }
}
