package work;

import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "rootroot";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/homework";

    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        while (true) {
            System.out.println("\n1. Вывести всю таблицу.");
            System.out.println("2. Создать нового пользователя.");
            System.out.println("3. Искать пользователя по имени или id.");
            System.out.println("4. Удалить пользователя по id.");
            System.out.println("5. Закрыть программу.");

            int command = scanner.nextInt();

            switch (command) {
                case (1) -> {
                    Statement statement = conn.createStatement();
                    String queryAll = "SELECT * FROM users";
                    ResultSet res = statement.executeQuery(queryAll);
                    while (res.next()) {
                        System.out.println(
                                res.getInt("id") + " " +
                                        res.getString("username") + " " +
                                        res.getString("info"));
                    }
                }

                 case (2) -> {

                    String queryDel = "INSERT INTO users (username, info) VALUES(?, ?)";

                    PreparedStatement preparedStatement = conn.prepareStatement(queryDel);

                    System.out.println("Введите username пользователя.");
                    String userName = scanner.next();

                    System.out.println("Введите info пользователя.");
                    String userInfo = scanner.next();

                    preparedStatement.setString(1, userName);
                    preparedStatement.setString(2, userInfo);
                    preparedStatement.executeUpdate();
                    System.out.println("Пользователь успешно добавлен!");

                }

                case (3) -> {
                    System.out.println("1. При помощи id.");
                    System.out.println("2. При помощи username");
                    int commandFind = scanner.nextInt();

                    switch (commandFind) {
                        case (1) -> {


                            String queryFind = "SELECT * FROM users WHERE id = ?";

                            PreparedStatement preparedStatement = conn.prepareStatement(queryFind);

                            System.out.println("Введите id пользователя для поиска.");
                            int userId = scanner.nextInt();
                            preparedStatement.setInt(1, userId);

//                            ResultSet resFind = statement.executeQuery(queryFind);
                            ResultSet resFind = preparedStatement.executeQuery();

                            while (resFind.next()) {
                                System.out.println(
                                        resFind.getInt("id") + " " +
                                                resFind.getString("username") + " " +
                                                resFind.getString("info"));
                            }

                        }

                        case (2) -> {
                            String queryFind = "SELECT * FROM users WHERE username = ?";

                            PreparedStatement preparedStatement = conn.prepareStatement(queryFind);

                            System.out.println("Введите username пользователя для поиска.");

                            String userName = scanner.next();
                            preparedStatement.setString(1, userName);
                            System.out.println(userName);

//                            ResultSet resFind = statement.executeQuery(queryFind);
                            ResultSet resFind = preparedStatement.executeQuery();

                            while (resFind.next()) {
                                System.out.println(
                                        resFind.getInt("id") + " " +
                                                resFind.getString("username") + " " +
                                                resFind.getString("info"));


                            }

                        }
                        default -> {
                            System.err.println("Команда не была распознана.");
                        }

                    }
                }


                case (4) -> {

                    String queryDel = "DELETE FROM users WHERE id = ?";

                    PreparedStatement preparedStatement = conn.prepareStatement(queryDel);

                    System.out.println("Введите id пользователя для удаления.");
                    int userId = scanner.nextInt();
                    preparedStatement.setInt(1, userId);
                    preparedStatement.executeUpdate();

                }


                case (5) -> System.exit(0);


                default -> System.err.println("Команда не распознана");


            }
        }
    }

}