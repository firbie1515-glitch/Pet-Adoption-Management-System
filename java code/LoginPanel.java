import java.awt.*;
import java.sql.*;
import javax.swing.*;

// 名叫 oginPanel 的類別，繼承自 JPanel，是主面版中的登入頁面
public class LoginPanel extends JPanel {
    // 建構子，參數是 MainFrame 的參考，可以呼叫 MainFrame 裡的方法
    public LoginPanel(MainFrame mainFrame) {
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

        // 建立email輸入欄位，欄位寬度15
        JTextField emailField = new JTextField(15);
        // 建立password輸入欄位，欄位寬度15
        JPasswordField passwordField = new JPasswordField(15);

        // 設定這個Email標籤要放在第0欄、第0列；輸入欄位要放在第1欄、第0列
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; formPanel.add(emailField, gbc);

        // 設定這個密碼標籤要放在第0欄、第1列，輸入欄位要放在第1欄、第1列
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("密碼:"), gbc);
        gbc.gridx = 1; formPanel.add(passwordField, gbc);

        // 建立登入按鈕
        JButton loginButton = new JButton("登入");
        // 建立返回按鈕
        JButton backButton = new JButton("返回");

        // 建立一個 btnPanel 按鈕面板，將兩個按鈕加進去
        JPanel btnPanel = new JPanel();
        btnPanel.add(loginButton);
        btnPanel.add(backButton);

        // 將 formPanel 面板加到中央區域
        add(formPanel, BorderLayout.CENTER);
        // 將 btnPanel 面板加到底部區域
        add(btnPanel, BorderLayout.SOUTH);

        // 登入按鈕點擊事件處理
        loginButton.addActionListener(e -> {
            // 嘗試連線資料庫
            try (Connection conn = DBConnection.getConnection()) {
                // 建立一個SQL查詢語句字串，從 Member 資料表中找出符合指定 email 和密碼的會員 ID
                String sql = "SELECT memberId FROM Member WHERE email = ? AND password = ?";
                // 建立一個預備查詢物件
                PreparedStatement ps = conn.prepareStatement(sql);
                // 將 email 傳入SQL的第1個?位置
                ps.setString(1, emailField.getText());
                // 將密碼轉為字串(原為char)傳入SQL的第2個?位置
                ps.setString(2, new String(passwordField.getPassword()));
                // 用剛剛設定好的ps執行查詢
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {// 如果有回傳結果
                    //取得 memberId，表示登入成功
                    int memberId = rs.getInt("memberId");
                    // 呼叫 MainFrame 中的 loginSuccess
                    mainFrame.loginSuccess(memberId);
                } else {
                    // 沒有找到符合帳密的會員，顯示錯誤訊息
                    JOptionPane.showMessageDialog(this, "帳號或密碼錯誤");
                }
            } catch (SQLException ex) {
                // 資料庫錯誤處理
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "登入失敗");
            }
        });
        // 為返回按鈕加上事件監聽器，點擊時切換到歡迎畫面（呼叫 MainFrame 中的 showWelcomePanel）
        backButton.addActionListener(e -> mainFrame.showWelcomePanel());
    }
}