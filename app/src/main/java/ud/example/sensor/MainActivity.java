package ud.example.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView ValorX, ValorY, ValorZ, LogText;
    private float X, Y, Z;
    private ScrollView scrollView;
    private SensorManager Sensores;
    private Sensor SensorAce;
    private SoundPool sPool;  //////Borrar ap que sirva
    private int sound3;//Borrar pa que sirva
    private CheckBox Latigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogText = findViewById(R.id.textView7);
        ValorX = findViewById(R.id.textView9);
        ValorY = findViewById(R.id.textView15);
        ValorZ = findViewById(R.id.textView17);
        scrollView = findViewById(R.id.scrollView);
        Latigo = findViewById(R.id.checkBox);
        Sensores = (SensorManager) getSystemService(SENSOR_SERVICE);
        ValorX.setText("0");
        ValorY.setText("0");
        ValorZ.setText("0");
        X=0; Y=0; Z=0;

        sPool = new SoundPool( 6, AudioManager.STREAM_MUSIC, 0); //Borrar Pa que sirva
        sound3 = sPool.load( this, R.raw.latigo, 1);//Borrar pa que sirva


        Sensores = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> ListSensores = Sensores.getSensorList(Sensor.TYPE_ALL);
        for(Sensor sensor:ListSensores){
            log("Sensor: "+ sensor.getName().toString());
        }

        SensorAce = Sensores.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensores.registerListener( this,SensorAce,SensorManager.SENSOR_DELAY_NORMAL);

    }
    private void log(String s){
        LogText.append("\n" + s);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if(mySensor.getType()==Sensor.TYPE_ACCELEROMETER){

            try{
                float Xa = sensorEvent.values[0];
                float Ya = sensorEvent.values[1];
                float Za = sensorEvent.values[2];

                if(Math.abs(Xa-X)>=5 || Math.abs(Ya-Y)>=1 || Math.abs(Za-Z)>=1){
                //if(Math.abs(Xa-X)>=1 ){
                    ValorX.setText(String.valueOf(sensorEvent.values[0]+" Valorx "));
                    ValorY.setText(String.valueOf(sensorEvent.values[1]));
                    ValorZ.setText(String.valueOf(sensorEvent.values[2]));
                    if(Latigo.isChecked() && sensorEvent.values[0] >= 6){
                            suenabotonLatigo();}
                }
                X = sensorEvent.values[0];
                Y = sensorEvent.values[1];
                Z = sensorEvent.values[2];

            }catch (Exception ex){}
        }

    }

    /*public void onSensorLat(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if(mySensor.getType()==Sensor.TYPE_ACCELEROMETER){

            try{
                float Xa = sensorEvent.values[0];
                float Ya = sensorEvent.values[1];
                float Za = sensorEvent.values[2];

                if() {
                    if (Math.abs(Xa - X) >= 1) {
                        ValorX.setText(String.valueOf(sensorEvent.values[0] + " Valorx "));
                        ValorY.setText(String.valueOf(sensorEvent.values[1]));
                        ValorZ.setText(String.valueOf(sensorEvent.values[2]));
                        suenabotonLeon();
                    }
                    X = sensorEvent.values[0];
                    Y = sensorEvent.values[1];
                    Z = sensorEvent.values[2];
                }
            }catch (Exception ex){}
        }

    }*/





    public void suenabotonLatigo(){sPool.play(sound3, 1, 1, 1, 0, 1);}

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}