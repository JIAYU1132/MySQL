import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCTestSelect {
    public static void main(String[] args) throws SQLException {
        // 0. 准备工作, 需要先在数据库中创建好数据库和数据表
        // 1. 创建 DataSource 对象, 这个对象是 "程序级" 的, 即每个程序只需要创建一个 DataSource 对象即可
        //      这种也称为 "单例"
        DataSource dataSource = new MysqlDataSource();
        // 2. 设置 DataSource 的属性, 为和数据库建立连接做准备
        //    MySQL 是个服务器, 要想访问 MySQL, 需要知道 MySQL 的 ip 地址, 端口号
        //    以及要访问的数据库名, 以及用户名和密码
        //    ip 地址: 一台主机的地址
        //    端口号: 一台主机上可能同时部署着很多的服务器程序, 到底要访问哪个服务器程序, 使用端口号来区分
        ((MysqlDataSource)dataSource).setURL("jdbc:mysql://127.0.0.1:3306/java?characterEncoding=utf8&&useSSL=true");
        ((MysqlDataSource)dataSource).setUser("root");
        ((MysqlDataSource)dataSource).setPassword("yunjiayu990814");
        // 3. 建立和数据库的连接
        Connection connection = dataSource.getConnection();
        // 4. 拼装 SQL 语句
        String sql = "select * from student";
        PreparedStatement statement = connection.prepareStatement(sql);
        // 5. 此处没有问号, 不需要替换, 直接执行即可
        //    resultSet 中就包含了查询结果
        ResultSet resultSet = statement.executeQuery();
        // 6. 遍历结果集合
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            System.out.println(name + ": " + age);
        }
        // 7. 释放资源, 顺序不能颠倒, 先创建的对象后释放
        resultSet.close();
        statement.close();
        connection.close();
    }
}
