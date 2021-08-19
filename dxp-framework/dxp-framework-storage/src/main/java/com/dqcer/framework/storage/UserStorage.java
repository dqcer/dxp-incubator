package com.dqcer.framework.storage;

/**
 * @author dongqin
 * @description 用户存储
 * @date 2021/08/19
 */
public class UserStorage {

    static ThreadLocal<Box> boxThreadLocal = new InheritableThreadLocal();

    public static Box getBox() {
        return boxThreadLocal.get();
    }

    public static void setBox(Box box) {
        boxThreadLocal.set(box);
    }

    public static void clearBox() {
        boxThreadLocal.remove();
    }


    public static class Box {
        private Long userId;
        private String username;
        private Long roleId;
        private String roleName;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Long getRoleId() {
            return roleId;
        }

        public void setRoleId(Long roleId) {
            this.roleId = roleId;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }


    }

}
