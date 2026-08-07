import java.awt.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.*;

// 名叫 DogSearchPanel 的類別，繼承自 JPanel，是內容面板中的流浪狗查詢
public class DogSearchPanel extends JPanel {

    // 建構子
    public DogSearchPanel() {
        setLayout(new BorderLayout());

        // 建立搜尋條件的下拉選單資料，放入字串陣列
        String[] locations = {"全部", "新北市板橋區", "新北市中和區", "新北市樹林區"};
        String[] genders = {"全部", "公", "母"};
        String[] breeds = {"全部", "哈士奇", "柴犬", "馬爾濟斯", "黃金獵犬", "吉娃娃"};

        // 使用下拉式選單建立搜尋條件元件
        JComboBox<String> locBox = new JComboBox<>(locations);
        JComboBox<String> genderBox = new JComboBox<>(genders);
        JComboBox<String> breedBox = new JComboBox<>(breeds);
        //建立搜尋按鈕
        JButton searchBtn = new JButton("搜尋");

        // 建立放置搜尋列的面板
        JPanel top = new JPanel();
        top.add(new JLabel("地點:")); // 加入地點標籤
        top.add(locBox); // 加入地點下拉選單
        top.add(new JLabel("性別:")); // 加入性別標籤
        top.add(genderBox); // 加入性別下拉選單
        top.add(new JLabel("品種:")); // 加入品種標籤
        top.add(breedBox); // 加入品種下拉選單
        top.add(searchBtn); // 加入搜尋按鈕

        // 建立顯示查詢結果的表格
        JTable resultTable = new JTable();
        // 用滾動面板包住表格
        JScrollPane scrollPane = new JScrollPane(resultTable);

        // 搜尋按鈕的事件處理器
        searchBtn.addActionListener(e -> {
            try (Connection conn = DBConnection.getConnection()) {
                // 建立一個SQL查詢語句字串，根據選擇條件查詢可被領養的流浪狗資料
                String sql = "SELECT sd.dogId, sd.dogName, ir.availableLocation, sd.gender, sd.breed, sd.age, sd.medicalStatus " +
                        "FROM straydog sd JOIN intakerecord ir ON sd.dogId = ir.dogId " +
                        "WHERE sd.isAdoptable = 1 AND (? = '全部' OR ir.availableLocation = ?) " +
                        "AND (? = '全部' OR sd.gender = ?) AND (? = '全部' OR sd.breed = ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                // 設置條件值
                String loc = (String) locBox.getSelectedItem();
                String gender = (String) genderBox.getSelectedItem();
                String breed = (String) breedBox.getSelectedItem();
                ps.setString(1, loc); // 判斷地點是否為全部
                ps.setString(2, loc); // 如果不是全部，則匹配該地點
                ps.setString(3, gender); // 判斷性別是否為全部
                ps.setString(4, gender); // 如果不是全部，則匹配該性別
                ps.setString(5, breed); // 判斷品種是否為全部
                ps.setString(6, breed); // 如果不是全部，則匹配該品種
                // 執行
                ResultSet rs = ps.executeQuery();
                // 將rs丟到buildTableModel方法轉為表格資料模型
                resultTable.setModel(buildTableModel(rs));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        // 將搜尋列放在上方
        add(top, BorderLayout.NORTH);
        // 將結果表格放在中央
        add(scrollPane, BorderLayout.CENTER);
    }

    // 將 ResultSet 轉為 JTable 可用的 TableModel 方法，註解同MemberHomePanel最下方之buildTableModel方法
    private static javax.swing.table.DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();

        Vector<String> columnNames = new Vector<>();
        for (int i = 1; i <= columnCount; i++) columnNames.add(meta.getColumnName(i));

        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            for (int i = 1; i <= columnCount; i++) row.add(rs.getObject(i));
            data.add(row);
        }

        return new javax.swing.table.DefaultTableModel(data, columnNames);
    }
}
