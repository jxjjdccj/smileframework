package org.smileframework.common.jdk;

import java.text.DecimalFormat;

/**
 * 大金额日志处理工具类. 解决double类型过大时、以科学计数法显示问题
 * 
 * @author cz
 */
public class DoubleUtil {
	/*默认格式化*/
    public static final String DEFAULT_FORMAT = "0.00";

    /*默认价格*/
    public static final String DEFAULT_PRICE ="0.000";

     /*默认比例显示*/
    public final static String DEFAULT_RATE_FORMAT = "##0.00";

    /*默认金钱显示*/
    public final static String DEFAULT_CURRENCY_FORMAT = "#,##0.00";//"#,###.00" -->表示整数每隔3位加一个"，";输出的数值可以保留两位小数，不足布0，小数部分多于两位的四舍五入.

    public final static String DEFAULT_PRICE_FORMAT = "#,##0.000";//"#,###.00" -->表示整数每隔3位加一个"，";输出的数值可以保留两位小数，不足布0，小数部分多于两位的四舍五入.

    public final static String DEFAULT_PERCENT_FORMAT = "#0.00%"; //##.00%   百分比格式，后面不足2位的用0补齐,小数点多于2位，会四舍五入
    
    public final static String SIMPLE_PERCENT_FORMAT_ONE = "#,##0";
     
    /**
     * 格式化价格，价格保留三位数
     * @param value
     * @return
     */
    public static String formatPrice(Double value){
      if (ObjectUtil.isEmpty(value)){
			return DEFAULT_PRICE;//如果值为空，返回默认的金额 0.00
		}
		return format(value, DEFAULT_PRICE_FORMAT);
    }

	/**
	 * double字符串进行格式化
	 * 
	 * @param value
	 *            --值
	 * @param format
	 *            --格式样式
	 * @return
	 */
	public static String format(Double value, String format) {
		if (value == null)
			return null;
		// 判断传入参数
		// 格式类型为空,使用默认格式
		if (format == null) {
			format = DEFAULT_FORMAT;//0.00
		}
		DecimalFormat df = new DecimalFormat(format);
		//
		return df.format(value);
	}

	/**
	 * 根据格式进行对Double格式进行转化
	 * 
	 * @param value
	 *            --值
	 * @return
	 */
	public static String format(Double value) {
		return format(value, null);
	}
	/**
	 * 根据格式进行对Double格式进行转化
	 * 
	 * @param value
	 *            --值
	 * @return eg:1,000.00
	 */
	public static String formatDefaultCurrency(Double value) {
		if (ObjectUtil.isEmpty(value)){
			return DEFAULT_FORMAT;//如果值为空，返回默认的金额 0.00
		}
		return format(value, DEFAULT_CURRENCY_FORMAT);
	}
	/**@return 00.10
	 * */
	public static String formatDefaultRate(Double value) {
		return format(value, DEFAULT_RATE_FORMAT);
	}
	

	/* double增加前缀处理,正数加+,负数则为+ */
	public static String prefix(Double value, String format) {
		if (value > 0) {
			return "+".concat(format(value, format));
		}
		return format(value, format);
	}


     /** @return  0.2225D ==>22.25% (相当于当前 数*100 然后加 %)
     *   如果传入值为空 ,返回 0.00
    */
    /*public static String formatPercentage(Double rate) {
    
    	
    	return format(rate, DEFAULT_PERCENT_FORMAT);
    }*/


    /**
     * 股票返回2位小数，基金返回3位小数
     * @param stockCode
     * @param amout
     * @return
     */
    public static String formatPrice(String stockCode, Double amout) {
    	if (stockCode.startsWith("15") || stockCode.startsWith("16") || stockCode.startsWith("51")) {
            return formatPrice(amout);
        }
        return formatDefaultCurrency(amout);
    }

}