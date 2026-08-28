import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

/**
 * 画面UIのクラスです
 * @author 山﨑優璃
 * @version 
 * @since 2026/08/13
 */
public class CalculatorFrame {

    private JLabel displayLabel = new JLabel();
    private JPanel keyPadPanel = new JPanel();
    private CalculatorContoroller contoroller;

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
            //ボタン押下の分岐
            button.addActionListener(e -> {
                Operator op = Operator.conversionOperator(label);
                if(label.matches("[0-9]")){ 
                    contoroller.onDigit(label.charAt(0));
                }else if(label.equals(".")){ 
                    contoroller.onDot();
                }else if(label.matches("[+\\-×÷]")){ 
                    if(op != null){
                    contoroller.onOperator(op);
                    }
                }else if(label.equals("=")){
                    contoroller.onEquals();
                }else if(label.equals("C")){
                contoroller.onClear();
                }
            });
        }
        //表示する
        frame.setVisible(true);
    }

    //連携クラスの受け取り
    public void setCalculatorFrame(CalculatorContoroller contoroller){
        this.contoroller = contoroller;
    }

}