package global;

import models.Users;
import play.GlobalSettings;
import play.Application;

/**
 * Created by yfle on 12/13/2015.
 */
public class global extends GlobalSettings {
    @Override
    public void onStart(Application application){
        if(Users.find.where().eq("username","admin").findUnique()==null) {
            Users admin = Users.createUser("admin", "ctpproj123", "fake@fakeemail.com");
            admin.admin = true;
            admin.save();
        }


        super.onStart(application);
    }

    @Override
    public void onStop(Application application) {
        super.onStop(application);
    }
}
