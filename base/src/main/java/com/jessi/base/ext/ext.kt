
package com.jessi.base.ext

import java.lang.reflect.ParameterizedType

/**
 * @author Created by CHEN on 2021/1/26
 * @email 188669@163.com
 * 全局扩展函数
 */

/**
 * 获取Class
 */
@Suppress("UNCHECKED_CAST")
fun <T> getClazz(obj: Any): T {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as T
}