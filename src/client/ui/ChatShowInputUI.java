package client.ui;

import client.util.ChatInstructionCode;
import client.util.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatShowInputUI extends JPanel {

    private Color BOTTOM_RIGHT_BGCOLOR = new Color(242, 242, 242);
    private JTextArea inputArea;
    private JTextArea showArea;
    private JLabel chatPersonName;

    private String str_Id = null;   // 接收消息的用户id
    private String str_name = null; // 接收消息的用户名称
    private Message message = null;
    private ChatInstructionCode CODE = new ChatInstructionCode();

    public JTextArea getInputArea() {
        return inputArea;
    }

    public JTextArea getShowArea() {
        return showArea;
    }

    public String getStr_Id() {
        return str_Id;
    }

    public String getStr_Name() {
        return str_name;
    }

    public ChatShowInputUI() {
    	init();
    }

    public ChatShowInputUI(String str_Id, String str_name, Message message) {
        this.str_Id = str_Id;
        this.str_name = str_name;
        this.message = message;
        init();
    }

    public void init() {
        // 聊天界面Panel
//        JPanel bottomRightPanel = new JPanel();
//        bottomRightPanel.setBackground(Color.ORANGE);
//        bottomRightPanel.setLayout(null);
//        bottomRightPanel.setBounds(310, 0, 809, 680);

        this.setBackground(Color.ORANGE);
        this.setLayout(null);
        this.setBounds(310, 0, 809, 680);
        this.setVisible(false);
        // 聊天界面TopBar
        JPanel chatTopPanel = new JPanel();
        chatTopPanel.setBounds(0, 0, 800, 45);
        chatTopPanel.setBackground(BOTTOM_RIGHT_BGCOLOR);
        chatTopPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(179, 179, 179)));
        this.add(chatTopPanel);
        chatTopPanel.setLayout(null);

        // 聊天界面联系人名称
        chatPersonName = new JLabel("测试");
        chatPersonName.setText(this.str_name);
        chatPersonName.setFont(new Font("微软雅黑", Font.BOLD, 20));
        chatPersonName.setBounds(14, 0, 200, 45);
        chatTopPanel.add(chatPersonName);

        // 聊天界面面板
        JPanel chatAreaPanel = new JPanel();
        chatAreaPanel.setBackground(BOTTOM_RIGHT_BGCOLOR);
        chatAreaPanel.setBounds(0, 45, 800, 635);
        this.add(chatAreaPanel);
        chatAreaPanel.setLayout(null);

        // 聊天记录面板
        // 滚动
        JScrollPane showAreasScrollPane = new JScrollPane();
        showAreasScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        showAreasScrollPane.setBounds(10, 5, 770, 405);
        showAreasScrollPane.setBorder(null);
        chatAreaPanel.add(showAreasScrollPane);
        // 显示框
        showArea = new JTextArea();
        showArea.setLineWrap(true);
        showArea.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        showArea.setBackground(BOTTOM_RIGHT_BGCOLOR);
        showArea.setEditable(false);
        showAreasScrollPane.setViewportView(showArea);

        // 聊天工具Bar
        JPanel chatToolPanel = new JPanel();
        chatToolPanel.setBounds(0, 415, 800, 35);
        chatToolPanel.setBackground(Color.PINK);
        chatToolPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(179, 179, 179)));
        chatAreaPanel.add(chatToolPanel);

        // 聊天输入面板
        // 滚动
        JScrollPane inputScrollPane = new JScrollPane();
        inputScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        inputScrollPane.setBounds(10, 460, 770, 120);
        inputScrollPane.setBorder(null);
        inputArea = new JTextArea();
        inputArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    getInputAreaText();
                }
            }
        });
        inputArea.setLineWrap(true);
        inputArea.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        inputArea.setBackground(BOTTOM_RIGHT_BGCOLOR);
        int height = 20000;
        Point p = new Point();
        p.setLocation(0, inputArea.getLineCount() * height);
        inputScrollPane.getViewport().setViewPosition(p);
        chatAreaPanel.add(inputScrollPane);
        inputScrollPane.setViewportView(inputArea);

        // 消息发送按钮
        JButton sendButton = new JButton("发 送");
        sendButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                getInputAreaText();
            }
        });
        sendButton.setForeground(Color.WHITE);
        sendButton.setBackground(new Color(105, 155, 178));
        sendButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        sendButton.setBounds(710, 590, 70, 35);
        chatAreaPanel.add(sendButton);
    }


    /**
     * 仅提取消息
     * 在语句的开头加上接收消息的用户id
     * 格式   [CODE]<FROM:>ID<TO>ID[MSG:]輸入内容
     */
    public void getInputAreaText() {
        String str = inputArea.getText();
        System.out.println(str_Id + "@"  + str);
        System.out.println("1#" + CODE.CLIENT_FROM_ID + message.getUser().getId() +
                CODE.CLIENT_TO + str_Id + CODE.MESSAGE_SPLIT_SYMBO + str);
        this.message.processSend("1#" + CODE.CLIENT_FROM_ID + message.getUser().getId() +
                CODE.CLIENT_FROM_NAME + message.getUser().getName() + CODE.CLIENT_TO + str_Id +
                CODE.MESSAGE_SPLIT_SYMBO + str); // 发送消息
        this.setShowAreaText(message.getUser().getName() + " " + getTime() + "\n" + str);
        inputArea.setText(null);
    }

    /**
     * 將从服务器接收的消息显示出来
     * @param str
     */
    public void setShowAreaText(String str) {
        this.showArea.append(str + "\n");
    }

    public String getTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }

    public static void main(String[] args) {
        ChatShowInputUI c = new ChatShowInputUI();
        JFrame jf = new JFrame();
        jf.setBounds(100,100,850,720);
        jf.add(c);
        jf.setVisible(true);
        c.setVisible(true);
    }

}
