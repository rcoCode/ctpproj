package controllers;

import models.Tools;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;
import views.html.*;

/**
 * Created by rebeca on 12/11/2015.
 */
public class Users extends Controller{
    public Result show(Long id){
        models.Users auser = models.Users.find.byId(id);
        if(auser == null){
            return notFound("not found");
        }
        else {
            List<Tools> tools= auser.toolList;
            return ok(views.html.Users.profile.render(""));
        }
    }
}
