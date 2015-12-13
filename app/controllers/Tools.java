package controllers;

import models.*;
import models.Users;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * Created by rebeca on 12/13/2015.
 */
public class Tools extends Controller{

    public Result index(){
        List<ToolType> stypes = ToolType.find.all();
        return ok(views.html.tools.index.render(stypes));
    }

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
        ntool.save();
        flash("success","Posted new Tool:"+ntool.name);
        return redirect(routes.Users.show(id));

    }

}
