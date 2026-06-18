public class CalculatorApp {
    
    /**
     * メインクラスです
     * @author 山﨑優璃
     * @version 
     * @since 2026/06/14
     */
    public static void main(String[] args)  {
        //仮
        CalculatorFrame view = new CalculatorFrame();
        CalculatorContoroller contoroller = new CalculatorContoroller(view);
        view.setCalculatorFrame(contoroller);
        view.bindController();
        
    }
}
