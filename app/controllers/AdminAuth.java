package controllers;

import models.*;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by yfle on 12/13/2015.
 */
public class AdminAuth extends Security.Authenticator {
    @Override
    public String getUsername(Http.Context context) {
        String userIdStr = context.session().get("user_id");
        if(userIdStr == null) return null;
        models.Users users = models.Users.find.byId(Long.parseLong(userIdStr));

        return ((users != null)&&(users.admin) ? users.id.toString() : null);
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        context.flash().put("error",
                "But you are not the admin");
        return redirect(routes.Application.index());
    }
}
