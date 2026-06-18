import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * 画面UIのクラスです
 * @author 山﨑優璃
 * @version 
 * @since 2026/06/14
 */
public class CalculatorFrame {

    private JLabel displayLabel = new JLabel();
    private JPanel keyPadPanel = new JPanel();
    private CalculatorContoroller contoroller = new CalculatorContoroller();
    private List<JButton> buttonList = new ArrayList<>();

    /**
     * 表示更新APIのメソッドです
     * @param text 画面に表示する数字
     */
    public void setDisplay(String text){
        displayLabel.setText(text);
    }

    /**
     * ボタンなどの画面表示に関連するメソッドです
     */
    public void bindController(){
        //ウインドウの生成
        JFrame frame = new JFrame();
        frame.setTitle("Calculator");
        frame.setSize(400,500);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
            buttonList.add(button);
            button.addActionListener(e -> {
                contoroller.onButtonPressed(button.getText());
            });
        }
        //表示する
        frame.setVisible(true);
    }

    //連携クラスの受け取り
    public void setCalculatorFrame(CalculatorContoroller contoroller){
        this.contoroller = contoroller;
    }

    //getter
    //表示エリア
    public JLabel getDisplayLabel(){
        return displayLabel;
    }
    //ボタンの文字（画面情報まとめて渡す用）
    public List<JButton> getButttonList(){
        return buttonList;
    }

}