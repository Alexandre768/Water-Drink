package com.example.water_drink;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
private Button botao;
private EditText ediText;
private TimePicker timePicker;
private boolean configurado=false;
private SharedPreferences preferencias;
private int hora;
private int minuto;
private int interv;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botao=findViewById(R.id.btnnotifica);
        ediText=findViewById(R.id.intervalo);
        timePicker=findViewById(R.id.relogio);
        timePicker.setIs24HourView(true);
        preferencias=getSharedPreferences("db", Context.MODE_PRIVATE);
        configurado=preferencias.getBoolean("ativado",false);

        if(configurado){
            botao.setText(R.string.pausar);
            botao.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.black));
            int interv=preferencias.getInt("intervalo",0);
            int hora =preferencias.getInt("hora",timePicker.getCurrentHour());
            int minuto =preferencias.getInt("minuto",timePicker.getCurrentMinute());
            ediText.setText(String.valueOf(interv));
            timePicker.setCurrentHour(hora);
            timePicker.setCurrentMinute(minuto);
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void button4(View view){
        String sInter=ediText.getText().toString();
        if (sInter.isEmpty()) {
            Toast.makeText(MainActivity.this, "O intervalo não foi informado",Toast.LENGTH_LONG).show();
            return;
        }
      if(!configurado){
          botao.setText(R.string.pausar);
          botao.setBackgroundTintList(ContextCompat.getColorStateList(this,android.R.color.black));
          configurado=true;
          SharedPreferences.Editor editor=preferencias.edit();
          editor.putBoolean("ativado",true);
          editor.putInt("intervalo",interv);
          editor.putInt("hora",hora);
          editor.putInt("minuto",minuto);
          editor.apply();
      }else{
          botao.setText(R.string.Notificar);
          botao.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.cor_boton));
          configurado=false;
          SharedPreferences.Editor editor=preferencias.edit();
          editor.putBoolean("ativado",false);
          editor.remove("intervalo");
          editor.remove("hora");
          editor.remove("minuto");
          editor.apply();

      }
        hora = timePicker.getCurrentMinute();
        minuto = timePicker.getCurrentMinute();
        interv= Integer.parseInt(sInter);
        Log.d("Teste"," Hora " +hora+ " Minuto " +minuto+ " Intervalo: "+ interv);
    }

}