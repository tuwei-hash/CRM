package com.bjpowernode.crm.settings.domain;

public class User {

    /*

        时间相关字符串的处理：

        常见的（在市面上常用的）用来表达日期和时间的方式如下：

        默认的，约定俗成的表达方式

        日期：yyyy-MM-dd   10位
        日期+时间：yyyy-MM-dd HH:mm:ss 19位

        对于我们现在的tbl_user表而言，表达日期及时间的字段如下：
        expireTime：失效时间 yyyy-MM-dd HH:mm:ss 19位
        createTime：创建时间 yyyy-MM-dd HH:mm:ss 19位
        editTime：修改时间   yyyy-MM-dd HH:mm:ss 19位

        我们对于以上日期时间的设置是char(19),为什么不是varchar(19)呢？

        varchar和char的区别:

        varchar(19):变长
            保存的数据字符数，是多少都可以，只要不超过19位就可以
            例如我们现在存一个abc
            那么varchar的保存方式就是："abc"

        char(19):定长
            保存的数据的字符数，是多少都可以，只要不超过19位就可以
            但是，如果使用char的话，不满19位，剩余的字符数默认会使用空格进行站位
            所以，只要我们使用char，你如果不满足这个位数，虽然数据能够保存，但是没有实际意义，可用性不强
            例如我们现在存一个abc
            那么char的保存方式就是："abc                          "

        未来的实际项目开发，由于字符串的长度经常会改变，所以使用varchar比较灵活，使用的最多
        char所保存的数据，查询效率要远远高于varchar
        也就是说，一旦我们确认了保存字符的长度，一定要使用char

        在tbl_user表中,其中我们还会看到id也是使用char
        长度为32：到时候我们使用UUID技术去做主键，将来主键是字符串，填满32位

        在tbl_user表中,其中我们还会看到lockState也是使用char
        长度为1：约定 只能保存两个数值 0和1

        注意：
            做sql语句的过程中，关于表名的使用，要严格区分大小写

            例如我们的表叫做：tbl_user

            select * from tbl_user

            不能这么写：
            select * from TBL_USER


        -------------------------------------------

        登录相关的操作：
        需要验证账号和密码 loginAct和loginPwd
        失效时间 expireTime
        锁定状态 lockState
        运许访问的ip地址群 allowIps

        以上需要验证的项，我们需要按照顺序一项一项进行验证
        验证通过了上一项，才能够继续向下验证
        如果我们在验证的过程中，有的验证项没有通过，则证明登录失败，接下来后续的验证项就无需验证了
        如果所有验证项都验证通过，则证明登录成功

        具体验证的流程

        不推荐

        1.需要验证账号和密码 loginAct和loginPwd
            根据查询得到的记录数来判断账号密码是否正确
            int count = select count(*) from tbl_user where loginAct=? and loginPwd=?
            count:0 表示没有查询出来任何条数，说明账号密码错误
            count:1 表示查询得到了一条记录，说明账号密码正确
            // count: >1 说明在开发时表中有垃圾数据

        2.验证失效时间 expireTime
            先将expireTime从表中查询出来，然后去验证它
            select expireTime from tbl_user where loginAct=? and loginPwd=?

        3.验证锁定状态 lockState
            先将lockState从表中查询出来，然后去验证它
            select lockState from tbl_user where loginAct=? and loginPwd=?

        4.。。。。。。。。。。。。。。
            select ...

        以上做法，与数据库做了4次交互，不推荐。

        推荐做法：

        1.需要验证账号和密码 loginAct和loginPwd
            根据这两个查询条件，查询User对象
            我们刚刚根据查询得到的条数count，有效的判断了账号密码是否正确
            这一次我们根据查询得到的User对象，能不能判断账号密码呢？
            User user = select * from tbl_user where loginAct=? and loginPwd=?
            user:为null 根据账号密码没有得到相应记录，说明账号密码错误
            user:不为null 根据账号和密码查询得到了相应的记录，说明账号密码正确
            这一次我们查询得到user对象的好处是什么呢？
            一旦账号密码正确，我们就会查询得到一个User对象
            然后我们可以根据该User对象取得其他的验证项继续进行验证

        2. 验证失效时间
            以前的做法：
            先将expireTime从表中查询出来，然后去验证它
            select expireTime from tbl_user where loginAct=? and loginPwd=?

            这次的做法：
            通过第一步得到的User对象直接get取得失效时间就可以了
            expireTime:user.getExpireTime();

        3.验证锁定状态
            lockState:user.getLockState();


        4.验证ip地址群
            allowIps:user.getAllowIps();



     */

    private String id;  //主键
    private String loginAct;    //登录账号
    private String name;    //用户真实姓名
    private String loginPwd;    //登录密码
    private String email;   //邮箱
    private String expireTime;  //失效时间
    private String lockState;   //锁定状态
    private String deptno;  //部门编号
    private String allowIps;    //允许访问的ip地址群
    private String createTime;  //创建时间
    private String createBy;    //创建人
    private String editTime;    //修改时间
    private String editBy;  //修改人

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginAct() {
        return loginAct;
    }

    public void setLoginAct(String loginAct) {
        this.loginAct = loginAct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getLockState() {
        return lockState;
    }

    public void setLockState(String lockState) {
        this.lockState = lockState;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

    public String getAllowIps() {
        return allowIps;
    }

    public void setAllowIps(String allowIps) {
        this.allowIps = allowIps;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }
}
