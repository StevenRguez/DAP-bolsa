import java.util.Objects;

/**
 * @brief Valor class.
 * @details Clase que define la estructura de un valor financiero, con su tipo de activo, nombre, precio y cambio (porcentaje).
 */
public class Valor {
    /// Atributos
    String tipoDeActivo_;           /**< Tipo de activo: Materias Primas, Forex, Bolsa de Valores. */
    String nombre_;                 /**< Nombre representativo del activo. */
    Double precio_;                 /**< Precio actual del activo. */
    Double cambio_;                 /**< Porcentaje en el cambio del activo */

    /**
     * @brief Constructor.
     * @param tipoDeActivo  [String] Tipo de activo: Materias Primas, Forex, Bolsa de Valores.
     * @param nombre [String] Nombre representativo del activo.
     * @param precio [Double] Precio actual del activo.
     * @param cambio [Double] Porcentaje de cambio del activo.
     */
    Valor(String tipoDeActivo, String nombre, Double precio, Double cambio) {
        this.tipoDeActivo_ = tipoDeActivo;
        this.nombre_ = nombre;
        this.precio_ = precio;
        this.cambio_ = cambio;
    }

    /**
     * @brief getter del tipo de activo.
     * @return [String] Tipo de activo.
     */
    public String getTipoDeActivo() { return this.tipoDeActivo_; }
    /**
     * @brief getter del nombre representativo del activo.
     * @return [String] Nombre representativo del activo.
     */
    public String getNombre()       { return this.nombre_; }
    /**
     * @brief getter del precio actual del activo.
     * @return [Double] Precio actual del activo.
     */
    public Double getPrecio()       { return this.precio_; }
    /**
     * @brief getter del porcentaje en el cambio del activo.
     * @return [Double] Porcentaje de cambio del activo.
     */
    public Double getCambio()       { return this.cambio_; }
    /**
     * @brief setter del tipo de activo.
     * @param tipoDeActivo [String] Tipo de activo.
     */
    public void setTipoDeActivo(String tipoDeActivo) {  this.tipoDeActivo_ = tipoDeActivo;  }
    /**
     * @brief setter del nombre representativo del activo.
     * @param nombre [String] Nombre representativo del activo.
     */

    public void setNombre(String nombre)             {  this.nombre_ = nombre;  }
    /**
     * @brief setter del precio actual del activo.
     * @param precio [Double] Precio actual del activo.
     */
    public void setPrecio(Double precio)             {  this.precio_ = precio;  }
    /**
     * @brief setter del porcentaje de cambio del activo.
     * @param cambio [Double] Porcentaje de cambio del activo.
     */
    public void setCambio(Double cambio)             {  this.cambio_ = cambio;  }
    /**
     * @brief Metodo para mostrar la información del objeto como una cadena de caracteres.
     * @return [String] Información del valor.
     */

    public String toString() {
        return "Nombre: " + this.nombre_ + " | Precio: " + this.precio_ + " | Cambio 1D: " + this.cambio_;
    }
    /**
     * @brief Metodo para mostrar la información del objeto por pantalla.
     */
    public void mostrarPorPantalla() {
        System.out.println(this.toString());
    }

    /**
     * @brief Metodo para comparar si los precios de dos activos son iguales.
     * @param precio [Double] Precio a comparar.
     * @return [boolean] True si son iguales, False en caso contrario.
     */
    public boolean compararPrecios(Double precio) {
        return !Objects.equals(precio, this.precio_);
    }
    /**
     * @brief Metodo para comparar si el porcentaje de cambio de dos activos es igual.
     * @param cambio [Double] Porcentaje de cambio a comparar.
     * @return [boolean] True si son iguales, False en caso contrario.
     */
    public boolean compararCambio(Double cambio) {
        return !Objects.equals(cambio, this.cambio_);
    }
}
