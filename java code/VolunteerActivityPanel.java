import java.awt.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.*;

// 名叫 VolunteerActivityPanel 的類別，繼承自 JPanel，是內容面板中的志工活動
public class VolunteerActivityPanel extends JPanel {
    //建構子
    public VolunteerActivityPanel() {
        // 設定面板使用 3 行 1 欄的 GridLayout（每個搜尋條件一列）
        setLayout(new GridLayout(3, 1));

        // 加入三個搜尋子面板，分別根據活動編號、活動名稱和活動日期搜尋
        add(createSearchTab("活動編號", 
            "SELECT activityId, activityName, activityType, activityDate, activityLocation FROM volunteeractivity WHERE activityId = ?"));
        add(createSearchTab("活動名稱", 
            "SELECT activityId, activityName, activityType, activityDate, activityLocation FROM volunteeractivity WHERE activityName LIKE CONCAT('%', ?, '%')"));
        add(createSearchTab("活動日期", 
            "SELECT activityId, activityName, activityType, activityDate, activityLocation FROM volunteeractivity WHERE activityDate = ?"));
    }

    // 建立搜尋子面板的方法，丟入標籤文字與對應 SQL 語句
    private JPanel createSearchTab(String label, String sql) {
        // 建立子面板
        JPanel panel = new JPanel(new BorderLayout());
        // 建立輸入框，長度為 20 字元
        JTextField input = new JTextField(20);
        // 建立查詢按鈕
        JButton searchBtn = new JButton("查詢");
        // 建立用於顯示查詢結果的 JTable
        JTable resultTable = new JTable();

        // 建立上方小面板
        JPanel top = new JPanel();
        top.add(new JLabel(label));// 加入顯示搜尋條件名稱的標籤
        top.add(input);// 加入輸入框
        top.add(searchBtn);// 加入查詢按鈕

        // 設定查詢按鈕點擊事件
        searchBtn.addActionListener(e -> {
            try (Connection conn = DBConnection.getConnection())  {
                PreparedStatement ps = conn.prepareStatement(sql);//預備執行
                ps.setString(1, input.getText()); //將輸入的內容丟入?
                ResultSet rs = ps.executeQuery(); //執行
                // 將rs丟到buildTableModel方法轉為表格資料模型
                resultTable.setModel(buildTableModel(rs));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // 將上方輸入區放入子面板的上方
        panel.add(top, BorderLayout.NORTH);
        // 將 JTable 包裹在捲軸面板中，放入子面板中央
        panel.add(new JScrollPane(resultTable), BorderLayout.CENTER);
        // 回傳整個搜尋子面板
        return panel;
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