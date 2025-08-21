package com.liuqi.vanasframework.core.cache.key;

import com.liuqi.vanasframework.core.cache.AppCacheNames;

/**
 * 类说明 <br>
 * <p>
 *    基础框架缓存key定义
 * </p>
 *
 * @author : lau.Q
 * @version v1.0 , Create at 2025/8/20 11:16
 */
public final class SystemSettingCacheKeys {

    private SystemSettingCacheKeys(){}

    /**
     * 系统所有可用菜单数据
     */
    public static final class MenusData {

        /**
         * 缓存名称
         */
        public static final String CACHE_NAME  = AppCacheNames.SETTING_CACHE;

        /**
         * 缓存key
         */
        public static final String KEY   = "SystemMenusData";

        /**
         * spring 注解的名称
         */
        public static final String ANNOTATION_KEY = "'" + KEY + "'";

        /*
         * 注册到统一的缓存key管理中
         */
        static { CacheKeyRegistry.register(CACHE_NAME, KEY); }

        private MenusData() {}
    }

    /**
     * 系统所有可用角色数据
     */
    public static final class RoleData {
        public static final String CACHE_NAME  = AppCacheNames.SETTING_CACHE;
        public static final String KEY   = "SystemRolesData";
        public static final String ANNOTATION_KEY = "'" + KEY + "'";
        static { CacheKeyRegistry.register(CACHE_NAME, KEY); }
        private RoleData() {}
    }

    /**
     * 系统所有可用权限数据
     */
    public static final class PermissionsData {
        public static final String CACHE_NAME  = AppCacheNames.SETTING_CACHE;
        public static final String KEY   = "SystemPermissionsData";
        public static final String ANNOTATION_KEY = "'" + KEY + "'";
        static { CacheKeyRegistry.register(CACHE_NAME, KEY); }
        private PermissionsData() {}
    }

    /**
     * 系统所有可用虚拟中心数据
     */
    public static final class VcData {
        public static final String CACHE_NAME  = AppCacheNames.SETTING_CACHE;
        public static final String KEY   = "SystemActivityVC";
        public static final String ANNOTATION_KEY = "'" + KEY + "'";
        static { CacheKeyRegistry.register(CACHE_NAME, KEY); }
        private VcData() {}
    }

    /**
     * 系统所有可用部门数据
     */
    public static final class DeptData {
        public static final String CACHE_NAME  = AppCacheNames.SETTING_CACHE;
        public static final String KEY   = "SystemDept";
        public static final String ANNOTATION_KEY = "'" + KEY + "'";
        static { CacheKeyRegistry.register(CACHE_NAME, KEY); }
        private DeptData() {}
    }

    /**
     * 系统所有可用字典数据
     */
    public static final class DictData {
        public static final String CACHE_NAME  = AppCacheNames.SETTING_CACHE;
        public static final String KEY   = "SystemDict";
        public static final String ANNOTATION_KEY = "'" + KEY + "'";
        static { CacheKeyRegistry.register(CACHE_NAME, KEY); }
        private DictData() {}
    }

}
