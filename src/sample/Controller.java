package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.*;

public class Controller {
    Connection conn;

    @FXML
    Button btnConnect;
    @FXML
    TextField tfData;
    @FXML
    ListView lvDBData;
    @FXML
    Button btnSaveToDB;

    public boolean connectToDB(){
        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/GB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "rootROOT");
            btnConnect.setDisable(true);
            btnSaveToDB.setDisable(false);
            reloadData();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public void saveDataToDB() {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String query = "insert into tTest (TestValue) values (\"" + tfData.getText() + "\")";
            stmt.executeUpdate(query);
            tfData.setText("");
            reloadData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void reloadData() {
        Statement stmt = null;
        try {
            lvDBData.getItems().clear();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select TestValue from tTest order by 1");
            while (rs.next()) {
                String str = rs.getString("TestValue");
                lvDBData.getItems().add(lvDBData.getItems().size(), str);
                lvDBData.scrollTo(str);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
