package bbdd;

public class Cliente {
    private String dni;
    private int id_cliente;
    private String direccion;
    private int telefono;
    private String nombre;

    public Cliente(String dni, int id_cliente, String direccion, int telefono, String nombre) {
        this.dni = dni;
        this.id_cliente = id_cliente;
        this.direccion = direccion;
        this.telefono = telefono;
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
