/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import db.User;
import org.apache.log4j.Logger;



/**
 *
 * @author Alessandro
 */
public class Mailer {
    static Logger log = Logger.getLogger(Mailer.class.getName());
    
    public boolean sendEmail(String email, String title, String text){
        
        final String username= "webProject2@gmail.com";
        final String password="forum2";
        
        return false;
    }
}
