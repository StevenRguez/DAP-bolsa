import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @brief FinancialData class. (Sujeto concreto - Concrete subject).
 * @details Clase que implementa la interfaz Subjeto, y que define el comportamiento del sujeto concreto para los datos financieros obtenidos de la API de Profit.
 * Mantiene su estado interno y, cuando cambia, notifica a los observadores registrados: ObservadorMateriasPrimas, ObservadorForex y ObservadorBolsaValores.
 */
public class FinancialData implements Sujeto {
    private List<Observador> observadores = new ArrayList<>();      /**< Lista de observadores registrados. */
    private String data = "";                                       /**< Datos financieros obtenidos de la API de Profit. */
    private String lastNotifiedData = "";                           /**< Últimos datos notificados a los observadores. */
    private final String filePath;                                  /**< Ruta del archivo donde se volcarán los datos financieros
                                                                    cada vez que se produzca un cambio. */

    /**
     * @brief Constructor.
     * @param filePath [String] Ruta del archivo donde se volcarán los datos
     *                 financieros cada vez que se produzca un cambio.
     */
    public FinancialData(String filePath) {
        this.filePath = filePath;
    }

   /**
        * @brief Metodo para suscribir un observador al sujeto.
        * @param observador [Observador] Observador a suscribir.
        */
    @Override
    public void suscribirObservador(Observador observador) {
        observadores.add(observador);
    }

    /**
     * @brief Metodo para desuscribir un observador al sujeto.
     * @param observador [Observador] Observador a desuscribir.
     */
    @Override
    public void desuscribirObservador(Observador observador) {
        observadores.remove(observador);
    }

    /**
     * @brief Metodo que notifica a los observadores de un cambio en los datos financieros obtenidos.
     */
    @Override
    public void notificarObservadores() {
        for (Observador observador : observadores) {
            observador.actualizar(data);
        }
    }

    /**
     * @brief Metodo que actualiza los datos financieros y notifica a los observadores cada vez que se produzca un cambio.
     * @param data [String] Datos financieros obtenidos de la API de Profit ó del archivo de texto donde se vuelcan los datos.
     */
    public synchronized void set_data(String data) {

        this.data = data;
        if (!Objects.equals(data, lastNotifiedData)) {
            notificarObservadores();

            // Volcar los datos a un archivo de texto
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        lastNotifiedData = data;
    }

    public String getData() {
        return data;
    }
}




