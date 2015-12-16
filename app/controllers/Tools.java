package controllers;

import models.*;
import models.Users;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rebeca on 12/13/2015.
 */
public class Tools extends Controller{

    @Security.Authenticated(UserAuth.class)
    public Result index(){
        List<ToolType> stypes = ToolType.find.all();
        return ok(views.html.tools.index.render(stypes));
    }

    @Security.Authenticated(UserAuth.class)
    public Result create(){
        Form<models.Tools> toolsForm = Form.form(models.Tools.class).bindFromRequest();
        String type_id = toolsForm.data().get("type_id");
        ToolType types = ToolType.find.byId(Long.parseLong(type_id));
        if(types == null){
            flash("error","A tool type is required");
            return redirect(routes.Tools.index());
        }
        Long id=Long.parseLong(session().get("user_id"));
        models.Users own = Users.find.byId(id);
        models.Tools ntool = toolsForm.get();
        ntool.category = types;
        ntool.owner = own;
        ntool.available = true;
        ntool.save();
        flash("success","Posted new Tool:"+ntool.name);
        return redirect(routes.Users.show(id));

    }

    public Result show(Long id){
        models.Tools mytool = models.Tools.find.byId(id);
        if(mytool == null){
            flash("error","error finding tool page please try again");
            return notFound("Not Found");
        }
        else{
            List<Comments> toolComs = mytool.commentList;
            List<Request> toolReq = mytool.reqList;
            if (toolComs == null){ toolComs = new ArrayList<>();}
            return ok(views.html.tools.show.render(mytool,toolComs,toolReq));
        }
    }

    @Security.Authenticated(UserAuth.class)
    public Result remove(Long id){
        models.Tools mytool = models.Tools.find.byId(id);
        if(mytool == null){
            flash("error","error finding tool page please try again");
            return notFound("Not Found");
        }
        Long u_id = Long.parseLong(session().get("user_id"));
         if(u_id != mytool.owner.id){
            flash("error","Only the tool owner can delete a tool");
             return notFound("Error");
        }
        else{
            mytool.delete();
             flash("success","Tool Deleted!");
            return redirect(routes.Users.show(u_id));
        }
    }

}
