package global;

import models.ToolType;
import models.Tools;
import models.Users;
import play.GlobalSettings;
import play.Application;
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
        List<String> toolName = Arrays.asList("Power Tools", "Hand Tools", "Outdoor", "Automotive");
        for(String i:toolName){
            if(ToolType.find.where().eq("name",i).findUnique()==null){
                ToolType type = ToolType.createType(i);
                type.save();
            }
        }
        if(Users.find.where().eq("username","mike").findUnique() == null){
            Users user = Users.createUser("mike","alien400","alien@alien.com");
            user.save();
            if(ToolType.find.where().eq("name","Power Tools").findUnique() != null){
                models.Tools tool = new models.Tools();
                tool.name = "Electric Sander";
                tool.description = "Electrical Sander that smooth out woods and other materials";
                tool.available = true;
                tool.category = ToolType.find.where().eq("name","Power Tools").findUnique();
                tool.owner = user;
                tool.save();
            }
            if(ToolType.find.where().eq("name","Outdoor").findUnique() != null){
                models.Tools tool = new models.Tools();
                tool.name = "Leaf Blower";
                tool.description = "Powerful and easy to use leaf blower for the fall season";
                tool.available = true;
                tool.category = ToolType.find.where().eq("name","Outdoor").findUnique();
                tool.owner = user;
                tool.save();
            }
        }
        if(Users.find.where().eq("username","anahi").findUnique() == null){
            Users user = Users.createUser("anahi","12345678","email@email.com");
            user.save();
            if(ToolType.find.where().eq("name","Hand Tools").findUnique() != null){
                models.Tools tool = new models.Tools();
                tool.name = "Pick Pointed Hammer";
                tool.description = "This hammer is ideal for all jobs hammer related";
                tool.available = true;
                tool.category = ToolType.find.where().eq("name","Hand Tools").findUnique();
                tool.owner = user;
                tool.save();
            }
            if(ToolType.find.where().eq("name","Power Tools").findUnique() != null){
                models.Tools tool = new models.Tools();
                tool.name = "Power Drills";
                tool.description = "Good power drill with different attachments for different jobs";
                tool.available = true;
                tool.category = ToolType.find.where().eq("name","Power Tools").findUnique();
                tool.owner = user;
                tool.save();
            }
        }
        super.onStart(application);
    }

    @Override
    public void onStop(Application application) {
        super.onStop(application);
    }
}
