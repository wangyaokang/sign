/**
 * 
 */
package com.wyk.framework.jdbc.dialect;

/**
 * 针对查询的数据库Dialect
 * 
 * @author bocar
 * 
 */
public abstract class Dialect {

	public boolean supportsLimit() {
		return false;
	}

	public boolean supportsLimitOffset() {
		return supportsLimit();
	}

	/**
	 * 将sql变成分页sql语句,直接使用offset,limit的值作为占位符.</br> 
	 * 源代码为: getLimitString(sql,offset,String.valueOf(offset),limit,String.valueOf(limit))
	 */
	public String getLimitString(String sql, int offset, int limit) {
		return getLimitString(sql, offset, Integer.toString(offset), limit, Integer.toString(limit));
	}

	/**
	 * 将sql变成分页sql语句,提供将offset及limit使用占位符(placeholder)替换.
	 * 
	 * <pre>
	 * 如mysql
	 * dialect.getLimitString("select * from user", 12, ":offset",0,":limit") 将返回
	 * select * from user limit :offset,:limit
	 * </pre>
	 * 
	 * @return 包含占位符的分页sql
	 */
	public abstract String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder);

	/**
	 * 将sql转换为总记录数SQL
	 * 
	 * @param sql 原始SQL语句
	 * @return 总记录数SQL
	 */
	public String getCountString(String sql) {
		return "select count(1) " + sql.substring(sql.toUpperCase().indexOf("FROM"));
	}
}
