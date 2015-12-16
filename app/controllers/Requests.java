package controllers;

import javafx.util.converter.DateStringConverter;
import models.*;
import models.Tools;
import models.Users;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import play.data.DynamicForm;
import play.data.Form;
import play.data.format.Formats;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by rebeca on 12/15/2015.
 */
public class Requests extends Controller{
    @Security.Authenticated(UserAuth.class)
    public Result borrow(Long id){
        models.Tools want = Tools.find.byId(id);
        if(want == null){
            flash("error","Could not find tool please try again");
            return redirect(routes.Tools.show(id));
        }
        Users borrower = Users.find.byId(Long.parseLong(session().get("user_id")));
        Users owns = want.owner;
        Form<Request> requestForm = Form.form(Request.class).bindFromRequest();
        String text = requestForm.data().get("text");
        String startTime = requestForm.data().get("startDate");
        String endTime = requestForm.data().get("endDate");

        org.joda.time.format.DateTimeFormatter format = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
        DateTime begin = format.parseDateTime(startTime);
        DateTime due = format.parseDateTime(endTime);

        Request nReq = Request.create(text,begin,due,want,borrower,owns);
        nReq.save();
        flash("success","Request Sent");
        return redirect(routes.Tools.show(id));
    }
    public Result acceptRequest(){
        return ok();
    }
}
