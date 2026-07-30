import java.math.BigDecimal;

/**
 * 計算結果の表示整形に関連するクラスです
 * @author 山﨑優璃
 * @version 0
 * @since 2026/07/28
 */
public class FormatterUtil {
    
    /**
     * 指数表記にするメソッドです。
     * @param v 計算結果
     * @param maxDigits 最大桁数
     * @return 指数
     */
    public String formatForDisplay(BigDecimal v, int maxDigits){
        return v.toPlainString();
    }
}
