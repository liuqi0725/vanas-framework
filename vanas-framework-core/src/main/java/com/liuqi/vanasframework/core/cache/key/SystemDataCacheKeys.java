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
public final class SystemDataCacheKeys {

    private SystemDataCacheKeys(){}

    /**
     * 角色信息
     */
    public static final class RoleInfo {
        public static final String CACHE_NAME  = AppCacheNames.DATA_CACHE;
        public static final String KEY   = "RoleInfo:R_";
        public static final String ANNOTATION_KEY = "'" + KEY + "'";
        static { CacheKeyRegistry.register(CACHE_NAME, KEY); }
        private RoleInfo() {}
    }

    /**
     * 用户信息
     */
    public static final class UserProfile {
        public static final String CACHE_NAME  = AppCacheNames.DATA_CACHE;
        public static final String KEY   = "UserProfile:U_";
        public static final String ANNOTATION_KEY = "'" + KEY + "'";
        static { CacheKeyRegistry.register(CACHE_NAME, KEY); }
        private UserProfile() {}
    }

    /**
     * vc 信息
     */
    public static final class VcInfo {
        public static final String CACHE_NAME  = AppCacheNames.DATA_CACHE;
        public static final String KEY   = "VcInfo:V_";
        public static final String ANNOTATION_KEY = "'" + KEY + "'";
        static { CacheKeyRegistry.register(CACHE_NAME, KEY); }
        private VcInfo() {}
    }

}
