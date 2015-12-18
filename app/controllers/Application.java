package controllers;

import models.Users;
import play.*;
import play.data.DynamicForm;
import play.mvc.*;

import views.html.*;

import java.util.regex.Pattern;

import static play.data.Form.form;

public class Application extends Controller {

    public Result index() {return ok(index.render("Tool Sharing"));}

    public Result getlogin(){ return ok(views.html.login.render(""));}

    public Result login() {
        DynamicForm userForm=form().bindFromRequest();
        String username=userForm.data().get("username");
        String password=userForm.data().get("password");

        Users users=Users.find.where().eq("username",username).findUnique();

        if(users != null && users.authenticate(password)) {
            session("user_id", users.id.toString());
            flash("success", "Welcome back " + users.username);
        } else {
            flash("error", "Invalid login. Check your username and password.");
            return redirect(routes.Application.getlogin());
        }

        return redirect(routes.Users.show(users.id));
    }

    public Result signup() { return ok(views.html.signup.render(""));}

    public Result newUser() {
        String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{4,12}$";
        Pattern pattern= Pattern.compile(USERNAME_PATTERN);

        DynamicForm userForm=form().bindFromRequest();
        String username=userForm.data().get("username");
        String password=userForm.data().get("password");
        String email=userForm.data().get("email");

        if(password.isEmpty() || username.isEmpty() || email.isEmpty()){
            flash("error","Empty Fields");
            return redirect(routes.Application.signup());
        }else if (!pattern.matcher(username).matches()){
            flash("error", "Invalid Character for Username");
            return redirect(routes.Application.signup());
        }else if (!pattern.matcher(password).matches()) {
            flash("error", "Invalid Character for Password");
            return redirect(routes.Application.signup());
        }else if (Users.find.where().eq("username", username).findUnique()!=null){
            flash("error","Duplicate Username");
            return redirect(routes.Application.signup());
        }else if (password.length()<8){
            flash("error", "Password Should Be At Least 8 Characters");
            return redirect(routes.Application.signup());
        }else {
            Users nUser = Users.createUser(username, password, email);
            nUser.save();
            flash("success", "Welcome new user " + nUser.username);
            session("user_id", nUser.id.toString());
            return redirect(routes.Users.show(nUser.id));
        }
    }

    public Result logout() {
        session().remove("user_id");
        return redirect(routes.Application.index());
    }

}
