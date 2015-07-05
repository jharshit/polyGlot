/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyglot_module;
import javax.swing.JFrame;
import java.io.*;
import java.sql.*;

/**
 * polyGlot CSC579/466 Project
 * Team Members: Harshit Jain & Nitin Goyal 
 */
public class createaccount extends JFrame 
{
    javax.swing.JLabel lbl_appname;
    javax.swing.JLabel lbl_trademark;
    javax.swing.JLabel lbl_tagline;
    javax.swing.JSeparator seperation;
    javax.swing.JLabel lbl_createaccountname;
    javax.swing.JPanel panel_createaccount;
    javax.swing.JTextField txt_username;
    javax.swing.JTextField txt_emailid;
    javax.swing.JLabel lbl_username;
    javax.swing.JLabel lbl_emailid;
    javax.swing.JButton btn_continue;
    javax.swing.JLabel img_appimage;
    
    File f,list[];      
    FileOutputStream fos;
    Connection co;
    PreparedStatement pst;
    static String emtext,uname;
    static FileInputStream fis;
    static DataInputStream dis;
    static chat_room c_room;
    static createaccount cr_account;
    
    public createaccount()
    {
        lbl_appname = new javax.swing.JLabel();
        lbl_trademark=new javax.swing.JLabel();
        lbl_tagline = new javax.swing.JLabel();
        seperation = new javax.swing.JSeparator();
        lbl_createaccountname = new javax.swing.JLabel();
        panel_createaccount = new javax.swing.JPanel();
        txt_username = new javax.swing.JTextField();
        txt_emailid = new javax.swing.JTextField();
        lbl_username = new javax.swing.JLabel();
        lbl_emailid = new javax.swing.JLabel();
        btn_continue = new javax.swing.JButton();
        img_appimage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);      
        getContentPane().setLayout(null);
        
        lbl_appname.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); 
        lbl_appname.setForeground(new java.awt.Color(255, 255, 255));
        lbl_appname.setText("polyGlot");
        getContentPane().add(lbl_appname).setBounds(160, 20, 93, 34);
        
        lbl_trademark.setFont(new java.awt.Font("Comic Sans MS", 1, 7)); 
        lbl_trademark.setForeground(new java.awt.Color(255, 255, 255));
        lbl_trademark.setText("TM");
        getContentPane().add(lbl_trademark).setBounds(255, 15, 40, 30);
        
        lbl_tagline.setFont(new java.awt.Font("Edwardian Script ITC", 0, 36));
        lbl_tagline.setForeground(new java.awt.Color(255, 255, 255));
        lbl_tagline.setText("connect with the world");
        getContentPane().add(lbl_tagline).setBounds(100, 50, 220, 43);
        
        seperation.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(seperation).setBounds(40, 100, 330, 10);
        
        lbl_createaccountname.setFont(new java.awt.Font("Tahoma", 1, 14));
        lbl_createaccountname.setForeground(new java.awt.Color(255, 255, 255));
        lbl_createaccountname.setText("Create an account");
        getContentPane().add(lbl_createaccountname).setBounds(50, 120, 128, 19);

        panel_createaccount.setLayout(null);

        lbl_username.setFont(new java.awt.Font("Tahoma", 0, 12));
        lbl_username.setText("Username");
        lbl_emailid.setFont(new java.awt.Font("Tahoma", 0, 12));
        lbl_emailid.setText("Email id");
        btn_continue.setText("Continue");
        
        panel_createaccount.add(txt_username).setBounds(25, 35,140, 20);
        panel_createaccount.add(lbl_username).setBounds(200, 35,140, 20);
        panel_createaccount.add(txt_emailid).setBounds(25, 80,140, 20);
        panel_createaccount.add(lbl_emailid).setBounds(200, 80,140, 20);
        panel_createaccount.add(btn_continue).setBounds(95, 120,100, 20);
        getContentPane().add(panel_createaccount).setBounds(60, 160, 302, 158);
        
        img_appimage.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("resource/create_account.jpg")));
        //img_appimage.setText("createaccount_image");
        getContentPane().add(img_appimage).setBounds(0, 0, 436, 389);
        
        btn_continue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_continueActionPerformed(evt);
            }
        });
        setSize(436,389);
        setVisible(true);
    }
    private void btn_continueActionPerformed(java.awt.event.ActionEvent evt) { 
        try
        {
            
        
		Class.forName("com.mysql.jdbc.Driver");
                co=DriverManager.getConnection("jdbc:mysql://50.28.14.178/vkodernt_moderntimedb?user=vkodernt_harshit&password=harshit@PASS32");
                uname=txt_username.getText();
                emtext=txt_emailid.getText();
                pst=co.prepareStatement("insert into users(username,emailid) values(?,?)");
                pst.setString(1, uname);
                pst.setString(2, emtext);
                pst.execute();
                pst.close();
                pst=co.prepareStatement("create table "+uname+"_FLIST(username varchar(25) primary key,emailid varchar(40))");              
                pst.execute();
                pst.close();
                c_room=new chat_room(uname,emtext);  
                c_room.setVisible(true);      
              
                
       
                try
                {
                    Runtime.getRuntime().exec("cmd /c md c:\\polyGlot");                 
                    Runtime.getRuntime().exec("attrib +s +h C:\\polyGlot");                    
                    fos=new FileOutputStream("C:\\polyGlot\\data.dll");
                    emtext=uname+"\n"+emtext;                    
                    byte []b=emtext.getBytes();
                    fos.write(b);
                    fos.close();
                }
                catch(Exception ex){System.out.print(ex);}
        }
        catch(Exception ex)
        {
            System.out.print("Testing"+ex.getMessage());
        }
        
        
       
    }
    public static void main(String par[])
    {
        java.awt.EventQueue.invokeLater(new Runnable() 
        {           
            public void run() 
            {
                
                try
                {
                    fis=new FileInputStream("C:\\polyGlot\\data.dll");
                    dis=new DataInputStream(fis);                                 
                    uname=dis.readLine();
                    emtext=dis.readLine();
                    if(emtext.isEmpty())
                    {
                        System.out.print("Coming");
                        
                        cr_account=new createaccount();
                        cr_account.setVisible(true);
                    }
                    else
                    {
                        c_room=new chat_room(uname,emtext); 
                        c_room.setSize(436,389);
                        c_room.setVisible(true);
                     
                    }
                }
                catch(Exception ex)
                {
                    if(ex.getMessage().equals("C:\\polyGlot\\data.dll (The system cannot find the file specified)"))
                    {
                        cr_account=new createaccount();
                        cr_account.setVisible(true);
                    }
                }
            }
        });
        
    }
    
}
