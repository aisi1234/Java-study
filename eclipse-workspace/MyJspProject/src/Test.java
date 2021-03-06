import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.CallableStatement;
import com.mysql.cj.jdbc.Driver;
import com.sun.xml.internal.ws.wsdl.writer.document.Types;


public class Test {
	public static void main(String[] args) throws Exception {
		
	}
	
	public static Statement getConnection() {
		String url = "com:mysql://localhost:3306/mydbs?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";
		String user = "root";
		String password = "123456";
		Statement statement = null;
		PreparedStatement pst = null;
//		CallableStatement
		if(statement == null) {
			try {
				Class.forName("com.mysql.cj.Driver");
				Connection conn = DriverManager.getConnection(url, user, password);
				statement = conn.createStatement();
				
			} catch (ClassNotFoundException e) {
				System.err.println("加载驱动失败");
			} catch (SQLException e) {
				System.err.println("获取数据库连接失败");
			}finally {
				
			}
		}
		
		return statement;
	}
	
	/*
	 * Callabletatement，基于Orecal数据库
	 * connection.prepareCall(参数 ： 存储过程或存储函数名)
	 * 参数格式：
	 * 	存储过程（无返回值return，用out参数替代）-》{ call 存储过程名(参数列表)}
	 * 	存储函数（有返回值return）-》{ ？ = call 存储函数名(参数列表)}
	 * 假设存在以下存储过程
	 * create or replace procedure addTwoNum(num1 in number, num2 in number, result out number )
	 * as 
	 * begin
	 * 		result := num1 + num2;
	 * end;
	 *\/(以斜杠结尾)
	 *
	 *假设有以下存储函数
	 *create or replace function procedure addTwoNum(num1 in number, num2 in number, result out number )
	 *return number
	 * as 
	 * 	result number;
	 * begin
	 * 		result := num1 + num2;
	 * end;
	 *\/(以斜杠结尾)
	 *
	 *调用存储过程步骤
	 *1.获得存储过成程对象
	 *2.设置输入参数值，输出参数类型
	 *3.执行过程
	 *4.获取处理结果
	 * 
	 */
	
	
	private static void invokeProcedure() {
		String url = "com:mysql://localhost:3306?mydbs?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&usrSSL=false";
		String user = "root";
		String pswd = "123456";
		
		Connection conn = null;
		CallableStatement sctmt = null;
		
		try {
			Class.forName("com.mysql.cj.Driver");
			conn = DriverManager.getConnection(url,user,pswd);
			//获取存储过程，
			sctmt = (CallableStatement) conn.prepareCall(  "{ call addTwoNum(?,?,?)}" );
			sctmt.setInt(1, 10); //设置第一个参数
			sctmt.setInt(2, 20); //设置第二个参数
			sctmt.registerOutParameter(3, Types.INTEGER); //设置输出参数类型
			
			sctmt.execute(); //执行，在此之前设置输入参数值，输出参数类型
			
			int result = sctmt.getInt(3); //获取返回值
			
		} catch (ClassNotFoundException e) {
			System.err.println("加载数据库驱动失败");
		} catch (SQLException e) {
			System.err.println("获取连接失败");
		}
		
	}
	
}
