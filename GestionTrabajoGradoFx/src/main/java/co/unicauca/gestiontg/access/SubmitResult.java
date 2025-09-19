package co.unicauca.gestiontg.access;

import java.util.UUID;

/**
 *
 * @author kthn1
 */
public class SubmitResult {
    private UUID formatoId;
    private int version;
    
    public SubmitResult(UUID formatoId, int version) {
        this.formatoId = formatoId;
        this.version = version;
    }

    public UUID getFormatoId() {
        return formatoId;
    }

    public void setFormatoId(UUID formatoId) {
        this.formatoId = formatoId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
