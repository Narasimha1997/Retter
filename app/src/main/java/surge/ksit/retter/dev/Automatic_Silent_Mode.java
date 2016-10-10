package surge.ksit.retter.dev;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.os.IBinder;
import android.widget.Toast;

public class Automatic_Silent_Mode extends Service {
    SensorManager sensorAPI;
    Sensor LIGHT_SENSOR;
    AudioManager am;
    Functions_Subs getCustomRetterFunctions;
    public Automatic_Silent_Mode() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try{
            getCustomRetterFunctions=new Functions_Subs(this);
            sensorAPI=(SensorManager)getSystemService(SENSOR_SERVICE);
            LIGHT_SENSOR=sensorAPI.getDefaultSensor(Sensor.TYPE_LIGHT);
            getCustomRetterFunctions.Custom_Notifications("Retter","Automatic silent mode on",
                    android.support.v7.appcompat.R.drawable.notification_template_icon_bg,3,true);
            am=(AudioManager)getSystemService(AUDIO_SERVICE);
            Toast.makeText(Automatic_Silent_Mode.this, "Service started", Toast.LENGTH_SHORT).show();
            sensorAPI.registerListener(getOpticalDensity,LIGHT_SENSOR,SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
        }catch (Exception e){
            Toast.makeText(Automatic_Silent_Mode.this, "Unable to launch the service.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorAPI.unregisterListener(getOpticalDensity);
        getCustomRetterFunctions.Custom_Notifications(null,null,0,3,false);
        Toast.makeText(Automatic_Silent_Mode.this, "Service stopped", Toast.LENGTH_SHORT).show();
        if((am.getRingerMode())!=AudioManager.RINGER_MODE_NORMAL)
            am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }
    SensorEventListener getOpticalDensity=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.values[0]<1.0f){
                if((am.getRingerMode())!=AudioManager.RINGER_MODE_SILENT)
                    am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            }else{
                if((am.getRingerMode())!=AudioManager.RINGER_MODE_NORMAL)
                    am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
