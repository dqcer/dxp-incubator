package com.dqcer.dxptools.dynamic.service

class DemoService1 extends HelpService {


    static void main(String[] args) {
        new DemoService1().statistical()
    }


    /**
     *   创建业务表
     *
     */
    @Override
    void createBizTable() {
        //  删除已处在的业务表
        def dropSql = 'DROP TABLE IF EXISTS `biz_permissions`'
        execute dropSql

        //  创建业务表
        def createTableSql = 'CREATE TABLE IF NOT EXISTS `biz_permissions` (\n' +
                '  `user_id` bigint(20) NOT NULL COMMENT \'用户主键\',\n' +
                '  `first_name` varchar(128) DEFAULT NULL COMMENT \'姓氏\',' +
                '  `mid_name` varchar(128) DEFAULT NULL COMMENT \'中间名\',\n' +
                '  `last_name` varchar(128) DEFAULT NULL COMMENT \'名字\',\n' +
                '  `role_id` bigint(20) NOT NULL COMMENT \'角色主键\',\n' +
                '  `role_name` varchar(512) DEFAULT NULL COMMENT \'角色名称\','+
                '  `study_id` bigint(20) DEFAULT NULL COMMENT \'项目主键\',\n' +
                '  `study_name` varchar(1024) DEFAULT NULL COMMENT \'项目名称\','+
                '  `site_id` bigint(20) DEFAULT NULL COMMENT \'试验中心主键\',\n' +
                '  `site_name` varchar(128) DEFAULT NULL COMMENT \'中心名称\' '+
                ') ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT=\'权限冗余表\''
        execute createTableSql
    }

    /**
     *   目标列表
     *
     * @return {  @link Object  }
     */
    @Override
    Object targetList() {
        //  统计后的结果集
        def selectResultSql = 'select \n' +
                'purses.user_id,\n' +
                'mu.first_name,\n' +
                'mu.mid_name ,\n' +
                'mu.last_name ,\n' +
                'purses .role_id ,\n' +
                'pr.name as role_name,\n' +
                'purses .study_id ,\n' +
                'ms.name as study_name,\n' +
                'purses.site_id ,\n' +
                'mo.name as site_name\n' +
                'FROM pub_user_role_study_env_site purses \n' +
                'join mds_user mu on purses .user_id = mu.id \n' +
                'join mds_study ms on purses .study_id = ms.id \n' +
                'join mds_institution mi on purses .site_id = mi.id \n' +
                'join mds_organization mo on mi.org_id = mo.id \n' +
                'join pub_role pr on pr.id = purses .role_id \n' +
                'group by purses.user_id,\n' +
                'mu.first_name,\n' +
                'mu.mid_name ,\n' +
                'mu.last_name ,\n' +
                'purses .role_id ,\n' +
                'pr.name,\n' +
                'purses .study_id ,\n' +
                'ms.name ,\n' +
                'purses.site_id ,\n' +
                'mo.name'
        def groovyRowResults = list selectResultSql
        return groovyRowResults
    }

    /**
     *   批量导入,插入到业务表中
     *
     * @param targetList 目标列表
     */
    @Override
    void batchImport(Object targetList) {
        def batch = 'insert into biz_permissions (user_id, first_name, mid_name, last_name, role_id, role_name, study_id, study_name, site_id, site_name) ' +
                'values (?,?,?,?,?,?,?,?,?,?)'
        batchInsert batch, targetList
    }

}
