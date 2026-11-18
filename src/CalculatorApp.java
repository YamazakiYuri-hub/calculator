public class CalculatorApp {
    
    /**
     * メインクラスです
     * @author 山﨑優璃
     * @version 
     * @since 2026/08/13
     */
    public static void main(String[] args)  {
        CalculatorFrame view = new CalculatorFrame();
        CalculatorController contoroller = new CalculatorController(view);
        view.setCalculatorFrame(contoroller);
        view.bindController();
        
    }
}
