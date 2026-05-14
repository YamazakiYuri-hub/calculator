import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

//画面UI関連
public class CalculatorFrame {

    private JLabel displayLabel = new JLabel();
    private JPanel keyPadPanel = new JPanel();

    //表示更新
    public void setDisplay(String text){
    }

    //ボタン生成・配置
    public void bindController(){
        //ウインドウの生成
        JFrame frame = new JFrame();
        frame.setTitle("Calculator");
        frame.setSize(300,400);
        frame.setLayout(new BorderLayout());
        //表示エリアレイアウト
        displayLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        displayLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
        displayLabel.setBorder(new EmptyBorder(10, 20, 10, 20));
        frame.add(displayLabel,BorderLayout.NORTH);
        //表示エリアの中身
        displayLabel.setText("0");
        //ボタンレイアウト
        GridLayout layout = new GridLayout(5,4,5,5);
        Font buttonFont = new Font(Font.MONOSPACED, Font.BOLD, 18);
        keyPadPanel.setLayout(layout);
        frame.add(keyPadPanel,BorderLayout.CENTER);
        //ボタンの生成
        String[] labels = {"7","8","9","÷","4","5","6","×","1","2","3","-","0",".","=","+","C"};
        for(String label : labels){
            JButton button = new JButton(label);
            button.setFont(buttonFont);
            button.setMargin(new Insets(0, 0, 0, 0));
            keyPadPanel.add(button);
        }
        //表示する
        frame.setVisible(true);
    }

}