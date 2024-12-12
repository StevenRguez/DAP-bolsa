import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

/**
 * @brief FileWatcher class.
 * @details Clase que implementa la interfaz Runnable, y que define el comportamiento de un hilo que observa un archivo de texto.
 * Cuando el archivo cambia, notifica al sujeto concreto (FinancialData) para que actualice sus datos.
 */
public class FileWatcher implements Runnable {
    private final Path filePath;                /**< Ruta del archivo a observar. */
    private final FinancialData financialData;  /**< Sujeto concreto al que notificar los cambios. */

    /**
     * @brief Constructor.
     * @param filePath [String] Ruta del archivo a observar.
     * @param financialData [FinancialData] Sujeto concreto al que notificar los cambios.
     */
    public FileWatcher(String filePath, FinancialData financialData) {
        this.filePath =  Paths.get(filePath).toAbsolutePath();  // Convierte la ruta a absoluta
        this.financialData = financialData;
    }

    /**
     * @brief Redefinición el metodo run de la interfaz Runnable.
     * @details Observa el archivo de texto, y cuando cambia, notifica al sujeto concreto (FinancialData) para que actualice sus datos.
     */
    @Override
    public void run() {
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            Path dir = filePath.getParent();
            System.out.println("Observando el directorio: " + dir);
            if (dir == null) return;
            dir.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            while (true) {
                WatchKey key = watchService.take(); // Bloquea hasta que hay eventos.
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path changed = dir.resolve(ev.context());

                    if (changed.equals(filePath)) {
                        // Leer datos del archivo y notificarlos
                        synchronized (financialData) {
                            try (Scanner scanner = new Scanner(filePath)) {
                                StringBuilder newData = new StringBuilder();
                                while (scanner.hasNextLine()) {
                                    newData.append(scanner.nextLine());
                                }
                                System.out.println("Datos actualizados: " + newData);
                                financialData.set_data(newData.toString());         // Notificar al sujeto concreto (FinancialData).
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                if (!key.reset()) break;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}