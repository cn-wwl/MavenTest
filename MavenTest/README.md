## 用户信息资源管理
关键字:  maven、selvert、fastjson、mybatis、mysql

[toc]

### 一、maven

maven是一个管理第三方架包的仓库工具，可以使用通过[下载](http://maven.apache.org/download.cgi)工具到本地,然后解压放在理想的位置，然后打开`conf`目录下的`settings.xml`。

1.配置本地仓库下载的地址

```xml
<localRepository>D:\Tools\Java\maven\maven_repository</localRepository>
```

2.配置下载架包的远程地址(由于默认的远程地址 下载架包的速度太慢，因此一般使用的都是阿里的公开仓库地址)

```xml
<mirrors>
    <mirror>
        <id>aliyunmaven</id>
        <mirrorOf>*</mirrorOf>
        <name>阿里云公共仓库</name>
        <url>http://maven.aliyun.com/nexus/content/groups/public</url>
    </mirror>
</mirrors>
```

3.通过IDE创建maven项目，项目会生成pom.xml的配置文件,下面是pom.xml的基本配置信息

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

  <!-- 模型版本-->
  <modelVersion>4.0.0</modelVersion>

  <!-- 公司或者组织的唯一标志，并且配置时生成的路径也是由此生成， 如com.winner.trade，maven会将该项目打成的jar包放本地路径：/com/wwl -->
  <groupId>com.wwl</groupId>
  <!-- 本项目的唯一ID，一个groupId下面可能多个项目，就是靠artifactId来区分的 -->
  <artifactId>MavenWebTest</artifactId>
  <!-- 本项目目前所处的版本号 -->
  <version>1.0-SNAPSHOT</version>
  <!-- 打包的机制，如pom,jar, maven-plugin, ejb, war, ear, rar, par，默认为jar -->
  <packaging>war</packaging>

  <!--项目的名称, Maven产生的文档用 -->
  <name>MavenWebTest Maven Webapp</name>

  <!--项目主页的URL, Maven产生的文档用 -->
  <url>http://example.org/</url>
 
  <!-- 定义本项目的依赖关系 -->
  <dependencies> 
    <!-- 每个dependency都对应这一个jar包 -->
    <!--一般情况下，maven是通过groupId、artifactId、version这三个元素值（俗称坐标）来检索该构件 --> 
    <dependency>      
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.38</version>
    </dependency> 
  </dependencies>
</project>

```



### 二、servlet

Servlet 是运行在 Web 服务器或应用服务器上的程序，它是作为来自 Web 浏览器或其他 HTTP 客户端的请求和 HTTP 服务器上的数据库或应用程序之间的中间层。跟.net webapi类似。

#### 1.HttpServlet接口介绍

> Sevlet接口

servlet是一个接口，服务端通过对外发布的服务`HttpServlet`是继承了`GenericServlet`,而`GenericServlet`就是实现了`Servlet`接口。所有

```java
//Servlet的生命周期:从Servlet被创建到Servlet被销毁的过程
//一次创建，到处服务
//一个Servlet只会有一个对象，服务所有的请求
/*
 * 1.实例化（使用构造方法创建对象）
 * 2.初始化  执行init方法
 * 3.服务     执行service方法
 * 4.销毁    执行destroy方法
 */
public interface Servlet {
    
    //生命周期方法:当Servlet第一次被创建对象时执行该方法,该方法在整个生命周期中只执行一次
    void init(ServletConfig var1) throws ServletException;

    ServletConfig getServletConfig();

    //生命周期方法:对客户端响应的方法,该方法会被执行多次，每次请求该servlet都会执行该方法
    void service(ServletRequest var1, ServletResponse var2) throws ServletException, IOException;

    String getServletInfo();

    //生命周期方法:当Servlet被销毁时执行该方法
    void destroy();
}
```



> HttpServlet抽象类

HttpServlet该种定义资源的形式是一种restful风格(`GET、POST、PUT、DELETE...`)，

```java
public abstract class HttpServlet extends GenericServlet {
    public HttpServlet() {
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    } 
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      
    }
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      
    }
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    } 
}
```

> HttpServlet资源接口

HttpServlet的资源名称必须用`@WebServlet("/name")`标记，并且每种网络访问请求都是继承于`GenericServlet`,因此实现方法再重写时需要加上`@Override`

```java
@WebServlet("/UserInfoServlet")
public class UserInfoServlet extends HttpServlet {
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
}
```

#### 2.HttpServletRequest

​	HttpServletRequest其实就是管理客户端访问服务端的对象，服务端可通过该对象获取客户端发送的信息进行相关操作

| 方法名                 | 作用                                            | 示例                          | 结果                    |
| ---------------------- | ----------------------------------------------- | ----------------------------- | ----------------------- |
| getQueryString ()      | 方法返回请求行中的参数部分【url后面拼接的参数】 | request.getQueryString()      | id=4&name=admin         |
| request.getParameter() | 根据参数名返回参数值 【url后面拼接的参数】      | request.getParameter("id")    | 4                       |
| getRemoteAddr()        | 返回发出请求的客户机的IP地址                    | getRemoteAddr()               | 0:0:0:0:0:0:0:0:1       |
| getLocalAddr()         | 返回WEB服务器的IP地址                           | getLocalAddr()                | 0:0:0:0:0:0:0:0:1       |
| request.getReader()    | 获取body里传输的参数信息                        | 见代码块                      | { "id":5,name":"test",} |
| setCharacterEncoding() | 设置客户端传输数据的编码格式                    | setCharacterEncoding("utf-8") |                         |



获取body里的数据，通过reque.getReader()循环读取每一行，直到为空停止读取。

```java
public static String readAsChars(HttpServletRequest request) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
```





#### 3.HttpServletResponse

HttpServletResponse是服务端返回给客户端操作的对象，服务端可通过该对象发送的信息、重定向进行相关操作

| 方法名                  | 作用             | 示例                                       | 结果 |
| ----------------------- | ---------------- | ------------------------------------------ | ---- |
| getWriter()             | 获取输出写入对象 | request.getWriter().print("您好")          | 您好 |
| setContentType()        | 设置输出内容类型 | setContentType("text/plain;charset=utf-8") |      |
| response.setStatus()    | 返回httpstatus   | response.setStatus(200)                    | 200  |
| response.addCookie()    | 设置cookie       | response.addCookie(new Cookie("id","1"))   |      |
| response.sendRedirect() | 重定向地址       | response.sendRedirect("/index.jsp")        |      |



### 三、fastjson

#### 1.序列化

```java
//将对象转换成json
String jsonStr = JSON.toJSONString(userInfo);
```



#### 2.反序列化

```java
//将json转换成对象
UserInfo userInfo = JSON.parseObject(jsonStr, UserInfo.class);
```



#### 3.json注解 @JSONField()

| 参数名    | 作用                   |
| --------- | ---------------------- |
| name      | 设置序列化后的字段名称 |
| serialize | 设置是否序列化         |
| ordinal   | 设置序列化的字段顺序   |
| format    | 设置字段的内容格式     |

示例：

```java
public class UserInfo implements Serializable {
 
    private int id;
    @JSONField(name = "UserName")
    private String name;
    @JSONField(ordinal = 1)
    private String sex;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;
    private String address;
    private String phone;
    @JSONField(serialize = false)
    private String IDCard;
}
```





### 四、mybatis

#### 1.数据库配置文件 mybatis-config.xml

> 数据库环境配置
```xml
<environments default="mysql">
        <!--配置mysql的环境-->
        <environment id="mysql">
            <!--配置事务的类型-->
            <transactionManager type="JDBC"></transactionManager>
            <!--配置连接池-->
            <dataSource type="POOLED">
                <!--配置连接数据库的4个基本信息-->
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/testdb?useSSL=false&useUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone=UTC"/>
                <property name="username" value="root"/>
                <property name="password" value="sa123"/>
            </dataSource>
        </environment>
    </environments>
```
> 映射文件配置
```xml
<!--指定映射配置文件的位置，映射配置文件指的是每个dao独立的配置文件-->
<mappers>
    <mapper resource="mapper/UserInfoMapper.xml"/> 
</mappers>
```

#### 2.数据库实体映射对象 javabean

 实体对象的成员字段需要与 数据库字段名称保持一致，否则需要通过 实体字段与数据库字段映射配置实现 映射
```java
public class UserInfo implements Serializable { 
    private int id;
    private String name;
    @JSONField(ordinal = 1)
    private String sex;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;
    private String address;
    private String phone;

    //region getter、setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //endregion 
}
```

#### 3.xml映射文件 mapper

mapper xml 文件是用来配置sql指令的文件，每条指令都有对应的id、参数类型、返回类型，参数类型可以是`parameterType`也可以是`parameterMap`也可以是其它类型,
返回类型亦可以是`resultType` 也可以是 其它类型，#{param}是参数对应的值，外部通过传值的形式调用对应的sql，然后去执行即可。


```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="UserInfoMapper">
    <select id="selectAllUserInfo" resultType="com.wwl.dao.bean.UserInfo">
        SELECT id, name, sex, birthday, address,phone FROM testdb.userinfo;
    </select>

    <select id="selectUserInfo" parameterType="int" resultType="com.wwl.dao.bean.UserInfo">
        SELECT Id, name, sex, birthday, address,phone FROM testdb.userinfo WHERE id =#{id};
    </select>

    <insert id="insertUserInfo" parameterType="com.wwl.dao.bean.UserInfo">
        INSERT INTO testdb.userinfo(name, sex, birthday, address,phone ) VALUES(#{name}, #{sex}, #{birthday}, #{address},#{phone});
    </insert>

    <update id="updateUserInfo" parameterType="com.wwl.dao.bean.UserInfo">
        UPDATE testdb.userinfo SET name=#{name}, sex=#{sex}, birthday=#{birthday}, address= #{address}, phone=#{phone} WHERE id=#{id};
    </update>

    <delete id="deleteUserInfo" parameterType="int" >
        DELETE FROM testdb.userinfo WHERE Id=#{id};
    </delete>
</mapper>
```

#### 4.数据库访问的 Helper

```java
 public class MySqlHelper {
    private static InputStream config = null;
    private static SqlSessionFactory ssf = null;

    static {
        try {
            // 读取配置文件 mybatis-config.xml
            config = Resources.getResourceAsStream("mybatis-config.xml");
            // 根据配置文件构建SqlSessionFactory
            ssf = new SqlSessionFactoryBuilder().build(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> List<T> SelectAllList(String sqlkey) {

        // 通过 SqlSessionFactory 创建 SqlSession
        SqlSession ss = ssf.openSession();
        try {
            // 查询所有用户
            List<T> result = ss.selectList(sqlkey);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭 SqlSession
            ss.close();
        }
        return null;
    }

    public static <T> T SelectOneObject(int id,String sqlkey) {

        // 通过 SqlSessionFactory 创建 SqlSession
        SqlSession ss = ssf.openSession();
        try {
            // 查询单个用户
            T result = ss
                    .selectOne(sqlkey,id);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭 SqlSession
            ss.close();
        }
        return null;
    }

    public static <T> int InsertEntity(T bean,String sqlkey){
        // 通过 SqlSessionFactory 创建 SqlSession
        SqlSession ss = ssf.openSession();
        try {
            return ss.insert(sqlkey,bean);

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
             ss.commit();
            // 关闭 SqlSession
            ss.close();
        }
    }

    public static <T> int UpdateEntity(T bean,String sqlkey){
        // 通过 SqlSessionFactory 创建 SqlSession
        SqlSession ss = ssf.openSession();
        try {
            return ss.update(sqlkey,bean);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            ss.commit();
            // 关闭 SqlSession
            ss.close();
        }
    }

    public static int DeleteEntity(int id,String sqlkey){
        // 通过 SqlSessionFactory 创建 SqlSession
        SqlSession ss = ssf.openSession();
        try {
            return ss.delete(sqlkey,id);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            ss.commit();
            // 关闭 SqlSession
            ss.close();
        }
    }

}

```



#### 5.增删改查实现(应用程序版)

```java
	   //新增用户信息
        UserInfo user = new UserInfo();
        user.setName("测试");
        user.setSex("女");
        user.setAddress("浙江.杭州");
        user.setBirthday(new Date());
        user.setPhone("10000002");
        int result = MySqlHelper.InsertEntity(user, "UserInfoMapper.insertUserInfo");
        System.out.println(result > 0 ? "添加成功" : "添加失败");

        //修改用户信息
        UserInfo user2 = new UserInfo();
        user2.setId(2);
        user2.setName("管理员");
        user2.setSex("男");
        user2.setAddress("浙江.杭州");
        user2.setBirthday(new Date());
        user2.setPhone("10000001");
        int result2 = MySqlHelper.UpdateEntity(user, "UserInfoMapper.updateUserInfo");
        System.out.println(result2 > 0 ? "修改成功" : "修改失败");

        //删除用户信息
        int result3 = MySqlHelper.DeleteEntity(3, "UserInfoMapper.deleteUserInfo");
        System.out.println(result3 > 0 ? "删除成功" : "删除失败");

        //查询用户信息(单个)
        UserInfo result4 = MySqlHelper.SelectOneObject(2, "UserInfoMapper.selectUserInfo");
        System.out.println("用户对象：" + result4);

        //查询用户信息(全部)
        List<UserInfo> userInfoList = MySqlHelper.<UserInfo>SelectAllList("UserInfoMapper.selectAllUserInfo");
        System.out.println("用户列表：" + userInfoList);
```

#### 6.增删改查实现(Servlet版)

```java
@WebServlet("/UserInfoServlet")
    public class UserInfoServlet extends HttpServlet {
    
    
        @Override
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/plain;charset=utf-8");
    
            String id = request.getParameter("id");
            if (id == null || id.isEmpty()) {
                List<UserInfo> userInfoList = MySqlHelper.<UserInfo>SelectAllList("UserInfoMapper.selectAllUserInfo");
    
                String jsonStr = JSON.toJSONString(userInfoList);
                response.getWriter().println(jsonStr);
    
            } else {
                UserInfo userInfo = MySqlHelper.<UserInfo>SelectOneObject(Integer.parseInt(id), "UserInfoMapper.selectUserInfo");
    
                String jsonStr = JSON.toJSONString(userInfo);
                response.getWriter().println(jsonStr);
            }
    
        }
    
        @Override
        public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/plain;charset=utf-8");
    
            StringBuilder erromessage = new StringBuilder();
            String data = HttpServletRequestBodyUtil.readAsChars(request);
            UserInfo user = JSON.parseObject(data, UserInfo.class);
    
            if (user.getName() == null || user.getName().isEmpty()) {
                erromessage.append("姓名不能为空!");
            }
            if (user.getSex() == null || user.getSex().isEmpty()) {
                erromessage.append("性别不能为空!");
            }
    
            if (erromessage != null) {
                response.getWriter().println(erromessage);
            }
    
            int result = MySqlHelper.InsertEntity(user, "UserInfoMapper.insertUserInfo");
            response.getWriter().println(result > 0 ? "添加成功" : "添加失败");
        }
    
        @Override
        public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/plain;charset=utf-8");
    
            StringBuilder erromessage = new StringBuilder();
            String data = HttpServletRequestBodyUtil.readAsChars(request);
            UserInfo user = JSON.parseObject(data, UserInfo.class);
    
            if (user.getName() == null || user.getName().isEmpty()) {
                erromessage.append("姓名不能为空!");
            }
            if (user.getSex() == null || user.getSex().isEmpty()) {
                erromessage.append("性别不能为空!");
            }
            if (erromessage != null) {
                response.getWriter().println(erromessage);
            }
    
            int result = MySqlHelper.UpdateEntity(user, "UserInfoMapper.updateUserInfo");
            response.getWriter().println(result > 0 ? "更新成功" : "更新失败");
        }
    
        @Override
        public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/plain;charset=utf-8");
    
            String id = request.getParameter("id");
            if (id == null || id.isEmpty()) {
    
                response.getWriter().println("id不能为空!");
            }
    
            int result = MySqlHelper.DeleteEntity(Integer.parseInt(id), "UserInfoMapper.deleteUserInfo");
            response.getWriter().println(result > 0 ? "删除成功" : "删除失败");
    
        }
    
    }
```



