package co.unicauca.gestiontg.infra;

import co.unicauca.gestiontg.events.DomainEvent;


public interface Observer {
    void update(DomainEvent event);
}
