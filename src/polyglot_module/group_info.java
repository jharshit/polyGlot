/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyglot_module;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.sql.*;
import javax.swing.JOptionPane; 
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
/**
 * polyGlot CSC579/466 Project
 *Team Members : Harshit Jain & Nitin Goyal
 */
public class group_info extends JFrame{
    javax.swing.JLabel lbl_creategrp,lbl_member;
    javax.swing.JPanel panel_addgroup;
    javax.swing.JPanel panel_viewgroup,panel_member;
    javax.swing.JTextField txt_groupname,txt_member;   
    javax.swing.JLabel lbl_groupname;
   

    java.awt.Choice choice_grp,choice_grp1;
    javax.swing.JButton btn_continue,btn_member;
    javax.swing.JButton btn_skip;
    javax.swing.JLabel img_appimage;
    
    ResultSet rs;
    String uname,emtext;
    PreparedStatement pst;
    Connection co;
    createaccount cr_account;
    JMenuBar menu_bar;
    JMenu menu;
    JMenuItem add_grp,view_grp,add_member;
    
    JTable table;
    DefaultTableModel model;
    String[] columnNames = {"User name", "Email"};
    JScrollPane scroll;
    boolean flag=false;
   
    public group_info(String uname,String emtext)
    {
        
        try
        {
            
            Class.forName("com.mysql.jdbc.Driver");
            co=DriverManager.getConnection("jdbc:mysql://50.28.14.178/vkodernt_moderntimedb?autoReconnect=true&characterSetResults=UTF-8&characterEncoding=UTF-8&useUnicode=yes&user=vkodernt_harshit&password=harshit@PASS32");                             
        }
        catch(Exception ex){}
        this.uname=uname;
        this.emtext=emtext;
        
        //Menu Details
        menu_bar=new JMenuBar();
        menu=new JMenu("Options");
        menu_bar.add(menu);
        setJMenuBar(menu_bar);
        
        add_grp=new JMenuItem("Add Group");
        menu.add(add_grp);
        add_grp.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lbl_creategrp.setText("Add a Group");
                lbl_creategrp.setVisible(true);
                panel_addgroup.setVisible(true);
                panel_viewgroup.setVisible(false);
                panel_member.setVisible(false);
            }
        });
        
        view_grp=new JMenuItem("View Group");
        menu.add(view_grp);
        view_grp.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lbl_creategrp.setText("View Group");
                lbl_creategrp.setVisible(true);
                panel_viewgroup.setVisible(true);
                panel_addgroup.setVisible(false);
                panel_member.setVisible(false);  
                view_grp(choice_grp);
            }
        });
        
        add_member=new JMenuItem("Add Member in Group");
        menu.add(add_member);
        add_member.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lbl_creategrp.setText("Add a Group Member");
                lbl_creategrp.setVisible(true);
                panel_addgroup.setVisible(false);
                panel_viewgroup.setVisible(false);
                panel_member.setVisible(true);
                view_grp(choice_grp1);
            }
        });
        
        lbl_creategrp = new javax.swing.JLabel();
        panel_addgroup = new javax.swing.JPanel();
        panel_viewgroup = new javax.swing.JPanel();
        panel_member = new javax.swing.JPanel();
        txt_groupname = new javax.swing.JTextField();      
        lbl_groupname = new javax.swing.JLabel();
        
        lbl_member=new javax.swing.JLabel("User Name");
        txt_member=new javax.swing.JTextField();
        btn_member=new javax.swing.JButton("Submit");
        
        btn_continue = new javax.swing.JButton();
        btn_skip = new javax.swing.JButton();
        img_appimage = new javax.swing.JLabel();
        
             
        choice_grp=new java.awt.Choice();
        choice_grp.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(java.awt.event.ItemEvent ex)
            {
                display();
            }
        });
        
        
        choice_grp1=new java.awt.Choice();
        
        table = new JTable();
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        
        
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        scroll = new JScrollPane(table);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        lbl_creategrp.setFont(new java.awt.Font("Calibri Light", 1, 18));
        lbl_creategrp.setForeground(new java.awt.Color(255, 255, 255));
        lbl_creategrp.setText("Add a Group");
        getContentPane().add(lbl_creategrp).setBounds(20, 30, 200, 40);

        //chng
        lbl_creategrp.setVisible(false);
        panel_addgroup.setVisible(false);
        panel_viewgroup.setVisible(false);
        panel_member.setVisible(false);//
        
        lbl_groupname.setFont(new java.awt.Font("Tahoma", 0, 12));
        lbl_groupname.setText("Group Name");
                
        btn_continue.setText("Continue");
        btn_skip.setText("Skip");

        getContentPane().add(btn_skip).setBounds(300, 10,100, 20);
        
        //Add group Panel
        panel_addgroup.setLayout(null);
        panel_addgroup.add(txt_groupname).setBounds(25, 35,140, 20);       
        panel_addgroup.add(lbl_groupname).setBounds(190, 35,140, 20);        
        panel_addgroup.add(btn_continue).setBounds(100, 120,100, 20);        
        getContentPane().add(panel_addgroup).setBounds(60, 120, 302, 158);
        
        //View Group Panel
        panel_viewgroup.setLayout(null);
        panel_viewgroup.add(choice_grp).setBounds(30,20,250,20);
        panel_viewgroup.add(scroll).setBounds(30,50,250,160);
        getContentPane().add(panel_viewgroup).setBounds(45, 90, 322, 220);
        
        
        //Add Member Panel
        panel_member.setLayout(null);
        panel_member.add(choice_grp1).setBounds(30,20,250,20);
        panel_member.add(txt_member).setBounds(30,70,160,20);
        panel_member.add(lbl_member).setBounds(215,70,140,20);
        panel_member.add(btn_member).setBounds(200,120,90,20);
        getContentPane().add(panel_member).setBounds(45, 90, 322, 158);
        
        
        //Adding Image in Background
        img_appimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/create_account.jpg")));
        //img_appimage.setText("createaccount_image");
        getContentPane().add(img_appimage).setBounds(0, 0, 436, 389);
        
        setSize(436,389);
        setVisible(true);
    
        btn_continue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                continueActionPerformed(evt);
            }
        });
        btn_skip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        
        btn_member.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMember();
            }
        });
        
    }

 private void addMember()
 {
     flag=false;
     if(choice_grp1.getSelectedIndex()!=0)
     {
                                         
            try
            {                       
                if(co.isValid(1000)==false) 
                    co=DriverManager.getConnection("jdbc:mysql://50.28.14.178/vkodernt_moderntimedb?autoReconnect=true&characterSetResults=UTF-8&characterEncoding=UTF-8&useUnicode=yes&user=vkodernt_harshit&password=harshit@PASS32");
                pst=co.prepareStatement("select * from "+uname+"_FLIST where username=?");
                pst.setString(1,txt_member.getText());
                //pst=co.prepareStatement("select username from "+uname+"_FLIST where emailid='Group' ");                                
                rs=pst.executeQuery();                    
                while(rs.next())
                {                
                    flag=true;
                }                
                //pst.close();  
                
                if(flag==true)
                {
                    pst=co.prepareStatement("insert into "+choice_grp1.getSelectedItem()+"(username) values(?)");                    
                    pst.setString(1,txt_member.getText());                    
                    pst.execute();                 
                    //pst.close(); 
                    
                    pst=co.prepareStatement("insert into "+txt_member.getText()+"_FLIST(username,emailid) values(?,'Group')");                    
                    pst.setString(1,choice_grp1.getSelectedItem());                    
                    pst.execute(); 
                    pst.close();
                    JOptionPane.showMessageDialog(this, "Member is successfully added in your group","Information",JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Given friend infomation is not found in Your Friend List","Information",JOptionPane.INFORMATION_MESSAGE);
                }
                
                    
           
                                          
            }                    
            catch(SQLException ex)
            {       
                System.out.print("dfhdfhd "+ex);
            }    
     }
     else
     {
         JOptionPane.showMessageDialog(this, "Please Select Group","Information",JOptionPane.INFORMATION_MESSAGE);
     }
 }
 private void display()
 {
     try
     {
        String usernm,email;
        
        if(choice_grp.getSelectedIndex()!=0)
        {
            if(co.isValid(1000)==false) 
                co=DriverManager.getConnection("jdbc:mysql://50.28.14.178/vkodernt_moderntimedb?autoReconnect=true&characterSetResults=UTF-8&characterEncoding=UTF-8&useUnicode=yes&user=vkodernt_harshit&password=harshit@PASS32");                             
            try
            {                       
                           
                model = new DefaultTableModel();
                model.setColumnIdentifiers(columnNames);
                table.setModel(model);
                pst=co.prepareStatement("select a.username,emailid from "+choice_grp.getSelectedItem()+" as a,users b where a.username=b.username");                                
                //pst=co.prepareStatement("select username from "+uname+"_FLIST where emailid='Group' ");                                
                rs=pst.executeQuery();                    
                while(rs.next())
                {                
                    usernm=rs.getString("username");
                    email=rs.getString("emailid");
                    //System.out.print(usernm+" "+email);
                    model.addRow(new Object[]{usernm, email});
                }
                
                pst.close();                    
                    
           
                                          
            }                    
            catch(SQLException ex)
            {       
                System.out.print("dfhdfhd "+ex);
            }
        }
     }
     catch(Exception ex)
     {
        System.out.print(ex.getMessage());
     }    
}
 private void view_grp(java.awt.Choice c)
 {              
     try
     {
                    
        if(co.isValid(1000)==false) 
            co=DriverManager.getConnection("jdbc:mysql://50.28.14.178/vkodernt_moderntimedb?autoReconnect=true&characterSetResults=UTF-8&characterEncoding=UTF-8&useUnicode=yes&user=vkodernt_harshit&password=harshit@PASS32");                             
        try
        {                   
            
            //pst=co.prepareStatement("select username,emailid from "+uname+"_FLIST as a,users b where a.username=b.username and a.emailid='Group' ");                                
            pst=co.prepareStatement("select username from "+uname+"_FLIST where emailid='Group' ");                                
            rs=pst.executeQuery();    
            c.removeAll();
            c.add("Select Group Name");
            while(rs.next())
            {                
                c.add(rs.getString("username"));
            }
            pst.close();                    
                    
           
                                          
        }                    
        catch(SQLException ex)
        {       
            System.out.print(ex);
        }
    }
    catch(Exception ex)
    {
        System.out.print(ex.getMessage());
    }
                 
 }
        public void continueActionPerformed(java.awt.event.ActionEvent evt)     
        {          
        try
        {
            
            Class.forName("com.mysql.jdbc.Driver");
            co=DriverManager.getConnection("jdbc:mysql://50.28.14.178/vkodernt_moderntimedb?autoReconnect=true&characterSetResults=UTF-8&characterEncoding=UTF-8&useUnicode=yes&user=vkodernt_harshit&password=harshit@PASS32");                             
            try
            {                    
                    pst=co.prepareStatement("insert into "+uname+"_FLIST(username,emailid) values(?,'Group')");                    
                    pst.setString(1,txt_groupname.getText()+"_");                    
                    pst.execute();                 
                    //pst.close();                   
                    
                     //Create table for group info
                    
                    pst=co.prepareStatement("create table "+txt_groupname.getText()+"_(username varchar(20)) DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci");                    
                    pst.execute();   
                    
                    
                    //add first member
                    pst=co.prepareStatement("insert into "+txt_groupname.getText()+"_(username) values(?)");                    
                    pst.setString(1,uname);                    
                    pst.execute(); 
                    
                    
                    //Create table for group msg
                    pst=co.prepareStatement("create table "+txt_groupname.getText()+"_msg(username varchar(20),msg text,msg_type varchar(10),status varchar(5),language varchar(5)) DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci");                    
                    pst.execute();       
                    pst.close();
                    
                    pst.close();
                    
                    JOptionPane.showMessageDialog(this, "Given Group is successfully created","Information",JOptionPane.INFORMATION_MESSAGE);
            }                    
            catch(Exception ex)
            {
                System.out.print(ex);
                     JOptionPane.showMessageDialog(this, "Given Friend is already Added","Information",JOptionPane.INFORMATION_MESSAGE);   
            }
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

