package com.bjpowernode.crm.test;

import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.MD5Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test01 {

    public static void main(String[] args) {

        //System.out.println(123);

        //验证失效时间的方式
        /*String expireTime = "2020-07-20 10:10:10";

        //取得当前的系统时间
        *//*Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(date);
        System.out.println(strDate);*//*

        String currentSysTime = DateTimeUtil.getSysTime();
        //System.out.println(currentSysTime);

        int num = expireTime.compareTo(currentSysTime);
        System.out.println(num);
        if(num<=0){

            System.out.println("账号已失效");

        }*/


        //锁定状态的验证方式
        /*

            关于锁定状态，在需求文档中约定：
            0：账号处于锁定状态
            1：账号处于启用状态

         */
        /*String lockState = "0";
        if("0".equals(lockState)){

            System.out.println("账号已锁定");

        }*/


        //允许访问ip地址群的判断方式
        /*

            我们在使用浏览器连接服务器的过程中，浏览器为服务器发出请求，服务器接收浏览器的请求
            服务器会经过判断，让不让浏览器访问，其中很重要的一项就是判断ip地址
            只有服务器允许访问的ip地址，才能够访问，其他的ip是不能够访问的

            假设：浏览器的ip地址是：192.168.1.1
                 服务器允许访问的ip地址群是：192.168.1.2,192.168.1.6,192.168.1.8

            如果表中记录的allowIps这一项为null，那么说明不需要判断，所有ip都能访问

         */
        //浏览器端的ip
        /*String ip = "192.168.1.1";
        //允许访问的ip地址群是（多个ip约定使用逗号分隔）
        String allowIps = "192.168.1.2,192.168.1.6,192.168.1.8";

        //我们现在判断ip有没有包含在allowIps当中
        if(allowIps!=null){

            if(!allowIps.contains(ip)){

                System.out.println("ip地址访问受限");

            }

        }*/

        /*

            小专题：关于验证密码

            假设我们现在的账号和密码分别是zs和123

            在数据库表中所保存的数据就是loginAct：zs  loginPwd:123

            思考：我们直接将密码123保存到数据库表中，这样做好吗？

            123是一组明文数据，直接保存在数据库表中，及其危险
            因为将来能够有机会接触到数据库表的人太多了...

            我们一定是要将密码加密为密文的形式，保存到数据库表中，才会绝对的安全

            观察：
                tbl_user表
                其中我们保存的密码的数据是：202cb962ac59075b964b07152d234b70
                这组数据就是通过MD5加密方式后，得到的密文，这个密文对应的明文其实就是123

            对于密码加密的方式有很多种，对于我们IT界对密码的加密方式，使用的几乎都是MD的形式进行加密
            从早期的MD4进化到现在主流的MD5，一直到还没有广泛应用的MD6，都是属于一类的MD散列计算的方式进行的加密
            具体加密的流程我们不用去了解，官方也是为我们提供了MD5加密的方法，我们直接拿来使用就可以了

            关于MD5加密方式，你需要知道的事：

            1.MD5是美国 罗纳德李维斯特团队创造的，MD5用来取代之前算法还不完整的MD4,
                现今已经成为了我们市面上最常用的加密手段

            2.MD5在流行期间，被破解过一次，破解的团队是中国的清华大学，领导人是清华大学密码学中心主任王小云

                破解之后，罗纳德带领团队将原有的MD5加密方式，进了一次加盐操作，这次加盐之后，MD5就再也没有被破解过

            3.如果我们在搜索引擎上对于MD5进行搜索，那么肯定会有一个网站非常显眼，叫做CMD5

                这个CMD5网站，就是用来"破解"加密密码的

                在该网站的中心处：
                我们可以填写：加密方式：MD5
                填写密文：202cb962ac59075b964b07152d234b70
                点击查询按钮：123就被"查"出来了

                这个CMD5网站，通过以穷举生成的方式，在数据库表中，生成90万亿条记录
                我们常见的密码和密码对应密文都保存在了这张表中

                通过以上CMD5这个网站，我们可以得到结论，虽然有MD5加密方式做保证，但是我们仍然不能将密码设置的太简单

                建议以后大家在注册密码的时候，尽量是 掺杂 小写字母+大写字母+数字+特殊符号

                这样的密码，在CMD5这样的网站中，肯定是查询不到的


            对于我们系统的验证：
                前端表单提交的密码肯定是明文123
                当123传递到控制器中之后，然后将123加密为MD5的密文
                通过加密后的密文与数据库表中所保存的密文做比对


         */

        //明文
        String loginPwd = "123";

        //密文
        loginPwd = MD5Util.getMD5(loginPwd);
        System.out.println(loginPwd);




    }

}



































