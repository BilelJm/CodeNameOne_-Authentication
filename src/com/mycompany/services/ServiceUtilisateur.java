/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import java.util.List;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.mycomany.utils.SessionManager;
import com.mycomany.utils.Statics;
import com.mycompany.gui.AdminForm;


import com.mycompany.gui.EditForm;
import com.mycompany.gui.NewsfeedForm;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Vector;


public class ServiceUtilisateur {
    
    
    
    private Resources res;
  //singleton 
    public static ServiceUtilisateur instance = null ;
    
    public static boolean resultOk = true;
    String json;
    public static boolean resultOK = true;

    //initilisation connection request 
    private ConnectionRequest req;
    
    
    public static ServiceUtilisateur getInstance() {
        if(instance == null )
            instance = new ServiceUtilisateur();
        return instance ;
    }
    
    
    
    public ServiceUtilisateur() {
        req = new ConnectionRequest();
        
    }
    
    public boolean signup(String username,String lastname,String firstname,String email,String password, Resources res) {
      //  System.out.println(u);
        System.out.println("******");
        //String url = Statics.BASE_URL + "create?name=" + t.getName() + "&status=" + t.getStatus();
        String url = Statics.BASE_URL + "/signup";

        req.setUrl(url);
        req.setPost(false);
      req.addArgument("firstname", firstname);
        req.addArgument("lastname", lastname);
        req.addArgument("email",email);
        req.addArgument("password", password);
        req.addArgument("username",username+ "");
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    
    //SignIn
    
    public void signin(TextField email ,TextField password, Resources rs ) {
        
        
            

        String url = Statics.BASE_URL + "/signin";
        req.setUrl(url);
        req.setPost(false);
        req.addArgument("email", email.getText());
        req.addArgument("password", password.getText());
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser j = new JSONParser();
                int json1 = req.getResponseData().length;

                try {
                    json = new String(req.getResponseData(), "utf-8");
                    System.out.println("++++++++>>>>>>  " + json);
                } catch (UnsupportedEncodingException ex) {
                    System.out.println(ex.getMessage());
                }

                if (json1 < 10) {
                    Dialog.show("Echec d'authentification", "email ou mot de passe est erroné", "OK", null);

                } else {
                    System.out.println("data--->" + json);
                    try {
                        Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
                        //List<Map<String, Object>> list = (List<Map<String, Object>>) user.get("root");

                        System.out.println("useeeeeeeer " + user);
                       
                            List roles = (List) user.get("roles");
                            String role = (String) roles.get(0);
                            float id = Float.parseFloat(user.get("id").toString());
                            SessionManager.setId((int) id);
                            SessionManager.setEmail(user.get("email").toString());
                            SessionManager.setNom(user.get("lastName").toString());
//                            SessionManager.setPicture(user.get("picture").toString());
                            SessionManager.setPrénom(user.get("firstName").toString());
                            System.out.println("role"+role);
                            if ( role.equals("ROLE_ADMIN")) {
                                new AdminForm(res).show();
                            } else {
                                new EditForm(res).show();
                            }
                            
                        
                        System.out.println("user =>" + SessionManager.getEmail());

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    public boolean edituser(String id, String firstname, String lastname, String email, String username) {

        String url = Statics.BASE_URL + "/editUser";

        req.setUrl(url);
        req.setPost(false);
        req.addArgument("id", id);
        req.addArgument("firstName", firstname);
        req.addArgument("lastName", lastname);
        req.addArgument("email", email);
        req.addArgument("username", username);

        JSONParser j = new JSONParser();

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                json = new String(req.getResponseData());
                int json1 = req.getResponseData().length;

                
                    System.out.println("++++++++>>>>>>  " + json);

                    try {
                        Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
                        com.codename1.ui.List<Map<String, Object>> list = (com.codename1.ui.List<Map<String, Object>>) user.get("root");
                        System.out.println("useeeeeeeer " + user.get("resetToken"));

                        Dialog.show("Succes", "Modification avec succes", "OK", null);

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                
                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    }
    



