import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class Main {
    public static void main(String[] args) {
        // Archivos para volcar los datos financieros obtenidos.
        String forexFile = "forex_data.json";
        String stocksFile = "stocks_data.json";
        String materialsFile = "materials_data.json";

        // Crear instancias de FinancialData para cada tipo de datos.
        FinancialData forexData = new FinancialData(forexFile);
        FinancialData stocksData = new FinancialData(stocksFile);
        FinancialData materialsData = new FinancialData(materialsFile);

        // Crear CountDownLatch para sincronizar la ejecución de los hilos.
        CountDownLatch latch = new CountDownLatch(1);

        // Crear la ventana de inicio para permitir al usuario seleccionar
        // y suscribirse a los tipos de datos financieros de los que desea recibir notificaciones.
        SwingUtilities.invokeLater(() -> new InicioWindow(forexData, stocksData, materialsData, latch));

        // Retrasar el inicio de las peticiones para suscribir a todos los observadores.
        try {
            latch.await();  // Esperar hasta que countDown() sea llamado, al hacer clic sobre el botón "Iniciar peticiones".
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Crear hilos para observar los archivos de texto donde se vuelcan los últimos datos financieros actualizados.
        Thread forexWatcher = new Thread(new FileWatcher(forexFile, forexData));
        Thread stocksWatcher = new Thread(new FileWatcher(stocksFile, stocksData));
        Thread materialsWatcher = new Thread(new FileWatcher(materialsFile, materialsData));

        forexWatcher.start();
        stocksWatcher.start();
        materialsWatcher.start();

        // Crear APIs para manejar los distintos datos financieros.
        ProfitAPI forexAPI = new ProfitAPI(forexData);
        ProfitAPI stocksAPI = new ProfitAPI(stocksData);
        ProfitAPI materialsAPI = new ProfitAPI(materialsData);

        // Programar peticiones periódicas para cada API cada 24 horas.
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Actualizando datos de Forex desde la API...");
            forexAPI.construirUrl("forex", "EURUSD");
        }, 0, 86400, TimeUnit.SECONDS);

        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Actualizando datos de acciones desde la API...");
            stocksAPI.construirUrl("stocks", "TSLA");
        }, 5, 86400, TimeUnit.SECONDS); // Esperar 15 segundos después de la primera petición.

        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Actualizando datos de materias primas desde la API...");
            materialsAPI.construirUrl("commodities", "LCO");
        }, 10, 86400, TimeUnit.SECONDS); // Esperar 30 segundos después de la primera petición.

    }
}
