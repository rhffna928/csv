package toicos.sample;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String csvFilePath = "D:\\OneDrive - (주)토이코스(강)\\OneDrive - (주)토이코스\\자료\\대구\\민원TOP100.csv";

        // MySQL DB 정보
        String dbUrl = "jdbc:mysql://localhost:3306/mj";
        String username = "root";
        String password = "1234";

        try {
            // MySQL 연결
            Connection connection = DriverManager.getConnection(dbUrl, username, password);

            // CSV 파일 읽기
            BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));
            CSVReader csvReader = new CSVReader(reader);

            String[] nextLine;
            String sql = "INSERT INTO dgconn (num) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // CSV 데이터를 MySQL에 삽입
            while ((nextLine = csvReader.readNext()) != null) {
                // CSV 파일의 각 라인에서 필요한 데이터 추출
                String column1Value = nextLine[0];

                // PreparedStatement에 데이터 바인딩
                preparedStatement.setString(1, column1Value);

                // 데이터베이스에 쿼리 실행
                preparedStatement.executeUpdate();
            }

            // 리소스 정리
            preparedStatement.close();
            csvReader.close();
            reader.close();
            connection.close();

            System.out.println("CSV 데이터가 MySQL에 성공적으로 삽입되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("CSV 데이터를 MySQL에 삽입하는 동안 오류가 발생했습니다.");
        }
    }
}