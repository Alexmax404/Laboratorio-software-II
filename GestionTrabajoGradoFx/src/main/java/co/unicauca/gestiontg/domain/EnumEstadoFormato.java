package co.unicauca.gestiontg.domain;

/**
 *
 * @author kthn1
 */
public enum EnumEstadoFormato {
    EN_REVISION,
    APROBADO,
    RECHAZADO,
    CORRECCIONES;

    public static EnumEstadoFormato fromString(String estado) {
        if (estado == null) {
            return null;
        }
        switch (estado.toUpperCase()) {
            case "ENREVISION":
            case "EN_REVISION":
                return EN_REVISION;
            case "APROBADO":
                return APROBADO;
            case "RECHAZADO":
                return RECHAZADO;
            case "CORRECCIONES":
                return CORRECCIONES;
            default:
                throw new IllegalArgumentException("Estado inválido: " + estado);
        }
    }
}
