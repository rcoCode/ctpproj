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
    public Result index(Long id){
        models.Tools want = models.Tools.find.byId(id);
        if(want == null){
            flash("error","error finding tool page please try again");
            return notFound("Not Found");
        }
        else{
            return ok(views.html.request.index.render(want));
        }
    }

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

        org.joda.time.format.DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime begin = format.parseDateTime(startTime);
        DateTime due = format.parseDateTime(endTime);

        Request nReq = Request.create(text,begin,due,want,borrower,owns);
        nReq.save();
        flash("success","Request Sent");
        return redirect(routes.Tools.show(id));
    }

    @Security.Authenticated(UserAuth.class)
    public Result show(Long id){
        Request update = Request.find.byId(id);
        if(update == null){
            flash("error","Can't find request!");
            return notFound("Not Found");
        }
        if (Long.parseLong(session().get("user_id")) != update.lender.id){
            flash("error","You are not the owner of this tool.");
            return redirect(routes.Tools.show(id));
        }
        return ok(views.html.request.show.render(update));
    }

    @Security.Authenticated(UserAuth.class)
    public Result acceptRequest(Long id){
        Request update = Request.find.byId(id);
        if(update == null){
            flash("error","Can't find request!");
            return notFound("Not Found");
        }
        if (Long.parseLong(session().get("user_id")) != update.lender.id){
            flash("error","You are not the owner of this tool.");
            return redirect(routes.Tools.show(id));
        }
        update.status = true;
        update.wanted.available = false;
        update.save();
        update.wanted.save();
        return redirect(routes.Users.show(Long.parseLong(session().get("user_id"))));
    }

    @Security.Authenticated(UserAuth.class)
    public Result dismiss(Long id){
        Request update = Request.find.byId(id);
        if(update == null){
            flash("error","Can't find request!");
            return notFound("Not Found");
        }
        if (Long.parseLong(session().get("user_id")) != update.lender.id){
            flash("error","You are not the owner of this tool.");
            return redirect(routes.Tools.show(update.wanted.id));
        }
        update.dismiss = true;
        update.save();
        return redirect(routes.Users.show(Long.parseLong(session().get("user_id"))));
    }

    @Security.Authenticated(UserAuth.class)
    public Result delReq(Long id){
        Request update = Request.find.byId(id);
        if(update == null){
            flash("error","Can't find request!");
            return notFound("Not Found");
        }
        if (Long.parseLong(session().get("user_id")) != update.borrower.id){
            flash("error","You are not allowed to perform this function.");
            return redirect(routes.Tools.show(id));
        }
        update.delete();
        return redirect(routes.Users.show(Long.parseLong(session().get("user_id"))));
    }

    @Security.Authenticated(UserAuth.class)
    public Result returnTool(Long id){
        Request update = Request.find.byId(id);
        if(update == null){
            flash("error","Can't find request!");
            return notFound("Not Found");
        }
        if (Long.parseLong(session().get("user_id")) != update.lender.id){
            flash("error","You are not the owner of this tool.");
            return redirect(routes.Tools.show(update.wanted.id));
        }
        update.delete();
        update.wanted.available =true;
        update.wanted.save();
        flash("success", "Tool Returned");
        return redirect(routes.Tools.show(update.wanted.id));
    }

}
