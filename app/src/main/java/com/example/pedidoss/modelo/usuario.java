package com.example.pedidoss.modelo;

public class usuario {
    private int id;
    private String Nombre , Apellidos, Usuario , Password;


    //Metodo constructor vacio usuario
    public usuario() {
    }

    //Metodo constructor usuario
    public usuario(String nombre, String apellidos, String usuario, String password) {
        Nombre = nombre;
        Apellidos = apellidos;
        Usuario = usuario;
        Password = password;
    }

    //Metodo para verificar que todos los campos del registro se llenen
    public boolean isNull(){
        if(Nombre.equals("") && Apellidos.equals("") && Usuario.equals("") && Password.equals("")){
            return false;
        }else {
            return true;
        }
    }

    //Metodo to String
    @Override
    public String toString() {
        return "usuario{" +
                "id=" + id +
                ", Nombre='" + Nombre + '\'' +
                ", Apellidos='" + Apellidos + '\'' +
                ", Usuario='" + Usuario + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }

    //metodos get y set
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

}
