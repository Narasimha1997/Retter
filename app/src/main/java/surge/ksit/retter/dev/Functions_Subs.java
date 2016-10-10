package surge.ksit.retter.dev;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Message;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Functions_Subs extends Activity {
    //contains all reusable implementations of the app
    Context AppContext;
    public boolean GLOBAL_CALLBACK = true;

    public Functions_Subs(Context AppContext) {
        this.AppContext = AppContext;
    }

    public boolean IO_File_Initialise(File file) {
        if (!file.exists()) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                return false;
            }
        } else {
            file.delete();
            try {
                return file.createNewFile();
            } catch (IOException e) {
                return false;
            }
        }
    }

    public boolean IO_FileWriter(File file, String Data) {
        if (IO_File_Initialise(file)) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(Data);
                writer.flush();
                writer.flush();
                return true;
            } catch (IOException e) {
                return false;
            }
        } else return false;
    }

    public boolean IO_FileReader(BufferedReader Reader, StringBuilder StringsList) {
        String LOCAL_STRING;
        try {
            while ((LOCAL_STRING = Reader.readLine()) != null) {
                StringsList.append(LOCAL_STRING);
            }
            Reader.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public String IO_InFileSearch(String key, String file) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openFileInput(file)));
            StringBuilder b = new StringBuilder();
            if (IO_FileReader(bufferedReader, b)) {
                //Check for a given string in the file:
                String list = b.toString();
                if (list.contains(key)) return key;
                    //Returns the key if String is found in the file, else returns null
                else return null;
            } else return null;
        } catch (IOException e) {
            return null;
        }
    }

    public boolean IO_Append_NewLine(File file, String line) {
        //TODO: function always returns false if file does not exist, Please make sure you

        if (file.exists()) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.append("\n" + line);
                writer.flush();
                writer.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        } else return false;
    }

    public void SMSReceive() {
    }

    public boolean SilentMode(boolean activation) {
        AudioManager getAudioApi = (AudioManager) AppContext.getSystemService(AUDIO_SERVICE);
        if (activation) {
            if ((getAudioApi.getRingerMode()) != AudioManager.RINGER_MODE_SILENT) {
                getAudioApi.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                return true;
            } else return false;
        } else {
            if ((getAudioApi.getRingerMode()) != AudioManager.RINGER_MODE_NORMAL) {
                getAudioApi.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                return true;
            } else return false;
        }
    }

    public boolean Custom_Notifications(String NotifHeader, String NotifBody, int DrawableIcon, int NotificationID, boolean Activation) {
        NotificationManager manageNotifications = (NotificationManager) AppContext.getSystemService(NOTIFICATION_SERVICE);
        try {
            if (Activation) {
                Notification.Builder setNotification = new Notification.Builder(this);
                setNotification.setContentTitle(NotifHeader);
                setNotification.setContentText(NotifBody);
                setNotification.setSmallIcon(DrawableIcon);
                manageNotifications.notify(NotificationID, setNotification.build());
            } else
                manageNotifications.cancel(NotificationID);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Convert_To_Command(String MessageBody, String PhoneNumber) {

        boolean s1 = true, s2 = true, s3 = true, s4 = true, s5 = true, s6 = true, s7 = true;
        String LowerCase_Message = MessageBody.toLowerCase();

        if (LowerCase_Message.contains("to retter:") || LowerCase_Message.contains("retter ")) {
            if (LowerCase_Message.contains("on silent")) {
                s1 = SilentMode(true);
            }
            if (LowerCase_Message.contains("on call reject")) {

            }
            if (LowerCase_Message.contains("off silent")) {
                s3 = SilentMode(false);
            }
            if (LowerCase_Message.contains("off call reject")) {

            }
            if (LowerCase_Message.contains("automatic silent")) {
                AppContext.startService(new Intent(this, Automatic_Silent_Mode.class));
                s5 = true;
            }
            if (LowerCase_Message.contains("off automatic silent")) {
                s6 = AppContext.stopService(new Intent(this, Automatic_Silent_Mode.class));
            }
            if (LowerCase_Message.contains("call me")) {
                if (PhoneNumber != null) {
                    GLOBAL_CALLBACK = true;
                    Call_Back_Starter(PhoneNumber);
                    s7 = true;
                } else s7 = false;
            } else {
                return true;
            }
            if (s1 && s3 && s5 && s6 && s7) return true;
            else return false;

        } else return true;
    }

    public void Call_Back(String PhoneNumber, boolean CallBackActivated) {
        if (CallBackActivated) {
            try {
                Intent STD_CALLER = new Intent(Intent.ACTION_CALL);
                STD_CALLER.setData(Uri.parse("tel:" + PhoneNumber));
                STD_CALLER.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                STD_CALLER.addFlags(CONTEXT_IGNORE_SECURITY);
                AppContext.startActivity(STD_CALLER);
            } catch (SecurityException e) {
                Intent STD_DIALER = new Intent(Intent.ACTION_DIAL);
                STD_DIALER.setData(Uri.parse("tel:" + PhoneNumber));
                AppContext.startActivity(STD_DIALER);
            }
        }
    }

    public void Call_Back_Starter(String PhoneNumber) {
        Call_Back(PhoneNumber, GLOBAL_CALLBACK);
    }

}