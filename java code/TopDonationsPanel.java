import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.*;

// 名叫 TopDonationsPanel 的類別，繼承自 JPanel，是內容面板中的熱門捐贈項目
public class TopDonationsPanel extends JPanel {

    // 建構子
    public TopDonationsPanel() {
        setLayout(new BorderLayout());

        // 建立 JTable 用來顯示捐贈資料
        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);// 加入捲軸

        try (Connection conn = DBConnection.getConnection())  {
            // 建立一個SQL查詢語句字串，找出被捐贈最多次的前五名物品
            String sql = "SELECT item, COUNT(*) AS donationCount FROM donationrecord GROUP BY item ORDER BY donationCount DESC LIMIT 5";
            PreparedStatement ps = conn.prepareStatement(sql);
            //執行
            ResultSet rs = ps.executeQuery();
            // 將rs丟到buildTableModel方法轉為表格資料模型
            table.setModel(buildTableModel(rs));

            // 為表格加入滑鼠點擊事件監聽器
            table.addMouseListener(new MouseAdapter() {
                //滑鼠點擊時，這個方法會被呼叫
                public void mouseClicked(MouseEvent e) {
                    //從 table 取得目前被選取的列號傳進row變數
                    int row = table.getSelectedRow();
                    // 取得該列第一欄
                    String item = table.getValueAt(row, 0).toString();
                    // 將item傳入showDonationDetails方法
                    showDonationDetails(item);
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 上方顯示說明文字
        add(new JLabel("點擊項目查看捐贈者資訊"), BorderLayout.NORTH);
        // 中央顯示表格
        add(scrollPane, BorderLayout.CENTER);
    }

    // 顯示指定項目的捐贈者詳細資料方法
    private void showDonationDetails(String item) {
        try (Connection conn = DBConnection.getConnection()){
            // 建立一個SQL查詢語句字串，取得捐贈該項目的會員名稱（遮蔽）、數量、捐贈日期
            String sql = "SELECT CONCAT(LEFT(m.name, 1), 'O', RIGHT(m.name, 1)) AS maskedName, dr.quantity, dr.donationDate " +
                         "FROM donationrecord dr JOIN member m ON dr.memberId = m.memberId WHERE dr.item = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, item);
            //執行
            ResultSet rs = ps.executeQuery();

            // 建立細節表格並顯示於 JOptionPane 中
            JTable detailTable = new JTable(buildTableModel(rs));
            // 用 JOptionPane 顯示表格和標題
            JOptionPane.showMessageDialog(this, new JScrollPane(detailTable), "捐贈者資訊", JOptionPane.PLAIN_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
