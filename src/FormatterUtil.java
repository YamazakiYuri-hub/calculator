import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 計算結果の表示整形に関連するクラスです
 * @author 山﨑優璃
 * @version 0
 * @since 2026/09/08
 */
public class FormatterUtil {
    
    /**
     * 指数表記にするメソッドです。
     * @param v 計算結果
     * @param maxDigits 最大桁数
     * @return 指数
     */
    public String formatForDisplay(BigDecimal v, int maxDigits){
        BigDecimal trimmed = v.stripTrailingZeros();
        if(trimmed.precision() > maxDigits || v.toPlainString().replaceAll("[^0-9]", "").length() > 8){
            DecimalFormat index = new DecimalFormat("0." + "0".repeat(maxDigits -1) + "E0");
            index.setMinimumFractionDigits(0);
            index.setMaximumFractionDigits(maxDigits-1);
            String formatted = index.format(trimmed).replace("E", "e");
            return formatted;
        }
        return v.toPlainString();
    }
}
