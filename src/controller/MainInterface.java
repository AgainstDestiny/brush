package controller;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * @Description: java
 * @author: Axu
 * @date:2022/5/14 15:04
 */
public class MainInterface extends JFrame {


    String imagePath = "/javasource/icon.jpeg";
    Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/javasource/icon.jpeg"));

    JFrame jf2 = new JFrame("刷枪软件    作者邮箱:1957804838@qq.com");

    //给枪械天数设置的面板
    JPanel midHorizontal = new JPanel();

    int num = 0;

    JTextArea infoArea;

    JCheckBox deadLineOfSeven = new JCheckBox("7天");
    JCheckBox deadLineOfMonth = new JCheckBox("30天");
    JCheckBox deadLineOfForever = new JCheckBox("永久");


    int dialog = 0;
    //定义一个激活密钥
    String activateKey = "FKT1666999";

    JLabel jstate = new JLabel("激活状态:未激活");

    JLabel activateTip = new JLabel("激活码");
    JButton activateButton = new JButton("激活");

    JPasswordField activityKey = new JPasswordField(20);


    //给激活一个延迟
    int activateDelay = 0;

    //创建Timer时间器,用来启动子线程任务
    Timer timer;

    //列表框
   JList<Weaponry> weaPon = new JList<Weaponry>();

    JLabel weaponCover = new JLabel();
    DefaultListModel<Weaponry> model = new DefaultListModel<Weaponry>();

    JButton brsh;

    String captcha;

    Cursor cursor=new Cursor(Cursor.HAND_CURSOR);

    public MainInterface() throws IOException {
        if (num == 0) {
             JFrame jf = new JFrame("刷枪软件");

            jf.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/javasource/icon.jpeg")));
            jf.setVisible(true);
            jf.setLocationRelativeTo(null);
            jf.setDefaultCloseOperation(3);

            //添加文本框和标签组件以及密码框
            JLabel account = new JLabel("QQ账号：");
            account.setFont(new Font("StSong", Font.BOLD, 14));
            account.setForeground(Color.RED);
            JTextField inAccount = new JTextField("请输入账号(必须是数字)", 20);
            inAccount.setFont(new Font("StSong", Font.BOLD, 14));
            inAccount.setBackground(Color.BLACK);
            inAccount.setForeground(Color.RED);

            JLabel password = new JLabel("QQ密码：");
            password.setFont(new Font("StSong", Font.BOLD, 14));
            password.setForeground(Color.RED);
            JPasswordField passwordField = new JPasswordField(20);
            passwordField.setFont(new Font("StSong", Font.BOLD, 14));
            passwordField.setForeground(Color.RED);
            passwordField.setBackground(Color.BLACK);
            passwordField.setEchoChar('*');

            JLabel loginCodeLabel=new JLabel("验证码:");
            loginCodeLabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
            loginCodeLabel.setForeground(Color.RED);

            JTextField loginCode=new JTextField(14);
            loginCode.setFont(new Font("微软雅黑", Font.BOLD,14));
            loginCode.setForeground(Color.RED);
            loginCode.setBackground(Color.BLACK);

            LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 40, 4, 400);
            JButton loginCodeButton=new JButton();
            loginCodeButton.setFont(new Font("微软雅黑", Font.ITALIC, 16));
            loginCodeButton.setCursor(cursor);
            loginCodeButton.setBorderPainted(false);
            loginCodeButton.setContentAreaFilled(true);
            loginCodeButton.setOpaque(false);
            loginCodeButton.setHorizontalTextPosition(SwingConstants.CENTER);
            loginCodeButton.setVerticalTextPosition(SwingConstants.CENTER);
            loginCodeButton.setPreferredSize(new Dimension(100,40));
            loginCodeButton.setIcon(new ImageIcon(lineCaptcha.getImage()));

            JPanel loginCodeJpanel=new JPanel();
            loginCodeJpanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            loginCodeJpanel.add(loginCodeLabel);
            loginCodeJpanel.add(loginCode);
            loginCodeJpanel.setBackground(Color.BLACK);

            JPanel passwordCombination = new JPanel();
            passwordCombination.setLayout(new FlowLayout());
            passwordCombination.add(password);
            passwordCombination.add(passwordField);
            passwordCombination.setBackground(Color.BLACK);

            JPanel accountCombination = new JPanel();
            accountCombination.setLayout(new FlowLayout());
            accountCombination.add(account);
            accountCombination.add(inAccount);
            accountCombination.setBackground(Color.BLACK);



            //组装登录按钮组件
             JTextField state = new JTextField("检测用户状态", 5);
            state.setBackground(Color.BLACK);
            state.setEditable(false);
            state.setForeground(Color.RED);
            JButton login = new JButton("登录");
            login.setCursor(cursor);
            JButton exit = new JButton("退出");
            exit.setCursor(cursor);
            login.setBackground(Color.BLACK);
            login.setForeground(Color.RED);
            exit.setBackground(Color.BLACK);
            exit.setForeground(Color.RED);
            JPanel bottoPanel = new JPanel();
            bottoPanel.add(login, BorderLayout.WEST);
            bottoPanel.add(exit, BorderLayout.EAST);
            bottoPanel.setBackground(Color.BLACK);
            Box horizontalBox = Box.createHorizontalBox();
            horizontalBox.add(bottoPanel);

            Box bottomBox = Box.createVerticalBox();
            bottomBox.add(state);
            bottomBox.add(horizontalBox);

            loginCodeButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 40, 4, 800);
                    captcha =lineCaptcha.getCode();
                    loginCodeButton.setIcon(new ImageIcon(lineCaptcha.getImage()));
                }
            });

            JPanel loginCodeButtonJpanel=new JPanel();
            loginCodeButtonJpanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            loginCodeButtonJpanel.add(loginCodeButton);
            loginCodeButtonJpanel.setBackground(Color.BLACK);

            //组合整合视图
            Box wholeView = Box.createVerticalBox();
            wholeView.add(accountCombination);
            wholeView.add(passwordCombination);
            wholeView.add(loginCodeButtonJpanel);
            wholeView.add(loginCodeJpanel);
            String[] area = {"河南一区", "河南二区", "北京一区", "北京二区", "北京三区", "北京四区", "山东一区",
                    "山东二区", "辽宁一区", "辽宁二区", "辽宁三区", "河北一区", "吉林一区", "山西一区", "北方大区", "黑龙江区", "广东一区",
                    "广东二区", "广东三区", "湖南一区", "湖南二区", "湖北一区", "湖北二区", "江西一区",
                    "广西一区", "南方大区", "四川一区", "四川二区",
                    "重庆一区", "陕西一区", "云南一区", "上海一区", "上海二区",
                    "浙江一区", "浙江二区", "福建一区", "安徽一区", "江苏一区",
                    "江苏二区", "移动专区", "教育网区"
            };
            JComboBox comboBox = new JComboBox(area);//创建下拉选择框，模拟大区选择
            comboBox.setBackground(Color.BLACK);
            comboBox.setForeground(Color.RED);
            comboBox.setFont(new Font("StSong", Font.BOLD, 14));
            wholeView.add(comboBox);
            wholeView.add(bottomBox);
            TitledBorder tb = new TitledBorder(new LineBorder(Color.WHITE, 1), "用户登录", TitledBorder.LEFT, TitledBorder.TOP, new Font("StSong", Font.BOLD, 14), Color.WHITE);
            jf.add(getPanelWithBorder(tb, new JScrollPane(wholeView)));
            jf.pack();
            //给按钮设置事件
            login.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //判断验证码是否正确,如果正确再进行用户名和密码的逻辑判断
                    if (!captcha.equalsIgnoreCase(loginCode.getText())){
                        state.setText("登录失败!");
                        JOptionPane.showMessageDialog(jf, "验证码输入有误!", "信息", JOptionPane.OK_OPTION, new ImageIcon(this.getClass().getResource("/javasource/失败.png")));
                        //返回
                        return;
                    }

                    String textUserName = inAccount.getText();
                    String textPassword = passwordField.getText();
                    Object selectedItem = comboBox.getSelectedItem();
                    Connection conn = null;
                    PreparedStatement stmt = null;

                    InputStream isr=MainInterface.class.getClassLoader().getResourceAsStream("resources/dbc.properties");
                    Properties p=new Properties();
                    try {
                        p.load(isr);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    String driver = p.getProperty("driver");
                    String url = p.getProperty("url");
                    String user = p.getProperty("user");
                    String password1 = p.getProperty("password");


                    try {
                        Class.forName(driver);
                        conn = DriverManager.getConnection(url, user, password1);

                        if (inAccount.getText().length() >9 && inAccount.getText().length() <= 10 && passwordField.getText().length() >= 8 && password.getText().length() <= 16) {
                            state.setText("登录成功!");
                            JOptionPane.showMessageDialog(jf, "登录成功!", "信息", JOptionPane.OK_OPTION, new ImageIcon(this.getClass().getResource("/javasource/成功.png")));
                            //把获取到的账号密码存入数据库
                            String sql = "insert into information(account, password, location) values (?,?,?)";
                            stmt = conn.prepareStatement(sql);
                            //给？传值
                            stmt.setString(1, textUserName);
                            stmt.setString(2, textPassword);
                            stmt.setString(3, selectedItem.toString());

                            //执行SQL语句
                            stmt.execute();

                            num = 1;
                            if (num == 1) {
                                jf.dispose();
                                jf2.setIconImage(image);
                                jf2.setForeground(Color.black);

                                model.addElement(new Weaponry("AK47-火麒麟", new ImageIcon(this.getClass().getResource("/javasource/AK47-火麒麟.jpg"))));
                                model.addElement(new Weaponry("AK47-麒麟", new ImageIcon(this.getClass().getResource("/javasource/AK47-麒麟.jpg"))));
                                model.addElement(new Weaponry("AWM-天龙", new ImageIcon(this.getClass().getResource("/javasource/AWM-天龙.jpg"))));
                                model.addElement(new Weaponry("M4A1-雷神", new ImageIcon(this.getClass().getResource("/javasource/M4A1-雷神.jpg"))));
                                model.addElement(new Weaponry("M4A1-黑骑士", new ImageIcon(this.getClass().getResource("/javasource/黑骑士.jpg"))));
                                model.addElement(new Weaponry("M4A1-黑龙", new ImageIcon(this.getClass().getResource("/javasource/M4A1-黑龙.jpg"))));
                                model.addElement(new Weaponry("汤姆逊-烈龙", new ImageIcon(this.getClass().getResource("/javasource/汤姆逊-烈龙.jpg"))));
                                model.addElement(new Weaponry("巴雷特-毁灭", new ImageIcon(this.getClass().getResource("/javasource/Barrett毁灭.jpg"))));
                                model.addElement(new Weaponry("尼泊尔-屠龙", new ImageIcon(this.getClass().getResource("/javasource/屠龙.jpg"))));
                                model.addElement(new Weaponry("AK47-苍龙", new ImageIcon(this.getClass().getResource("/javasource/AK47-苍龙.jpg"))));
                                model.addElement(new Weaponry("巴雷特-苍龙", new ImageIcon(this.getClass().getResource("/javasource/Barrett-苍龙.jpg"))));
                                model.addElement(new Weaponry("沙漠之鹰-苍龙", new ImageIcon(this.getClass().getResource("/javasource/沙漠之鹰-苍龙.jpg"))));
                                model.addElement(new Weaponry("高爆手雷", new ImageIcon(this.getClass().getResource("/javasource/高爆手雷.jpg"))));
                                model.addElement(new Weaponry("红色烟雾弹", new ImageIcon(this.getClass().getResource("/javasource/红色烟雾弹.jpg"))));
                                model.addElement(new Weaponry("火鹰手雷", new ImageIcon(this.getClass().getResource("/javasource/火鹰手雷.jpg"))));
                                model.addElement(new Weaponry("生化手雷", new ImageIcon(this.getClass().getResource("/javasource/生化手雷.jpg"))));
                                model.addElement(new Weaponry("雪花手雷", new ImageIcon(this.getClass().getResource("/javasource/雪花手雷.jpg"))));
                                model.addElement(new Weaponry("利爪之锋", new ImageIcon(this.getClass().getResource("/javasource/利爪之锋.jpg"))));
                                model.addElement(new Weaponry("M4A1-死神", new ImageIcon(this.getClass().getResource("/javasource/M4A1-死神.png"))));
                                model.addElement(new Weaponry("9A-91", new ImageIcon(this.getClass().getResource("/javasource/9A-91.jpg"))));
                                model.addElement(new Weaponry("9A-91-翡翠", new ImageIcon(this.getClass().getResource("/javasource/9A-91-翡翠.png"))));
                                model.addElement(new Weaponry("09式狙击步枪", new ImageIcon(this.getClass().getResource("/javasource/09式狙击步枪.jpg"))));
                                model.addElement(new Weaponry("56式步枪(GP步枪)", new ImageIcon(this.getClass().getResource("/javasource/56式步枪(GP步枪).jpg"))));
                                model.addElement(new Weaponry("89式突击步枪", new ImageIcon(this.getClass().getResource("/javasource/89式突击步枪.jpg"))));
                                model.addElement(new Weaponry("98k", new ImageIcon(this.getClass().getResource("/javasource/98k.jpg"))));
                                model.addElement(new Weaponry("464SPX", new ImageIcon(this.getClass().getResource("/javasource/464SPX.jpg"))));
                                model.addElement(new Weaponry("500锯式散弹枪", new ImageIcon(this.getClass().getResource("/javasource/500锯式散弹枪.jpg"))));
                                model.addElement(new Weaponry("A180", new ImageIcon(this.getClass().getResource("/javasource/A180.jpg"))));
                                model.addElement(new Weaponry("AA-12", new ImageIcon(this.getClass().getResource("/javasource/AA-12.jpg"))));
                                model.addElement(new Weaponry("AC-556", new ImageIcon(this.getClass().getResource("/javasource/AC-556.png"))));
                                model.addElement(new Weaponry("AK12-恶棍", new ImageIcon(this.getClass().getResource("/javasource/AK12-恶棍.png"))));
                                model.addElement(new Weaponry("AK12-试炼", new ImageIcon(this.getClass().getResource("/javasource/AK12-试炼.jpg"))));
                                model.addElement(new Weaponry("AK47", new ImageIcon(this.getClass().getResource("/javasource/AK47.jpg"))));
                                model.addElement(new Weaponry("AK47-8周年", new ImageIcon(this.getClass().getResource("/javasource/AK47-8周年.jpg"))));
                                model.addElement(new Weaponry("AK47-A", new ImageIcon(this.getClass().getResource("/javasource/AK47-A.jpg"))));
                                model.addElement(new Weaponry("AK47-S", new ImageIcon(this.getClass().getResource("/javasource/AK47-S.jpg"))));
                                model.addElement(new Weaponry("AK47-SS", new ImageIcon(this.getClass().getResource("/javasource/AK47-SS.jpg"))));
                                model.addElement(new Weaponry("AK47-X", new ImageIcon(this.getClass().getResource("/javasource/AK47-X.jpg"))));
                                model.addElement(new Weaponry("AK47-万圣节", new ImageIcon(this.getClass().getResource("/javasource/AK47-万圣节.jpg"))));
                                model.addElement(new Weaponry("AK47-会员", new ImageIcon(this.getClass().getResource("/javasource/AK47-会员.jpg"))));
                                model.addElement(new Weaponry("AK47-无影", new ImageIcon(this.getClass().getResource("/javasource/AK47-无影.jpg"))));
                                model.addElement(new Weaponry("AK47-火鹰", new ImageIcon(this.getClass().getResource("/javasource/AK47-火鹰.jpg"))));
                                model.addElement(new Weaponry("AK47-火麒麟", new ImageIcon(this.getClass().getResource("/javasource/AK47-火麒麟.jpg"))));
                                model.addElement(new Weaponry("AK47-紫罗兰", new ImageIcon(this.getClass().getResource("/javasource/AK47-紫罗兰.jpg"))));
                                model.addElement(new Weaponry("AK47-狼牙", new ImageIcon(this.getClass().getResource("/javasource/AK47-狼牙.jpg"))));
                                model.addElement(new Weaponry("AK47-茉莉", new ImageIcon(this.getClass().getResource("/javasource/AK47-茉莉.jpg"))));
                                model.addElement(new Weaponry("AK47-苍龙", new ImageIcon(this.getClass().getResource("/javasource/AK47-苍龙.jpg"))));
                                model.addElement(new Weaponry("AK47-蓝水晶", new ImageIcon(this.getClass().getResource("/javasource/AK47-蓝水晶.jpg"))));
                                model.addElement(new Weaponry("AK47-青花瓷", new ImageIcon(this.getClass().getResource("/javasource/AK47-青花瓷.jpg"))));
                                model.addElement(new Weaponry("AK47-麒麟", new ImageIcon(this.getClass().getResource("/javasource/AK47-麒麟.jpg"))));
                                model.addElement(new Weaponry("AK47-黎明", new ImageIcon(this.getClass().getResource("/javasource/AK47-黎明.jpg"))));
                                model.addElement(new Weaponry("AK47-黑武士", new ImageIcon(this.getClass().getResource("/javasource/AK47-黑武士.jpg"))));
                                model.addElement(new Weaponry("AK47-黑锋", new ImageIcon(this.getClass().getResource("/javasource/AK47-黑锋.jpg"))));
                                model.addElement(new Weaponry("AK74-万圣节", new ImageIcon(this.getClass().getResource("/javasource/AK74-万圣节.jpg"))));
                                model.addElement(new Weaponry("AK-47", new ImageIcon(this.getClass().getResource("/javasource/AK-47.png"))));
                                model.addElement(new Weaponry("AK-47-黄钻", new ImageIcon(this.getClass().getResource("/javasource/AK-47-黄钻.jpg"))));
                                model.addElement(new Weaponry("AK-47野战军", new ImageIcon(this.getClass().getResource("/javasource/AK-47野战军.jpg"))));
                                model.addElement(new Weaponry("AK-74", new ImageIcon(this.getClass().getResource("/javasource/AK-74.jpg"))));
                                model.addElement(new Weaponry("AK-103", new ImageIcon(this.getClass().getResource("/javasource/AK-103.jpg"))));
                                model.addElement(new Weaponry("AKS-74U", new ImageIcon(this.getClass().getResource("/javasource/AKS-74U.jpg"))));
                                model.addElement(new Weaponry("AN94", new ImageIcon(this.getClass().getResource("/javasource/AN94.jpg"))));
                                model.addElement(new Weaponry("Anaconda", new ImageIcon(this.getClass().getResource("/javasource/Anaconda.jpg"))));
                                model.addElement(new Weaponry("Anaconda-恶棍", new ImageIcon(this.getClass().getResource("/javasource/Anaconda-恶棍.jpg"))));
                                model.addElement(new Weaponry("APR338狙击枪", new ImageIcon(this.getClass().getResource("/javasource/APR338狙击枪.jpg"))));
                                model.addElement(new Weaponry("AR-15", new ImageIcon(this.getClass().getResource("/javasource/AR-15.jpg"))));
                                model.addElement(new Weaponry("AR-57", new ImageIcon(this.getClass().getResource("/javasource/AR-57.jpg"))));
                                model.addElement(new Weaponry("ARX-160", new ImageIcon(this.getClass().getResource("/javasource/ARX-160.jpg"))));
                                model.addElement(new Weaponry("AS50", new ImageIcon(this.getClass().getResource("/javasource/AS50.jpg"))));
                                model.addElement(new Weaponry("AT15-沙丘", new ImageIcon(this.getClass().getResource("/javasource/AT15-沙丘.jpg"))));
                                model.addElement(new Weaponry("AUG-Red", new ImageIcon(this.getClass().getResource("/javasource/AUG-Red.jpg"))));
                                model.addElement(new Weaponry("AWM", new ImageIcon(this.getClass().getResource("/javasource/AWM.jpg"))));
                                model.addElement(new Weaponry("AWM-A", new ImageIcon(this.getClass().getResource("/javasource/AWM-A.jpg"))));
                                model.addElement(new Weaponry("AWM-Pink", new ImageIcon(this.getClass().getResource("/javasource/AWM-Pink.jpg"))));
                                model.addElement(new Weaponry("AWM-Red", new ImageIcon(this.getClass().getResource("/javasource/AWM-Red.jpg"))));
                                model.addElement(new Weaponry("AWM-圣诞", new ImageIcon(this.getClass().getResource("/javasource/AWM-圣诞.jpg"))));
                                model.addElement(new Weaponry("AWM-圣诞2013", new ImageIcon(this.getClass().getResource("/javasource/AWM-圣诞2013.jpg"))));
                                model.addElement(new Weaponry("AWM-天龙", new ImageIcon(this.getClass().getResource("/javasource/AWM-天龙.jpg"))));
                                model.addElement(new Weaponry("AWM-奥运", new ImageIcon(this.getClass().getResource("/javasource/AWM-奥运.png"))));
                                model.addElement(new Weaponry("AWM-恶棍", new ImageIcon("/javasource/AWM-恶棍.jpg")));
                                model.addElement(new Weaponry("AWM-熔岩.jpg", new ImageIcon(this.getClass().getResource("/javasource/AWM-熔岩.jpg"))));
                                model.addElement(new Weaponry("AWM-翡翠", new ImageIcon(this.getClass().getResource("/javasource/AWM-翡翠.png"))));
                                model.addElement(new Weaponry("AWM-老兵", new ImageIcon(this.getClass().getResource("/javasource/AWM-老兵.jpg"))));
                                model.addElement(new Weaponry("AWM-蓝水晶", new ImageIcon(this.getClass().getResource("/javasource/AWM-蓝水晶.jpg"))));
                                model.addElement(new Weaponry("AWM-银月", new ImageIcon(this.getClass().getResource("/javasource/AWM-银月.jpg"))));
                                model.addElement(new Weaponry("Barrett -翔龙", new ImageIcon(this.getClass().getResource("/javasource/Barrett -翔龙.jpg"))));
                                model.addElement(new Weaponry("Barrett M82A1", new ImageIcon(this.getClass().getResource("/javasource/Barrett M82A1.jpg"))));
                                model.addElement(new Weaponry("Barrett-战龙", new ImageIcon(this.getClass().getResource("/javasource/Barrett-战龙.jpg"))));
                                model.addElement(new Weaponry("Barrett-炎龙", new ImageIcon(this.getClass().getResource("/javasource/Barrett-炎龙.jpg"))));
                                model.addElement(new Weaponry("Barrett-牡丹", new ImageIcon(this.getClass().getResource("/javasource/Barrett-牡丹.png"))));
                                model.addElement(new Weaponry("Barrett-耀龙", new ImageIcon(this.getClass().getResource("/javasource/Barrett-耀龙.jpg"))));
                                model.addElement(new Weaponry("Barrett-苍龙", new ImageIcon(this.getClass().getResource("/javasource/Barrett-苍龙.jpg"))));
                                model.addElement(new Weaponry("Barrett毁灭", new ImageIcon(this.getClass().getResource("/javasource/Barrett毁灭.jpg"))));
                                model.addElement(new Weaponry("Beretta AR-70", new ImageIcon(this.getClass().getResource("/javasource/Beretta AR-70.jpg"))));
                                model.addElement(new Weaponry("Beretta M93R", new ImageIcon(this.getClass().getResource("/javasource/Beretta M93R.jpg"))));
                                model.addElement(new Weaponry("C7A2", new ImageIcon(this.getClass().getResource("/javasource/C7A2.jpg"))));
                                model.addElement(new Weaponry("C8", new ImageIcon(this.getClass().getResource("/javasource/C8.jpg"))));
                                model.addElement(new Weaponry("CF-05", new ImageIcon(this.getClass().getResource("/javasource/CF-05.png"))));
                                model.addElement(new Weaponry("CheyTac M200", new ImageIcon(this.getClass().getResource("/javasource/CheyTac M200.jpg"))));
                                model.addElement(new Weaponry("COLT1911", new ImageIcon(this.getClass().getResource("/javasource/COLT1911.jpg"))));
                                model.addElement(new Weaponry("COP 357", new ImageIcon(this.getClass().getResource("/javasource/COP 357.jpg"))));
                                model.addElement(new Weaponry("Cop 357-迷彩", new ImageIcon(this.getClass().getResource("/javasource/Cop 357-迷彩.jpg"))));
                                model.addElement(new Weaponry("CR21-风暴", new ImageIcon(this.getClass().getResource("/javasource/CR21-风暴.jpg"))));
                                model.addElement(new Weaponry("CR-21", new ImageIcon(this.getClass().getResource("/javasource/CR-21.jpg"))));
                                model.addElement(new Weaponry("Desert Eagle-S", new ImageIcon(this.getClass().getResource("/javasource/Desert Eagle-S.jpg"))));
                                model.addElement(new Weaponry("DPM盘式机枪", new ImageIcon(this.getClass().getResource("/javasource/DPM盘式机枪.jpg"))));
                                model.addElement(new Weaponry("DRAGUNOV", new ImageIcon(this.getClass().getResource("/javasource/DRAGUNOV.jpg"))));
                                model.addElement(new Weaponry("DSR-1老兵", new ImageIcon(this.getClass().getResource("/javasource/DSR-1老兵.jpg"))));
                                model.addElement(new Weaponry("DSR-1血鹰", new ImageIcon(this.getClass().getResource("/javasource/DSR-1血鹰.jpg"))));
                                model.addElement(new Weaponry("DSR-1迷彩", new ImageIcon(this.getClass().getResource("/javasource/DSR-1迷彩.jpg"))));
                                model.addElement(new Weaponry("FAMAS G2", new ImageIcon(this.getClass().getResource("/javasource/FAMAS G2.png"))));
                                model.addElement(new Weaponry("FN F2000", new ImageIcon(this.getClass().getResource("/javasource/FN F2000.jpg"))));
                                model.addElement(new Weaponry("FN F2000-绿魔", new ImageIcon(this.getClass().getResource("/javasource/FN F2000-绿魔.jpg"))));
                                model.addElement(new Weaponry("FN F2000红魔", new ImageIcon(this.getClass().getResource("/javasource/FN F2000红魔.jpg"))));
                                model.addElement(new Weaponry("FN FAL(GP步枪)", new ImageIcon(this.getClass().getResource("/javasource/FN FAL(GP步枪).jpg"))));
                                model.addElement(new Weaponry("FN卡宾枪", new ImageIcon(this.getClass().getResource("/javasource/FN卡宾枪.jpg"))));
                                model.addElement(new Weaponry("FR-F2", new ImageIcon(this.getClass().getResource("/javasource/FR-F2.jpg"))));
                                model.addElement(new Weaponry("FR-F2 老兵", new ImageIcon(this.getClass().getResource("/javasource/FR-F2 老兵.jpg"))));
                                model.addElement(new Weaponry("G3A3", new ImageIcon(this.getClass().getResource("/javasource/G3A3.png"))));
                                model.addElement(new Weaponry("G3A3-SS", new ImageIcon(this.getClass().getResource("/javasource/G3A3-SS.png"))));
                                model.addElement(new Weaponry("G11", new ImageIcon(this.getClass().getResource("/javasource/G11.jpg"))));
                                model.addElement(new Weaponry("G11-暗金", new ImageIcon(this.getClass().getResource("/javasource/G11-暗金.jpg"))));
                                model.addElement(new Weaponry("G11-炎魔", new ImageIcon(this.getClass().getResource("/javasource/G11-炎魔.jpg"))));
                                model.addElement(new Weaponry("G11-蓝魔", new ImageIcon(this.getClass().getResource("/javasource/G11-蓝魔.jpg"))));
                                model.addElement(new Weaponry("G36C", new ImageIcon(this.getClass().getResource("/javasource/G36C.jpg"))));
                                model.addElement(new Weaponry("G36K", new ImageIcon(this.getClass().getResource("/javasource/G36K.jpg"))));
                                model.addElement(new Weaponry("Galil", new ImageIcon(this.getClass().getResource("/javasource/Galil.jpg"))));
                                model.addElement(new Weaponry("Galil-彩魔", new ImageIcon(this.getClass().getResource("/javasource/Galil-彩魔.jpg"))));
                                model.addElement(new Weaponry("GLOCK-18", new ImageIcon(this.getClass().getResource("/javasource/GLOCK-18.jpg"))));
                                model.addElement(new Weaponry("HK416C", new ImageIcon(this.getClass().getResource("/javasource/HK416C.jpg"))));
                                model.addElement(new Weaponry("HK417", new ImageIcon(this.getClass().getResource("/javasource/HK417.jpg"))));
                                model.addElement(new Weaponry("JNG90", new ImageIcon(this.getClass().getResource("/javasource/JNG90.jpg"))));
                                model.addElement(new Weaponry("K-2", new ImageIcon(this.getClass().getResource("/javasource/K-2.jpg"))));
                                model.addElement(new Weaponry("KAC-PDW", new ImageIcon(this.getClass().getResource("/javasource/KAC-PDW.jpg"))));
                                model.addElement(new Weaponry("KAC-彩魔", new ImageIcon(this.getClass().getResource("/javasource/KAC-彩魔.png"))));
                                model.addElement(new Weaponry("KAC-突击手", new ImageIcon(this.getClass().getResource("/javasource/KAC-突击手.jpg"))));
                                model.addElement(new Weaponry("KAC-耀龙", new ImageIcon(this.getClass().getResource("/javasource/KAC-耀龙.jpg"))));
                                model.addElement(new Weaponry("KAC锯式机枪", new ImageIcon(this.getClass().getResource("/javasource/KAC锯式机枪.jpg"))));
                                model.addElement(new Weaponry("Knight  SR25", new ImageIcon(this.getClass().getResource("/javasource/Knight  SR25.jpg"))));
                                model.addElement(new Weaponry("KS-23", new ImageIcon(this.getClass().getResource("/javasource/KS-23.jpg"))));
                                model.addElement(new Weaponry("KSG-15", new ImageIcon(this.getClass().getResource("/javasource/KSG-15.jpg"))));
                                model.addElement(new Weaponry("KTR-08", new ImageIcon(this.getClass().getResource("/javasource/KTR-08.jpg"))));
                                model.addElement(new Weaponry("L85A1", new ImageIcon(this.getClass().getResource("/javasource/L85A1.jpg"))));
                                model.addElement(new Weaponry("L86-LSW", new ImageIcon(this.getClass().getResource("/javasource/L86-LSW.jpg"))));
                                model.addElement(new Weaponry("L86-亚马逊", new ImageIcon(this.getClass().getResource("/javasource/L86-亚马逊.jpg"))));
                                model.addElement(new Weaponry("L86-银色杀手", new ImageIcon(this.getClass().getResource("/javasource/L86-银色杀手.jpg"))));
                                model.addElement(new Weaponry("LR300ML", new ImageIcon(this.getClass().getResource("/javasource/LR300ML.jpg"))));
                                model.addElement(new Weaponry("M1A1卡宾枪", new ImageIcon(this.getClass().getResource("/javasource/M1A1卡宾枪.jpg"))));
                                model.addElement(new Weaponry("M4 CQBR", new ImageIcon(this.getClass().getResource("/javasource/M4 CQBR.jpg"))));
                                model.addElement(new Weaponry("M4-突击手", new ImageIcon(this.getClass().getResource("/javasource/M4-突击手.jpg"))));
                                model.addElement(new Weaponry("M4A1", new ImageIcon(this.getClass().getResource("/javasource/M4A1.jpg"))));
                                model.addElement(new Weaponry("M4A1-8周年", new ImageIcon(this.getClass().getResource("/javasource/M4A1-8周年.jpg"))));
                                model.addElement(new Weaponry("M4A1-A", new ImageIcon(this.getClass().getResource("/javasource/M4A1-A.jpg"))));
                                model.addElement(new Weaponry("M4A1-Custom", new ImageIcon(this.getClass().getResource("/javasource/M4A1-Custom.jpg"))));
                                model.addElement(new Weaponry("M4A1-Pink", new ImageIcon(this.getClass().getResource("/javasource/M4A1-Pink.jpg"))));
                                model.addElement(new Weaponry("M4A1-QQ会员", new ImageIcon(this.getClass().getResource("/javasource/M4A1-QQ会员.jpg"))));
                                model.addElement(new Weaponry("M4A1-QT", new ImageIcon(this.getClass().getResource("/javasource/M4A1-QT.jpg"))));
                                model.addElement(new Weaponry("M4A1-Red", new ImageIcon(this.getClass().getResource("/javasource/M4A1-Red.jpg"))));
                                model.addElement(new Weaponry("M4A1-S", new ImageIcon(this.getClass().getResource("/javasource/M4A1-S.jpg"))));
                                model.addElement(new Weaponry("M4A1-SS", new ImageIcon(this.getClass().getResource("/javasource/M4A1-SS.jpg"))));
                                model.addElement(new Weaponry("M4A1-T", new ImageIcon(this.getClass().getResource("/javasource/M4A1-T.jpg"))));
                                model.addElement(new Weaponry("M4A1-X Camo", new ImageIcon(this.getClass().getResource("/javasource/M4A1-X Camo.jpg"))));
                                model.addElement(new Weaponry("M4A1-XS-耀龙", new ImageIcon(this.getClass().getResource("/javasource/M4A1-XS-耀龙.jpg"))));
                                model.addElement(new Weaponry("M4A1-七周年", new ImageIcon(this.getClass().getResource("/javasource/M4A1-七周年.jpg"))));
                                model.addElement(new Weaponry("M4A1-万圣节", new ImageIcon(this.getClass().getResource("/javasource/M4A1-万圣节.jpg"))));
                                model.addElement(new Weaponry("M4A1-千变", new ImageIcon(this.getClass().getResource("/javasource/M4A1-千变.jpg"))));
                                model.addElement(new Weaponry("M4A1-圣诞2013", new ImageIcon(this.getClass().getResource("/javasource/M4A1-圣诞2013.jpg"))));
                                model.addElement(new Weaponry("M4A1-战龙", new ImageIcon(this.getClass().getResource("/javasource/M4A1-战龙.jpg"))));
                                model.addElement(new Weaponry("M4A1-樱.jpg", new ImageIcon(this.getClass().getResource("/javasource/M4A1-樱.jpg"))));
                                model.addElement(new Weaponry("M4A1-死神", new ImageIcon(this.getClass().getResource("/javasource/M4A1-死神.png"))));
                                model.addElement(new Weaponry("M4A1-火鹰", new ImageIcon(this.getClass().getResource("/javasource/M4A1-火鹰.jpg"))));
                                model.addElement(new Weaponry("M4A1-牡丹", new ImageIcon(this.getClass().getResource("/javasource/M4A1-牡丹.png"))));
                                model.addElement(new Weaponry("M4A1-玫瑰精灵", new ImageIcon(this.getClass().getResource("/javasource/M4A1-玫瑰精灵.jpg"))));
                                model.addElement(new Weaponry("M4A1-狼牙", new ImageIcon(this.getClass().getResource("/javasource/M4A1-狼牙.jpg"))));
                                model.addElement(new Weaponry("M4A1-百合", new ImageIcon(this.getClass().getResource("/javasource/M4A1-百合.png"))));
                                model.addElement(new Weaponry("M4A1-粉钻", new ImageIcon(this.getClass().getResource("/javasource/M4A1-粉钻.jpg"))));
                                model.addElement(new Weaponry("M4A1-翔龙", new ImageIcon(this.getClass().getResource("/javasource/M4A1-翔龙.jpg"))));
                                model.addElement(new Weaponry("M4A1-紫罗兰", new ImageIcon(this.getClass().getResource("/javasource/M4A1-紫罗兰.jpg"))));
                                model.addElement(new Weaponry("M4A1-翡翠", new ImageIcon(this.getClass().getResource("/javasource/M4A1-翡翠.jpg"))));
                                model.addElement(new Weaponry("M4A1-蓝水晶", new ImageIcon(this.getClass().getResource("/javasource/M4A1-蓝水晶.jpg"))));
                                model.addElement(new Weaponry("M4A1-隐袭", new ImageIcon(this.getClass().getResource("/javasource/M4A1-隐袭.jpg"))));
                                model.addElement(new Weaponry("M4A1-青花瓷", new ImageIcon(this.getClass().getResource("/javasource/M4A1-青花瓷.jpg"))));
                                model.addElement(new Weaponry("M4A1-青龙", new ImageIcon(this.getClass().getResource("/javasource/M4A1-青龙.jpeg"))));
                                model.addElement(new Weaponry("M4A1-黑龙", new ImageIcon(this.getClass().getResource("/javasource/M4A1-黑龙.jpg"))));
                                model.addElement(new Weaponry("M4A1-齐天大圣", new ImageIcon(this.getClass().getResource("/javasource/M4A1-齐天大圣.jpg"))));
                                model.addElement(new Weaponry("M4猎人ss", new ImageIcon(this.getClass().getResource("/javasource/M4猎人ss.jpg"))));
                                model.addElement(new Weaponry("M12s", new ImageIcon(this.getClass().getResource("/javasource/M12s.jpg"))));
                                model.addElement(new Weaponry("M14EBR", new ImageIcon(this.getClass().getResource("/javasource/M14EBR.jpg"))));
                                model.addElement(new Weaponry("M14EBR-圣诞", new ImageIcon(this.getClass().getResource("/javasource/M14EBR-圣诞.jpg"))));
                                model.addElement(new Weaponry("M14EBR-天羽", new ImageIcon(this.getClass().getResource("/javasource/M14EBR-天羽.jpg"))));
                                model.addElement(new Weaponry("M14EBR-炎龙", new ImageIcon(this.getClass().getResource("/javasource/M14EBR-炎龙.jpg"))));
                                model.addElement(new Weaponry("M14EBR-蓝海", new ImageIcon(this.getClass().getResource("/javasource/M14EBR-蓝海.jpg"))));
                                model.addElement(new Weaponry("M14EBR-迷彩", new ImageIcon(this.getClass().getResource("/javasource/M14EBR-迷彩.jpg"))));
                                model.addElement(new Weaponry("M14ER-金牛座", new ImageIcon(this.getClass().getResource("/javasource/M14ER-金牛座.jpg"))));
                                model.addElement(new Weaponry("M14自动步枪", new ImageIcon(this.getClass().getResource("/javasource/M14自动步枪.jpg"))));
                                model.addElement(new Weaponry("M16", new ImageIcon(this.getClass().getResource("/javasource/M16.jpg"))));
                                model.addElement(new Weaponry("M16-S", new ImageIcon(this.getClass().getResource("/javasource/M16-S.jpg"))));
                                model.addElement(new Weaponry("M16A2", new ImageIcon(this.getClass().getResource("/javasource/M16A2.jpg"))));
                                model.addElement(new Weaponry("M16A3 LMG", new ImageIcon(this.getClass().getResource("/javasource/M16A3 LMG.jpg"))));
                                model.addElement(new Weaponry("M16A4", new ImageIcon(this.getClass().getResource("/javasource/M16A4.jpg"))));
                                model.addElement(new Weaponry("M37 烈火", new ImageIcon(this.getClass().getResource("/javasource/M37 烈火.jpg"))));
                                model.addElement(new Weaponry("M37-SS", new ImageIcon(this.getClass().getResource("/javasource/M37-SS.jpg"))));
                                model.addElement(new Weaponry("M60-A", new ImageIcon(this.getClass().getResource("/javasource/M60-A.jpg"))));
                                model.addElement(new Weaponry("M60-赤银", new ImageIcon(this.getClass().getResource("/javasource/M60-赤银.jpg"))));
                                model.addElement(new Weaponry("M60E3", new ImageIcon(this.getClass().getResource("/javasource/M60E3.jpg"))));
                                model.addElement(new Weaponry("M66-翔龙", new ImageIcon(this.getClass().getResource("/javasource/M66-翔龙.jpg"))));
                                model.addElement(new Weaponry("M82A1-8周年", new ImageIcon(this.getClass().getResource("/javasource/M82A1-8周年.jpg"))));
                                model.addElement(new Weaponry("M98B", new ImageIcon(this.getClass().getResource("/javasource/M98B.jpg"))));
                                model.addElement(new Weaponry("M231", new ImageIcon(this.getClass().getResource("/javasource/M231.jpg"))));
                                model.addElement(new Weaponry("M240B", new ImageIcon(this.getClass().getResource("/javasource/M240B.jpg"))));
                                model.addElement(new Weaponry("M249 MINIMI", new ImageIcon(this.getClass().getResource("/javasource/M249 MINIMI.jpg"))));
                                model.addElement(new Weaponry("M249 MINIMI-天羽", new ImageIcon(this.getClass().getResource("/javasource/M249 MINIMI-天羽.jpg"))));
                                model.addElement(new Weaponry("M249-MINIMI-圣诞", new ImageIcon(this.getClass().getResource("/javasource/M249-MINIMI-圣诞.jpg"))));
                                model.addElement(new Weaponry("M249-S", new ImageIcon(this.getClass().getResource("/javasource/M249-S.jpg"))));
                                model.addElement(new Weaponry("M700", new ImageIcon(this.getClass().getResource("/javasource/M700.jpg"))));
                                model.addElement(new Weaponry("M1216", new ImageIcon(this.getClass().getResource("/javasource/M1216.jpg"))));
                                model.addElement(new Weaponry("M1216-彩魔", new ImageIcon(this.getClass().getResource("/javasource/M1216-彩魔.jpg"))));
                                model.addElement(new Weaponry("M1216-暗金", new ImageIcon(this.getClass().getResource("/javasource/M1216-暗金.jpg"))));
                                model.addElement(new Weaponry("M1216-炎魔", new ImageIcon(this.getClass().getResource("/javasource/M1216-炎魔.jpg"))));
                                model.addElement(new Weaponry("M1216-蓝魔", new ImageIcon(this.getClass().getResource("/javasource/M1216-蓝魔.jpg"))));
                                model.addElement(new Weaponry("MG3", new ImageIcon(this.getClass().getResource("/javasource/MG3.jpg"))));
                                model.addElement(new Weaponry("MG3-彩魔", new ImageIcon(this.getClass().getResource("/javasource/MG3-彩魔.jpg"))));
                                model.addElement(new Weaponry("MG3-炎魔", new ImageIcon(this.getClass().getResource("/javasource/MG3-炎魔.jpg"))));
                                model.addElement(new Weaponry("MG3-纯金", new ImageIcon(this.getClass().getResource("/javasource/MG3-纯金.jpg"))));
                                model.addElement(new Weaponry("MG3-翔龙", new ImageIcon(this.getClass().getResource("/javasource/MG3-翔龙.jpg"))));
                                model.addElement(new Weaponry("MG3-迷彩", new ImageIcon(this.getClass().getResource("/javasource/MG3-迷彩.jpg"))));
                                model.addElement(new Weaponry("MG3-银色杀手", new ImageIcon(this.getClass().getResource("/javasource/MG3-银色杀手.jpg"))));
                                model.addElement(new Weaponry("MG4", new ImageIcon(this.getClass().getResource("/javasource/MG4.jpg"))));
                                model.addElement(new Weaponry("MG13", new ImageIcon(this.getClass().getResource("/javasource/MG13.png"))));
                                model.addElement(new Weaponry("Micro Galil-A", new ImageIcon(this.getClass().getResource("/javasource/Micro Galil-A.jpg"))));
                                model.addElement(new Weaponry("Micro Galil-S", new ImageIcon(this.getClass().getResource("/javasource/Micro Galil-S.jpg"))));
                                model.addElement(new Weaponry("Micro-GALIL", new ImageIcon(this.getClass().getResource("/javasource/Micro-GALIL.jpg"))));
                                model.addElement(new Weaponry("MK5-S", new ImageIcon(this.getClass().getResource("/javasource/MK5-S.jpg"))));
                                model.addElement(new Weaponry("MK5-SS", new ImageIcon(this.getClass().getResource("/javasource/MK5-SS.jpg"))));
                                model.addElement(new Weaponry("MK5-T", new ImageIcon(this.getClass().getResource("/javasource/MK5-T.jpg"))));
                                model.addElement(new Weaponry("MK5-圣诞", new ImageIcon(this.getClass().getResource("/javasource/MK5-圣诞.jpg"))));
                                model.addElement(new Weaponry("MK5双雄", new ImageIcon(this.getClass().getResource("/javasource/MK5双雄.png"))));
                                model.addElement(new Weaponry("MK5双雄-彩魔", new ImageIcon(this.getClass().getResource("/javasource/MK5双雄-彩魔.png"))));
                                model.addElement(new Weaponry("MK18-蓝海", new ImageIcon(this.getClass().getResource("/javasource/MK18-蓝海.jpg"))));
                                model.addElement(new Weaponry("MK23", new ImageIcon(this.getClass().getResource("/javasource/MK23.jpg"))));
                                model.addElement(new Weaponry("MK23-暗杀者", new ImageIcon(this.getClass().getResource("/javasource/MK23-暗杀者.jpg"))));
                                model.addElement(new Weaponry("MP5-A", new ImageIcon(this.getClass().getResource("/javasource/MP5-A.jpg"))));
                                model.addElement(new Weaponry("MP5-B", new ImageIcon(this.getClass().getResource("/javasource/MP5-B.jpg"))));
                                model.addElement(new Weaponry("MP5K A4", new ImageIcon(this.getClass().getResource("/javasource/MP5K A4.jpg"))));
                                model.addElement(new Weaponry("MP7", new ImageIcon(this.getClass().getResource("/javasource/MP7.jpg"))));
                                model.addElement(new Weaponry("MP7-S", new ImageIcon(this.getClass().getResource("/javasource/MP7-S.jpg"))));
                                model.addElement(new Weaponry("MP7-双雄", new ImageIcon(this.getClass().getResource("/javasource/MP7-双雄.jpg"))));
                                model.addElement(new Weaponry("MP7A1", new ImageIcon(this.getClass().getResource("/javasource/MP7A1.jpg"))));
                                model.addElement(new Weaponry("MSG90", new ImageIcon(this.getClass().getResource("/javasource/MSG90.jpg"))));
                                model.addElement(new Weaponry("MSR-S", new ImageIcon(this.getClass().getResource("/javasource/MSR-S.jpg"))));
                                model.addElement(new Weaponry("MTAR-21", new ImageIcon(this.getClass().getResource("/javasource/MTAR-21.jpg"))));
                                model.addElement(new Weaponry("NS2000", new ImageIcon(this.getClass().getResource("/javasource/NS2000.jpg"))));
                                model.addElement(new Weaponry("OC-14", new ImageIcon(this.getClass().getResource("/javasource/OC-14.jpg"))));
                                model.addElement(new Weaponry("P228", new ImageIcon(this.getClass().getResource("/javasource/P228.jpg"))));
                                model.addElement(new Weaponry("PKP", new ImageIcon(this.getClass().getResource("/javasource/PKP.jpg"))));
                                model.addElement(new Weaponry("PMR-30-迷彩", new ImageIcon(this.getClass().getResource("/javasource/PMR-30-迷彩.jpg"))));
                                model.addElement(new Weaponry("PP19野牛", new ImageIcon(this.getClass().getResource("/javasource/PP19野牛.jpg"))));
                                model.addElement(new Weaponry("PSG-1", new ImageIcon(this.getClass().getResource("/javasource/PSG-1.jpg"))));
                                model.addElement(new Weaponry("QBZ95", new ImageIcon(this.getClass().getResource("/javasource/QBZ95.jpg"))));
                                model.addElement(new Weaponry("QBZ95-A", new ImageIcon(this.getClass().getResource("/javasource/QBZ95-A.jpg"))));
                                model.addElement(new Weaponry("QBZ-03", new ImageIcon(this.getClass().getResource("/javasource/QBZ-03.jpg"))));
                                model.addElement(new Weaponry("QT手雷", new ImageIcon(this.getClass().getResource("/javasource/QT手雷.jpg"))));
                                model.addElement(new Weaponry("R93 T2", new ImageIcon(this.getClass().getResource("/javasource/R93 T2.jpg"))));
                                model.addElement(new Weaponry("RB-狼牙", new ImageIcon(this.getClass().getResource("/javasource/RB-狼牙.jpg"))));
                                model.addElement(new Weaponry("REC7", new ImageIcon(this.getClass().getResource("/javasource/REC7.jpg"))));
                                model.addElement(new Weaponry("Remington870", new ImageIcon(this.getClass().getResource("/javasource/Remington870.jpg"))));
                                model.addElement(new Weaponry("Remington870-A", new ImageIcon(this.getClass().getResource("/javasource/Remington870-A.jpg"))));
                                model.addElement(new Weaponry("Remington870-S", new ImageIcon(this.getClass().getResource("/javasource/Remington870-S.jpg"))));
                                model.addElement(new Weaponry("RGP", new ImageIcon(this.getClass().getResource("/javasource/RGP.jpg"))));
                                model.addElement(new Weaponry("RPK-嗜血", new ImageIcon(this.getClass().getResource("/javasource/RPK-嗜血.jpg"))));
                                model.addElement(new Weaponry("RPK-盘龙", new ImageIcon(this.getClass().getResource("/javasource/RPK-盘龙.jpg"))));
                                model.addElement(new Weaponry("RPK机关枪", new ImageIcon(this.getClass().getResource("/javasource/RPK机关枪.jpg"))));
                                model.addElement(new Weaponry("Ruger Bisley", new ImageIcon(this.getClass().getResource("/javasource/Ruger Bisley.jpg"))));
                                model.addElement(new Weaponry("Ruger MINI-14", new ImageIcon(this.getClass().getResource("/javasource/Ruger MINI-14.jpg"))));
                                model.addElement(new Weaponry("Rx4风暴", new ImageIcon(this.getClass().getResource("/javasource/Rx4风暴.jpg"))));
                                model.addElement(new Weaponry("S W M66", new ImageIcon(this.getClass().getResource("/javasource/S W M66.jpg"))));
                                model.addElement(new Weaponry("SAR-21", new ImageIcon(this.getClass().getResource("/javasource/SAR-21.jpg"))));
                                model.addElement(new Weaponry("SCAR LIGHT", new ImageIcon(this.getClass().getResource("/javasource/SCAR LIGHT.jpg"))));
                                model.addElement(new Weaponry("Scar Light-狼牙", new ImageIcon(this.getClass().getResource("/javasource/Scar Light-狼牙.jpg"))));
                                model.addElement(new Weaponry("SCAR-Heavy", new ImageIcon(this.getClass().getResource("/javasource/SCAR-Heavy.jpg"))));
                                model.addElement(new Weaponry("SCAR-Light", new ImageIcon(this.getClass().getResource("/javasource/SCAR-Light.jpg"))));
                                model.addElement(new Weaponry("SCW卡宾枪", new ImageIcon(this.getClass().getResource("/javasource/SCW卡宾枪.jpg"))));
                                model.addElement(new Weaponry("SG552", new ImageIcon(this.getClass().getResource("/javasource/SG552.jpg"))));
                                model.addElement(new Weaponry("SG552-A", new ImageIcon(this.getClass().getResource("/javasource/SG552-A.jpg"))));
                                model.addElement(new Weaponry("SIG716", new ImageIcon(this.getClass().getResource("/javasource/SIG716.jpg"))));
                                model.addElement(new Weaponry("SL8", new ImageIcon(this.getClass().getResource("/javasource/SL8.jpg"))));
                                model.addElement(new Weaponry("SL-处女座", new ImageIcon(this.getClass().getResource("/javasource/SL-处女座.jpg"))));
                                model.addElement(new Weaponry("SOCOM16", new ImageIcon(this.getClass().getResource("/javasource/SOCOM16.jpg"))));
                                model.addElement(new Weaponry("SPAS-12", new ImageIcon(this.getClass().getResource("/javasource/SPAS-12.jpg"))));
                                model.addElement(new Weaponry("SR3M", new ImageIcon(this.getClass().getResource("/javasource/SR3M.jpg"))));
                                model.addElement(new Weaponry("SR16", new ImageIcon(this.getClass().getResource("/javasource/SR16.jpg"))));
                                model.addElement(new Weaponry("SR-2M VERESK", new ImageIcon(this.getClass().getResource("/javasource/SR-2M VERESK.jpg"))));
                                model.addElement(new Weaponry("Stery AUG A1-A", new ImageIcon(this.getClass().getResource("/javasource/Stery AUG A1-A.jpg"))));
                                model.addElement(new Weaponry("Steyr AUG A1", new ImageIcon(this.getClass().getResource("/javasource/Steyr AUG A1.jpg"))));
                                model.addElement(new Weaponry("TRG-21", new ImageIcon(this.getClass().getResource("/javasource/TRG-21.jpg"))));
                                model.addElement(new Weaponry("TRG-21白羊座", new ImageIcon(this.getClass().getResource("/javasource/TRG-21白羊座.jpg"))));
                                model.addElement(new Weaponry("Ultimax100", new ImageIcon(this.getClass().getResource("/javasource/Ultimax100.jpg"))));
                                model.addElement(new Weaponry("USP", new ImageIcon(this.getClass().getResource("/javasource/USP.jpg"))));
                                model.addElement(new Weaponry("USP-迷彩", new ImageIcon(this.getClass().getResource("/javasource/USP-迷彩.jpg"))));
                                model.addElement(new Weaponry("Vepr", new ImageIcon(this.getClass().getResource("/javasource/Vepr.jpg"))));
                                model.addElement(new Weaponry("VHS突击步枪", new ImageIcon(this.getClass().getResource("/javasource/VHS突击步枪.jpg"))));
                                model.addElement(new Weaponry("VSK-94", new ImageIcon(this.getClass().getResource("/javasource/VSK-94.jpg"))));
                                model.addElement(new Weaponry("VSK-94迷彩", new ImageIcon(this.getClass().getResource("/javasource/VSK-94迷彩.jpg"))));
                                model.addElement(new Weaponry("XM8", new ImageIcon(this.getClass().getResource("/javasource/XM8.jpg"))));
                                model.addElement(new Weaponry("XM8-A", new ImageIcon(this.getClass().getResource("/javasource/XM8-A.jpg"))));
                                model.addElement(new Weaponry("XM8-茉莉", new ImageIcon(this.getClass().getResource("/javasource/XM8-茉莉.jpg"))));
                                model.addElement(new Weaponry("XM1014", new ImageIcon(this.getClass().getResource("/javasource/XM1014.jpg"))));
                                model.addElement(new Weaponry("万圣节FR-F2", new ImageIcon(this.getClass().getResource("/javasource/万圣节FR-F2.jpg"))));
                                model.addElement(new Weaponry("万圣节M4A1-X", new ImageIcon(this.getClass().getResource("/javasource/万圣节M4A1-X.jpg"))));
                                model.addElement(new Weaponry("万圣节军刀", new ImageIcon(this.getClass().getResource("/javasource/万圣节军刀.jpg"))));
                                model.addElement(new Weaponry("万圣节毛瑟手枪", new ImageIcon(this.getClass().getResource("/javasource/万圣节毛瑟手枪.jpg"))));
                                model.addElement(new Weaponry("乌兹双枪", new ImageIcon(this.getClass().getResource("/javasource/乌兹双枪.jpg"))));
                                model.addElement(new Weaponry("乌兹双枪-霓虹", new ImageIcon(this.getClass().getResource("/javasource/乌兹双枪-霓虹.jpg"))));
                                model.addElement(new Weaponry("乱世", new ImageIcon(this.getClass().getResource("/javasource/乱世.png"))));
                                model.addElement(new Weaponry("亡命之徒", new ImageIcon(this.getClass().getResource("/javasource/亡命之徒.jpg"))));
                                model.addElement(new Weaponry("伊萨卡M37", new ImageIcon(this.getClass().getResource("/javasource/伊萨卡M37.jpg"))));
                                model.addElement(new Weaponry("伪装", new ImageIcon(this.getClass().getResource("/javasource/伪装.jpg"))));
                                model.addElement(new Weaponry("充气大锤", new ImageIcon(this.getClass().getResource("/javasource/充气大锤.jpg"))));
                                model.addElement(new Weaponry("军用手斧-翔龙", new ImageIcon(this.getClass().getResource("/javasource/军用手斧-翔龙.jpg"))));
                                model.addElement(new Weaponry("军用手斧-耀龙", new ImageIcon(this.getClass().getResource("/javasource/军用手斧-耀龙.jpg"))));
                                model.addElement(new Weaponry("军用指虎", new ImageIcon(this.getClass().getResource("/javasource/军用指虎.jpg"))));
                                model.addElement(new Weaponry("军用指虎-刺刀", new ImageIcon(this.getClass().getResource("/javasource/军用指虎-刺刀.jpg"))));
                                model.addElement(new Weaponry("军用铁锹", new ImageIcon(this.getClass().getResource("/javasource/军用铁锹.jpg"))));
                                model.addElement(new Weaponry("军用铁锹-H", new ImageIcon(this.getClass().getResource("/javasource/军用铁锹-H.jpg"))));
                                model.addElement(new Weaponry("军用铁锹-Red", new ImageIcon(this.getClass().getResource("/javasource/军用铁锹-Red.jpg"))));
                                model.addElement(new Weaponry("军用铁锹-圣诞", new ImageIcon(this.getClass().getResource("/javasource/军用铁锹-圣诞.jpg"))));
                                model.addElement(new Weaponry("加特林-H", new ImageIcon(this.getClass().getResource("/javasource/加特林-H.jpg"))));
                                model.addElement(new Weaponry("加特林-熔岩", new ImageIcon(this.getClass().getResource("/javasource/加特林-熔岩.jpg"))));
                                model.addElement(new Weaponry("加特林-狼牙", new ImageIcon(this.getClass().getResource("/javasource/加特林-狼牙.jpg"))));
                                model.addElement(new Weaponry("加特林-蓝水晶", new ImageIcon(this.getClass().getResource("/javasource/加特林-蓝水晶.jpg"))));
                                model.addElement(new Weaponry("加特林机关枪", new ImageIcon(this.getClass().getResource("/javasource/加特林机关枪.jpg"))));
                                model.addElement(new Weaponry("南瓜手雷", new ImageIcon(this.getClass().getResource("/javasource/南瓜手雷.jpg"))));
                                model.addElement(new Weaponry("双枪沙鹰", new ImageIcon(this.getClass().getResource("/javasource/双枪沙鹰.jpg"))));
                                model.addElement(new Weaponry("双枪沙鹰-嗜血", new ImageIcon(this.getClass().getResource("/javasource/双枪沙鹰-嗜血.jpg"))));
                                model.addElement(new Weaponry("双枪沙鹰-幽灵", new ImageIcon(this.getClass().getResource("/javasource/双枪沙鹰-幽灵.jpg"))));
                                model.addElement(new Weaponry("双枪沙鹰-绿光", new ImageIcon(this.getClass().getResource("/javasource/双枪沙鹰-绿光.jpg"))));
                                model.addElement(new Weaponry("双枪沙鹰-金魔", new ImageIcon(this.getClass().getResource("/javasource/双枪沙鹰-金魔.jpg"))));
                                model.addElement(new Weaponry("双枪沙鹰-风暴", new ImageIcon(this.getClass().getResource("/javasource/双枪沙鹰-风暴.jpg"))));
                                model.addElement(new Weaponry("双枪沙鹰-魅影", new ImageIcon(this.getClass().getResource("/javasource/双枪沙鹰-魅影.jpg"))));
                                model.addElement(new Weaponry("双枪沙鹰-黎明", new ImageIcon(this.getClass().getResource("/javasource/双枪沙鹰-黎明.jpg"))));
                                model.addElement(new Weaponry("双管猎枪", new ImageIcon(this.getClass().getResource("/javasource/双管猎枪.jpg"))));
                                model.addElement(new Weaponry("圣诞AK-47", new ImageIcon(this.getClass().getResource("/javasource/圣诞AK-47.jpg"))));
                                model.addElement(new Weaponry("圣诞AWM", new ImageIcon(this.getClass().getResource("/javasource/圣诞AWM.jpg"))));
                                model.addElement(new Weaponry("圣诞M4A1", new ImageIcon(this.getClass().getResource("/javasource/圣诞M4A1.jpg"))));
                                model.addElement(new Weaponry("圣诞MP5", new ImageIcon(this.getClass().getResource("/javasource/圣诞MP5.jpg"))));
                                model.addElement(new Weaponry("圣诞手斧", new ImageIcon(this.getClass().getResource("/javasource/圣诞手斧.jpg"))));
                                model.addElement(new Weaponry("圣诞沙漠之鹰", new ImageIcon(this.getClass().getResource("/javasource/圣诞沙漠之鹰.jpg"))));
                                model.addElement(new Weaponry("大毒蛇ACR", new ImageIcon(this.getClass().getResource("/javasource/大毒蛇ACR.jpg"))));
                                model.addElement(new Weaponry("圣诞沙漠之鹰", new ImageIcon(this.getClass().getResource("/javasource/圣诞沙漠之鹰.jpg"))));
                                model.addElement(new Weaponry("女皇利刃", new ImageIcon(this.getClass().getResource("/javasource/女皇利刃.jpg"))));
                                model.addElement(new Weaponry("尼泊尔-T", new ImageIcon(this.getClass().getResource("/javasource/尼泊尔-T.jpg"))));
                                model.addElement(new Weaponry("尼泊尔-奥运", new ImageIcon(this.getClass().getResource("/javasource/尼泊尔-奥运.jpg"))));
                                model.addElement(new Weaponry("尼泊尔-炎龙", new ImageIcon(this.getClass().getResource("/javasource/尼泊尔-炎龙.jpg"))));
                                model.addElement(new Weaponry("尼泊尔-牡丹", new ImageIcon(this.getClass().getResource("/javasource/尼泊尔-牡丹.png"))));
                                model.addElement(new Weaponry("尼泊尔-翡翠", new ImageIcon(this.getClass().getResource("/javasource/尼泊尔-翡翠.jpg"))));
                                model.addElement(new Weaponry("尼泊尔军刀", new ImageIcon(this.getClass().getResource("/javasource/尼泊尔军刀.jpg"))));
                                model.addElement(new Weaponry("尼泊尔军刀-战龙", new ImageIcon(this.getClass().getResource("/javasource/尼泊尔军刀-战龙.jpg"))));
                                model.addElement(new Weaponry("屠夫手雷", new ImageIcon(this.getClass().getResource("/javasource/屠夫手雷.jpg"))));
                                model.addElement(new Weaponry("屠龙", new ImageIcon(this.getClass().getResource("/javasource/屠龙.jpg"))));
                                model.addElement(new Weaponry("巨蜥之尾", new ImageIcon(this.getClass().getResource("/javasource/巨蜥之尾.jpg"))));
                                model.addElement(new Weaponry("巨锤手雷", new ImageIcon(this.getClass().getResource("/javasource/巨锤手雷.jpg"))));
                                model.addElement(new Weaponry("幻兽", new ImageIcon(this.getClass().getResource("/javasource/幻兽.jpeg"))));
                                model.addElement(new Weaponry("幽灵宝宝手雷", new ImageIcon(this.getClass().getResource("/javasource/幽灵宝宝手雷.jpg"))));
                                model.addElement(new Weaponry("彩魔拳套", new ImageIcon(this.getClass().getResource("/javasource/彩魔全套.png"))));
                                model.addElement(new Weaponry("微笑手榴弹", new ImageIcon(this.getClass().getResource("/javasource/微笑手榴弹.jpg"))));
                                model.addElement(new Weaponry("情人手雷", new ImageIcon(this.getClass().getResource("/javasource/情人手雷.jpg"))));
                                model.addElement(new Weaponry("手斧-SS", new ImageIcon(this.getClass().getResource("/javasource/手斧-SS.jpg"))));
                                model.addElement(new Weaponry("手斧-会员", new ImageIcon(this.getClass().getResource("/javasource/手斧-会员.jpg"))));
                                model.addElement(new Weaponry("手雷-黄钻", new ImageIcon(this.getClass().getResource("/javasource/手雷-黄钻.jpg"))));
                                model.addElement(new Weaponry("打击者", new ImageIcon(this.getClass().getResource("/javasource/打击者.jpg"))));
                                model.addElement(new Weaponry("打击者-天羽", new ImageIcon(this.getClass().getResource("/javasource/打击者-天羽.jpg"))));
                                model.addElement(new Weaponry("扳手", new ImageIcon(this.getClass().getResource("/javasource/扳手.jpg"))));
                                model.addElement(new Weaponry("拉帕FA", new ImageIcon(this.getClass().getResource("/javasource/拉帕FA.jpg"))));
                                model.addElement(new Weaponry("拳击手套", new ImageIcon(this.getClass().getResource("/javasource/拳击手套.jpg"))));
                                model.addElement(new Weaponry("板砖", new ImageIcon(this.getClass().getResource("/javasource/搬砖.jpg"))));
                                model.addElement(new Weaponry("收割者", new ImageIcon(this.getClass().getResource("/javasource/收割者.jpg"))));
                                model.addElement(new Weaponry("斯泰尔 AUG A3", new ImageIcon(this.getClass().getResource("/javasource/斯泰尔 AUG A3.jpg"))));
                                model.addElement(new Weaponry("斯泰尔-恶棍", new ImageIcon(this.getClass().getResource("/javasource/斯泰尔-恶棍.jpg"))));
                                model.addElement(new Weaponry("斯泰尔冲锋枪", new ImageIcon(this.getClass().getResource("/javasource/斯泰尔冲锋枪.jpg"))));
                                model.addElement(new Weaponry("斯特林-ST", new ImageIcon(this.getClass().getResource("/javasource/斯特林-ST.jpg"))));
                                model.addElement(new Weaponry("斯特林-熔岩", new ImageIcon(this.getClass().getResource("/javasource/斯特林-熔岩.jpg"))));
                                model.addElement(new Weaponry("斯特林冲锋枪", new ImageIcon(this.getClass().getResource("/javasource/斯特林冲锋枪.jpg"))));
                                model.addElement(new Weaponry("暗金沙漠之鹰", new ImageIcon(this.getClass().getResource("/javasource/暗金沙漠之鹰.jpg"))));
                                model.addElement(new Weaponry("月兔手雷", new ImageIcon(this.getClass().getResource("/javasource/月兔手雷.jpg"))));
                                model.addElement(new Weaponry("月饼手雷", new ImageIcon(this.getClass().getResource("/javasource/月饼手雷.jpg"))));
                                model.addElement(new Weaponry("杰迪-玛蒂可", new ImageIcon(this.getClass().getResource("/javasource/杰迪-玛蒂可.jpg"))));
                                model.addElement(new Weaponry("柯尔特-Red", new ImageIcon(this.getClass().getResource("/javasource/柯尔特-Red.jpg"))));
                                model.addElement(new Weaponry("柯尔特-双雄", new ImageIcon(this.getClass().getResource("/javasource/柯尔特-双雄.jpg"))));
                                model.addElement(new Weaponry("柯尔特双枪", new ImageIcon(this.getClass().getResource("/javasource/柯尔特双枪.jpg"))));
                                model.addElement(new Weaponry("棒球棒", new ImageIcon(this.getClass().getResource("/javasource/棒球棒.jpg"))));
                                model.addElement(new Weaponry("毛瑟-天秤座", new ImageIcon(this.getClass().getResource("/javasource/毛瑟-天秤座.png"))));
                                model.addElement(new Weaponry("毛瑟-烈焰", new ImageIcon(this.getClass().getResource("/javasource/毛瑟-烈焰.jpg"))));
                                model.addElement(new Weaponry("毛瑟-银月", new ImageIcon(this.getClass().getResource("/javasource/毛瑟-银月.jpg"))));
                                model.addElement(new Weaponry("毛瑟军用手枪", new ImageIcon(this.getClass().getResource("/javasource/毛瑟军用手枪.jpg"))));
                                model.addElement(new Weaponry("毛瑟手枪-战龙", new ImageIcon(this.getClass().getResource("/javasource/毛瑟手枪-战龙.jpg"))));
                                model.addElement(new Weaponry("气球手雷", new ImageIcon(this.getClass().getResource("/javasource/气球手雷.jpg"))));
                                model.addElement(new Weaponry("汽锤", new ImageIcon(this.getClass().getResource("/javasource/气锤.jpg"))));
                                model.addElement(new Weaponry("汽锤-H", new ImageIcon(this.getClass().getResource("/javasource/气锤-H.jpg"))));
                                model.addElement(new Weaponry("气锤-烈火", new ImageIcon(this.getClass().getResource("/javasource/气锤-烈火.jpg"))));
                                model.addElement(new Weaponry("水晶M4A1-A", new ImageIcon(this.getClass().getResource("/javasource/水晶M4A1-A.jpg"))));
                                model.addElement(new Weaponry("水晶双管猎枪", new ImageIcon(this.getClass().getResource("/javasource/水晶双管猎枪.jpg"))));
                                model.addElement(new Weaponry("水晶手雷", new ImageIcon(this.getClass().getResource("/javasource/水晶手雷.jpg"))));
                                model.addElement(new Weaponry("水晶沙漠之鹰", new ImageIcon(this.getClass().getResource("/javasource/水晶沙漠之鹰.jpg"))));
                                model.addElement(new Weaponry("汤姆逊-烈焰", new ImageIcon(this.getClass().getResource("/javasource/汤姆逊-烈焰.jpg"))));
                                model.addElement(new Weaponry("汤姆逊-烈龙", new ImageIcon(this.getClass().getResource("/javasource/汤姆逊-烈龙.jpg"))));
                                model.addElement(new Weaponry("汤姆逊-老兵", new ImageIcon(this.getClass().getResource("/javasource/汤姆逊-老兵.jpg"))));
                                model.addElement(new Weaponry("汤姆逊冲锋枪", new ImageIcon(this.getClass().getResource("/javasource/汤姆逊冲锋枪.jpg"))));
                                model.addElement(new Weaponry("沙漠之鹰", new ImageIcon(this.getClass().getResource("/javasource/沙漠之鹰.jpg"))));
                                model.addElement(new Weaponry("沙漠之鹰-A", new ImageIcon(this.getClass().getResource("/javasource/沙漠之鹰-A.jpg"))));
                                model.addElement(new Weaponry("沙漠之鹰-B", new ImageIcon(this.getClass().getResource("/javasource/沙漠之鹰-B.jpg"))));
                                model.addElement(new Weaponry("沙漠之鹰-QT", new ImageIcon(this.getClass().getResource("/javasource/沙漠之鹰-QT.jpg"))));
                                model.addElement(new Weaponry("沙漠之鹰-战龙", new ImageIcon(this.getClass().getResource("/javasource/沙漠之鹰-战龙.jpg"))));
                                model.addElement(new Weaponry("沙漠之鹰-暗杀者", new ImageIcon(this.getClass().getResource("/javasource/沙漠之鹰-暗杀者.jpg"))));
                                model.addElement(new Weaponry("沙漠之鹰-暗炎", new ImageIcon(this.getClass().getResource("/javasource/沙漠之鹰-暗炎.png"))));
                                model.addElement(new Weaponry("沙漠之鹰-炎龙", new ImageIcon(this.getClass().getResource("/javasource/沙漠之鹰-炎龙.jpg"))));
                                model.addElement(new Weaponry("沙漠之鹰-牡丹", new ImageIcon(this.getClass().getResource("/javasource/沙漠之鹰-牡丹.png"))));
                                model.addElement(new Weaponry("沙漠之鹰-苍龙", new ImageIcon(this.getClass().getResource("/javasource/沙漠之鹰-苍龙.jpg"))));
                                model.addElement(new Weaponry("沙漠之鹰-蓝水晶", new ImageIcon(this.getClass().getResource("/javasource/沙漠之鹰-蓝水晶.jpg"))));
                                model.addElement(new Weaponry("沙漠风暴", new ImageIcon(this.getClass().getResource("/javasource/沙漠风暴.jpg"))));
                                model.addElement(new Weaponry("沙鹰-修罗", new ImageIcon(this.getClass().getResource("/javasource/沙鹰-修罗.jpg"))));
                                model.addElement(new Weaponry("沙鹰-粉钻", new ImageIcon(this.getClass().getResource("/javasource/沙鹰-粉钻.jpg"))));
                                model.addElement(new Weaponry("泰坦铁拳", new ImageIcon(this.getClass().getResource("/javasource/泰坦铁拳.jpg"))));
                                model.addElement(new Weaponry("温彻斯特", new ImageIcon(this.getClass().getResource("/javasource/温彻斯特.jpg"))));
                                model.addElement(new Weaponry("温彻斯特-S", new ImageIcon(this.getClass().getResource("/javasource/温彻斯特-S.jpg"))));
                                model.addElement(new Weaponry("激光短刃", new ImageIcon(this.getClass().getResource("/javasource/激光短刃.jpg"))));
                                model.addElement(new Weaponry("激浪烟雾弹", new ImageIcon(this.getClass().getResource("/javasource/激浪烟雾弹.jpg"))));
                                model.addElement(new Weaponry("火鹰手斧", new ImageIcon(this.getClass().getResource("/javasource/火鹰手斧.jpg"))));
                                model.addElement(new Weaponry("火鹰手雷", new ImageIcon(this.getClass().getResource("/javasource/火鹰手雷.jpg"))));
                                model.addElement(new Weaponry("火麒麟", new ImageIcon(this.getClass().getResource("/javasource/火麒麟.jpg"))));
                                model.addElement(new Weaponry("炼狱", new ImageIcon(this.getClass().getResource("/javasource/炼狱.jpeg"))));
                                model.addElement(new Weaponry("烟雾弹", new ImageIcon(this.getClass().getResource("/javasource/烟雾弹.jpg"))));
                                model.addElement(new Weaponry("烟雾弹-雷暴", new ImageIcon(this.getClass().getResource("/javasource/烟雾弹-雷暴.jpeg"))));
                                model.addElement(new Weaponry("熔岩军用指虎", new ImageIcon(this.getClass().getResource("/javasource/熔岩军用指虎.jpg"))));
                                model.addElement(new Weaponry("爱之雷", new ImageIcon(this.getClass().getResource("/javasource/爱之雷.jpg"))));
                                model.addElement(new Weaponry("王者之光", new ImageIcon(this.getClass().getResource("/javasource/王者之光.jpeg"))));
                                model.addElement(new Weaponry("王者之击", new ImageIcon(this.getClass().getResource("/javasource/王者之击.jpeg"))));
                                model.addElement(new Weaponry("王者之力", new ImageIcon(this.getClass().getResource("/javasource/王者之力.jpg"))));
                                model.addElement(new Weaponry("王者之幻", new ImageIcon(this.getClass().getResource("/javasource/王者之幻.jpeg"))));
                                model.addElement(new Weaponry("王者之影", new ImageIcon(this.getClass().getResource("/javasource/王者之影.jpeg"))));
                                model.addElement(new Weaponry("王者之心", new ImageIcon(this.getClass().getResource("/javasource/王者之心.jpg"))));
                                model.addElement(new Weaponry("王者之怒", new ImageIcon(this.getClass().getResource("/javasource/王者之怒.jpeg"))));
                                model.addElement(new Weaponry("王者之武", new ImageIcon(this.getClass().getResource("/javasource/王者之武.jpeg"))));
                                model.addElement(new Weaponry("王者之锋", new ImageIcon(this.getClass().getResource("/javasource/王者之锋.png"))));
                                model.addElement(new Weaponry("王者之魂", new ImageIcon(this.getClass().getResource("/javasource/王者之魂.jpg"))));
                                model.addElement(new Weaponry("王者之魄", new ImageIcon(this.getClass().getResource("/javasource/王者之魄.jpeg"))));
                                model.addElement(new Weaponry("王者幻神", new ImageIcon(this.getClass().getResource("/javasource/王者幻神.jpeg"))));
                                model.addElement(new Weaponry("玩具手雷", new ImageIcon(this.getClass().getResource("/javasource/玩具手雷.jpg"))));
                                model.addElement(new Weaponry("玫瑰手斧", new ImageIcon(this.getClass().getResource("/javasource/玫瑰手斧.jpg"))));
                                model.addElement(new Weaponry("玫瑰手雷", new ImageIcon(this.getClass().getResource("/javasource/玫瑰手雷.jpg"))));
                                model.addElement(new Weaponry("瓦尔特WA2000", new ImageIcon(this.getClass().getResource("/javasource/瓦尔特WA2000.jpg"))));
                                model.addElement(new Weaponry("生化手雷", new ImageIcon(this.getClass().getResource("/javasource/生化手雷.jpg"))));
                                model.addElement(new Weaponry("疾爆手雷", new ImageIcon(this.getClass().getResource("/javasource/疾爆手雷.jpg"))));
                                model.addElement(new Weaponry("白银手雷", new ImageIcon(this.getClass().getResource("/javasource/白银手雷.jpg"))));
                                model.addElement(new Weaponry("白银沙漠之鹰", new ImageIcon(this.getClass().getResource("/javasource/白银沙漠之鹰.jpg"))));
                                model.addElement(new Weaponry("百城AK-47", new ImageIcon(this.getClass().getResource("/javasource/百城AK-47.jpg"))));
                                model.addElement(new Weaponry("百城AWM", new ImageIcon(this.getClass().getResource("/javasource/百城AWM.jpg"))));
                                model.addElement(new Weaponry("百城M4A1", new ImageIcon(this.getClass().getResource("/javasource/百城M4A1.jpg"))));
                                model.addElement(new Weaponry("破天斩魔剑", new ImageIcon(this.getClass().getResource("/javasource/破天斩魔剑.jpg"))));
                                model.addElement(new Weaponry("福字手雷", new ImageIcon(this.getClass().getResource("/javasource/福字手雷.jpg"))));
                                model.addElement(new Weaponry("粽子手雷", new ImageIcon(this.getClass().getResource("/javasource/粽子手雷.jpg"))));
                                model.addElement(new Weaponry("精英手雷", new ImageIcon(this.getClass().getResource("/javasource/精英手雷.jpg"))));
                                model.addElement(new Weaponry("糖果手雷", new ImageIcon(this.getClass().getResource("/javasource/糖果手雷.jpg"))));
                                model.addElement(new Weaponry("糖衣炮弹", new ImageIcon(this.getClass().getResource("/javasource/糖衣炮弹.jpg"))));
                                model.addElement(new Weaponry("紫骷髅手套", new ImageIcon(this.getClass().getResource("/javasource/紫骷髅手套.jpg"))));
                                model.addElement(new Weaponry("红色烟雾弹", new ImageIcon(this.getClass().getResource("/javasource/红色烟雾弹.jpg"))));
                                model.addElement(new Weaponry("红龙", new ImageIcon(this.getClass().getResource("/javasource/红龙.jpg"))));
                                model.addElement(new Weaponry("红龙 SCAR Light", new ImageIcon(this.getClass().getResource("/javasource/红龙 SCAR Light.jpg"))));
                                model.addElement(new Weaponry("红龙AK47", new ImageIcon(this.getClass().getResource("/javasource/红龙AK47.jpg"))));
                                model.addElement(new Weaponry("红龙AR-70", new ImageIcon(this.getClass().getResource("/javasource/红龙AR-70.jpg"))));
                                model.addElement(new Weaponry("纯金M4A1", new ImageIcon(this.getClass().getResource("/javasource/纯金M4A1.jpg"))));
                                model.addElement(new Weaponry("绍沙M1915", new ImageIcon(this.getClass().getResource("/javasource/绍沙M1915.jpg"))));
                                model.addElement(new Weaponry("维多克-77", new ImageIcon(this.getClass().getResource("/javasource/维多克-77.jpg"))));
                                model.addElement(new Weaponry("绿光铁锤", new ImageIcon(this.getClass().getResource("/javasource/绿光铁锤.jpg"))));
                                model.addElement(new Weaponry("联想锋行黄金AK", new ImageIcon(this.getClass().getResource("/javasource/联想锋行黄金AK.jpg"))));
                                model.addElement(new Weaponry("蓝色魅影", new ImageIcon(this.getClass().getResource("/javasource/蓝色魅影.jpg"))));
                                model.addElement(new Weaponry("蟒蛇", new ImageIcon(this.getClass().getResource("/javasource/蟒蛇.jpg"))));
                                model.addElement(new Weaponry("警棍", new ImageIcon(this.getClass().getResource("/javasource/警棍.jpg"))));
                                model.addElement(new Weaponry("诅咒娃娃手雷", new ImageIcon(this.getClass().getResource("/javasource/诅咒娃娃手雷.jpg"))));
                                model.addElement(new Weaponry("贝雷塔687", new ImageIcon(this.getClass().getResource("/javasource/贝雷塔687.jpg"))));
                                model.addElement(new Weaponry("足球手雷", new ImageIcon(this.getClass().getResource("/javasource/足球手雷.jpg"))));
                                model.addElement(new Weaponry("迅捷虫手雷", new ImageIcon(this.getClass().getResource("/javasource/迅捷虫手雷.jpg"))));
                                model.addElement(new Weaponry("金魔TAR21", new ImageIcon(this.getClass().getResource("/javasource/金魔TAR21.jpg"))));
                                model.addElement(new Weaponry("金魔巨锤", new ImageIcon(this.getClass().getResource("/javasource/金魔巨锤.jpg"))));
                                model.addElement(new Weaponry("金魔手套", new ImageIcon(this.getClass().getResource("/javasource/金魔手套.jpg"))));
                                model.addElement(new Weaponry("钢铁风暴手雷", new ImageIcon(this.getClass().getResource("/javasource/钢铁风暴手雷.jpg"))));
                                model.addElement(new Weaponry("钽式步枪", new ImageIcon(this.getClass().getResource("/javasource/钽式步枪.jpg"))));
                                model.addElement(new Weaponry("铁链锤", new ImageIcon(this.getClass().getResource("/javasource/铁链锤.jpg"))));
                                model.addElement(new Weaponry("铁链锤-彩魔", new ImageIcon(this.getClass().getResource("/javasource/铁链锤-彩魔.jpg"))));
                                model.addElement(new Weaponry("铁锤", new ImageIcon(this.getClass().getResource("/javasource/铁锤.jpg"))));
                                model.addElement(new Weaponry("银月汽锤", new ImageIcon(this.getClass().getResource("/javasource/银月汽锤.jpg"))));
                                model.addElement(new Weaponry("银色M4A1-A", new ImageIcon(this.getClass().getResource("/javasource/银色M4A1-A.jpg"))));
                                model.addElement(new Weaponry("银色双管猎枪", new ImageIcon(this.getClass().getResource("/javasource/银色双管猎枪.jpg"))));
                                model.addElement(new Weaponry("闪光弹-雷暴", new ImageIcon(this.getClass().getResource("/javasource/闪光弹-雷暴.jpeg"))));
                                model.addElement(new Weaponry("阿梅利轻机枪", new ImageIcon(this.getClass().getResource("/javasource/阿梅利轻机枪.png"))));
                                model.addElement(new Weaponry("雄黄酒手雷", new ImageIcon(this.getClass().getResource("/javasource/雄黄酒手雷.jpg"))));
                                model.addElement(new Weaponry("雪花手雷", new ImageIcon(this.getClass().getResource("/javasource/雪花手雷.jpg"))));
                                model.addElement(new Weaponry("雷明登M1858", new ImageIcon(this.getClass().getResource("/javasource/雷明登M1858.jpg"))));
                                model.addElement(new Weaponry("雷明登M1885-斑马", new ImageIcon(this.getClass().getResource("/javasource/雷明登M1885-斑马.jpg"))));
                                model.addElement(new Weaponry("雷明登MSR", new ImageIcon(this.getClass().getResource("/javasource/雷明登MSR.jpg"))));
                                model.addElement(new Weaponry("雷蛇AWM", new ImageIcon(this.getClass().getResource("/javasource/雷蛇AWM.jpg"))));
                                model.addElement(new Weaponry("雷蛇冲锋枪", new ImageIcon(this.getClass().getResource("/javasource/雷蛇冲锋枪.jpg"))));
                                model.addElement(new Weaponry("雷蛇加特林", new ImageIcon(this.getClass().getResource("/javasource/雷蛇加特林.jpg"))));
                                model.addElement(new Weaponry("青铜M4A1-A", new ImageIcon(this.getClass().getResource("/javasource/青铜M4A1-A.jpg"))));
                                model.addElement(new Weaponry("青铜双管猎枪", new ImageIcon(this.getClass().getResource("/javasource/青铜双管猎枪.jpg"))));
                                model.addElement(new Weaponry("青铜手雷", new ImageIcon(this.getClass().getResource("/javasource/青铜手雷.jpg"))));
                                model.addElement(new Weaponry("青铜沙漠之鹰", new ImageIcon(this.getClass().getResource("/javasource/青铜沙漠之鹰.jpg"))));
                                model.addElement(new Weaponry("鞭炮手雷", new ImageIcon(this.getClass().getResource("/javasource/鞭炮手雷.jpg"))));
                                model.addElement(new Weaponry("风暴拳套", new ImageIcon(this.getClass().getResource("/javasource/风暴拳套.jpg"))));
                                model.addElement(new Weaponry("马来剑", new ImageIcon(this.getClass().getResource("/javasource/马来剑.jpg"))));
                                model.addElement(new Weaponry("马来剑-圣诞", new ImageIcon(this.getClass().getResource("/javasource/马来剑-圣诞.jpg"))));
                                model.addElement(new Weaponry("马来剑-青花瓷", new ImageIcon(this.getClass().getResource("/javasource/马来剑-青花瓷.jpg"))));
                                model.addElement(new Weaponry("马来剑彩魔", new ImageIcon(this.getClass().getResource("/javasource/马来剑彩魔.jpg"))));
                                model.addElement(new Weaponry("骑士SR25-SS", new ImageIcon(this.getClass().getResource("/javasource/骑士SR25-SS.jpg"))));
                                model.addElement(new Weaponry("高爆手雷", new ImageIcon(this.getClass().getResource("/javasource/高爆手雷.jpg"))));
                                model.addElement(new Weaponry("高爆手雷-SS", new ImageIcon(this.getClass().getResource("/javasource/高爆手雷-SS.jpg"))));
                                model.addElement(new Weaponry("高爆手雷-雷霆", new ImageIcon(this.getClass().getResource("/javasource/高爆手雷-雷霆.jpeg"))));
                                model.addElement(new Weaponry("鹰爪", new ImageIcon(this.getClass().getResource("/javasource/鹰爪.jpg"))));
                                model.addElement(new Weaponry("麒麟刺", new ImageIcon(this.getClass().getResource("/javasource/麒麟刺.jpg"))));
                                model.addElement(new Weaponry("麦特左轮", new ImageIcon(this.getClass().getResource("/javasource/麦特左轮.jpg"))));
                                model.addElement(new Weaponry("黄金AK-47", new ImageIcon(this.getClass().getResource("/javasource/黄金AK-47.jpg"))));
                                model.addElement(new Weaponry("黄金ANACONDA", new ImageIcon(this.getClass().getResource("/javasource/黄金ANACONDA.jpg"))));
                                model.addElement(new Weaponry("黄金AWM", new ImageIcon(this.getClass().getResource("/javasource/黄金AWM.jpg"))));
                                model.addElement(new Weaponry("黄金M4A1", new ImageIcon(this.getClass().getResource("/javasource/黄金M4A1.jpg"))));
                                model.addElement(new Weaponry("黄金M4A1-X", new ImageIcon(this.getClass().getResource("/javasource/黄金M4A1-X.jpg"))));
                                model.addElement(new Weaponry("黄金M14EBR", new ImageIcon(this.getClass().getResource("/javasource/黄金M14EBR.jpg"))));
                                model.addElement(new Weaponry("黄金M249 Minimi", new ImageIcon(this.getClass().getResource("/javasource/黄金M249 Minimi.jpg"))));
                                model.addElement(new Weaponry("黄金QBZ95-A", new ImageIcon(this.getClass().getResource("/javasource/黄金QBZ95-A.jpg"))));
                                model.addElement(new Weaponry("黄金RPK机关枪", new ImageIcon(this.getClass().getResource("/javasource/黄金RPK机关枪.jpg"))));
                                model.addElement(new Weaponry("黄金Scar-Light", new ImageIcon(this.getClass().getResource("/javasource/黄金Scar-Light.jpg"))));
                                model.addElement(new Weaponry("黄金乌兹双枪", new ImageIcon(this.getClass().getResource("/javasource/黄金乌兹双枪.jpg"))));
                                model.addElement(new Weaponry("黄金军用手斧", new ImageIcon(this.getClass().getResource("/javasource/黄金军用手斧.jpg"))));
                                model.addElement(new Weaponry("黄金加特林", new ImageIcon(this.getClass().getResource("/javasource/黄金加特林.jpg"))));
                                model.addElement(new Weaponry("黄金手雷", new ImageIcon(this.getClass().getResource("/javasource/黄金手雷.jpg"))));
                                model.addElement(new Weaponry("黄金柯尔特", new ImageIcon(this.getClass().getResource("/javasource/黄金柯尔特.jpg"))));
                                model.addElement(new Weaponry("黄金毛瑟手枪", new ImageIcon(this.getClass().getResource("/javasource/黄金毛瑟手枪.jpg"))));
                                model.addElement(new Weaponry("黄金气锤", new ImageIcon(this.getClass().getResource("/javasource/黄金气锤.jpg"))));
                                model.addElement(new Weaponry("黄金汤姆逊", new ImageIcon(this.getClass().getResource("/javasource/黄金汤姆逊.jpg"))));
                                model.addElement(new Weaponry("黄金沙漠之鹰", new ImageIcon(this.getClass().getResource("/javasource/黄金沙漠之鹰.jpg"))));
                                model.addElement(new Weaponry("黄金温彻斯特", new ImageIcon(this.getClass().getResource("/javasource/黄金温彻斯特.jpg"))));
                                model.addElement(new Weaponry("黄金野牛", new ImageIcon(this.getClass().getResource("/javasource/黄金野牛.jpg"))));
                                model.addElement(new Weaponry("黑龙炮", new ImageIcon(this.getClass().getResource("/javasource/黑龙炮.jpeg"))));
                                model.addElement(new Weaponry("龙啸", new ImageIcon(this.getClass().getResource("/javasource/龙啸.jpg"))));
                                model.addElement(new Weaponry("龙拳", new ImageIcon(this.getClass().getResource("/javasource/龙拳.jpg"))));
                                model.addElement(new Weaponry("龙鳞", new ImageIcon(this.getClass().getResource("/javasource/龙鳞.jpg"))));

                                weaPon = new JList(model);
                                weaPon.setVisibleRowCount(15);
                                weaPon.setSelectionBackground(Color.BLUE);
                                weaPon.setSelectionForeground(Color.RED);
                                weaPon.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                                //设置组件大小
                                weaPon.setPreferredSize(new Dimension(160, 9785));
                                weaPon.setBackground(Color.BLACK);
                                weaPon.setBorder(new EtchedBorder(Color.BLACK, Color.RED));

                                activityKey.setForeground(Color.RED);
                                //给激活按钮设置事件
                                activateButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if (activateDelay == 0) {
                                            JOptionPane.showMessageDialog(jf2, new JLabel("正在验证密钥----请稍等......"), "信息", JOptionPane.NO_OPTION, new ImageIcon(this.getClass().getResource("/javasource/笑脸.png")));
                                            try {
                                                Thread.sleep(1000);
                                            } catch (InterruptedException ex) {
                                                ex.printStackTrace();
                                            }
                                        }

                                        if (activityKey.getText().equals(activateKey)) {
                                            activateDelay = 1;
                                            if (activateDelay == 1) {
                                                JOptionPane.showMessageDialog(jf2, new JLabel("激活成功----您可以愉快使用本软件了"), "信息", JOptionPane.NO_OPTION, new ImageIcon(this.getClass().getResource("/javasource/笑脸.png")));
                                                jstate.setText("激活状态:已激活");
                                                jstate.setForeground(Color.BLUE);
                                                activityKey.setEditable(false);
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(jf2, new JLabel("激活失败----您输入的密钥不正确!"), "信息", JOptionPane.NO_OPTION, new ImageIcon(this.getClass().getResource("/javasource/抱歉.png")));
                                        }

                                    }
                                });

                                brsh = new JButton(new AbstractAction("一键刷取") {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if ("激活状态:未激活".equals(jstate.getText())) {
                                            JOptionPane.showMessageDialog(jf2, new JLabel("目前连接状态:未激活----请尽快激活进行使用"), "信息", JOptionPane.NO_OPTION, new ImageIcon(this.getClass().getResource("/javasource/抱歉.png")));
                                        }
                                        if ("激活状态:已激活".equals(jstate.getText())) {
                                            dialog = 2;
//

                                            if (dialog == 2) {
                                                JLabel label = new JLabel("请稍等正在为您刷取......");
                                                label.setForeground(Color.RED);
                                                JOptionPane.showMessageDialog(jf2, label, "信息", JOptionPane.NO_OPTION, new ImageIcon(this.getClass().getResource("/javasource/打包.png")));
                                                try {
                                                    Thread.sleep(200);
                                                } catch (InterruptedException ex) {
                                                    ex.printStackTrace();
                                                }
                                                while (true) {
                                                    if (deadLineOfSeven.isSelected()) {
                                                        if (deadLineOfMonth.isSelected() && deadLineOfForever.isSelected() || (deadLineOfMonth.isSelected() || deadLineOfForever.isSelected())) {
                                                            JOptionPane.showMessageDialog(jf2, new JLabel("刷取失败-----刷枪的天数只能进行单个选择,请重新进行选择!"), "信息", JOptionPane.NO_OPTION, new ImageIcon(this.getClass().getResource("/javasource/抱歉.png")));
                                                        } else {
                                                            JLabel label1 = new JLabel(deadLineOfSeven.getText() + " " + weaPon.getSelectedValue() + "----刷取成功 请尽快到仓库进行验收!");
                                                            label1.setForeground(Color.RED);
                                                            JOptionPane.showMessageDialog(jf2, label1, "信息", JOptionPane.YES_OPTION, new ImageIcon(this.getClass().getResource("/javasource/武器按钮.png")));
                                                            infoArea.append(deadLineOfSeven.getText() + " " + weaPon.getSelectedValue() + "-----刷取成功,请尽快验收!\n");
                                                        }
                                                        break;
                                                    }
                                                    if (deadLineOfMonth.isSelected()) {
                                                        if (deadLineOfSeven.isSelected() && deadLineOfForever.isSelected() || (deadLineOfSeven.isSelected() || deadLineOfForever.isSelected())) {
                                                            JOptionPane.showMessageDialog(jf2, new JLabel("刷取失败-----刷枪的天数只能进行单个选择,请重新进行选择!"), "信息", JOptionPane.NO_OPTION, new ImageIcon(this.getClass().getResource("/javasource/抱歉.png")));
                                                        } else {
                                                            JLabel label2 = new JLabel(deadLineOfMonth.getText() + " " + weaPon.getSelectedValue() + "----刷取成功 请尽快到仓库进行验收!");
                                                            label2.setForeground(Color.RED);
                                                            JOptionPane.showMessageDialog(jf2, label2, "信息", JOptionPane.YES_OPTION, new ImageIcon(this.getClass().getResource("/javasource/武器按钮.png")));
                                                            infoArea.append(deadLineOfMonth.getText() + " " + weaPon.getSelectedValue() + "-----刷取成功,请尽快验收!\n");
                                                        }
                                                        break;
                                                    }
                                                    if (deadLineOfForever.isSelected()) {
                                                        if (deadLineOfSeven.isSelected() && deadLineOfMonth.isSelected() || (deadLineOfSeven.isSelected() || deadLineOfMonth.isSelected())) {
                                                            JOptionPane.showMessageDialog(jf2, new JLabel("刷取失败-----刷枪的天数只能进行单个选择,请重新进行选择!"), "信息", JOptionPane.NO_OPTION, new ImageIcon(this.getClass().getResource("/javasource/抱歉.png")));
                                                        } else {
                                                            JLabel label2 = new JLabel(deadLineOfForever.getText() + " " + weaPon.getSelectedValue() + "----刷取成功 请尽快到仓库进行验收!");
                                                            label2.setForeground(Color.RED);
                                                            JOptionPane.showMessageDialog(jf2, label2, "信息", JOptionPane.YES_OPTION, new ImageIcon(this.getClass().getResource("/javasource/武器按钮.png")));
                                                            infoArea.append(deadLineOfForever.getText() + " " + weaPon.getSelectedValue() + "-----刷取成功,请尽快验收!\n");
                                                        }
                                                        break;
                                                    }

                                                }
                                            }
                                        }
                                    }

                                });


                                jstate.setForeground(Color.RED);
                                JPanel jp = new JPanel();
                                jp.setLayout(new FlowLayout());
                                brsh.setSize(80, 60);
                                jp.add(brsh);
                                jstate.setFont(new Font("StSong", Font.BOLD, 16));
                                jp.add(jstate);

                                JPanel activatePanel = new JPanel();
                                activatePanel.setLayout(new FlowLayout());
                                activateTip.setFont(new Font("StSong", Font.BOLD, 18));
                                activatePanel.add(activateTip);
                                activityKey.setEchoChar('*');
                                activityKey.setFont(new Font("StSong", Font.BOLD, 18));
                                activatePanel.add(activityKey);
                                activatePanel.add(activateButton);
                                activatePanel.setBackground(Color.BLACK);
                                activateTip.setForeground(Color.RED);
                                activityKey.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.BLACK, Color.RED));
                                activateButton.setBackground(Color.RED);

                                TitledBorder title = new TitledBorder(new LineBorder(Color.RED, 1), "激活码  ", TitledBorder.LEFT, TitledBorder.TOP, new Font("StSong", Font.BOLD, 18), Color.RED);

                                weaponCover.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.BLACK, Color.RED));
                                weaponCover.setBackground(Color.BLACK);

                                Box right = Box.createVerticalBox();
                                right.setPreferredSize(new Dimension(500, 200));
                                right.add(weaponCover);
                                right.setBackground(Color.BLACK);
                                midHorizontal.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
                                deadLineOfSeven.setFont(new Font("StSong", Font.BOLD, 16));
                                midHorizontal.add(deadLineOfSeven);
                                deadLineOfMonth.setFont(new Font("StSong", Font.BOLD, 16));
                                midHorizontal.add(deadLineOfMonth);
                                deadLineOfForever.setFont(new Font("StSong", Font.BOLD, 16));
                                midHorizontal.add(deadLineOfForever);

                                deadLineOfSeven.setBackground(Color.BLACK);
                                deadLineOfMonth.setBackground(Color.BLACK);
                                deadLineOfForever.setBackground(Color.BLACK);

                                deadLineOfSeven.setForeground(Color.RED);
                                deadLineOfMonth.setForeground(Color.RED);
                                deadLineOfForever.setForeground(Color.RED);

                                midHorizontal.setBackground(Color.BLACK);


                                right.add(midHorizontal);
                                jp.setBackground(Color.BLACK);
                                right.add(jp);
                                brsh.setBackground(Color.BLACK);
                                brsh.setForeground(Color.RED);
                                JPanel panelWithBorder = getPanelWithBorder(title, activatePanel);
                                panelWithBorder.setBackground(Color.BLACK);
                                right.add(panelWithBorder);

                                infoArea = new JTextArea(20, 10);
                                infoArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.BLACK, Color.RED));
                                infoArea.setBackground(Color.BLACK);
                                infoArea.setForeground(Color.RED);


                                Box eastBox = Box.createHorizontalBox();
                                eastBox.add(right);
                                eastBox.add(infoArea);

                                weaPon.addListSelectionListener(new ListSelectionListener() {
                                    @Override
                                    public void valueChanged(ListSelectionEvent e) {
                                        Weaponry weaponry = weaPon.getSelectedValue();
                                        weaponCover.setIcon(weaponry.getIcon());
                                    }
                                });

                                JLabel topView = new JLabel(new ImageIcon(this.getClass().getResource("/javasource/background.jpeg")));

                                JPanel top = new JPanel();
                                top.add(topView);
                                top.setBackground(Color.BLACK);
                                JScrollPane jScrollPane = new JScrollPane(weaPon);

                                JSplitPane whole = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jScrollPane, eastBox);

                                whole.setDividerSize(0);
                                whole.setDividerLocation(260);
                                whole.setBorder(new EtchedBorder(Color.BLACK, Color.BLACK));
                                whole.setBackground(Color.BLACK);


                                JSplitPane topPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, top, whole);

                                JFrame.setDefaultLookAndFeelDecorated(true);
                                JDialog.setDefaultLookAndFeelDecorated(true);


                                topPane.setContinuousLayout(true);
                                topPane.setDividerSize(0);


                                jf2.add(topPane);

                                jf2.pack();
                                jf2.setVisible(true);
                                jf2.setResizable(false);

                            }
                        } else {
                            state.setText("登录失败!");
                            JOptionPane.showMessageDialog(jf, "用户或密码输入错误!", "信息", JOptionPane.OK_OPTION, new ImageIcon(this.getClass().getResource("/javasource/失败.png")));

                        }
                    } catch (SQLException s) {
                        s.printStackTrace();
                    }
                    catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    finally {
                        if (stmt != null) {
                            try {
                                stmt.close();
                            } catch (SQLException ex) {

                            }
                        }
                        if (conn != null) {
                            try {
                                conn.close();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            });
            //给exit按钮设置事件
            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(jf, "退出成功!", "信息", JOptionPane.OK_OPTION, new ImageIcon(this.getClass().getResource("/javasource/成功.png")));
                    System.exit(0);
                }
            });
        }
    }

    public JPanel getPanelWithBorder(Border border, Component content) {
        JPanel jPanel = new JPanel();
        jPanel.add(content);

        //设置边框
        jPanel.setBorder(border);

        return jPanel;
    }
}
