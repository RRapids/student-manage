package com.sm.frame;

import com.sm.factory.DAOFactory;
import com.sm.factory.ServiceFactory;
import com.sm.utils.ResultEntity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AdminLoginFrame extends JFrame {
    private JPanel rootPanel;
    private JLabel accountLabel;
    private JTextField accountField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;

    public AdminLoginFrame() {
        init();
        setTitle("管理员登录");
        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //获得输入的账号和密码，注意密码框组件的使用方法
                String account = accountField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                ResultEntity resultEntity = ServiceFactory.getAdminServiceInstance().adminLogin(account, password);
                JOptionPane.showMessageDialog(rootPanel, resultEntity.getMessage());
                //登录成功，进入主界面，并关闭登录界面
                if (resultEntity.getCode() == 0) {
                    try {
                        new AdminMainFrame(DAOFactory.getAdminDAOInstance().getAdminByAccount("aaa@qq.com"));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    AdminLoginFrame.this.dispose();

                } else if (resultEntity.getCode() == 1) {  //密码错误，清空密码框
                    passwordField.setText("");
                } else {   //账号错误，清空两个输入框
                    accountField.setText("");
                    passwordField.setText("");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountField.setText("");
                passwordField.setText("");
            }
        });
    }

    private void init() {

        Font font = new Font("楷体", Font.PLAIN, 20);
        //创建JPanel对象的同时，将背景图绘制上去
        rootPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                try {
                    BufferedImage bg = ImageIO.read(new File("C:/img/bg.gif"));
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        loginButton = new JButton(){
            protected void paintComponent(Graphics g) {
                try {
                    BufferedImage bg = ImageIO.read(new File("C:/img/login.png"));
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        cancelButton = new JButton(){
            protected void paintComponent(Graphics g) {
                try {
                    BufferedImage bg = ImageIO.read(new File("C:/img/center.png"));
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        loginButton.setFocusPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        cancelButton.setFocusPainted(false);
        cancelButton.setContentAreaFilled(false);
        cancelButton.setBorderPainted(false);
        rootPanel.setLayout(null);

        accountField.setBackground(new Color(240, 240, 240));
        passwordField.setBackground(new Color(240, 240, 240));
        accountLabel.setBounds(50, 60, 100, 35);
        accountField.setBounds(120, 60, 250, 35);
        passwordLabel.setBounds(50, 140, 100, 35);
        passwordField.setBounds(120, 140, 250, 35);
        loginButton.setBounds(100, 220, 40, 40);
        cancelButton.setBounds(280, 220, 40, 40);
        accountLabel.setFont(font);
        passwordLabel.setFont(font);
        rootPanel.add(accountLabel);
        rootPanel.add(passwordLabel);
        rootPanel.add(accountField);
        rootPanel.add(passwordField);
        rootPanel.add(loginButton);
        rootPanel.add(cancelButton);
        add(rootPanel);
    }

    public static void main(String[] args) throws Exception {
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        UIManager.setLookAndFeel(lookAndFeel);
        new AdminLoginFrame();
    }
}
