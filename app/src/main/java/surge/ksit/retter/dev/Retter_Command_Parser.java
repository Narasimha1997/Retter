package surge.ksit.retter.dev;

import android.content.Context;
import android.support.annotation.BoolRes;
import android.widget.Toast;

public class Retter_Command_Parser {
    Context AppContext;
    public Retter_Command_Parser(Context AppContext){
        this.AppContext=AppContext;
    }



    public boolean Convert_To_Command(String MessageBody){

        boolean s1=true, s2=true, s3=true, s4=true;
        String LowerCase_Message= MessageBody;

        if(LowerCase_Message.contains("To Retter:")){
            if(LowerCase_Message.contains("On Silent Mode")){

            }
            if(LowerCase_Message.contains("on call reject")){

            }
            if(LowerCase_Message.contains("off silent mode")){

            }
            if(LowerCase_Message.contains("off call reject")){

            }
            else{
                return true;
            }
            if(s1&&s3) return true;
            else return false;

        }
        else return true;
    }

}
