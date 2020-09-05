import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

public class GUI {
    LeftDelete Ld= new LeftDelete();
    JTextArea userText = new JTextArea();    //原文法输入框
    JTextArea passwordText = new JTextArea(); //消除左递归后的输出框
    //表格，以表格的形式输出结果
    void MainFunction(){
        prepareGUI();
    }                                 //UI主函数，用于初始化UI
    private void prepareGUI(){

        JFrame frame = new JFrame("编译课设1:消除左递归小程序");
        // Setting the width and height of frame
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel panel = new JPanel();
        // 添加面板
        frame.add(panel);
        panel.setLayout(null);

        // 创建 JLabel
        JLabel userLabel = new JLabel("输入原文法");
        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        userLabel.setBounds(20,20,80,25);
        panel.add(userLabel);

        /*
         * 创建文本域用于用户输入
         */

        userText.setBounds(20,50,165,160);
        panel.add(userText);

        // 输入密码的文本域
        JLabel passwordLabel = new JLabel("消除左递归后的文法");
        passwordLabel.setBounds(23,220,200,40);
        panel.add(passwordLabel);

        /*
         *这个类似用于输入的文本域
         * 但是输入的信息会以点号代替，用于包含密码的安全性
         */

        passwordText.setBounds(20,250,165,160);
        panel.add(passwordText);

        // 创建登录按钮
        JButton startButton = new JButton("开始");
        JButton resetButton = new JButton("重置");
        startButton.setBounds(150, 420, 80, 25);
        resetButton.setBounds(240,420,80,25);
        panel.add(startButton);
        panel.add(resetButton);
        startButton.setActionCommand("start");
        resetButton.setActionCommand("reset");
        startButton.addActionListener(new SelfButtonActionListener());
        resetButton.addActionListener(new SelfButtonActionListener());
        StringBuffer infoString = new StringBuffer("开始键：开始       \n清除键：重置");
        JLabel info =new JLabel(infoString.toString());

        info.setBounds(210,100,220,100);
        panel.add(info);
        frame.setVisible(true);
    }

    /*
   按钮相应类（继承事件响应类）
   用于响应点击各按钮的事件编写
    */
    class SelfButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            String command=e.getActionCommand();
            //点击开始按钮，进入对测试句的文法分析
            if(command.equals("start")){
                Ld.RAWString=userText.getText();
                Ld.divide();
                Ld.DirectLeft();
                Ld.UpdateNon();
                while (Ld.whetherDLStart())
                {
                    Ld.IndirectLeft(Ld.StartIndirect);
                    Ld.DirectLeft();
                    Ld.UpdateNon();
                }


                passwordText.setText(Ld.PrintOut());
            }
            else if(command.equals("reset")){
                passwordText.setText("");
                userText.setText("");
                Ld=new LeftDelete();
            }
            //对follow集进行输出，大体原理与上面first输出类似

            }
            //对当前预测分析表进行输出
        }
    }