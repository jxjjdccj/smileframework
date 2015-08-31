/**   
 *  Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
* @Title: MoneyUtil.java 
* @Package ret.common.util 
* @author ret
* @date 2015-3-4 下午3:11:36 
* @version V1.0   
*/
package org.smileframework.common.jdk;

import java.math.BigDecimal;

/** 
 * @ClassName: MoneyUtil 
 * @Description: 价格，数量，货币相关转换 
 * @author FCJ 
 * @date 2015-3-4 下午3:11:36 
 * @version 1.0 
 */
public class MoneyUtil {
	/**
	 * 金额从分转成元
	 * @return
	 */
	public static String fenToYuan(String fen) {
		return String.valueOf(new BigDecimal(fen).divide(new BigDecimal(100)));
	}
	
	/**
	 * 金额从元转成分
	 * @return
	 */
	public static String yuanToFen(String yuan){
		BigDecimal bd = new BigDecimal(yuan);
		return bd.multiply(new BigDecimal(100)).longValue()+"";
	}
	
	/**
	 * 金额转换成12位
	 * @return
	 */
	public static String toCPFormat(String fen){
		String zeroStr = "";
		if (fen != null) {
			if (fen.length() < 12) {				
				for (int i = 0; i < (12 - fen.length()); i++) {
					zeroStr = "0" + zeroStr; 
				}
			}				
		} else {
			return null;
		}
		return zeroStr + fen;
	}
	
	public static String toNormalFenFormat(String fen){
		return String.valueOf(new BigDecimal(fen));
	}
}
