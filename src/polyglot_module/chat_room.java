/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyglot_module;
import javax.swing.JFrame;

/**
 * polyGlot CSC579/466 Project
 *Team Members : Harshit Jain & Nitin Goyal
 */
public class chat_room extends JFrame{
    
    javax.swing.JLabel lbl_chatroom;
    javax.swing.JButton btn_addfriend;
    java.awt.Choice choice_selectfriend;
    java.awt.Choice choice_selectlanguage;
    javax.swing.JScrollPane chat_panel;
    javax.swing.JTextArea chat_textarea;
    javax.swing.JTextField txt_chatwrite;
    javax.swing.JButton btn_send;
    javax.swing.JLabel img_appimage;
    
    public chat_room()
    {
        lbl_chatroom = new javax.swing.JLabel();
        btn_addfriend = new javax.swing.JButton();
        choice_selectfriend = new java.awt.Choice();
        choice_selectlanguage = new java.awt.Choice();
        chat_panel = new javax.swing.JScrollPane();
        chat_textarea = new javax.swing.JTextArea();
        txt_chatwrite = new javax.swing.JTextField();
        btn_send = new javax.swing.JButton();
        img_appimage = new javax.swing.JLabel();
        
        choice_selectfriend.add("Select Friend");
        choice_selectlanguage.add("Select Language");
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);


        lbl_chatroom.setFont(new java.awt.Font("Calibri Light", 1, 18)); 
        lbl_chatroom.setForeground(new java.awt.Color(255, 255, 255));
        lbl_chatroom.setText("Chat Room");
        getContentPane().add(lbl_chatroom).setBounds(20, 30, 110, 40);
        
        btn_addfriend.setText("Add Friend");
        getContentPane().add(btn_addfriend).setBounds(25, 80, 120, 20);
        
        chat_textarea.setColumns(20);
        chat_textarea.setRows(5);
        chat_panel.setViewportView(chat_textarea);
        
        getContentPane().add(choice_selectfriend).setBounds(170, 80, 105, 20);
        getContentPane().add(choice_selectlanguage).setBounds(290, 80, 105, 20);
        getContentPane().add(chat_panel).setBounds(25, 110, 370, 120);
        getContentPane().add(txt_chatwrite).setBounds(25, 240, 370, 40);

        btn_send.setText("Send");
        getContentPane().add(btn_send).setBounds(165, 290, 90, 20);
        
        img_appimage.setIcon(new javax.swing.ImageIcon("C:\\Users\\Harshit\\Documents\\NetBeansProjects\\create_account.jpg"));
        //img_appimage.setText("createaccount_image");
        getContentPane().add(img_appimage).setBounds(0, 0, 436, 389);
        
        btn_addfriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) { 
        add_friends af=new add_friends();
        af.setVisible(true);
    }
    public static void main(String par[])
    {
        chat_room cr=new chat_room();
        cr.setSize(436,389);
        cr.setVisible(true);
    }
}
