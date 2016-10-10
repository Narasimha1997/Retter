package surge.ksit.retter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import surge.ksit.retter.dev.Automatic_Silent_Mode;
import surge.ksit.retter.dev.Functions_Subs;
import surge.ksit.retter.dev.Message_Receiver_Service;
public class MainActivity_Test extends AppCompatActivity {
    Functions_Subs RETTER_STDS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity__test);
        RETTER_STDS=new Functions_Subs(this);
    }
    public void Start(View v)
    {
        startService(new Intent(this,Message_Receiver_Service.class));
        Toast.makeText(MainActivity_Test.this, "Retter started working!!", Toast.LENGTH_SHORT).show();
    }
    public void startAutoSilent(View v){
        startService(new Intent(this, Automatic_Silent_Mode.class));
    }
    public void stop(View v){
        stopService(new Intent(this,Automatic_Silent_Mode.class));
    }
    public void callBack(View v){
        RETTER_STDS=new Functions_Subs(this);
        RETTER_STDS.GLOBAL_CALLBACK=false;
    }
}
