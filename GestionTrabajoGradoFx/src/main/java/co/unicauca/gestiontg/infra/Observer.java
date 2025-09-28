package co.unicauca.gestiontg.infra;

import co.unicauca.gestiontg.events.DomainEvent;


public interface Observer {
    public void update(Object o);
    void update(DomainEvent event);
}
