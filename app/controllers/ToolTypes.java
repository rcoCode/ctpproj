package controllers;

import models.Tools;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import models.ToolType;
import play.mvc.Result;
import play.mvc.Security;
import models.Users;
import java.util.List;


/**
 * Created by yfle on 12/13/2015.
 */
public class ToolTypes extends Controller{
    public Result index(){
        Users a = Users.find.where().eq("username", "admin").findUnique();
        List<ToolType> types = ToolType.find.all();
        return ok(views.html.types.index.render(types,a));
    }

    @Security.Authenticated(AdminAuth.class)
    public Result create(){
        DynamicForm type = Form.form().bindFromRequest();
        String name = type.data().get("name");
        ToolType newType = ToolType.createType(name);
        if (newType==null){
            flash("error","Already a ToolType");
            return redirect(routes.Application.index());
        }
        newType.save();
        flash("success", "saved new ToolType" + newType.name);
        return redirect(routes.ToolTypes.index());
    }

    public Result show(Long id){
        Users a = Users.find.where().eq("username", "admin").findUnique();
        ToolType type = ToolType.find.byId(id);
        if(session().get("user_id")!=null){
            Long user_id = Long.parseLong(session().get("user_id"));
            List<Tools> tools = Tools.find.where().eq("category_id",id).eq("available",true).ne("owner_id", user_id).orderBy("name").findList();
            return ok(views.html.types.show.render(type, tools));
        }

        if (type==null){
            flash("error","No tools found");
            return notFound("not Found");
        }else {
            List<Tools> tools = Tools.find.where().eq("category_id",id).eq("available",true).orderBy("name").findList();
            return ok(views.html.types.show.render(type, tools));
        }
    }


}
