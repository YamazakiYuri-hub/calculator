
/**
 * ボタン操作に関連するクラスです
 * @author 山﨑優璃
 * @version 
 * @since 2026/07/23
 */
public class CalculatorContoroller {

    private CalculatorModel model = new CalculatorModel();
    private CalculatorFrame view;
    private StringBuilder displayBuilder = new StringBuilder();
    private String displayScreen;

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
        if(displayScreen != null && displayScreen.endsWith("0") && ch !=0){
            //0上書き
            model.appendDigit(ch);
            displayBuilder.deleteCharAt(displayBuilder.length()-1);
            displayScreen = displayBuilder.append(ch).toString();
            view.setDisplay(displayScreen);
            }else if(model.appendDigit(ch) == true){
            displayScreen = displayBuilder.append(ch).toString();
            view.setDisplay(displayScreen);
            }
        }

    /**
     * 小数点ボタンを押下した場合のメソッドです
     */
    public void onDot(){
        if(!model.getDisplayText().contains(".")){
            if(displayScreen != null && displayScreen.endsWith("0")){
                //一の位が０の場合の入力受付
                model.appendDot();
                displayScreen = displayBuilder.append(".").toString();
                view.setDisplay(displayScreen);
                }else if(displayScreen != null && model.getDisplayText().equals("0")){
                //初回小数点入力無視
                }else{
                //入力受付
                model.appendDot();
                displayScreen = displayBuilder.append(".").toString();
                view.setDisplay(displayScreen);
                }
            }
        }

    /**
     * 演算子ボタンを押下した場合のメソッドです
     * @param op 押下した演算子ボタン
     */
    public void onOperator(Operator op){
        switch (op) {
            case ADD:
                if(model.getDisplayText() != null && model.getDisplayText().equals("0")){
                    //初回入力無視
                }else if(model.getDisplayText() != null && displayScreen.endsWith("+")){
                    //連続入力無視
                }else if(model.getDisplayText() != null && displayScreen.equals("-")){
                    //負の符号のみの場合入力無視
                }else{
                    //入力受付
                    if(displayScreen.endsWith("-")||displayScreen.endsWith("×")||displayScreen.endsWith("÷")){
                        displayBuilder.deleteCharAt(displayBuilder.length()-1);
                    }
                    displayScreen = displayBuilder.append("+").toString();
                    view.setDisplay(displayScreen);
                    model.inputOperator(op);
                }
                break;
            case SUB:
                if(model.getDisplayText() != null && model.getDisplayText().equals("0")){
                    //初回の負の記号
                    displayScreen = displayBuilder.append("-").toString();
                    view.setDisplay(displayScreen);
                    model.inputOperator(op);
                }else if(model.getDisplayText() != null && displayScreen.endsWith("-")){
                    //連続入力無視
                }else{
                    //入力受付
                    if(displayScreen.endsWith("+")||displayScreen.endsWith("×")||displayScreen.endsWith("÷")){
                        displayBuilder.deleteCharAt(displayBuilder.length()-1);
                    }
                    displayScreen = displayBuilder.append("-").toString();
                    view.setDisplay(displayScreen);
                    model.inputOperator(op);
                }
                break;
            case MUL:
                if(model.getDisplayText() != null && model.getDisplayText().equals("0")){
                    //初回入力無視
                }else if(model.getDisplayText() != null && displayScreen.endsWith("×")){
                    //連続入力無視
                }else if(model.getDisplayText() != null && displayScreen.equals("-")){
                    //負の符号のみの場合入力無視
                }else{
                    //入力受付
                    if(displayScreen.endsWith("+")||displayScreen.endsWith("-")||displayScreen.endsWith("÷")){
                        displayBuilder.deleteCharAt(displayBuilder.length()-1);
                    }
                    displayScreen = displayBuilder.append("×").toString();
                    view.setDisplay(displayScreen);
                    model.inputOperator(op);
                }
                break;
            case DIV:
                if(model.getDisplayText() != null && model.getDisplayText().equals("0")){
                    //初回入力無視
                }else if(model.getDisplayText() != null && displayScreen.endsWith("÷")){
                    //連続入力無視
                }else if(model.getDisplayText() != null && displayScreen.equals("-")){
                    //負の符号のみの場合入力無視
                }else{
                    //入力受付
                    if(displayScreen.endsWith("+")||displayScreen.endsWith("-")||displayScreen.endsWith("×")){
                        displayBuilder.deleteCharAt(displayBuilder.length()-1);
                    }
                    displayScreen = displayBuilder.append("÷").toString();
                    view.setDisplay(displayScreen);
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
        if(displayScreen.contains("÷") && displayScreen.endsWith("0")){
            //0除算エラー表示
            model.equalsOp();
            displayBuilder.setLength(0);
            displayScreen = "Error";
            view.setDisplay(displayScreen);
        }else{
            //計算結果表示
            model.equalsOp();
            displayBuilder.setLength(0);
            displayScreen = displayBuilder.append(model.getDisplayText()).toString();
            view.setDisplay(displayScreen);
        }
    }

    /**
     * 「C」(全消し)ボタンを押下した場合のメソッドです
     */
    public void onClear(){
        model.clearAll();
        displayBuilder.setLength(0);
        displayScreen = null;
        view.setDisplay(model.getDisplayText());
    }


}