import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * @brief ProfitAPI class.
 * @details Clase que iteractúa con la API de Profit para obtener distintos tipos de datos financieros.
 */
public class ProfitAPI {
    private final FinancialData financialData;                                              /**< Sujeto concreto para datos financieros. */
    private static final String API_KEY = "d1e47fb1b15641dd971dc9b0ce0a212c";               /**< Clave de autenticación */

    /**
     * @brief Constructor.
     * @param financialData [FinancialData] Sujeto concreto para datos financieros.
     */
    ProfitAPI(FinancialData financialData) {
        this.financialData = financialData;
    }

    /**
     * @brief Metodo que construye la URL para obtener los datos financieros de una categoría específica.
     * @param endpoint [String] Endpoint de la API de Profit.
     * @param parametro [String] Parámetro de la API de Profit.
     */
    public void construirUrl(String endpoint, String parametro) {
        String apiUrl = "https://api.profit.com/data-api/fundamentals/" + endpoint + "/peers/" + parametro + "?token=" + API_KEY;
        request(apiUrl);
    }

    /**
     * @brief Metodo que realiza una solicitud a la API de Profit.
     * @param apiUrl [String] URL de la API de Profit.
     */
    public void request(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Notificar al sujeto concreto de los cambios en los datos financieros de la API de Profit.
                financialData.set_data(response.toString());
            } else {
                System.out.println("Error al realizar la petición. Código de respuesta: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
