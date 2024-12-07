/**
 * @brief Observador interface (Observador - Observer).
 * @details Interfaz que define los métodos que los observadores concretos deben implementar, cuando el sujeto notifica un cambio.
 */
public interface Observador {
    /**
     * @brief Metodo que se invoca cuando el sujeto notifica un cambio y actualiza el estado del observador concreto.
     * @param data [String] Datos a actualizar.
     */
    void actualizar(String data);

    /**
     * @brief Metodo para mostrar la información relativa a los datos por consola.
     */
    void mostrarValores();
}