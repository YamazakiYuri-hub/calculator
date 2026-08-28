/**
 * エラー関連のクラスです
 * @author 山﨑優璃
 * @version 
 * @since 2026/08/13
 */
public class ErrorHandler {
    
    /**
     * エラーハンドリングです
     * @param e
     */
    public void handle(Exception e){
        System.err.println("error:0除算不可");
    }
}
