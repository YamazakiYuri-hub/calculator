
/**
 * ボタン操作に関連するクラスです
 * @author 山﨑優璃
 * @version 
 * @since 2026/08/13
 */
public class CalculatorController {

    private CalculatorModel model = new CalculatorModel();
    private CalculatorFrame view;

    //コンストラクタ
    public CalculatorController(CalculatorFrame view){
        this.view = view;
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
        model.inputOperator(op);
        view.setDisplay(model.getDisplayText());
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