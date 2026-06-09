package com.elderly.common.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.regex.Pattern;

/**
 * SQL注入检测拦截器
 * 用于在SQL执行前检测是否存在SQL注入攻击
 */
@Intercepts({
    @Signature(type = Executor.class, method = "update", 
            args = {MappedStatement.class, Object.class}),
    @Signature(type = Executor.class, method = "query", 
            args = {MappedStatement.class, Object.class, 
                    org.apache.ibatis.session.RowBounds.class, 
                    org.apache.ibatis.session.ResultHandler.class})
})
public class SqlInjectInterceptor implements Interceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(SqlInjectInterceptor.class);
    
    // SQL注入检测正则 - 检测常见的SQL注入关键词
    private static final Pattern SQL_INJECT_PATTERN = Pattern.compile(
        "(?i)(union\\s+select|exec\\s+|execute\\s+|insert\\s+into|delete\\s+from|" +
        "update\\s+.*set|drop\\s+table|alter\\s+table|truncate\\s+table|" +
        "create\\s+table|rename\\s+table|desc\\s+|--\\s*|;\\s*|'\\s*|\\|\\s*|" +
        "\\*\\s*|\\?\\s*|\\[\\s*|\\]\\s*|\\{\\s*|\\}\\s*|<script>|<iframe>|" +
        "or\\s+\\d+=\\d+|and\\s+\\d+=\\d+|sleep\\s*\\(|benchmark\\s*\\()"
    );

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        
        // 检查参数中是否包含SQL注入特征
        if (parameter != null) {
            String paramStr = parameter.toString();
            if (SQL_INJECT_PATTERN.matcher(paramStr).find()) {
                logger.warn("检测到SQL注入攻击尝试: method={}, parameter={}", 
                    ms.getId(), paramStr);
                throw new SecurityException("请求参数包含非法字符，请检查输入内容");
            }
        }
        
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 可以通过配置文件传入额外的参数
    }
}