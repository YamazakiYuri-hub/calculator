import java.math.BigDecimal;
//import java.math.MathContext;
import java.math.MathContext;

/**
 * 計算機能に関連するクラスです
 * @author 山﨑優璃
 * @version 
 * @since 2026/06/16
 */
public class CalculatorModel {

    private BigDecimal leftOperand = BigDecimal.ZERO;
    private StringBuilder currentInput = new StringBuilder();
    private String DisplayText = "0";
    private Operator pendingOp = null;
    private InputState state = InputState.READY;
    private int maxDigits = 8;

    /**
     * 桁制限に関するメソッドです
     * @param ch 入力数値
     * @return trueは入力許容、falseは入力拒否
     */
    public boolean appendDigit(char ch){
        if(state == InputState.READY){
            //初期入力
            if(getDisplayText().equals("0")){
                DisplayText = currentInput.append(ch).toString();
                state = InputState.INPUT_NUMBER;
            }else if(getDisplayText() != "0"){
                System.out.println("確認用："+getDisplayText());
                DisplayText = currentInput.append(ch).toString();
                state = InputState.INPUT_NUMBER;
            }
            return true;
        }else if(state == InputState.INPUT_NUMBER){
            //00入力無効
            if(getDisplayText().equals("0") && ch =='0'){
                return false;
            }
            //冒頭の0を置換
            if(getDisplayText().equals("0") && ch !='0'){
                return true;
            }
            //入力,小数点込みで8桁まで
            if(getDisplayText().replaceAll("[.-]", "").length() < maxDigits && maxDigits <= 8){
                DisplayText = currentInput.append(ch).toString();
                return true;
            }
        }else if(state == InputState.INPUT_OPERATOR){
            //00入力無効
            if(getDisplayText().equals("0")){
                return false;
            }
            //次の数値入力開始,String変換
            DisplayText = currentInput.append(ch).toString();
            state = InputState.INPUT_NUMBER;
            return true;
        }
        else if(state == InputState.ERROR){
            return false;
        }
        return false;
    }

    /**
     * 小数点の入力制限に関するメソッドです
     * @return trueは入力許容、falseは入力拒否
     */
    public boolean appendDot(){
        //初期状態の小数点入力
        if(state == InputState.READY){
            DisplayText = currentInput.append("0").toString();
            DisplayText = currentInput.append(".").toString();
            state = InputState.INPUT_NUMBER;
            return true;
        }
        //小数点を1つだけ許容
        if(!DisplayText.contains(".") && state == InputState.INPUT_NUMBER){
            DisplayText = currentInput.append(".").toString();
            return true;
        }
        return false;
    }

    /**
     * 入力演算子(+,-,×,÷)ごとの処理に関するメソッドです
     * 「-」は負の符号になる場合もあります
     * @param op 入力演算子
     */
    public void inputOperator(Operator op){
        switch (op) {
            case ADD:
                if(pendingOp == null && state == InputState.INPUT_NUMBER || state == InputState.READY){
                    //初めの数字確定、「+」入力
                    leftOperand = new BigDecimal(getDisplayText());
                    pendingOp = Operator.ADD;
                    currentInput.setLength(0);
                    state = InputState.INPUT_OPERATOR;
                }else if(pendingOp != null && state == InputState.INPUT_OPERATOR){
                    //上書き
                    pendingOp = Operator.ADD;
                    currentInput.setLength(0);
                }else if(pendingOp != null && state == InputState.INPUT_NUMBER){
                    //2つ目以降の数字確定、「+」入力
                    apply();
                    pendingOp = Operator.ADD;
                    currentInput.setLength(0);
                    state = InputState.INPUT_OPERATOR;
                }
                break;
            case SUB:
                if(getDisplayText().equals("0") && state == InputState.READY){
                    //負の符号許容
                    DisplayText = currentInput.append('-').toString();
                    state = InputState.INPUT_NUMBER;
                }else if(pendingOp == null && state == InputState.INPUT_NUMBER || state == InputState.READY){
                    //初めの数字確定、「-」入力
                    leftOperand = new BigDecimal(getDisplayText());
                    currentInput.setLength(0);
                    pendingOp = Operator.SUB;
                    state = InputState.INPUT_OPERATOR;
                }else if(pendingOp != null && state == InputState.INPUT_OPERATOR){
                    //上書き
                    pendingOp = Operator.SUB;
                    currentInput.setLength(0);
                }else if(pendingOp != null && state == InputState.INPUT_NUMBER){
                    //2つ目以降の数字確定、「-」入力
                    apply();
                    pendingOp = Operator.SUB;
                    currentInput.setLength(0);
                    state = InputState.INPUT_OPERATOR;
                }
                break;
            case MUL:
                if(pendingOp == null && state == InputState.INPUT_NUMBER || state == InputState.READY){
                    //初めの数字確定、「×」入力
                    leftOperand = new BigDecimal(getDisplayText());
                    currentInput.setLength(0);
                    pendingOp = Operator.MUL;
                    state = InputState.INPUT_OPERATOR;
                }else if(pendingOp != null && state == InputState.INPUT_OPERATOR){
                    //上書き
                    pendingOp = Operator.MUL;
                    currentInput.setLength(0);
                }else if(pendingOp != null && state == InputState.INPUT_NUMBER){
                    //2つ目以降の数字確定、「×」入力
                    apply();
                    pendingOp = Operator.MUL;
                    currentInput.setLength(0);
                    state = InputState.INPUT_OPERATOR;
                }
                break;
            case DIV:
                if(pendingOp == null && state == InputState.INPUT_NUMBER || state == InputState.READY){
                    //初めの数字確定、「÷」入力
                    leftOperand = new BigDecimal(getDisplayText());
                    currentInput.setLength(0);
                    pendingOp = Operator.DIV;
                    state = InputState.INPUT_OPERATOR;
                }else if(pendingOp != null && state == InputState.INPUT_OPERATOR){
                    //上書き
                    pendingOp = Operator.DIV;
                    currentInput.setLength(0);
                }else if(pendingOp != null && state == InputState.INPUT_NUMBER){
                    //2つ目以降の数字確定、「÷」入力
                    apply();
                    pendingOp = Operator.DIV;
                    currentInput.setLength(0);
                    state = InputState.INPUT_OPERATOR;
                }
                break;
            default:
                break;
        }
    }

    /**
     * 計算機能のメソッドです
     */
    public void apply(){
        switch (pendingOp) {
            case ADD:
                BigDecimal RightOperand = new BigDecimal(getDisplayText());
                leftOperand = leftOperand.add(RightOperand,MathContext.DECIMAL64);
                break;
            case SUB:
                RightOperand = new BigDecimal(getDisplayText());
                leftOperand = leftOperand.subtract(RightOperand,MathContext.DECIMAL64);
                break;
            case MUL:
                RightOperand = new BigDecimal(getDisplayText());
                leftOperand = leftOperand.multiply(RightOperand,MathContext.DECIMAL64);
                break;
            case DIV:
                //割り算
                break;
            default:
                break;
        }
    }

    /**
     * 「=」ボタン押下で計算結果を確定させるメソッドです
     */
    public void equalsOp(){
        if(pendingOp != null && state == InputState.INPUT_NUMBER){
            //計算処理
            apply();
            DisplayText = leftOperand.toString();
            pendingOp = null;
            state = InputState.READY;
        }
        if(pendingOp == null || state == InputState.INPUT_OPERATOR){
            //演算子未入力、演算子直後
        }
    }

    /**
     * 「C」ボタン押下で全消しするメソッドです
     */
    public void clearAll(){
        currentInput.setLength(0);
        DisplayText = "0";
        leftOperand = BigDecimal.ZERO;
        pendingOp = null;
        state = InputState.READY;
    }

    //getter
    public String getDisplayText(){
        return DisplayText;
    }
}   
