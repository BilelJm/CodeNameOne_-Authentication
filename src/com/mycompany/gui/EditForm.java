package com.mycompany.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycomany.utils.SessionManager;
import com.mycomany.utils.Statics;
import com.mycompany.services.ServiceUtilisateur;


import java.io.IOException;
import java.util.List;
import java.util.Map;



public class EditForm extends BaseForm {

    private ConnectionRequest req = new ConnectionRequest();
    String json;

    public EditForm(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Profile");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        tb.addSearchCommand(e -> {
        });

        String url = Statics.BASE_URL + "/give-user";
        req.setUrl(url);
        req.setPost(false);
        req.addArgument("id", String.valueOf(SessionManager.getId()));
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                json = new String(req.getResponseData());
                int json1 = req.getResponseData().length;
                JSONParser j = new JSONParser();


                
                try {
                    Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) user.get("root");
                    float userid = Float.parseFloat(user.get("id").toString());
                    int id = (int)userid;
                    String username = (String) user.get("username");
                    String firstname = (String) user.get("firstName");
                    String useremail = (String) user.get("email");
                    String lastname = (String) user.get("lastName");
                    

                    TextField surnom = new TextField(username);
                    surnom.setUIID("TextFieldBlack");
                    addStringValue("Surnom", surnom);

                    TextField prenom = new TextField(lastname);
                    prenom.setUIID("TextFieldBlack");
                    addStringValue("Prenom", prenom);
                    TextField email = new TextField(useremail, "E-Mail", 20, TextField.EMAILADDR);
                    email.setUIID("TextFieldBlack");
                    addStringValue("E-Mail", email);


                    TextField nom = new TextField(firstname);
                    nom.setUIID("TextFieldBlack");
                    addStringValue("nom", nom);
                    
                    Button edit = new Button("Modifier");
                    edit.addActionListener(e -> {


                        ServiceUtilisateur.getInstance().edituser( String.valueOf(id), prenom.getText(),  nom.getText(),  email.getText() ,surnom.getText()  
                                ) ;

                    });
                    add(edit);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
}
