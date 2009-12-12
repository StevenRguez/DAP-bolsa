import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * @brief ObservadorMatPrimas class. (Observador concreto - Concrete observer).
 * @details Clase que implementa la interfaz Observador, y que define el comportamiento del observador concreto
 * que recibe notificaciones sobre los datos financieros de las Materias Primas obtenidos de la API de Profit.
 */
class ObservadorMatPrimas implements Observador {
    private final String nombre;                                            /**< Nombre del observador de materias primas. */
    private final ArrayList<Valor> valoresAsociados = new ArrayList<>();    /**< Lista de activos de Materias Primas. */
    private ObservadorWindow window;                                        /**< Ventana que muestra los detalles de los activos de materias primas. */

    /**
     * @brief Constructor.
     * @param nombre [String] Nombre del observador de Materias Primas.
     */
    public ObservadorMatPrimas(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @brief Metodo que se invoca cuando el sujeto (FinalcialData) notifica un cambio en los datos de las Materias Primas.
     * @param data [String] Datos a actualizar.
     */
    @Override
    public void actualizar(String data) {
        System.out.println("Observador " + nombre + " actualizó sus datos de Materias Primas.");

        // Parsear los datos recibidos en formato JSON.
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(data, JsonArray.class);

        valoresAsociados.clear();


        for (JsonElement element : jsonArray) {
            JsonObject objeto = element.getAsJsonObject();

            String name = objeto.get("name").getAsString();
            double lastPrice = objeto.get("last_price").getAsDouble();
            double changePercentage = objeto.get("change_percentage").getAsDouble();

            valoresAsociados.add(new Valor("Materias Primas", name, lastPrice, changePercentage));
        }

        // Mostrar los datos en una ventana gráfica.
        if (window == null) {
            window = new ObservadorWindow("Materias Primas", this.nombre, valoresAsociados);
            window.setVisible(true);
        } else {
            window.actualizarDatos(valoresAsociados);
        }
    }

    /**
     * @brief Metodo para mostrar la información relativa a los datos por consola.
     */
    @Override
    public void mostrarValores() {
        System.out.println("Materias Primas:");
        for (Valor valor : valoresAsociados) {
            valor.mostrarPorPantalla();
        }
    }

    /**
     * @brief ObservadorForex class. (Observador concreto - Concrete observer).
     * @details Clase que implementa la interfaz Observador, y que define el comportamiento del observador concreto
     * que recibe notificaciones sobre los datos financieros de Forex obtenidos de la API de Profit.
     */

    class ObservadorForex implements Observador {
        private final String nombre;                                            /**< Nombre del observador de Forex. */
        private final ArrayList<Valor> valoresAsociados = new ArrayList<>();    /**< Lista de activos de Forex. */
        private ObservadorWindow window;                                        /**< Ventana que muestra los detalles de los activos de Forex. */

        /**
         * @brief Constructor.
         * @param nombre [String] Nombre del observador de Forex.
         */
        public ObservadorForex(String nombre) {
            this.nombre = nombre;
        }

        /**
         * @brief Metodo que se invoca cuando el sujeto (FinalcialData) notifica un cambio en los datos de Forex.
         * @param data [String] Datos a actualizar.
         */
        @Override
        public void actualizar(String data) {
            System.out.println("Observador " + nombre + " actualizó sus datos de Forex.");

            // Parsear los datos recibidos en formato JSON.
            Gson gson = new Gson();
            JsonArray jsonArray = gson.fromJson(data, JsonArray.class);

            valoresAsociados.clear();

            for (JsonElement element : jsonArray) {
                JsonObject objeto = element.getAsJsonObject();

                String name = objeto.get("name").getAsString();
                double lastPrice = objeto.get("last_price").getAsDouble();
                double changePercentage = objeto.get("change_percentage").getAsDouble();

                valoresAsociados.add(new Valor("Forex", name, lastPrice, changePercentage));
            }

            // Mostrar los datos en una ventana gráfica.
            if (window == null) {
                window = new ObservadorWindow("Forex", this.nombre, valoresAsociados);
                window.setVisible(true);
            } else {
                window.actualizarDatos(valoresAsociados);
            }
        }

        /**
         * @brief Metodo para mostrar la información relativa a los datos por consola.
         */
        @Override
        public void mostrarValores() {
            System.out.println("Forex:");
            for (Valor valor : valoresAsociados) {
                valor.mostrarPorPantalla();
            }
        }
    }

}

