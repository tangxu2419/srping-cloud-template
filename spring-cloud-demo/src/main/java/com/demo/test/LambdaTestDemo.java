package com.demo.test;

import org.apache.commons.lang3.StringUtils;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2019/3/510:54
 */
public class LambdaTestDemo {

    private static int addByInt(int var1, int var2) {
        return var1 + var2;
    }

    private static boolean compareByString(String str1, String str2) {
        return StringUtils.compare(str1, str2) == 0;
    }

    public static void main(String[] args) {
        int var1 = 2;
        int var2 = 3;
        Function function = ((param1, param2) -> param1 + param2);
        System.out.println(function.calculation(var1, var2));
        System.out.println(functionCalculation((param1, param2) -> param1 + param2, var1, var2));
        System.out.println(functionCalculation(LambdaTestDemo::addByInt, var1, var2));
        String str1 = "2";
        String str2 = "3";
        System.out.println(functionCompare(LambdaTestDemo::compareByString, str1, str2));
        MyFunction<String> myFunction = LambdaTestDemo::compareByString;
        System.out.println(myFunction.compare(str1, str2));
        System.out.println(myFunction.calculation(var1, var2));
    }

    private static int functionCalculation(Function function, int param1, int param2) {
        return function.calculation(param1, param2);
    }

    static <T> boolean functionCompare(MyFunction<T> function, T param1, T param2) {
        return function.compare(param1, param2);
    }

}

/**
 * 定义一个方法接口
 */
@FunctionalInterface
interface Function {

    /**
     * 计算参数
     *
     * @param param1 参数1
     * @param param2 参数2
     * @return R 结果
     */
    int calculation(int param1, int param2);

}

interface MyFunction<T> extends Function {

    /**
     * 设置继承接口的默认方法
     *
     * @param param1 参数1
     * @param param2 参数2
     * @return R 结果
     */
    @Override
    default int calculation(int param1, int param2) {
        return param1 + param2;
    }

    /**
     * 比较
     *
     * @param t1 参数1
     * @param t2 参数2
     * @return 比较结果
     */
    boolean compare(T t1, T t2);
}
