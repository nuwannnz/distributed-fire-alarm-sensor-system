package com.firealarm.services;

import com.firealarm.helpers.APIHelper;
import com.firealarm.helpers.JsonHelper;
import firealarm.rmi.api.UserService;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserServiceImpl extends UnicastRemoteObject implements UserService {

    private final static String USER_URL = Constants.FIRE_ALARM_API_URL +  "/user";

    public UserServiceImpl() throws RemoteException {

    }

    @Override
    public boolean hasAdmin() throws RemoteException {
        StringBuffer res = null;

        try {
            res = APIHelper.get(USER_URL + "/has-admin");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(res == null){
            return false;
        }

        JsonObject hasAdminResult = JsonHelper.getJsonObjectFromString(res.toString());

        boolean hasAdmin = hasAdminResult.getBoolean("hasAdmin");

        return hasAdmin;
    }

    @Override
    public boolean createAdmin(String email, String password) throws RemoteException {

        JsonObject signupParams = Json.createObjectBuilder()
                .add("email", email)
                .add("password", password)
                .build();


        StringBuffer res = null;
        try {
            res = APIHelper.post(USER_URL + "/signup", signupParams, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(res == null){
            return false;
        }

        JsonObject signupResponse = JsonHelper.getJsonObjectFromString(res.toString());

        boolean isSuccess = signupResponse.getBoolean("status");
        if(isSuccess){
           return true;
        }
        // signup failed
        return false;
    }

    @Override
    public String login(String email, String password) throws RemoteException {

        JsonObject loginParams = Json.createObjectBuilder()
                .add("email", email)
                .add("password", password)
                .build();


        StringBuffer res = null;
        try {
            res = APIHelper.post(USER_URL + "/login", loginParams, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(res == null){
            return null;
        }

        JsonObject loginResponse = JsonHelper.getJsonObjectFromString(res.toString());

        boolean isAuth = loginResponse.getBoolean("isAuth");
        if(isAuth){
            // logged in
            String token = loginResponse.getString("token");
            return  token;
        }
        // logged in failed
        return null;
    }
}




















