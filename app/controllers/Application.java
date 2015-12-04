package controllers;

import models.Users;
import play.*;
import play.data.DynamicForm;
import play.mvc.*;

import views.html.*;

import static play.data.Form.form;

public class Application extends Controller {

    public Result index() {return ok(index.render(""));}

    public Result login(){ return ok(views.html.login.render(""));}

    public Result newLogin() {
        DynamicForm userForm=form().bindFromRequest();
        String username=userForm.data().get("username");
        String password=userForm.data().get("password");

        Users users=Users.find.where().eq("username",username).findUnique();

        if(users != null && users.authenticate(password)) {
            session("user_id", users.id.toString());
            flash("success", "Welcome back " + users.username);
        } else {
            flash("error", "Invalid login. Check your username and password.");
            return redirect(routes.Application.index());
        }

        return redirect(routes.Application.index());
    }

    public Result signup() { return ok(views.html.signup.render(""));}

    public Result userSignup() {
        DynamicForm userForm=form().bindFromRequest();
        String username=userForm.data().get("username");
        String password=userForm.data().get("password");
        String email=userForm.data().get("email");

        Users nUser=Users.createUser(username,password,email);
        if (nUser == null){
            flash("error","Invalid User");
            return redirect(routes.Application.index());
        }
        nUser.save();

        flash("success", "Welcome new user " + nUser.username);
        session("user_id", nUser.id.toString());
        return redirect(routes.Application.index());
    }

    public Result logout() {
        session().remove("user_id");
        return redirect(routes.Application.index());
    }

}
