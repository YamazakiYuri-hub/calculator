import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 計算結果の表示整形に関連するクラスです
 * @author 山﨑優璃
 * @version 0
 * @since 2026/08/13
 */
public class FormatterUtil {
    
    /**
     * 指数表記にするメソッドです。
     * @param v 計算結果
     * @param maxDigits 最大桁数
     * @return 指数
     */
    public String formatForDisplay(BigDecimal v, int maxDigits){
        if(v.toPlainString().length() > maxDigits){
            DecimalFormat index = new DecimalFormat("0.0000000E0");
            return index.format(v).replace("E", "e");
        }
        return v.toPlainString();
    }
}
