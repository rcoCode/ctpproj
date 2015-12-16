package controllers;

import models.Request;
import models.Tools;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

import play.mvc.Security;
import views.html.*;

/**
 * Created by rebeca on 12/11/2015.
 */
public class Users extends Controller{
    @Security.Authenticated(UserAuth.class)
    public Result show(Long id){
        if(Long.parseLong(session().get("user_id")) != id){
            flash().put("error","Nice try, but that is not your profile!");
            return redirect(routes.Application.index());
        }
        models.Users auser = models.Users.find.byId(id);
        if(auser == null){
            return notFound("not found");
        }
        else {
            List<models.Tools> mytools = auser.toolList;
            List<Request> accepted = auser.borrow;
            if(mytools == null){
                mytools = new ArrayList<>();
                return ok(views.html.Users.profile.render(mytools,accepted));
            }
            if(accepted == null){
                accepted = new ArrayList<>();
                return ok(views.html.Users.profile.render(mytools,accepted));
            }
            return ok(views.html.Users.profile.render(mytools,accepted));
        }
    }
}
