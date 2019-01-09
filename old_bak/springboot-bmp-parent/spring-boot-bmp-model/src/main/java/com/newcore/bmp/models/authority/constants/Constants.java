package com.newcore.bmp.models.authority.constants;

public final class Constants {

    private Constants() {

    }

    /* 有效 */
    public static final String AVALIABLE_YES = "Y";
    /* 无效 */
    public static final String AVALIABLE_NO = "N";
    /* session_key */
    public static final String SESSION_MODEL_KEY = "_SESSIONMODEL";
    /* session_clerkNo_key */
    public static final String SESSION_CLERKNO_KEY = "web.auth.token.session.username";
    /* 跳转页面功能，数据加密秘钥 */
    public static final String SESSION_SECRET_KEY = "_SECRET_KEY";
    /* 资源模块类型-菜单模块 */
    public static final String RESOURCE_MOD_TYPE_MENU_MODULE = "M";
    /* 资源模块类型-单独显示模块 */
    public static final String RESOURCE_MOD_TYPE_SEPARATE_DISPLAY_MODULE = "S";
    /* 资源模块类型-工具模块 */
    public static final String RESOURCE_MOD_TYPE_TOOLS_MODULE = "T";

    /* 资源类型-系统 */
    public static final String RESOURCE_TYPE_SYSTEM = "S";
    /* 资源类型-模块 */
    public static final String RESOURCE_TYPE_MODULE = "M";
    /* 资源类型-页面*/
    public static final String RESOURCE_TYPE_HTML = "H";
    /* 资源类型-按钮 */
    public static final String RESOURCE_TYPE_BUTTON = "B";

    /* 机构查询方式-上级 */
    public static final String SELECT_SUP_BRANCH_TYPE = "sup";
    /* 机构查询方式-下级 */
    public static final String SELECT_SUB_BRANCH_TYPE = "sub";
    /* 机构查询方式-上下级 */
    public static final String SELECT_ALL_BRANCH_TYPE = "all";

    /* 总公司机构号 */
    public static final String HEADQUARTERS_BRACNCH_NO = "000000";

}
