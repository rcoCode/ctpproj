package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by rebeca on 12/15/2015.
 */
public class Requests extends Controller{
    @Security.Authenticated(UserAuth.class)
    public Result borrow(){

    }
}
