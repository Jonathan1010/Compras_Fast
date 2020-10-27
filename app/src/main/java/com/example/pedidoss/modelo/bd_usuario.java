package com.example.pedidoss.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class bd_usuario {
    Context c;
    usuario u;
    ArrayList<usuario> lista;
    SQLiteDatabase sql;
    String bd = "bdUsuario";
    //creamos la tabla
    String tabla = "create table if not exists usuario(id integer primary key autoincrement, usuario text, password text, nombre text, apellido text)";


    //Metodo constructor
    public bd_usuario(Context c){
        this.c=c;
        //creamos la base de datos
        sql=c.openOrCreateDatabase(bd, c.MODE_PRIVATE, null);
        //creamos la tabla
        sql.execSQL(tabla);
        u=new usuario();
    }

    //metodo para insertarel usuario
    public boolean insertUsuario(usuario u){
        if(buscar(u.getUsuario())==0){
            ContentValues content = new ContentValues();
            content.put("usuario",u.getUsuario());
            content.put("password",u.getPassword());
            content.put("nombre",u.getNombre());
            content.put("apellido",u.getApellidos());
            //nos devuelve el usuario ya insertado en nuestra base de datos
            return  (sql.insert("usuario",null,content)>0);
        }else{
            return false;
        }
    }

    //Metodo buscar
    public int buscar(String u){
        int a = 0;
        lista=selectUsuario();
        //este for sirve para comproibar si ya estamos registrados en nuestra base de datos nos busca en una lista
        for(usuario us:lista){
            //obtenemos el usuario y lo comparamos si esta o no mediante la variable a
            if(us.getUsuario().equals(u)){
                //si a = 0 entonces no nos devuelve nada, encambio si a en la linea 54 nos suma 1 nos busca la persona ya registrada
                a++;
            }
        }
        return a;
    }

    //Nos devuelve todos los usuarios de la base de datos
    public ArrayList<usuario> selectUsuario(){
        //creamos una lista para almacenar los personas registradas
        ArrayList<usuario> lista = new ArrayList<usuario>();
        lista.clear();
        //aqui buscamos todas las personas registradas
        Cursor cursor=sql.rawQuery("select * from usuario" , null);
        if(cursor!= null && cursor.moveToFirst()){
            do{
                //extraemos todos los datos de la tabla
                //se los ordena por columnas de 0 hasta el numero de campos que corresponde al registro
                usuario usuario = new usuario();
                usuario.setId(cursor.getInt(0));
                usuario.setUsuario(cursor.getString(1));
                usuario.setPassword(cursor.getString(2));
                usuario.setNombre(cursor.getString(3));
                usuario.setApellidos(cursor.getString(4));
                //añadimos a una lista
                lista.add(usuario);
                //El cursor comienza antes de la primera fila de resultados, por lo que en la primera iteración se mueve al primer resultado si existe .
                //Si el cursor está vacío, o la última fila ya ha sido procesada, entonces el ciclo sale ordenadamente.
            }while (cursor.moveToNext());
        }
        //retornamos la lista
        return lista;
    }

    //Este metodo sirve para comparar los datos ya registrados por los daos que se inicia sesion
    public int login(String user, String pasword){
        int a = 0;
        Cursor crs = sql.rawQuery("select * from usuario" , null);
        if(crs!= null && crs.moveToFirst()){
           do{
               //compara datos de la columa 1 en este caso es del usuario y de  la columna dos que es de la contraseña
               if(crs.getString(1).equals(user) && crs.getString(2).equals(pasword)){
                   a++;

               }
               //El cursor comienza antes de la primera fila de resultados, por lo que en la primera iteración se mueve al primer resultado si existe .
               //Si el cursor está vacío, o la última fila ya ha sido procesada, entonces el ciclo sale ordenadamente.
           }while(crs.moveToNext());
        }
        return a;
    }

    //este metodo nos sirve para buscar a la persona ya registrada por el uasuario
    public usuario getUsuario(String usuario, String contraseña){
        //devuelve la lista de usuarios
        lista = selectUsuario();
        //en este for se compara el usuario y la contraseña si es igual o no al usuario ya registrado
        for(usuario us:lista){
            if(u.equals(us.getUsuario().equals(usuario) && us.getPassword().equals(contraseña))){
                return us;
            }
        }
        //si no compara nada o no hay ningun valor devuelve null
        return null;
    }

    //este metodo nos sirve para buscar a la persona por wl ID ya registrada
    public usuario getUsuarioPorId (int id){
        //devuelve la lista de usuarios
        lista = selectUsuario();
        //compara al usuario por el id en este ciclo for
        for(usuario usid:lista){
            if(usid.equals(usid.getId()==id)){
                return usid;
            }
        }
        //si no compara nada o no hay ningun valor devuelve null
        return null;
    }
}
