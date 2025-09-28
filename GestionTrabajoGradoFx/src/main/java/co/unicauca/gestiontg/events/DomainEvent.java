package co.unicauca.gestiontg.events;

/**
 *
 * @author kthn1
 */
public class DomainEvent {

    private final EnumEventType name;
    private final Object data;

    public DomainEvent(EnumEventType name, Object data) {
        this.name = name;
        this.data = data;
    }

    public EnumEventType getName() {
        return name;
    }

    public Object getData() {
        return data;
    }
}
