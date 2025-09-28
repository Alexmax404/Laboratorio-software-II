package co.unicauca.gestiontg.infra;

import co.unicauca.gestiontg.events.DomainEvent;

/**
 *
 * @author kthn1
 */
public interface ISubject {
    void addObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers(DomainEvent event);
}
