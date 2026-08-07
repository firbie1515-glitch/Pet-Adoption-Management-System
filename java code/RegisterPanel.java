import java.awt.*;
import java.sql.*;
import javax.swing.*;

// 名叫 RegisterPanel 的類別，繼承自 JPanel，是主面板中的會員註冊畫面
public class RegisterPanel extends JPanel {
    // 建構子，參數是 MainFrame 的參考，可以呼叫 MainFrame 裡的方法
    public RegisterPanel(MainFrame mainFrame) {
        // 使用 BorderLayout 作為版面配置
        setLayout(new BorderLayout());
        // 建立一個 formPanel 面板來放置輸入欄位，使用 GridBagLayout 排版
        JPanel formPanel = new JPanel(new GridBagLayout());

        // 建立一個格子排版設定的物件，會告訴GridBagLayout，每加入一個元件前，都會先設定一次gbc
        GridBagConstraints gbc = new GridBagConstraints();
        // 設定元件四周的外邊距（空白）上下左右各為 5 像素
        gbc.insets = new Insets(5, 5, 5, 5);
        // 設定元件在橫向（水平）上填滿所在格子的寬度
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 建立name、phone、gennder、address、email輸入欄位，欄位寬度15
        JTextField name = new JTextField(15);
        JTextField phone = new JTextField(15);
        JTextField gender = new JTextField(15);
        JTextField address = new JTextField(15);
        JTextField email = new JTextField(15);
        // 建立password輸入欄位，欄位寬度15
        JPasswordField password = new JPasswordField(15);

        // 使用變數 y 控制欄位的垂直位置，每一列遞增
        int y = 0;

        // 設定這個姓名標籤要放在第0欄、第0列，輸入欄位要放在第1欄、第0列
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("姓名"), gbc);
        gbc.gridx = 1; formPanel.add(name, gbc);

        // 設定這個電話標籤要放在第0欄、第1列，輸入欄位要放在第1欄、第1列
        gbc.gridx = 0; gbc.gridy = ++y; formPanel.add(new JLabel("電話"), gbc);
        gbc.gridx = 1; formPanel.add(phone, gbc);

        // 設定這個性別標籤要放在第0欄、第2列，輸入欄位要放在第1欄、第2列
        gbc.gridx = 0; gbc.gridy = ++y; formPanel.add(new JLabel("性別"), gbc);
        gbc.gridx = 1; formPanel.add(gender, gbc);

        // 設定這個地址標籤要放在第0欄、第3列，輸入欄位要放在第1欄、第3列
        gbc.gridx = 0; gbc.gridy = ++y; formPanel.add(new JLabel("地址"), gbc);
        gbc.gridx = 1; formPanel.add(address, gbc);

        // 設定這個Email標籤要放在第0欄、第4列，輸入欄位要放在第1欄、第4列
        gbc.gridx = 0; gbc.gridy = ++y; formPanel.add(new JLabel("Email"), gbc);
        gbc.gridx = 1; formPanel.add(email, gbc);

        // 設定這個密碼標籤要放在第0欄、第5列，輸入欄位要放在第1欄、第5列
        gbc.gridx = 0; gbc.gridy = ++y; formPanel.add(new JLabel("密碼"), gbc);
        gbc.gridx = 1; formPanel.add(password, gbc);

        // 建立註冊按鈕
        JButton registerButton = new JButton("註冊");
        // 建立返回按鈕
        JButton backButton = new JButton("返回");

        // 建立一個 btnPanel 按鈕面板，將兩個按鈕加進去
        JPanel btnPanel = new JPanel();
        btnPanel.add(registerButton);
        btnPanel.add(backButton);

        // 將 formPanel 面板加到中央區域
        add(formPanel, BorderLayout.CENTER);
        // 將 btnPanel 面板加到底部區域
        add(btnPanel, BorderLayout.SOUTH);

        // 註冊按鈕點擊事件處理
        registerButton.addActionListener(e -> {
            // 嘗試連線資料庫
            try (Connection conn = DBConnection.getConnection()) {
                // 建立一個SQL新增字串，將資料新增進Member資料表
                String sql = "INSERT INTO Member (memberId, name, phone, gender, address, email, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
                // 建立一個預備查詢物件
                PreparedStatement ps = conn.prepareStatement(sql);
                // 將 getNextMemberId 回傳的memberId傳入SQL的第1個?位置
                ps.setInt(1, getNextMemberId(conn));
                // 將 name、phone、gennder、address、email 傳入SQL的第2~6個?位置
                ps.setString(2, name.getText());
                ps.setString(3, phone.getText());
                ps.setString(4, gender.getText());
                ps.setString(5, address.getText());
                ps.setString(6, email.getText());
                // 將密碼轉為字串(原為char)傳入SQL的第7個?位置
                ps.setString(7, new String(password.getPassword()));
                // 用剛剛設定好的ps執行查詢
                ps.executeUpdate();
                // 顯示註冊成功的訊息
                JOptionPane.showMessageDialog(this, "註冊成功");
                // 返回登入畫面
                mainFrame.showLoginPanel();
            } catch (SQLException ex) {
                // 資料庫錯誤處理
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "註冊失敗\n" + ex.getMessage());
            }
        });
        // 為返回按鈕加上事件監聽器，點擊時切換到歡迎畫面（呼叫 MainFrame 中的 showWelcomePanel）
        backButton.addActionListener(e -> mainFrame.showWelcomePanel());
    }
    // 用來從資料庫取得下一個 memberId 的私有方法
    private int getNextMemberId(Connection conn) throws SQLException {
        // 建立一個 基本 SQL 執行物件，放入變數用來執行查詢
        Statement stmt = conn.createStatement();
        // 查詢目前最大的 memberId
        ResultSet rs = stmt.executeQuery("SELECT MAX(memberId) FROM Member");
        // 若查詢有結果，且結果第一欄不為零
        if (rs.next() && rs.getInt(1) != 0) {
            //回傳查到的結果加1
            return rs.getInt(1) + 1;
        } else {
            //沒查到資料則從100開始
            return 100; 
        }
    }
}