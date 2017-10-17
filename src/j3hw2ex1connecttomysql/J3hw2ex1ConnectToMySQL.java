/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j3hw2ex1connecttomysql;

/**
 *
 * @author Yuri Tveritin
 */
import java.sql.*;// библиотека для работы с БД
public class J3hw2ex1ConnectToMySQL {

    private static PreparedStatement psInsert; //подготовленный запрос
    
    public static void main(String[] args) {
        String url="jdbc:mysql://localhost:3306/";//ссылка на БД(адрес порт имя 
        //и после ? дополнительные параметы)
        String dbName   = "users?useSSL=no";
        String userName = "root"; //пользователь БД
        String password = "123456"; //пароль
    
    
    try {
        // Соединяемся с БД
        //Class.forName(com.mysql.jdbc.Driver);//регистрация драйвера работы
        //с базой данных
        System.out.println(url + dbName);
        Connection connect = DriverManager.getConnection(url + dbName, 
                userName, password);//создание объекта подключения к БД
        //формируем запрос к БД
        Statement stmt = connect.createStatement();//объект для выполнения 
        //запросов к базе
        stmt.executeUpdate("DELETE FROM prodtable;");
//        stmt.executeUpdate("INSERT INTO prodtable (prodid, title, cost) VALUES"
//                + "('id_товара1', 'товар1', '10' );");
        
          
//        long t = System.currentTimeMillis();
        connect.setAutoCommit(false);//отключение подтвердения операции
        psInsert = connect.prepareStatement("INSERT INTO prodtable (prodid, "
                + "title, cost) VALUES (?, ?, ?);");
        for (int i = 0; i < 100; i++) {
            psInsert.setString(1, "id_товара" + (1+i));
            psInsert.setString(2, "товар" + (1+i));
            psInsert.setInt(3, 10+(i * 10));
            psInsert.addBatch();//добавить в список запросов
        }
        psInsert.executeBatch();//выполнить список запросов
        connect.setAutoCommit(true);//подтвердить операции
//        System.out.println(System.currentTimeMillis() - t);
    

            //ResultSet rs = stmt.executeQuery("SELECT * FROM users.users WHERE"
            //        + " login='yuri';"); //результат запроса к базе
//            rs.next();//перевод указателя курсора на следующую строку(в нашем 
//            //случае на первую строку, т.к. изначально курсор находится над 
//            //первой строкой)
//            System.out.print(rs.getString("passwd"));//выводим результат колонки 
//            //System.out.print(rs);
//            rs.close();
            stmt.close();
            connect.close();
        } catch (Exception ex) {
            System.out.println (ex);
        }
    }
}
    
