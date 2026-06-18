
/**
 * ボタン操作に関連するクラスです
 * @author 山﨑優璃
 * @version 
 * @since 2026/06/14
 */
public class CalculatorContoroller {

    private CalculatorModel model = new CalculatorModel();
    private CalculatorFrame view;

    //コンストラクタ
    public CalculatorContoroller(){
    }
    
    //コンストラクタ
    public CalculatorContoroller(CalculatorFrame view){
        this.view = view;
    }

    /**
     * ボタンを押下した処理に関連するメソッドです
     * @param label 押下したボタンの種類
     */
    public void onButtonPressed(String label){
        Operator op = Operator.conversionOperator(label);
        if(label.matches("[0-9]")){ 
            onDigit(label.charAt(0));
        }else if(label.equals(".")){ 
            onDot();
        }else if(label.matches("[+\\-×÷]")){ 
            if(op != null){
                onOperator(op);
            }
        }else if(label.equals("=")){
            onEquals();
        }else if(label.equals("C")){
            onClear();
        }
    }

    /**
     * 数字ボタンを押下した場合のメソッドです
     * @param ch 押下したボタン
     */
    public void onDigit(char ch){
        model.appendDigit(ch);
        view.setDisplay(model.getDisplayText());
        }

    /**
     * 小数点ボタンを押下した場合のメソッドです
     */
    public void onDot(){
        model.appendDot();
        view.setDisplay(model.getDisplayText());
        }

    /**
     * 演算子ボタンを押下した場合のメソッドです
     * @param op 押下した演算子ボタン
     */
    public void onOperator(Operator op){
        switch (op) {
            case ADD:
                if(model.getDisplayText() != null && model.getDisplayText().equals("0")){
                    //入力無視
                }else{
                    //入力受付
                    view.setDisplay("+");
                    model.inputOperator(op);
                    
                }
                break;
            case SUB:
                //処理
                view.setDisplay("-");
                model.inputOperator(op);
                break;
            case MUL:
                if(model.getDisplayText() != null && model.getDisplayText().equals("0")){
                    //入力無視
                }else{
                    //入力受付
                    view.setDisplay("×");
                    model.inputOperator(op);
                }
                break;
            case DIV:
                if(model.getDisplayText() != null && model.getDisplayText().equals("0")){
                    //入力無視
                }else{
                    //入力受付
                    view.setDisplay("÷");
                    model.inputOperator(op);
                }
                break;
            default:
                break;
        }
        }

    /**
     * 「=」ボタンを押下した場合のメソッドです
     */
    public void onEquals(){
        model.equalsOp();
        view.setDisplay(model.getDisplayText());
        }

    /**
     * 「C」(全消し)ボタンを押下した場合のメソッドです
     */
    public void onClear(){
        model.clearAll();
        view.setDisplay(model.getDisplayText());
    }

}