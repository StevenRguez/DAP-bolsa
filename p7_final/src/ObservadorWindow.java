import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * @brief ObservadorWindow class.
 * @details Clase que define la ventana que muestra los datos financieros correspondientes a un tipo de activo (Materias Primas, Forex, Bolsa de Valores).
 */
class ObservadorWindow extends JFrame {
    private final JTextArea textArea;                   /**< Área de texto para mostrar los datos financieros. */
    private final String tipo;                          /**< Tipo de activo financiero. */
    private final DefaultCategoryDataset dataset;       /**< Conjunto de datos para el gráfico de barras. */
    private final JFreeChart chart;                     /**< Gráfico de barras. */

    /**
     * @brief Constructor. Inicializa los componentes de la ventana.
     * @param tipo [String] Tipo de activo financiero.
     * @param nombre [String] Nombre del observador.
     * @param valoresIniciales [ArrayList<Valor>] Lista de activos financieros.
     */
    public ObservadorWindow(String tipo, String nombre, ArrayList<Valor> valoresIniciales) {
        this.tipo = tipo;

        // Título y configuración inicial de la ventana.
        setTitle(nombre + ": " + tipo);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Etiqueta del título.
        JLabel label = new JLabel(nombre + ": " + tipo);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        // Panel para mostrar los datos en texto
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS)); // Cambiamos el layout a BoxLayout

        // Área de texto donde se mostrarán los datos obtenidos de la petición.
        textArea = new JTextArea();
        textArea.setEditable(false);
        textPanel.add(new JScrollPane(textArea));

        // Dataset que permite al gráfico de barras manejar los activos financieros.
        dataset = new DefaultCategoryDataset();

        // Condiguación inicial del gráfico de barras.
        chart = ChartFactory.createBarChart(
                "Datos Financieros",            // Título del gráfico
                "Activo",                            // Eje X
                "Precio - Porcentaje de cambio",     // Eje Y
                dataset,                             // Conjunto de datos
                PlotOrientation.VERTICAL,            // Orientación
                true,                                // Leyenda
                true,                                // Tooltips
                false                                // URLs
        );

        // Panel para el gráfico de barras.
        ChartPanel chartPanel = new ChartPanel(chart);
        textPanel.add(chartPanel);      // Añadir el panel del gráfico al panel de texto.

        // Agregar el panel de texto y gráfico a la ventana.
        add(textPanel, BorderLayout.CENTER);

        // Actualizar los datos en panel de texto y gráfico.
        actualizarDatos(valoresIniciales);
    }

    /**
     * @brief Metodo para mostrar y formatear los datos financieros actualizados en el panel de texto y gráfico.
     * @param valores [ArrayList<Valor>] Lista de activos financieros.
     */
    public void actualizarDatos(ArrayList<Valor> valores) {
        StringBuilder builder = new StringBuilder();
        for (Valor valor : valores) {
            builder.append("Nombre: ").append(valor.getNombre())
                    .append("   |   Precio: ").append(valor.getPrecio())
                    .append("   |   Cambio 1D: ").append(valor.getCambio()).append("\n");
        }

        // Actualizar panel de texto.
        textArea.setText(builder.toString());

        // Actualizar gráfico de barras.
        actualizarDataset(valores);

    }

    /**
     * @brief Metodo para actualizar el conjunto de datos que permite al gráfico de barras manejar los activos financieros.
     * @param valores [ArrayList<Valor>] Lista de activos financieros.
     */
    private void actualizarDataset(ArrayList<Valor> valores) {
        dataset.clear();    // Limpiar el dataset.

        // Agregar los valores de los activos al dataset.
        for (Valor valor : valores) {
            dataset.addValue(valor.getPrecio(), "Precio", valor.getNombre());
            dataset.addValue(valor.getCambio(), "Porcentaje de cambio", valor.getNombre());
        }

        revalidate(); // Revalidar el layout.
        repaint();    // Repintar el componente gráfico.

    }
}
