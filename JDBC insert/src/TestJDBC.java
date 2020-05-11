import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestJDBC {
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
        // 3. 创建 Connection 对象, 用来表示和数据库建立一次连接.
        Connection connection = dataSource.getConnection();
        // 4. 操作数据库, 核心就是拼装 SQL 语句
        // 拼装 SQL 并没有真正执行 SQL
        //  String sql = "insert into student values('张三', 20)";
        //  此处的 SQL 语句结尾可以没有分号
        String sql = "insert into student values(?, ?)"; // 此处的 ? 是占位符, 可以在后面把具体的变量替换到 ? 位置上
        // statement 是用来辅助拼装 SQL 语句的, setxxx 的类型, 需要和数据库表的类型匹配
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "张三"); // 这里需要注意 ? 的下标从 1 开始计算
        statement.setInt(2, 20);
        System.out.println(statement);
        // 5. 执行 SQL
        int ret = statement.executeUpdate();
        System.out.println(ret);
        // 6. 释放相关资源, 一定是先释放 statement 再释放 connection, 顺序一定不能搞错, 否则会出现资源泄露问题
        statement.close();
        connection.close();
    }
}
