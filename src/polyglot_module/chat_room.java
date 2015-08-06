/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyglot_module;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import javax.swing.JFrame;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.Timer;
import com.google.gson.* ;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import javax.net.ssl.*;
import java.util.*;


/**
 * polyGlot CSC579/466 Project
 *Team Members : Harshit Jain & Nitin Goyal
 */
public class chat_room extends JFrame implements Runnable
{
    
    javax.swing.JLabel lbl_chatroom;
    javax.swing.JButton btn_addfriend;
    javax.swing.JButton btn_group;    
    java.awt.Choice choice_selectfriend;
    java.awt.Choice choice_selectlanguage;
    javax.swing.JScrollPane chat_panel;
    javax.swing.JTextArea chat_textarea;
    javax.swing.JTextField txt_chatwrite;
    javax.swing.JButton btn_send;
    javax.swing.JLabel img_appimage;
    
    String uname,emtext,source,dest,fname,srclang,destlang,smsg,msg,srclang1,destlang1,smsg1,type1;
    Connection co;
    PreparedStatement pst,pst1;
    ResultSet rs,rs1;
    Timer t;
    String lang[]={"en","zh-CN","zh-TW","en","fr","de","it","ja","ko","ru","es"};
    int pos,flag=0,item;
    static String type,user;
    Vector v=new Vector();
    Vector v_msg=new Vector<String>();
    Vector v_lang=new Vector<String>();
    boolean check=false;
    java.util.Date dt2,dt3;
    public chat_room(String uname,String emtext)
    {
        this.uname=uname;
        this.emtext=emtext;        
        lbl_chatroom = new javax.swing.JLabel();
        
        //chng
        btn_group = new javax.swing.JButton();//
        btn_addfriend = new javax.swing.JButton();
        choice_selectfriend = new java.awt.Choice();
        choice_selectlanguage = new java.awt.Choice();
        chat_panel = new javax.swing.JScrollPane();
        chat_textarea = new javax.swing.JTextArea();
        txt_chatwrite = new javax.swing.JTextField();
        btn_send = new javax.swing.JButton();
        img_appimage = new javax.swing.JLabel();
        
        choice_selectlanguage.add("Select Language");
        choice_selectlanguage.add("Chinese Simplified");
        choice_selectlanguage.add("Chinese Traditional");
        choice_selectlanguage.add("English");
        choice_selectlanguage.add("French");
        choice_selectlanguage.add("German");
        choice_selectlanguage.add("Italian");
        choice_selectlanguage.add("Japanese");
        choice_selectlanguage.add("Korean");
        choice_selectlanguage.add("Russian");
        choice_selectlanguage.add("Spanish");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);


        lbl_chatroom.setFont(new java.awt.Font("Calibri Light", 1, 18)); 
        lbl_chatroom.setForeground(new java.awt.Color(255, 255, 255));
        lbl_chatroom.setText("Chat Room");
        getContentPane().add(lbl_chatroom).setBounds(25, 10, 110, 40);
        
        
        
        //chng
        btn_group.setText("Add Group");
        getContentPane().add(btn_group).setBounds(25, 50, 120, 20);//
        
        btn_addfriend.setText("Add Friend");
        getContentPane().add(btn_addfriend).setBounds(25, 80, 120, 20);
        
        chat_textarea.setColumns(20);
        chat_textarea.setRows(5);
        chat_textarea.setWrapStyleWord(true);
        chat_panel.setViewportView(chat_textarea);
       
        getContentPane().add(choice_selectfriend).setBounds(170, 80, 105, 20);
        getContentPane().add(choice_selectlanguage).setBounds(290, 80, 105, 20);
        getContentPane().add(chat_panel).setBounds(25, 110, 370, 145);
        getContentPane().add(txt_chatwrite).setBounds(25, 270, 370, 30);

        btn_send.setText("Send");
        getContentPane().add(btn_send).setBounds(165, 315, 90, 20);
        
        img_appimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/create_account.jpg")));
        //img_appimage.setText("createaccount_image");
        getContentPane().add(img_appimage).setBounds(0, 0, 436, 389);
        
        btn_addfriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        
        //chng
        btn_group.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupActionPerformed(evt);
            }
        });//
        
        btn_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendActionPerformed();
            }
        });
         
        txt_chatwrite.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyPressed(java.awt.event.KeyEvent evt)
             {
                 if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
                 {
                 sendActionPerformed();
                 }
             }
        });
         
        chat_textarea.addFocusListener(new FocusAdapter()
        {
           public void focusGained(FocusEvent ex)
           {
               flag=1;  
           }
           public void focusLost(FocusEvent e)
           {
               flag=0;
           }
        });
         
        choice_selectfriend.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                choice_selectfriendItemStateChanged(evt);
            }
        });
        
        choice_selectfriend.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt)
            {                
                friend_fetch();
        }});
        
        choice_selectlanguage.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                choice_selectlanguageItemStateChanged(evt);
            }
        });
        // Retrieve list of all friends in dropdown.
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            co=DriverManager.getConnection("jdbc:mysql://50.28.14.178/vkodernt_moderntimedb?autoReconnect=true&characterSetResults=UTF-8&characterEncoding=UTF-8&useUnicode=yes&user=vkodernt_harshit&password=harshit@PASS32");
            pst=co.prepareStatement("select distinct username,emailid from "+uname+"_FLIST");               
            rs=pst.executeQuery();
            choice_selectfriend.removeAll();
            choice_selectfriend.add("Select Friend");
            
            while(rs.next())
            {
               fname=rs.getString("username").trim();
               choice_selectfriend.add(fname); 
               v.addElement(fname);
            }
            pst.close(); 
            
            
            //Fetch language of mine            
            pst=co.prepareStatement("select language from users where username=?");
            pst.setString(1,uname);
            rs=pst.executeQuery();
            while(rs.next())
            {
               source=rs.getString("language");
            }            
            pst.close();            
            List l=Arrays.asList(lang);
            pos=l.indexOf(source);
            if(source.equalsIgnoreCase("en"))
                pos=3;
            choice_selectlanguage.select(pos);
            
            //Receiving the message
            Thread tt=new Thread(this);
            tt.start();
            }
            catch(Exception ex)
            {
                System.out.print(ex);
            }        
        
    }
     public void friend_fetch()
     {
        try
        {    
            if(co.isValid(1000)==false)
            co=DriverManager.getConnection("jdbc:mysql://50.28.14.178/vkodernt_moderntimedb?autoReconnect=true&characterSetResults=UTF-8&characterEncoding=UTF-8&useUnicode=yes&user=vkodernt_harshit&password=harshit@PASS32");
                    pst=co.prepareStatement("select distinct username,emailid from "+uname+"_FLIST");               
                    rs=pst.executeQuery();
                    choice_selectfriend.removeAll();
                    choice_selectfriend.add("Select Friend");
                    while(rs.next())
                    {
                        choice_selectfriend.add(rs.getString("username")); 
                    }
                    pst.close(); 
                }
                catch(Exception ex)
                {
                    System.out.print("Bye"+ex);
                }   
     }
     public void run()
     {
       while(true)
       {
            try
            {
                dt2=new java.util.Date();
                Thread.sleep(1000);
                if(choice_selectfriend.getSelectedIndex()!=0)
                {                                   
                    user=choice_selectfriend.getSelectedItem();
                              
                    try
                    {
                        if(user.endsWith("_"))
                        {                            
                            destlang1=lang[choice_selectlanguage.getSelectedIndex()];
                            try
                            {
                                if(co.isValid(1000)==false)
                                co=DriverManager.getConnection("jdbc:mysql://50.28.14.178/vkodernt_moderntimedb?autoReconnect=true&characterSetResults=UTF-8&characterEncoding=UTF-8&useUnicode=yes&user=vkodernt_harshit&password=harshit@PASS32");                    
                                pst1=co.prepareStatement("select * from "+user+"msg where status='not' and username!=?");
                                pst1.setString(1, uname);
                                rs1=pst1.executeQuery();
                               
                                check=false;
                                while(rs1.next())
                                {
                                    type1=rs1.getString("username");
                                    srclang1=rs1.getString("language");
                                    smsg1=rs1.getString("msg");                                                                                                                                                       
                                    msg=translate(smsg1,srclang1,destlang1);                                    
                                    chat_textarea.append(type1+": "+msg+String.valueOf((char)10));
                                    check=true;
                                }              
                                pst1.close();
                                if(flag==0)
                                chat_textarea.setCaretPosition(chat_textarea.getText().length());
                                if(check==true)
                                {
                                    dt3=new java.util.Date();
                                     
                                    System.out.print("Time to receive msg in group chat \n "+(dt3.getTime()-dt2.getTime())+" ms.");
                                    pst=co.prepareStatement("update "+user+"msg set status='read' where username!=?");
                                    pst.setString(1,uname);
                                    if(pst.isClosed())
                                    {
                                        pst=co.prepareStatement("update "+user+"msg set status='read' where username!=?");
                                        pst.setString(1,uname);
                                    }
                        
                                    pst.execute();
                                    pst.close();
                                }
                            }
                            catch(SQLException ex)
                            {
                                System.out.print(ex);
                            }
                        }
                        else
                        {                                    
                            
                            if(co.isValid(1000)==false)
                                co=DriverManager.getConnection("jdbc:mysql://50.28.14.178/vkodernt_moderntimedb?autoReconnect=true&characterSetResults=UTF-8&characterEncoding=UTF-8&useUnicode=yes&user=vkodernt_harshit&password=harshit@PASS32");                    
                            pst=co.prepareStatement("select * from "+uname+"_"+user+"t where status='not' and msg_type='receive'");                                 
                            if(pst.isClosed())
                                pst=co.prepareStatement("select * from "+uname+"_"+user+"t where status='not' and msg_type='receive'");                                 
                            rs=pst.executeQuery();
                            check=false;
                            while(rs.next())
                            {
                                msg=rs.getString("msg");
                                chat_textarea.append(user+": "+msg+String.valueOf((char)10));
                                check=true;                                
                            }                                                                                                  
                            if(flag==0)
                                chat_textarea.setCaretPosition(chat_textarea.getText().length());
                            if(check==true)
                            {
                                dt3=new java.util.Date();
                                System.out.print("Time to receive msg in individual chat \n "+(dt3.getTime()-dt2.getTime())+" ms.");
                                pst=co.prepareStatement("update "+uname+"_"+user+"t set status='read' where msg_type='receive'");               
                                pst.execute();
                            }
                            //Fetch information of friend language
                            pst=co.prepareStatement("select language from users where username=?");
                            pst.setString(1,user);
            
                            rs=pst.executeQuery();
                            while(rs.next())
                            {
                                dest=rs.getString("language");
                            }
                            pst.close();
           
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.print(e.getMessage());
                    }
                }   
            }
            catch(Exception ex)
            {       
            }
            
      }
       
   }
    private void choice_selectlanguageItemStateChanged(java.awt.event.ItemEvent evt)
    {
        try
        {     
            source=lang[choice_selectlanguage.getSelectedIndex()];
            if(co.isValid(1000)==false)        
               co=DriverManager.getConnection("jdbc:mysql://50.28.14.178/vkodernt_moderntimedb?characterSetResults=UTF-8&characterEncoding=UTF-8&useUnicode=yes&user=vkodernt_harshit&password=harshit@PASS32");
            pst=co.prepareStatement("update users set language=? where username=?");
            pst.setString(1,source);
            pst.setString(2, uname);
            pst.execute();
            pst.close();
        } 
        
        catch(Exception ex)
        {
            System.out.print(ex);
        }
       
    }
    private void  choice_selectfriendItemStateChanged(java.awt.event.ItemEvent evt)
    {
        chat_textarea.setText("");               
        
        if(choice_selectfriend.getSelectedIndex()!=0)
        {                                  
            user=choice_selectfriend.getSelectedItem();
           
            if(user.endsWith("_"))
            {
                try
                {
                    destlang=lang[choice_selectlanguage.getSelectedIndex()];
                    if(co.isValid(1000)==false)
                        co=DriverManager.getConnection("jdbc:mysql://50.28.14.178/vkodernt_moderntimedb?autoReconnect=true&characterSetResults=UTF-8&characterEncoding=UTF-8&useUnicode=yes&user=vkodernt_harshit&password=harshit@PASS32");                    
                    pst=co.prepareStatement("select * from "+user+"msg");                    
                    rs=pst.executeQuery();
                   
                    while(rs.next())
                    {
                        type=rs.getString("username");
                        srclang=rs.getString("language");
                        smsg=rs.getString("msg");                         
                        if(type.equalsIgnoreCase(uname))
                        {
                            msg=translate(smsg,srclang,destlang);
                            chat_textarea.append("Me: "+msg+String.valueOf((char)10));
                        }
                        else
                        {                                                                                                                                 
                            msg=translate(smsg,srclang,destlang);                                    
                            chat_textarea.append(type+": "+msg+String.valueOf((char)10));    
                        }
                    }
                   
                    chat_textarea.setCaretPosition(chat_textarea.getText().length());
                    pst.close();   
                
                        
                    pst=co.prepareStatement("update "+user+"msg set status='read' where username!=?");
                    pst.setString(1,uname);
                    if(pst.isClosed())
                    {
                        pst=co.prepareStatement("update "+user+"msg set status='read' where username!=?");
                        pst.setString(1,uname);
                    }
                        
                    pst.execute();
                    pst.close();
                }
                catch(SQLException ex)
                {
                    System.out.print(ex);
                }
            }
            else
            {                            
                try
                {
                    if(co.isValid(1000)==false)
                        co=DriverManager.getConnection("jdbc:mysql://50.28.14.178/vkodernt_moderntimedb?autoReconnect=true&characterSetResults=UTF-8&characterEncoding=UTF-8&useUnicode=yes&user=vkodernt_harshit&password=harshit@PASS32");
                       
                    //Fetch information of friend language
                    pst=co.prepareStatement("select language from users where username=?");
                    pst.setString(1,user);
            
                    rs=pst.executeQuery();
                    while(rs.next())
                    {
                        dest=rs.getString("language");
                    }
                    pst.close();
           
           
                    //Fetching list of conversation with friend.
            
                    pst=co.prepareStatement("select * from "+uname+"_"+user+"t"); 
            
                    rs=pst.executeQuery();
            
                    while(rs.next())
                    {
                        type=rs.getString("msg_type");
                        if(type.equalsIgnoreCase("sent"))
                        {
                            chat_textarea.append("Me: "+rs.getString("msg")+String.valueOf((char)10));
                        }
                        else
                        {
                            chat_textarea.append(user+": "+rs.getString("msg")+String.valueOf((char)10));
                        }
                    }
                    chat_textarea.setCaretPosition(chat_textarea.getText().length());
                    pst.close();   
                
                        
                    pst=co.prepareStatement("update "+uname+"_"+user+"t set status='read' where msg_type='receive'"); 
                    if(pst.isClosed())
                        pst=co.prepareStatement("update "+uname+"_"+user+"t set status='read' where msg_type='receive'");
                    pst.execute();
                    pst.close();
                }
                catch(Exception e)
                {
                    System.out.print("fsdfs"+e);
                }
            }
        }
    }
   
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        add_friends af=new add_friends(uname,emtext);
        af.setVisible(true);
        setVisible(false);
    }
    
     //chng
     private void groupActionPerformed(java.awt.event.ActionEvent evt) {        
        group_info gi=new group_info(uname,emtext);
        gi.setVisible(true);        
        setVisible(false);
    }//
    
    String translate(String text, String source, String dest) 
		{ 
                    dt2=new java.util.Date();
                    System.out.println("time before translating  \n" +dt2);
                    
                   if(source.equalsIgnoreCase(dest)==true)
                   {
                      
                       dt3=new java.util.Date();
                       System.out.println("time taken for translating when same language \n" +(dt3.getTime()-dt2.getTime())+" ms.");
                       return text;
                      
                   }
                   else
                   {
        		StringBuilder result = new StringBuilder();
        		try 
			{
            			String encodedText = URLEncoder.encode(text, "UTF-8");
            			String urlStr = "https://www.googleapis.com/language/translate/v2?key=AIzaSyBKvjKVgpxPjL8aNq3CbKFFqyAyakSkLg8&q=" + encodedText + "&target=" + dest + "&source=" + source;
 
            			URL url = new URL(urlStr);
 
            			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            			InputStream stream;
            			if (conn.getResponseCode() == 200) //success
            			{
                				stream = conn.getInputStream();
            			} 
			else
                				stream = conn.getErrorStream();
 
            			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            			String line;
            			while ((line = reader.readLine()) != null) 
			{
                				result.append(line);
            			}
 
            			JsonParser parser = new JsonParser();
 
            			JsonElement element = parser.parse(result.toString());
 
            			if (element.isJsonObject()) 
			{
                				JsonObject obj = element.getAsJsonObject();
                				if (obj.get("error") == null)
				 {
                    					String translatedText = obj.get("data").getAsJsonObject().
                    					get("translations").getAsJsonArray().
                    					get(0).getAsJsonObject().
                    					get("translatedText").getAsString();
                                                        
                                                        dt3=new java.util.Date();
                       System.out.println("time taken for translating when digfferent language \n" +(dt3.getTime()-dt2.getTime())+" ms.");
                    					return translatedText;
 
                				}
            			}
 
            			if (conn.getResponseCode() != 200) 
			{
                				System.err.println(result);
            			}
 
        		} 
		catch (IOException ex) 
		{
            			System.err.println(ex.getMessage());
        		}
 		catch (JsonSyntaxException ex) 
		{
            			System.err.println(ex.getMessage());
        		}
                        
        		return text;
                   }
    	}
    private void  sendActionPerformed()
    {       
        
        user=choice_selectfriend.getSelectedItem();        
        if(user.substring(user.length()-1).equalsIgnoreCase("_"))
        {
            try
            {
                dt2=new java.util.Date();
               
                System.out.println("time when sending in group chat \n" +dt2);
                
                pst=co.prepareStatement("insert into "+user+"msg(username,msg,status,language) values(?,?,?,?)");                                
               
                pst.setString(1, uname);
                pst.setString(2, txt_chatwrite.getText());
                pst.setString(3,"not");
                pst.setString(4, lang[choice_selectlanguage.getSelectedIndex()]);
               
                pst.execute(); 
                dt3=new java.util.Date();
             
                
                System.out.println("time taken to send message and saved in database in group chat \n " +(dt3.getTime()-dt2.getTime())+" ms.");
                chat_textarea.append("Me: "+txt_chatwrite.getText()+String.valueOf((char)10));
                txt_chatwrite.setText("");
                chat_textarea.setCaretPosition(chat_textarea.getText().length());
            }
            catch(SQLException ex)
            {
                System.out.print(ex);
            }
        }
        else
        {
             dt2=new java.util.Date();
             
             
            System.out.println("time when sending in individual chat \n" +dt2);
            String translated_text;
            
        //System.out.print("test is "+translated_text+"\n\n");
            try
            {               
               
               
               if(co.isValid(1000)==false)
                    co=DriverManager.getConnection("jdbc:mysql://50.28.14.178/vkodernt_moderntimedb?autoReconnect=true&characterSetResults=UTF-8&characterEncoding=UTF-8&useUnicode=yes&user=vkodernt_harshit&password=harshit@PASS32");
              
            
              
              
               pst=co.prepareStatement("insert into "+uname+"_"+user+"t(msg,msg_type,status) values(?,?,?)");                                
               
               pst.setString(1, txt_chatwrite.getText());
               pst.setString(2, "sent");
               pst.setString(3,"not");
               
               pst.execute();               
               pst.close();               
               chat_textarea.append("Me: "+txt_chatwrite.getText()+String.valueOf((char)10));
               dt3=new java.util.Date();
               System.out.println("time taken to send message and saved in database in individual chat while saving in mine table \n" +(dt3.getTime()-dt2.getTime())+" ms."); 
               pst=co.prepareStatement("insert into "+user+"_"+uname+"t(msg,msg_type,status) values(?,?,?)");                                
               dt2=new java.util.Date();
               translated_text=translate(txt_chatwrite.getText(),source,dest);
               pst.setString(1, translated_text);
               pst.setString(2, "receive");
               pst.setString(3,"not");
               pst.execute();
                          
               pst.close();
               dt3=new java.util.Date();
               System.out.println("time taken to send message and saved in database in individual chat while after trnaslation and other table \n" +(dt3.getTime()-dt2.getTime())+" ms.");
                
          
               txt_chatwrite.setText("");
               chat_textarea.setCaretPosition(chat_textarea.getText().length());
            }
            catch(SQLException ex)
            {
                System.out.print(ex);
            }
        }
       
    }  
}