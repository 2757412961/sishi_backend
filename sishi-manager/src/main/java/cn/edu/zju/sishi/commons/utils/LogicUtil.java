package cn.edu.zju.sishi.commons.utils;

public class LogicUtil {

    private static final String AND = "AND";
    private static final String OR = "OR";

    public static String getLogicByIsAdmins(Boolean isAdministrator) {
        if (isAdministrator) {
            return OR;
        }

        return AND;
    }

}
