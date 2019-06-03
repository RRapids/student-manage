package com.sm.frame;

import com.eltima.components.ui.DatePicker;
import com.sm.entity.Rewards;
import com.sm.factory.ServiceFactory;
import com.sm.ui.ImgPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RewardsAddFrame extends JFrame {
    private ImgPanel rootPanel;
    private JTextField typeTextField;
    private JTextField numberTextField;
    private JTextField nameTextField4;
    private JTextField reasonTextField;
    private JPanel timePanel;
    private JButton 新增Button2;
    private JLabel closeLabel2;
    private JTextField bianhaoTextField;
    private AdminMainFrame adminMainFrame;

    public RewardsAddFrame(AdminMainFrame adminMainFrame) {
        this.adminMainFrame = adminMainFrame;
        rootPanel.setFileName("bg4.jpeg");
        setTitle("新增学生界面");
        setContentPane(rootPanel);
        setSize(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        closeLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RewardsAddFrame.this.dispose();
                adminMainFrame.setEnabled(true);
            }
        });
        DatePicker datepick = getDatePicker();
        timePanel.add(datepick);
        timePanel.revalidate();

        新增Button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Rewards rewards = new Rewards();
                rewards.setId(bianhaoTextField.getText());
                rewards.setType(typeTextField.getText());
                rewards.setRewardsDate((Date) datepick.getValue());
                rewards.setNumber(numberTextField.getText());
                rewards.setName(nameTextField4.getText());
                rewards.setReason(reasonTextField.getText());
                int n = ServiceFactory.getRewardsServiceInstance().insertRewards(rewards);
                if (n == 1){
                    JOptionPane.showMessageDialog(rootPanel,"新增成功");
                    RewardsAddFrame.this.dispose();
                    adminMainFrame.setEnabled(true);
                    List<Rewards> rewardsList = ServiceFactory.getRewardsServiceInstance().getAll();
                    adminMainFrame.showRewardsTable(rewardsList);
                }
            }
        });
    }

    private static DatePicker getDatePicker() {
        final DatePicker datepick;
        // 格式
        String DefaultFormat = "yyyy-MM-dd";
        // 当前时间
        Date date = new Date();
        // 字体
        Font font = new Font("Times New Roman", Font.PLAIN, 18);
        Dimension dimension = new Dimension(200, 30);
        int[] hilightDays = {1, 3, 5, 7};
        int[] disabledDays = {4, 6, 5, 9};
        //构造方法（初始时间，时间显示格式，字体，控件大小）
        datepick = new DatePicker(date, DefaultFormat, font, dimension);
        datepick.setHightlightdays(hilightDays, Color.red);
        // 设置一个月份中不需要的日子，呈灰色显示
        datepick.setDisableddays(disabledDays);
        // 设置国家
        datepick.setLocale(Locale.CHINA);
        return datepick;
    }
}
