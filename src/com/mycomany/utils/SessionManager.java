/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycomany.utils;

import com.codename1.io.Preferences;

/**
 *
 * @author bilel
 */
public class SessionManager {
    public static Preferences pref;

    public static int id;
    public static String nom, prénom, email,role , picture;

    public static String getEmail() {
        return pref.get("email", email);
    }
    public static int numTel;

    public static Preferences getPref() {
        return pref;
    }

    public static void setPref(Preferences pref) {
        SessionManager.pref = pref;
    }

    public static int getId() {
        return pref.get("id", id);
    }
    public static String getPicture() {
        return pref.get("picture", picture);
    }

    public static String getNom() {
        return pref.get("nom", nom);
    }
    
    public static String getRole() {
        return pref.get("role", role);
    }
    

    public static String getPrénom() {
        return pref.get("prenom", prénom);
    }

    public static int getNumTel() {
        return pref.get("numéro", numTel);
    }

    public static void setId(int id) {
        pref.set("id", id);
    }

    public static void setEmail(String email) {
        pref.set("email", email);
    }

    public static void setPicture(String picture) {
        pref.set("picture", picture);
    }
    
    public static void setNom(String nom) {
        pref.set("nom", nom);
    }

    public static void setPrénom(String prénom) {
        pref.set("prenom", prénom);
    }

     public static void setRole(String role) {
        pref.set("role", role);
    }

    public static void setNumTel(int numTel) {
        pref.set("numéro", numTel);
    }
    
}
