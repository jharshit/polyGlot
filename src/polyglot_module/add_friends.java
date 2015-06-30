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
    
    public add_friends()
    {
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
        
        img_appimage.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("resource/create_account.jpg")));
        //img_appimage.setText("createaccount_image");
        getContentPane().add(img_appimage).setBounds(0, 0, 436, 389);
        
        setSize(436,389);
        setVisible(true);
    }
    public static void main(String par[])
    {
     add_friends af=new add_friends(); 
    }
}
