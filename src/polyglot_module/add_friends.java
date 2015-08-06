/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyglot_module;
import javax.swing.JFrame;
import java.io.*;
import java.sql.*;
import javax.swing.JOptionPane;
import java.util.*;
/**
 * polyGlot CSC579/466 Project
 *Team Members : Harshit Jain & Nitin Goyal
 */
public class add_friends extends JFrame{
    javax.swing.JLabel lbl_addfriend;
    javax.swing.JPanel panel_addfriend;
    javax.swing.JTextField txt_username;
    javax.swing.JTextField txt_emailid;
    javax.swing.JLabel lbl_username;
    javax.swing.JLabel lbl_emailid;
    javax.swing.JButton btn_continue;
    javax.swing.JButton btn_skip;
    javax.swing.JLabel img_appimage;
    
    ResultSet rs;
    String uname,emtext;
    PreparedStatement pst;
    Connection co;
    createaccount cr_account;
    java.util.Date dt2,dt3;
    public add_friends(String uname,String emtext)
    {
        this.uname=uname;
        this.emtext=emtext;
        lbl_addfriend = new javax.swing.JLabel();
        panel_addfriend = new javax.swing.JPanel();
        txt_username = new javax.swing.JTextField();
        txt_emailid = new javax.swing.JTextField();
        lbl_username = new javax.swing.JLabel();
        lbl_emailid = new javax.swing.JLabel();
        btn_continue = new javax.swing.JButton();
        btn_skip = new javax.swing.JButton();
        img_appimage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        lbl_addfriend.setFont(new java.awt.Font("Calibri Light", 1, 18));
        lbl_addfriend.setForeground(new java.awt.Color(255, 255, 255));
        lbl_addfriend.setText("Add a friend");
        getContentPane().add(lbl_addfriend).setBounds(20, 30, 110, 40);


        lbl_username.setFont(new java.awt.Font("Tahoma", 0, 12));
        lbl_username.setText("Username");
        lbl_emailid.setFont(new java.awt.Font("Tahoma", 0, 12));
        lbl_emailid.setText("Email id");
        btn_continue.setText("Continue");
        btn_skip.setText("Skip");

        panel_addfriend.setLayout(null);
        panel_addfriend.add(txt_username).setBounds(25, 35,140, 20);
        panel_addfriend.add(txt_emailid).setBounds(25, 80,140, 20);
        panel_addfriend.add(lbl_username).setBounds(190, 35,140, 20);
        panel_addfriend.add(lbl_emailid).setBounds(190, 80,140, 20);
        panel_addfriend.add(btn_continue).setBounds(35, 120,100, 20);
        panel_addfriend.add(btn_skip).setBounds(180, 120,100, 20);
        getContentPane().add(panel_addfriend).setBounds(60, 120, 302, 158);
        
        img_appimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/create_account.jpg")));
        //img_appimage.setText("createaccount_image");
        getContentPane().add(img_appimage).setBounds(0, 0, 436, 389);
        
        setSize(436,389);
        setVisible(true);
    
    btn_continue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
    btn_skip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        
    }

        public void jButton1ActionPerformed(java.awt.event.ActionEvent evt)     
        { 
          
        try
        {
            dt2=new java.util.Date();
            Class.forName("com.mysql.jdbc.Driver");
            co=DriverManager.getConnection("jdbc:mysql://50.28.14.178/vkodernt_moderntimedb?autoReconnect=true&characterSetResults=UTF-8&characterEncoding=UTF-8&useUnicode=yes&user=vkodernt_harshit&password=harshit@PASS32");
            pst=co.prepareStatement("select * from users where username=?");
            pst.setString(1,txt_username.getText());
            rs=pst.executeQuery();
            boolean flag=false;
            while(rs.next())
            {
                flag=true;
            }
                if(flag==false)
                {
                    JOptionPane.showMessageDialog(this, "Sorry! Given user id is not registered in polyGlot","Information",JOptionPane.INFORMATION_MESSAGE);
                    
                }
                else
                {
                    
                    //Friend List first
                    try
                    {
                    pst.close();
                    pst=co.prepareStatement("insert into "+uname+"_FLIST(username,emailid) values(?,?)");                    
                    pst.setString(1,txt_username.getText());
                    pst.setString(2, txt_emailid.getText());
                    pst.execute();                 
                    pst.close();
                    
                   //Friend List Second
                  
                    pst=co.prepareStatement("insert into "+txt_username.getText()+"_FLIST(username,emailid) values(?,?)");                    
                    pst.setString(1, uname);
                    pst.setString(2, emtext);
                    pst.execute();                  
                    JOptionPane.showMessageDialog(this, "Given Friend is successfully added","Information",JOptionPane.INFORMATION_MESSAGE);
                    pst.close();
                    
                    
                    dt3=new java.util.Date();
                    System.out.println("\n time taken to add friend \n" +(dt3.getTime()-dt2.getTime())+" ms.");
                    //Create table for friend msg
                    
                    pst=co.prepareStatement("create table "+uname+"_"+txt_username.getText()+"t(msg text,msg_type varchar(10),status varchar(5)) DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci");                    
                    pst.execute();       
                    pst.close();
                    
                    //Create table for friend msg in revsers
                    
                    pst=co.prepareStatement("create table "+txt_username.getText()+"_"+uname+"t(msg text,msg_type varchar(10),status varchar(5)) DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci");                    
                    pst.execute();
                    txt_username.setText("");
                    txt_emailid.setText("");
                    }                    
                    catch(Exception ex)
                    {
                     JOptionPane.showMessageDialog(this, "Given Friend is already Added","Information",JOptionPane.INFORMATION_MESSAGE);   
                    }
                }
                pst.close();
              
        }
        catch(Exception ex)
        {
           System.out.print(ex.getMessage());
        }
            
    }
    public void jButton2ActionPerformed(java.awt.event.ActionEvent evt)
    {
       chat_room ch=new chat_room(uname,emtext);
        ch.setSize(436,389);
        ch.setVisible(true);
       
       setVisible(false);
    }
}

