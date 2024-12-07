import java.util.List;
import java.util.ArrayList;

/**
 * @brief Sujeto interface. (Sujeto - Subject). El objeto observado.
 * @details Interfaz que define los métodos que los sujetos concretos deben implementar;
 * para añadir, quitar y notificar a los observadores cuando ocurra un cambio.
 */
public interface Sujeto {
    List<Observador> observadores = new ArrayList<>();  /**< Lista de observadores. */

    /**
     * @brief Metodo para suscribir un observador al sujeto.
     * @param observador [Observador] Observador a suscribir.
     */
    void suscribirObservador(Observador observador);

    /**
     * @brief Metodo para desuscribir un observador al sujeto.
     * @param observador [Observador] Observador a desuscribir.
     */
    void desuscribirObservador(Observador observador);

    /**
     * @brief Metodo que notifica a los observadores de un cambio en el sujeto.
     */
    void notificarObservadores();
}
