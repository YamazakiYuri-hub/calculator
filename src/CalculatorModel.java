import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/*
 * 計算機能に関連するクラスです
 * @author 山﨑優璃
 * @version 
 * @since 2026/08/13
 */
public class CalculatorModel {

    /**
     * 入力済みの被演算子、計算結果
     */
    private BigDecimal leftOperand = BigDecimal.ZERO;

    /**
     * 新たに入力する被演算子
     */
    private BigDecimal rightOperand; 

    /**
     * 現在入力中の数値
     */
    private StringBuilder currentInput = new StringBuilder();

    /**
     * 保留中の演算子
     */
    private Operator pendingOp = null;

    /**
     * 状態管理
     */
    private InputState state = InputState.READY;

    /**
     * 最大桁数
     */
    private int maxDigits = 8;

    /**
     * エラーハンドリング
     */
    private ErrorHandler errorHandler = new ErrorHandler();

    /**
     * 指数表記
     */
    private FormatterUtil formatNumber = new FormatterUtil();

    /**
     * 桁制限、数値入力に関するメソッドです
     * @param ch 入力数値
     * @return trueは入力許容、falseは入力拒否
     */
    public boolean appendDigit(char ch){
        //エラー
        if(state == InputState.ERROR){
            return false;
        }else if(state == InputState.READY){
            //初期入力
            if(currentInput.length() == 0){
                currentInput.setLength(0);
                currentInput.append(ch);
                state = InputState.INPUT_NUMBER;
            //計算結果が出た後に続けて数値入力
            }else if(currentInput.length() != 0){
                currentInput.setLength(0);
                currentInput.append(ch);
                state = InputState.INPUT_NUMBER;
            }
            return true;
        }else if(state == InputState.INPUT_NUMBER){
            //00入力無効
            if(currentInput.toString().equals("0") && ch =='0'){
                return false;
            }
            //0を数値に上書き
            if(currentInput.toString().equals("0") && ch !='0'){
                currentInput.setLength(0);
                currentInput.append(ch).toString();
                return true;
            }
            //入力,小数点込みで8桁まで
            if(currentInput.toString().replaceAll("[.\\-]", "").length() < maxDigits && maxDigits <= 8){
                currentInput.append(ch).toString();
                return true;
            }
        }else if(state == InputState.INPUT_OPERATOR){
            //次の数値入力開始
            currentInput.append(ch).toString();
            state = InputState.INPUT_NUMBER;
            return true;
        }
        return false;
    }

    /**
     * 小数点の入力制限に関するメソッドです
     * @return trueは入力許容、falseは入力拒否
     */
    public boolean appendDot(){
        //エラー
        if(state == InputState.ERROR){
            return false;
        }
        //初期状態の小数点入力
        if(state == InputState.READY){
            currentInput.append("0").toString();
            currentInput.append(".").toString();
            state = InputState.INPUT_NUMBER;
            return true;
        }
        //小数点を1つだけ許容
        if(!currentInput.toString().contains(".") && state == InputState.INPUT_NUMBER){
            currentInput.append(".").toString();
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
        //エラー
        if(state == InputState.ERROR){
            return;
        }
        switch (op) {
            case ADD:
                if(currentInput.length() > 0 && pendingOp == null && (state == InputState.INPUT_NUMBER || state == InputState.READY)){
                    //初めの数字確定、「+」入力
                    leftOperand = new BigDecimal(currentInput.toString());
                    currentInput.setLength(0);
                    pendingOp = Operator.ADD;
                    state = InputState.INPUT_OPERATOR;
                }else if(pendingOp != null && state == InputState.INPUT_OPERATOR){
                    //上書き
                    pendingOp = Operator.ADD;
                    currentInput.setLength(0);
                }else if(pendingOp != null && state == InputState.INPUT_NUMBER){
                    //2つ目以降の数字確定、「+」入力
                    rightOperand = new BigDecimal(currentInput.toString());
                    apply(pendingOp,leftOperand,rightOperand);
                    pendingOp = Operator.ADD;
                    currentInput.setLength(0);
                    state = InputState.INPUT_OPERATOR;
                }
                break;
            case SUB:
                if(leftOperand.compareTo(BigDecimal.ZERO) == 0 && state == InputState.READY){
                    //負の符号許容
                    currentInput.append('-').toString();
                    state = InputState.INPUT_NUMBER;
                }else if(currentInput.length() > 0 && pendingOp == null && (state == InputState.INPUT_NUMBER || state == InputState.READY)){
                    //初めの数字確定、「-」入力
                    leftOperand = new BigDecimal(currentInput.toString());
                    currentInput.setLength(0);
                    pendingOp = Operator.SUB;
                    state = InputState.INPUT_OPERATOR;
                }else if(pendingOp != null && state == InputState.INPUT_OPERATOR){
                    //上書き
                    pendingOp = Operator.SUB;
                    currentInput.setLength(0);
                }else if(pendingOp != null && state == InputState.INPUT_NUMBER){
                    //2つ目以降の数字確定、「-」入力
                    rightOperand = new BigDecimal(currentInput.toString());
                    apply(pendingOp,leftOperand,rightOperand);
                    pendingOp = Operator.SUB;
                    currentInput.setLength(0);
                    state = InputState.INPUT_OPERATOR;
                }
                break;
            case MUL:
                if(currentInput.length() > 0 && pendingOp == null && (state == InputState.INPUT_NUMBER || state == InputState.READY)){
                    //初めの数字確定、「×」入力
                    leftOperand = new BigDecimal(currentInput.toString());
                    currentInput.setLength(0);
                    pendingOp = Operator.MUL;
                    state = InputState.INPUT_OPERATOR;
                }else if(pendingOp != null && state == InputState.INPUT_OPERATOR){
                    //上書き
                    pendingOp = Operator.MUL;
                    currentInput.setLength(0);
                }else if(pendingOp != null && state == InputState.INPUT_NUMBER){
                    //2つ目以降の数字確定、「×」入力
                    rightOperand = new BigDecimal(currentInput.toString());
                    apply(pendingOp,leftOperand,rightOperand);
                    pendingOp = Operator.MUL;
                    currentInput.setLength(0);
                    state = InputState.INPUT_OPERATOR;
                }
                break;
            case DIV:
                if(currentInput.length() > 0 && pendingOp == null && (state == InputState.INPUT_NUMBER || state == InputState.READY)){
                    //初めの数字確定、「÷」入力
                    leftOperand = new BigDecimal(currentInput.toString());
                    currentInput.setLength(0);
                    pendingOp = Operator.DIV;
                    state = InputState.INPUT_OPERATOR;
                }else if(pendingOp != null && state == InputState.INPUT_OPERATOR){
                    //上書き
                    pendingOp = Operator.DIV;
                    currentInput.setLength(0);
                }else if(pendingOp != null && state == InputState.INPUT_NUMBER){
                    //2つ目以降の数字確定、「÷」入力
                    rightOperand = new BigDecimal(currentInput.toString());
                    apply(pendingOp,leftOperand,rightOperand);
                    //エラーが起きたら中断
                    if(state == InputState.ERROR){
                        return;
                    }
                    pendingOp = Operator.DIV;
                    currentInput.setLength(0);
                    state = InputState.INPUT_OPERATOR;
                }
                break;
        }
    }

    /**
     * 計算機能のメソッドです
     */
    public void apply(Operator pendingOp,BigDecimal leftOperand,BigDecimal rightOperand){
        switch (pendingOp) {
            case ADD:
                this.leftOperand = leftOperand.add(rightOperand,MathContext.DECIMAL64);
                break;
            case SUB:
                this.leftOperand = leftOperand.subtract(rightOperand,MathContext.DECIMAL64);
                break;
            case MUL:
                this.leftOperand = leftOperand.multiply(rightOperand,MathContext.DECIMAL64);
                break;
            case DIV:
                try{
                    this.leftOperand = leftOperand.divide(rightOperand,7,RoundingMode.HALF_UP).stripTrailingZeros();
                }catch(ArithmeticException e){
                    errorHandler.handle(e);
                    state = InputState.ERROR;
                }
                break;
        }
    }

    /**
     * 「=」ボタン押下で計算結果を確定させるメソッドです
     */
    public void equalsOp(){
        //エラー
        if(state == InputState.ERROR){
            return;
        }
        if(pendingOp != null && state == InputState.INPUT_NUMBER){
            //計算処理
            rightOperand = new BigDecimal(currentInput.toString());
            apply(pendingOp,leftOperand,rightOperand);
            //エラーが起きたら中断
            if(state == InputState.ERROR){
                return;
            }
            pendingOp = null; 
            currentInput.setLength(0);
            currentInput.append(leftOperand.toString());
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
        leftOperand = BigDecimal.ZERO;
        pendingOp = null;
        state = InputState.READY;
    }

    /**
     * 画面表示に関係するメソッドです
     * @return 表示数字、演算子
     */
    public String getDisplayText(){
        //エラー
        if(state == InputState.ERROR){
            return "Error";
        }
        StringBuilder displayText = new StringBuilder();
        displayText.append(formatNumber.formatForDisplay(leftOperand,maxDigits));
        //演算子未入力時の数字表示
        if(pendingOp == null && currentInput.length() > 0 && state == InputState.INPUT_NUMBER){
            return currentInput.toString();
        }
        //演算子入力
        if(pendingOp != null){
            switch (pendingOp) {
                case ADD:
                    displayText.append('+');
                    break;
                case SUB:
                    displayText.append('-');
                    break;
                case MUL:
                    displayText.append('×');
                    break;
                case DIV:
                    displayText.append('÷');
                    break;
            }
            //演算子入力後の数字表示
            if(currentInput.length() > 0){
                displayText.append(currentInput.toString());
            }
        }
        return displayText.toString();
    }

}   
