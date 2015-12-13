package controllers;

import models.Tools;
import play.data.Form;
import play.mvc.Controller;
import models.ToolType;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;


/**
 * Created by yfle on 12/13/2015.
 */
public class ToolTypes extends Controller{
    public Result index(){
        List<ToolType> types = ToolType.find.all();
        return ok(views.html.types.index.render(types));
    }

    public Result create(){
        ToolType type = Form.form(ToolType.class).bindFromRequest().get();
        type.save();
        flash("success", "saved new ToolType" + type.name);
        return redirect(routes.ToolTypes.index());
    }

    public Result show(Long id){
        ToolType type = ToolType.find.byId(id);
        if (type==null){
            return notFound("not Found");
        }else {
            List<Tools> tools = type.toolList;
            return ok(views.html.types.show.render(type, tools));
        }
    }


}
