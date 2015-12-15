package controllers;

import models.*;
import models.Tools;
import models.Users;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.text.DateFormat;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rebeca on 12/14/2015.
 */
public class comments extends Controller{
    @Security.Authenticated(UserAuth.class)
    public Result add(Long id){
        if (session().get("user_id") == null){
            flash("error","Must be logged in to post comment");
            return redirect(routes.Tools.show(id));
        }
        if (Tools.find.byId(id) == null){
            flash("error","Tool not found");
        }
        Form<Comments> commentsForm = Form.form(Comments.class).bindFromRequest();
        Long u_id = Long.parseLong(session().get("user_id"));
        Users post = Users.find.byId(u_id);
        Comments com = commentsForm.get();
        com.poster = post;
        com.topic = Tools.find.byId(id);
        Date submit = new Date();
        com.postTime = submit;
        com.save();
        flash("success","Comment Posted");
        return redirect(routes.Tools.show(id));
    }

}
