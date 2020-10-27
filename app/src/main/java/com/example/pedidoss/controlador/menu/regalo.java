package com.example.pedidoss.controlador.menu;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pedidoss.R;

public class regalo extends AppCompatActivity {
    private EditText autotextlugar;
    private EditText autotextproducto;
    private EditText cantidad;
    private Button pagar,regresar;

    /// Aqui agregamos datos al autocomplement///
    private static final String[] REGALO = new String[]{
            "La Regalona", "Todo por 50", "FELIZ FIETSA", "LA SORPRESA"
    };

    private static final String[] REGALOMENU = new String[]{
            "REGALO SEXI", "Flores", "DULCES","Ropa"
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regalos);
        ///// Aqui le damos funcion a que muestre los datos alingresar cuna letra //
        AutoCompleteTextView restaurante = findViewById(R.id.seleccionR);
        //aqui se crea una lista para llamar a la lista de nuestro activity.xml
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, REGALO);
        restaurante.setAdapter(adapter);

        AutoCompleteTextView selec_menu = findViewById(R.id.selc_menu);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, REGALOMENU);
        selec_menu.setAdapter(adapter1);

////// Aqui estamos llamando los capos de texto para luego seruna validacion que este llenos para avilitar el boton pagar///
        autotextlugar = findViewById(R.id.seleccionR);
        autotextproducto = findViewById(R.id.selc_menu);
        cantidad = findViewById(R.id.cantidad);
        pagar= findViewById(R.id.btnpagar);
        //// Aqui vamos agregando los datos para su revision y habilitar el boton pagar/////
        autotextlugar.addTextChangedListener(pedido);
        autotextproducto.addTextChangedListener(pedido);
        cantidad.addTextChangedListener(pedido);
        ////boton regresar///
        regresar=(Button) findViewById(R.id.btnregresar);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regresar= new Intent(regalo.this, menuC.class);
                startActivity(regresar);
            }
        });


        /////se da funcion al boto pagar//
        pagar= findViewById(R.id.btnpagar);
        pagar.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"La compra se realizo Exitosamente",Toast.LENGTH_LONG).show();
                Intent pagar= new Intent(regalo.this, salirtodo.class);
                startActivity(pagar);
            }
        }));

    }

    /// Aqui se efectuara la validacion de los campos llenos para ser avilitado el boton pagar//
    private TextWatcher pedido= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        /// Aqui se pondran los campos que se debe comparar ////
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String local = autotextlugar.getText().toString().trim();
            String menu = autotextproducto.getText().toString().trim();
            String cant = cantidad.getText().toString().trim();
            pagar.setEnabled(!local.isEmpty() && !menu.isEmpty()  && !cant.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

}
