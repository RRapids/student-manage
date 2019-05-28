package com.sm.frame;

import com.sm.dao.DepartmentDAO;
import com.sm.entity.*;
import com.sm.factory.DAOFactory;
import com.sm.factory.ServiceFactory;
import com.sm.service.DepartmentService;
import com.sm.ui.ImgPanel;
import com.sm.utils.AliOSSUtil;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import sun.swing.table.DefaultTableCellHeaderRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class AdminMainFrame extends JFrame {
    private ImgPanel rootPanel;
    private JButton 院系管理Button;
    private JButton 班级管理Button;
    private JButton 学生管理Button;
    private JButton 奖惩管理Button;
    private JPanel centerPanel;
    private JPanel departmentPanel;
    private JPanel topPanel;
    private JPanel leftPanel;
    private JScrollPane scrollPane;
    private JPanel classPanel;
    private JPanel studentPanel;
    private JPanel rewardPunishPanel;
    private JButton 刷新数据Button;
    private JButton 新增院校Button;
    private JTextField depNameField;
    private JButton 选择院系Logo图Button;
    private JButton 新增Button;
    private JPanel contentPanel;
    private JLabel timeLabel;
    private JLabel adminLabel;
    private JComboBox<Department> depcomboBox;
    private JTextField textField1;
    private JButton 新增班级Button;
    private JPanel treePanel;
    private JPanel classContentPanel;
    private JPanel stuTopPanel;
    private JComboBox<Department> comboBox1;
    private JComboBox<CClass> comboBox2;
    private JTextField textField2;
    private JButton 搜素Button;
    private JButton 新增学生Button;
    private JButton 批量导入Button;
    private ImgPanel infoPanel;
    private JPanel tablePanel;
    private JLabel stuAvatarLabel;
    private JLabel xuehao;
    private JLabel department;
    private JLabel classLabel;
    private JLabel nameLabel;
    private JLabel gender;
    private JLabel birthday;
    private JTextField addressTextField;
    private JTextField phoneTextField;
    private JButton 编辑Button;
    private JButton 初始化数据Button;
    private TimerTask clockTask;
    private Timer timer;
    private Admin admin;
    private DepartmentDAO departmentDAO;
    private File file;
    private JLabel logoLabel;
    private String uploadFileUrl;
    private int departmentId = 0;
    int row;
    private DepartmentService departmentService = ServiceFactory.getDepartmentServiceInstance();

    private void showDepartments() {
        //移除原有数据
        contentPanel.removeAll();
        //从service层获取到所有院系列表
        List<Map> departmentList = ServiceFactory.getDepartmentServiceInstance().selectDepartmentInfo();
        GridLayout gridLayout = new GridLayout(0, 4, 10, 10);
        contentPanel.setLayout(gridLayout);
        for (Map map : departmentList) {
            //给每个院系对象创建一个面板
            JPanel depPanel = new JPanel();
            Department department = (Department) map.get("department");
            int classCount = (int) map.get("classCount");
            int studentCount = (int) map.get("studentCount");
            depPanel.setPreferredSize(new Dimension(300, 300));
            //将院系名称设置给面板标题
            depPanel.setBorder(BorderFactory.createTitledBorder(department.getDepartmentName()));
            //新建一个Label用来放置院系logo，并指定大小
            logoLabel = new JLabel("<html><img src='" + department.getLogo() + "' width=200 height=200/></html>");
            //右点击图片删除
            JPopupMenu jPopupMenu = new JPopupMenu();
            JMenuItem item = new JMenuItem("删除");
            jPopupMenu.add(item);
            logoLabel.add(jPopupMenu);
            logoLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == 3) {
                        jPopupMenu.show(depPanel, e.getX(), e.getY());
                        item.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int n = JOptionPane.showConfirmDialog(null, "确定要删除这行数据吗？", "删除警告",
                                        JOptionPane.YES_OPTION);
                                if (n == JOptionPane.YES_OPTION) {
                                    contentPanel.remove(depPanel);
                                    contentPanel.repaint();
                                    ServiceFactory.getDepartmentServiceInstance().deleteDepartment(department.getId());
                                }
                            }
                        });
                    }
                }
            });
            //图标标签加入院系面板
            depPanel.add(logoLabel);
            //统计数据加入院系面板
            JLabel infoLabel = new JLabel("班级" + classCount + "个，学生" + studentCount + "人");
            depPanel.add(infoLabel);
            //院系面板加入主体内容面板
            contentPanel.add(depPanel);
            //刷新主体内容面板
            contentPanel.revalidate();
        }
    }

    private void showClassPanel() {
        List<Department> departmentList = ServiceFactory.getDepartmentServiceInstance().selectAll();
        showCombobox(departmentList);
        showTree(departmentList);
        showClasses(departmentList);
    }

    private void showCombobox(List<Department> departmentList) {
        for (Department department : departmentList) {
            depcomboBox.addItem(department);
        }
    }

    private void showTree(List<Department> departmentList) {
        treePanel.removeAll();
        //左侧树形结构根节点
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("南工院");
        for (Department department : departmentList) {
            DefaultMutableTreeNode group = new DefaultMutableTreeNode(department.getDepartmentName());
            top.add(group);
            List<CClass> cClassList = ServiceFactory.getCClassServiceInstance().selectByDepartmentId(department.getId());
            for (CClass cClass : cClassList) {
                int num = ServiceFactory.getStudentServiceInstance().countStudentByClassId(cClass.getId());
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(cClass.getClassName() + "（" + num + "人)");
                group.add(node);
            }
        }
        final JTree tree = new JTree(top);
        tree.setRowHeight(30);
        tree.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        treePanel.add(tree);
        treePanel.revalidate();
    }

    private void showClasses(List<Department> departmentList) {
        classContentPanel.removeAll();
        //右侧流式布局显示
        Font titleFont = new Font("微软雅黑", Font.PLAIN, 22);
        for (Department department : departmentList) {
            ImgPanel depPanel = new ImgPanel();
            depPanel.setFileName("bg3.jpg");
            depPanel.repaint();
            depPanel.setPreferredSize(new Dimension(350, 500));
            depPanel.setLayout(null);
            JLabel depNameLabel = new JLabel(department.getDepartmentName());
            depNameLabel.setFont(titleFont);
            depNameLabel.setBounds(130, 15, 200, 30);
            //获得这个院系的所有数据
            List<CClass> cClassList = ServiceFactory.getCClassServiceInstance().selectByDepartmentId(department.getId());
            //数据模型
            DefaultListModel listModel = new DefaultListModel();
            //遍历班级集合，以此加入数据模型
            for (CClass cClass : cClassList) {
                listModel.addElement(cClass);
            }
            //使用数据模型创建一个JList组件
            JList<CClass> jList = new JList<>(listModel);
            //JList加入滚动面板
            JScrollPane listScrollPane = new JScrollPane(jList);
            listScrollPane.setBounds(90, 120, 200, 260);
            depPanel.add(depNameLabel);
            depPanel.add(listScrollPane);
            classContentPanel.add(depPanel);

            JPopupMenu jPopupMenu = new JPopupMenu();
            JMenuItem item1 = new JMenuItem("修改");
            JMenuItem item2 = new JMenuItem("删除");
            jPopupMenu.add(item1);
            jPopupMenu.add(item2);
            jList.add(jPopupMenu);
            jList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //选中项的下标
                    int index = jList.getSelectedIndex();
                    //点击鼠标右键
                    if (e.getButton() == 3) {
                        //在鼠标位置弹出菜单
                        jPopupMenu.show(jList, e.getX(), e.getY());
                        //取出选中项数据
                        CClass cClass = jList.getModel().getElementAt(index);
                        //对“删除”菜单项调价监听
                        item2.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int choice = JOptionPane.showConfirmDialog(depPanel, "确定删除吗？");
                                if (choice == 0) {
                                    ServiceFactory.getCClassServiceInstance().deleteClass(cClass.getId());
                                    listModel.remove(index);
                                    showTree(ServiceFactory.getDepartmentServiceInstance().selectAll());
                                }
                            }
                        });
                    }
                }
            });

        }

    }

    public void showStudentTable(List<StudentVO> studentList) {
        tablePanel.removeAll();
        //获得所有学生视图数据
//        List<StudentVO> studentVOList = ServiceFactory.getStudentServiceInstance().selectAll();
        //创建表格
        JTable table = new JTable();
        //表格数据模型
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        //表头内容
        model.setColumnIdentifiers(new String[]{"学号", "院系", "班级", "姓名", "性别", "地址", "手机号", "出生日期", "头像"});
        //遍历List,转成Objuect数组
        for (StudentVO student : studentList) {
            Object[] objects = new Object[]{student.getId(), student.getDepartmentName(), student.getClassName(), student.getStudentName(),
                    student.getGender(), student.getAddress(), student.getPhone(), student.getBirthday(), student.getAvatar()};
            model.addRow(objects);
        }
        //将最后一列隐藏头像地址不显示在表格中
        TableColumn tableColumn = table.getColumnModel().getColumn(8);
        tableColumn.setMinWidth(0);
        tableColumn.setMaxWidth(0);
        //获得表头
        JTableHeader head = table.getTableHeader();
        //表头居中
        DefaultTableCellHeaderRenderer hr = new DefaultTableCellHeaderRenderer();
        hr.setHorizontalAlignment(JLabel.CENTER);
        head.setDefaultRenderer(hr);
        //设置表头大小
        head.setPreferredSize(new Dimension(head.getWidth(), 40));
        //设置表头字体
        head.setFont(new Font("楷体", Font.PLAIN, 22));
        //设置表格行高
        table.setRowHeight(35);
        //表格背景色
        table.setBackground(new Color(212, 212, 212));
        //表格内容居中
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, r);
        //表格加入滚动面板，水平垂直方向带滚动条
        JScrollPane scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        tablePanel.add(scrollPane);
        tablePanel.revalidate();

        //弹出菜单
        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem item = new JMenuItem("删除");
        jPopupMenu.add(item);
        table.add(jPopupMenu);
        //表格监听

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //将选中的行数据显示到右侧个人信息预览区
                row = table.getSelectedRow();
                xuehao.setText(table.getValueAt(row, 0).toString());
                department.setText(table.getValueAt(row, 1).toString());
                classLabel.setText(table.getValueAt(row, 2).toString());
                nameLabel.setText(table.getValueAt(row, 3).toString());
                gender.setText(table.getValueAt(row, 4).toString());
                addressTextField.setText(table.getValueAt(row, 5).toString());
                phoneTextField.setText(table.getValueAt(row, 6).toString());
                birthday.setText(table.getValueAt(row, 7).toString());
                stuAvatarLabel.setText("<html><img src='" + table.getValueAt(row, 8).toString() + "'/></html>");
                stuAvatarLabel.setVisible(true);
                //显示按钮
                编辑Button.setVisible(true);
                编辑Button.setText("编辑");
                编辑Button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (e.getActionCommand().equals("编辑")) {
                            addressTextField.setEditable(true);
                            addressTextField.setEnabled(true);
                            phoneTextField.setEditable(true);
                            phoneTextField.setEnabled(true);
                            编辑Button.setText("保存");
                        }
                        //如果按钮文字为”保存“，则通过输入内容创建需要修改的Student对象，调用service进行信息更新
                        if (e.getActionCommand().equals("保存")) {
                            Student student = new Student();
                            student.setId(xuehao.getText());
                            student.setAddress(addressTextField.getText());
                            student.setPhone(phoneTextField.getText());
                            int n = ServiceFactory.getStudentServiceInstance().updateStudent(student);
                            if (n == 1) {
                                //更新表格中对应行的地址、电话单元内容
                                model.setValueAt(addressTextField.getText(), row, 5);
                                model.setValueAt(phoneTextField.getText(), row, 6);
                                //文本框恢复不可用状态
                                addressTextField.setEditable(false);
                                addressTextField.setEnabled(false);
                                phoneTextField.setEditable(false);
                                phoneTextField.setEnabled(false);
                                //按钮文字恢复“编辑”
                                编辑Button.setText("编辑");
                            }
                        }
                    }
                });
                //右侧弹出菜单
                if (e.getButton() == 3) {
                    jPopupMenu.show(table, e.getX(), e.getY());
                }
            }
        });
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = (String) table.getValueAt(row, 0);
                int choice = JOptionPane.showConfirmDialog(tablePanel, "确定删除吗");
                if (choice == 0) {
                    if (row != -1) {
                        model.removeRow(row);
                    }
                    //刷新表格数据
                    ServiceFactory.getStudentServiceInstance().deleteById(id);
                }

            }
        });

    }

    public AdminMainFrame(Admin admin) {
        this.admin = admin;
        adminLabel.setText("管理员：" + admin.getAdminName());
        rootPanel.setFileName("bg.jpg");
        rootPanel.repaint();
        setTitle("主界面");
        setContentPane(rootPanel);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        leftPanel.setVisible(false);
        //调用院系所有的方法
        showDepartments();
        //获取centerPanel的布局,获得是layoutManager，向下转型为cardLayout
        CardLayout cardLayout = (CardLayout) centerPanel.getLayout();
        clockTask = new TimerTask() {
            @Override
            public void run() {
                while (true) {
                    Date date = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss EE");
                    timeLabel.setText(dateFormat.format(date));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Font timeFont = new Font("微软雅黑", Font.PLAIN, 20);
        timeLabel.setFont(timeFont);
        timer = new Timer();
        timer.schedule(clockTask, 0, 1000);

        搜素Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keywords = textField2.getText().trim();
                List<StudentVO> studentList = ServiceFactory.getStudentServiceInstance().selectByKeywords(keywords);
                if (studentList != null) {
                    showStudentTable(studentList);
                }
            }
        });

        院系管理Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(centerPanel, "Card1");
            }
        });
        班级管理Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(centerPanel, "Card2");
                showClassPanel();
            }
        });

        新增院校Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag = leftPanel.isVisible();
                if (flag == true) {
                    leftPanel.setVisible(false);
                } else {
                    leftPanel.setVisible(true);
                }
            }
        });
        学生管理Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(centerPanel, "Card3");
                //右侧个人信息面板加入背景图
                infoPanel.setFileName("info.jpg");
                infoPanel.repaint();
                //调用显示学生数据的方法
                List<StudentVO> studentList = ServiceFactory.getStudentServiceInstance().selectAll();
                showStudentTable(studentList);
                //两个下拉框初始化提示数据，因为里面元素都是对象，可以这样进行处理
                Department tip1 = new Department();
                tip1.setDepartmentName("请选择院系");
                comboBox1.addItem(tip1);
                CClass tip2 = new CClass();
                tip2.setClassName("请选择班级");
                comboBox2.addItem(tip2);
                //初始化院系下拉框数据
                List<Department> departmentList = ServiceFactory.getDepartmentServiceInstance().selectAll();
                for (Department department : departmentList) {
                    comboBox1.addItem(department);
                }
                //初始化班级下拉框数据
                List<CClass> cClassList = ServiceFactory.getCClassServiceInstance().selectAll();
                for (CClass cClass : cClassList) {
                    comboBox2.addItem(cClass);
                }
                //院系下拉框监听，选中哪项，表格中显示该院系所有学生，并级联查除该院系的所有班级，更新到班级下拉框
                comboBox1.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if (e.getStateChange() == ItemEvent.SELECTED) {
                            int index = comboBox1.getSelectedIndex();
                            //排除第一项，那是提示信息，不能作为查询依据
                            if (index != 0) {
                                departmentId = comboBox1.getItemAt(index).getId();
                                //获得该院系的学生并显示在表格中
                                List<StudentVO> studentList = ServiceFactory.getStudentServiceInstance().selectByDepartmentId(departmentId);
                                showStudentTable(studentList);
                                //二级联动——班级n下拉框的内容随着选择院系的不同而变化
                                List<CClass> cClassList1 = ServiceFactory.getCClassServiceInstance().selectByDepartmentId(departmentId);
                                //一定要先移除之前数据，否则下拉框内容会叠加
                                comboBox2.removeAllItems();
                                CClass tip = new CClass();
                                tip.setClassName("请选择班级");
                                comboBox2.addItem(tip);
                                for (CClass cClass : cClassList1) {
                                    comboBox2.addItem(cClass);
                                }
                            }
                        }
                    }
                });
                comboBox2.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if (e.getStateChange() == ItemEvent.SELECTED) {
                            int index = comboBox2.getSelectedIndex();
                            if (index != 0) {
                                int classId = comboBox2.getItemAt(index).getId();
                                List<StudentVO> studentList = ServiceFactory.getStudentServiceInstance().selectByClassId(classId);
                                showStudentTable(studentList);
                            }
                        }
                    }
                });
                //文本框恢复不可用状态
                addressTextField.setEditable(false);
                addressTextField.setEnabled(false);
                phoneTextField.setEditable(false);
                phoneTextField.setEnabled(false);
            }
        });
        奖惩管理Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(centerPanel, "Card4");
            }
        });
        depNameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                depNameField.setText("");
            }
        });

        选择院系Logo图Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("C:\\Users\\79876\\Pictures"));
                int result = fileChooser.showOpenDialog(rootPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    //选中文件
                    file = fileChooser.getSelectedFile();
//                    //通过文件创建icon对象
//                    Icon icon = new ImageIcon(file.getAbsolutePath());
//                    //通过标签显示图片
//                    logoLabel.setIcon(icon);
//                    //设置标签可见
//                    logoLabel.setVisible(true);
                }
            }
        });

        新增Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //上传文件到OSS并返回外链
                uploadFileUrl = AliOSSUtil.ossUpload(file);
                //创建Department对象，并设置相应属性
                Department department = new Department();
                department.setDepartmentName(depNameField.getText().trim());
                department.setLogo(uploadFileUrl);
                //调用service实现新增功能
                long n = ServiceFactory.getDepartmentServiceInstance().addDepartment(department);
                if (n == 1) {
                    ImageIcon icon = new ImageIcon(file.getAbsolutePath());

                    icon.setImage(icon.getImage().getScaledInstance(200, 200, Image.SCALE_AREA_AVERAGING));
                    logoLabel.setIcon(icon);
                    logoLabel.setVisible(true);
                    JOptionPane.showMessageDialog(rootPanel, "新增院系成功");
                    //新增成功后，将侧边栏隐藏
                    leftPanel.setVisible(false);
                    //刷新界面数据
                    showDepartments();
                    //将图片预览标签隐藏
                    logoLabel.setVisible(false);
                    //将选择图片的按钮可见
                    选择院系Logo图Button.setVisible(true);
                    //清空文本框
                    depNameField.setText("");
                } else {
                    JOptionPane.showMessageDialog(rootPanel, "新增院系失败");
                }
            }
        });
        刷新数据Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDepartments();
            }
        });
        depcomboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //得到选中项的索引
                int index = depcomboBox.getSelectedIndex();
                //按照索引取出项，就是一个Department对象，然后取出其id备用
                departmentId = depcomboBox.getItemAt(index).getId();
            }
        });
        新增班级Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CClass cClass = new CClass();
                cClass.setDepartmentId(departmentId);
                cClass.setClassName(textField1.getText().trim());
                long n = ServiceFactory.getCClassServiceInstance().addClass(cClass);
                if (n == 1) {
                    JOptionPane.showMessageDialog(classPanel, "新增班级成功");
                    showClassPanel();
                }
            }
        });

        初始化数据Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //还原表格数据
                List<StudentVO> studentList = ServiceFactory.getStudentServiceInstance().selectAll();
                showStudentTable(studentList);
                //院系下拉框还原
                comboBox1.setSelectedIndex(0);
                //班级下拉框数据还原
                comboBox2.removeAllItems();
                CClass tip2 = new CClass();
                tip2.setClassName("请选择班级");
                comboBox2.addItem(tip2);
                List<CClass> cClassList = ServiceFactory.getCClassServiceInstance().selectAll();
                for (CClass cClass : cClassList) {
                    comboBox2.addItem(cClass);
                }
                //右侧个人信息显示区域数据还原
                stuAvatarLabel.setText("<html><img src='https://rapids.oss-cn-beijing.aliyuncs.com/wenhao.jpg'/></html>");
                xuehao.setText("未选择");
                department.setText("未选择");
                classLabel.setText("未选择");
                nameLabel.setText("未选择");
                gender.setText("未选择");
                birthday.setText("未选择");
                addressTextField.setText("");
                phoneTextField.setText("");
                //搜索框清空
                textField2.setText("");
                //编制按钮隐藏
                编辑Button.setVisible(false);
            }
        });

        新增学生Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddFrame(AdminMainFrame.this);
                AdminMainFrame.this.setEnabled(false);
            }
        });
    }

    public static void main(String[] args) throws Exception {
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        UIManager.setLookAndFeel(lookAndFeel);
        new AdminMainFrame(DAOFactory.getAdminDAOInstance().getAdminByAccount("aaa@qq.com"));
    }
}
