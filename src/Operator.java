/**
 * 演算子を定数として定義しているクラスです
 * @author 山﨑優璃
 * @version 
 * @since 2026/06/14
 */
public enum Operator {
    //定数
    ADD("+"),SUB("-"),MUL("×"),DIV("÷");
    
    //フィールド変数
    private String operator;

    //コンストラクタ
    private Operator(String operator){
        this.operator = operator;
    }

    /**
     * 演算子を定数に変換するメソッドです
     * @param operator 入力演算子
     * @return null
     */
    public static Operator conversionOperator(String operator){
        for(Operator op : Operator.values()){
            if(op.getOperator().equals(operator)){
                return op;
            }
        }
        return null;
    }

    //getter
    public String getOperator(){
        return operator;
    }
}
