import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CountDownLatch;

/**
 * @brief InicioWindow class.
 * @details Clase que define la ventana de inicio de la aplicación, donde el usuario puede introducir su nombre y seleccionar el tipo de activo financiero al que desea suscribirse.
 */
public class InicioWindow extends JFrame {
    private FinancialData forexData;        /**< Sujeto concreto para los datos financieros de Forex. */
    private FinancialData stocksData;       /**< Sujeto concreto para los datos financieros de la Bolsa de Valores. */
    private FinancialData materialsData;    /**< Sujeto concreto para los datos financieros de Materias Primas. */
    private CountDownLatch latch;

    /**
     * @brief Constructor. Inicializa los componentes de la ventana.
     * @param forexData [FinancialData] Sujeto concreto para los datos financieros de Forex.
     * @param stocksData [FinancialData] Sujeto concreto para los datos financieros de la Bolsa de Valores.
     * @param materialsData [FinancialData] Sujeto concreto para los datos financieros de Materias Primas.
     */
    public InicioWindow(FinancialData forexData, FinancialData stocksData, FinancialData materialsData, CountDownLatch latch) {
        this.forexData = forexData;
        this.stocksData = stocksData;
        this.materialsData = materialsData;
        this.latch = latch;

        // Título y configuración inicial de la ventana.
        setTitle("Inicio");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel con los campos de entrada:
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        // Etiqueta y campos de texto para introducir el nombre del usuario.
        JLabel nombreLabel = new JLabel("Introduce tu nombre:");
        JTextField nombreField = new JTextField();
        // Etiqueta y lista desplegable para seleccionar el tipo de activo financiero.
        JLabel categoriaLabel = new JLabel("Selecciona el tipo de activo financiero:");
        JComboBox<String> categoriaBox = new JComboBox<>(new String[]{"Forex", "Bolsa de Valores", "Materias Primas"});

        // Botón para suscribir al usuario al tipo de activo financiero.
        JButton agregarObservadorButton = new JButton("Suscribirse");
        JButton iniciarButton = new JButton("Iniciar peticiones");

        // Área de texto para mostrar las suscripciones de los usuarios.
        JTextArea observadoresArea = new JTextArea();
        observadoresArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(observadoresArea);

        // Añadir los compoenentes al panel y a la ventana.
        inputPanel.add(nombreLabel);
        inputPanel.add(nombreField);
        inputPanel.add(categoriaLabel);
        inputPanel.add(categoriaBox);
        inputPanel.add(new JLabel());
        inputPanel.add(agregarObservadorButton);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(iniciarButton, BorderLayout.SOUTH);

        // Acción al hacer clic en el botón de suscripción:
        agregarObservadorButton.addActionListener(e -> {
            // Obtener el nombre y el tipo de activo financiero seleccionado.
            String nombre = nombreField.getText();
            String categoria = (String) categoriaBox.getSelectedItem();

            // Comprobar que se ha introducido un nombre.
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, introduce un nombre.");
                return;
            }

            // Crear un observador concreto y suscribirlo al sujeto correspondiente.
            Observador observador;
            switch (categoria) {
                case "Forex":
                    observador = new ObservadorForex(nombre);
                    forexData.suscribirObservador(observador);
                    break;
                case "Bolsa de Valores":
                    observador = new ObservadorBolsaValores(nombre);
                    stocksData.suscribirObservador(observador);
                    break;
                case "Materias Primas":
                    observador = new ObservadorMatPrimas(nombre);
                    materialsData.suscribirObservador(observador);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Selección inválida.");
                    return;
            }
            observadoresArea.append(nombre + " se ha suscrito para recibir notificaciones sobre: " + categoria + "\n");
            nombreField.setText("");
        });

        // Acción al hacer clic en el botón de inicio:
        iniciarButton.addActionListener(e -> {
            latch.countDown();  // Liberar bloqueo en main
            dispose();          // Cerrar la ventana
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
