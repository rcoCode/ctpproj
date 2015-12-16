package global;

import models.ToolType;
import models.Users;
import play.GlobalSettings;
import play.Application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yfle on 12/13/2015.
 * Our site:     https://hidden-sands-9597.herokuapp.com/
 */
public class global extends GlobalSettings {
    @Override
    public void onStart(Application application){
        if(Users.find.where().eq("username","admin").findUnique()==null) {
            Users admin = Users.createUser("admin", "ctpproj123", "fake@fakeemail.com");
            admin.admin = true;
            admin.save();
        }
        List<String> toolName = Arrays.asList("Power Tools", "Hand Tools", "Outdoor", "Interior", "Automotive");
        for(String i:toolName){
            if(ToolType.find.where().eq("name",i).findUnique()==null){
                ToolType type = ToolType.createType(i);
                type.save();
            }
        }

        super.onStart(application);
    }

    @Override
    public void onStop(Application application) {
        super.onStop(application);
    }
}
