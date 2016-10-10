package surge.ksit.retter.dev;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class Message_Receiver_Service extends Service {
    NotificationManager setNotification; int NotificationID;
    Retter_Command_Parser RETTER_STD_PARSER;
    Functions_Subs RETTER_STD_FUNCTIONS;
    public Message_Receiver_Service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            RegisterDefaultListener();
            Toast.makeText(Message_Receiver_Service.this, "Service started", Toast.LENGTH_SHORT).show();
            RETTER_STD_FUNCTIONS=new Functions_Subs(this);
        }catch (Exception e){
            Toast.makeText(Message_Receiver_Service.this, "Message service couldn't start.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(Message_Receiver_Service.this, "Service stopped", Toast.LENGTH_SHORT).show();
        setNotification.cancel(NotificationID);
    }
    BroadcastReceiver contentReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            SmsMessage[] messages=null;
            Bundle b=intent.getExtras();
            if(action.equals("android.provider.Telephony.SMS_RECEIVED")){
                if(b!=null){
                    final Object[] pdusObj=(Object[])b.get("pdus");
                    for(int i=0; i<pdusObj.length; i++)
                    {
                        SmsMessage smsMessage=SmsMessage.createFromPdu((byte[])pdusObj[i]);
                        String PhoneNumber=smsMessage.getDisplayOriginatingAddress();
                        String dataSms=smsMessage.getDisplayMessageBody();
                        Toast.makeText(context,PhoneNumber+": "+dataSms, Toast.LENGTH_SHORT).show();
                        PASS_TO_PARSER(dataSms, PhoneNumber);
                    }
                }
            }

        }
    };
    public void RegisterDefaultListener(){
        IntentFilter filter=new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(contentReceiver,filter);
    }
    public void PASS_TO_PARSER(String MessageBody, String PhoneNumber){
        RETTER_STD_FUNCTIONS=new Functions_Subs(this);
        boolean checkState=RETTER_STD_FUNCTIONS.Convert_To_Command(MessageBody, PhoneNumber);
        if(!checkState)
            Toast.makeText(Message_Receiver_Service.this, "Couldn't execute one or more commands, Refer to the manual," +
                    "this happens because the Phone is already in the state you tried to turn on.So don't worry" +
                    "", Toast.LENGTH_LONG).show();
    }
    public void Check_PhoneNumber(String PhoneNo){

    }
}
