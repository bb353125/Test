package com.keeko.common.aop;

import com.keeko.master.entity.User;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpelParser {

	private static ExpressionParser parser = new SpelExpressionParser();
	
	/**
	 * 
	 * @param key el表达式字符串，占位符以#开头
	 * @param paramsNames 形参名称，可以理解为占位符名称
	 * @param args 参数值,可以理解为占位符真实的值
	 * @return 返回el表达式经过参数替换后的字符串值
	 */
	public static Object getUser(String key,String[] paramsNames,Object[] args){
		Expression exp = parser.parseExpression(key); //将key字符串解析为el表达式
		EvaluationContext context = new StandardEvaluationContext();//初始化赋值上下文
		if(args.length <= 0){
			return null;
		}
		//将形参和形参值以配对的方式配置到赋值上下文中
		for(int i = 0;i < args.length;i++){
			context.setVariable(paramsNames[i], args[i]);
		}
		//根据赋值上下文运算el表达式
		return exp.getValue(context,Object.class);
	}
}
