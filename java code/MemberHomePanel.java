import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Vector;
import javax.swing.*;

// 名叫 MemberHomePanel 的類別，繼承自 JPanel，是內容面板中的會員主頁
class MemberHomePanel extends JPanel {
    // 儲存登入會員的 ID
    private int memberId;

    // 建構子，接收會員 ID 作為參數
    public MemberHomePanel(int memberId) {
        this.memberId = memberId;
        
        // 使用 BorderLayout 作為版面配置
        setLayout(new BorderLayout());

        // 建立一個分頁面板，用來顯示不同的會員相關資訊
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // 在分頁面板中加入會員資料頁面，連到會員資料方法，回傳面板
        tabbedPane.addTab("會員資料", createMemberInfoPanel());
        // 在分頁面板中加入領養紀錄頁面，連到領養紀錄方法，回傳面板
        tabbedPane.addTab("領養紀錄", createAdoptionPanel());
        // 在分頁面板中加入捐贈紀錄頁面，連到捐贈紀錄方法，回傳面板
        tabbedPane.addTab("捐贈紀錄", createDonationRecordPanel());

        // 建立一個登出按鈕
        JButton logoutButton = new JButton("登出");
        // 設定登出按鈕的點擊事件處理器
        logoutButton.addActionListener(e -> {
            // 找到目前面板所在的最上層視窗
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            // 關閉這個視窗，登出後退出目前畫面
            topFrame.dispose();
            // 重新開啟 MainFrame，也就是跳到 Welcome 畫面
            new MainFrame();
        });

        // 將分頁面板放在中間區域
        add(tabbedPane, BorderLayout.CENTER);
        // 將登出按鈕放在下方區域
        add(logoutButton, BorderLayout.SOUTH);
    }

    // 建立會員資料面板方法
    private JPanel createMemberInfoPanel() {
        // 建立會員資料面板，使用 BorderLayout 排版
        JPanel panel = new JPanel(new BorderLayout());

        // 顯示會員編號的標籤
        JLabel memberIdLabel = new JLabel("會員編號:"+memberId);
        //字體為SansSerif，粗體，大小16pt
        memberIdLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        // 文字置中顯示
        memberIdLabel.setHorizontalAlignment(SwingConstants.CENTER); 

        // 建立一個 8 行 2 欄 的 GridLayout 表單面板
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        // setBorder(...)設定這個JPanel的邊框，建立一個只有空白間距的邊框（上10、左10、下3、右10 像素）
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 3, 10));

        // 建立各個欄位的標籤
        JLabel nameLabel = new JLabel("姓名:");
        JLabel phoneLabel = new JLabel("電話:");
        JLabel genderLabel = new JLabel("性別:");
        JLabel addressLabel = new JLabel("地址:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("密碼:");

        // 建立各個欄位的輸入框
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField genderField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        // 更新按鈕
        JButton updateBtn = new JButton("更新資料");

        // 嘗試連線資料庫
        try (Connection conn = DBConnection.getConnection()){
            // 建立一個SQL查詢語句字串，從 Member 資料表中找出一樣的memberId，並顯示會員資料
            String sql = "SELECT name, phone, gender, address, email, password FROM member WHERE memberId = ?";
            // 建立一個預備查詢物件
            PreparedStatement ps = conn.prepareStatement(sql);
            // 將 memberId 傳入SQL的?位置
            ps.setInt(1, memberId);
            // 用剛剛設定好的rs執行查詢
            ResultSet rs = ps.executeQuery();

            // 如果有回傳結果，設定各欄位的Field顯示取得的資料
            if (rs.next()) {
                nameField.setText(rs.getString("name"));
                phoneField.setText(rs.getString("phone"));
                genderField.setText(rs.getString("gender"));
                addressField.setText(rs.getString("address"));
                emailField.setText(rs.getString("email"));
                passwordField.setText(rs.getString("password"));
            }
        } catch (SQLException e) {
            // 資料庫錯誤處理
            JOptionPane.showMessageDialog(this, "連線失敗，無法顯出會員資料");
            e.printStackTrace();
        }

        // 為更新按鈕加上事件監聽器，點擊時將更新內容寫入資料庫
        updateBtn.addActionListener(e -> {
            // 嘗試連線資料庫
            try (Connection conn = DBConnection.getConnection()){
                // 建立一個SQL查詢語句字串，更新member這張資料表，找到一樣的memberId並更新其資料內容
                String sql = "UPDATE member SET name=?, phone=?, gender=?, address=?, email=?, password=? WHERE memberId=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                // 將各欄位資料傳入SQL的?位置
                ps.setString(1, nameField.getText());
                ps.setString(2, phoneField.getText());
                ps.setString(3, genderField.getText());
                ps.setString(4, addressField.getText());
                ps.setString(5, emailField.getText());
                ps.setString(6, new String(passwordField.getPassword()));
                ps.setInt(7, memberId);
                //執行SQL
                ps.executeUpdate();
                //顯示更新成功告訴使用者
                JOptionPane.showMessageDialog(this, "更新成功!");
            } catch (SQLException ex) {
                // 資料庫錯誤處理
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "更新失敗!");
            }
        });

        // 加入各個欄位到表單面板
        formPanel.add(nameLabel); formPanel.add(nameField);
        formPanel.add(phoneLabel); formPanel.add(phoneField);
        formPanel.add(genderLabel); formPanel.add(genderField);        
        formPanel.add(addressLabel); formPanel.add(addressField);
        formPanel.add(emailLabel); formPanel.add(emailField);
        formPanel.add(passwordLabel); formPanel.add(passwordField);
        // 空白格 + 按鈕
        formPanel.add(new JLabel()); formPanel.add(updateBtn);

        // 會員資料面板上方顯示會員編號
        panel.add(memberIdLabel, BorderLayout.NORTH);
        // 會員資料面板中央顯示表單面板
        panel.add(formPanel, BorderLayout.CENTER);
        //回傳會員資料面板
        return panel;
    }

    // 建立領養紀錄面板方法
    private JPanel createAdoptionPanel() {
        // 建立領養紀錄面板，使用 BorderLayout 排版
        JPanel panel = new JPanel(new BorderLayout());
        // 建立顯示紀錄的表格
        JTable table = new JTable();

        // 嘗試連線資料庫
        try (Connection conn = DBConnection.getConnection()){
            // 建立一個SQL查詢語句字串，從 adoptionrecord和straydog資料表中，找一樣的狗狗id，一樣的memberId，並顯示狗狗id、狗狗名稱、領養日期
            String sql = "SELECT sd.dogId, sd.dogName, ar.adoptDate FROM adoptionrecord ar JOIN straydog sd ON ar.dogId = sd.dogId WHERE ar.memberId = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, memberId);
            //執行
            ResultSet rs = ps.executeQuery();
            // 將查詢結果轉換成表格的資料模型，並套用到 JTable
            table.setModel(buildTableModel(rs));

            // 為表格加上一個滑鼠事件監聽器，處理使用者在表格上的滑鼠點擊行為
            table.addMouseListener(new MouseAdapter() {
                    //滑鼠點擊時，這個方法會被呼叫
                    public void mouseClicked(MouseEvent e) {
                    //從 table 取得目前被選取的列號傳進row變數
                    int row = table.getSelectedRow();
                    //getValueAt(row, 0)回傳該列第0欄的資料，轉成字串再轉成整數
                    int dogId = Integer.parseInt(table.getValueAt(row, 0).toString());
                    //呼叫單一領養紀錄詳細資料方法並傳dogId進去
                    showAdoptionDetails(dogId);
                }
            });
        } catch (SQLException e) {
            // 資料庫錯誤處理
            e.printStackTrace();
        }
        // 領養紀錄面板中央顯示領養資料，超出範圍會自動顯示卷軸查看完整內容
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        //回傳領養紀錄面板
        return panel;
    }

    // 顯示單一領養紀錄詳細資料方法
    private void showAdoptionDetails(int dogId) {
        // 嘗試連線資料庫
        try (Connection conn = DBConnection.getConnection()){
            // 建立一個SQL查詢語句字串，查詢在adoptionrecord還有straydog資料表中dogId一樣的，且memberId、dogId都要跟?一樣，印出查詢結果
            String sql = "SELECT sd.dogId, sd.dogName, sd.breed, sd.age, sd.gender, ar.adoptLocation, ar.adoptDate, ar.adoptStatus " +
                         "FROM adoptionrecord ar JOIN straydog sd ON ar.dogId = sd.dogId WHERE ar.memberId = ? AND ar.dogId = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, memberId);
            ps.setInt(2, dogId);
            //執行
            ResultSet rs = ps.executeQuery();
            // 將rs丟到buildTableModel方法轉為表格資料模型
            JTable detailTable = new JTable(buildTableModel(rs));
            // 將detailTable放入scrollPane
            JScrollPane scrollPane = new JScrollPane(detailTable);
            // 用 JOptionPane 顯示表格和標題
            JOptionPane optionPane = new JOptionPane(scrollPane, JOptionPane.PLAIN_MESSAGE);

            // 建立一個對應的對話框，放表格和標題
            JDialog dialog = optionPane.createDialog(this, "領養詳細資訊");
            //外框大小(寬750, 長300)
            dialog.setSize(750, 300); 
            // 讓它顯示在畫面中央
            dialog.setLocationRelativeTo(this); 
            // 顯示出來
            dialog.setVisible(true);

        } catch (SQLException e) {
            // 資料庫錯誤處理
            e.printStackTrace();
        }
    }

    // 建立領養紀錄面板方法
    private JPanel createDonationRecordPanel() {
        // 建立領養紀錄面板，使用 BorderLayout 排版
        JPanel panel = new JPanel(new BorderLayout());
        // 用字串陣列放入日期項目
        String[] options = {"全部", "近一週", "近一個月", "近六個月"};
        // 建立一個下拉選單，並把options內容填入
        JComboBox<String> timeBox = new JComboBox<>(options);
        // 建立一個搜尋按鈕
        JButton searchBtn = new JButton("查詢");
        // 建立顯示領養紀錄的表格
        JTable table = new JTable();

        //建立一個放在最上方的面板，並把timeBox跟searchBtn都放進去
        JPanel top = new JPanel();
        top.add(timeBox);
        top.add(searchBtn);

        // 查詢按鈕的事件處理
        searchBtn.addActionListener(e -> {
            //連線資料庫
            try (Connection conn = DBConnection.getConnection()){
                // 取得使用者在下拉選單 timeBox 中選擇的選項
                String selection = (String) timeBox.getSelectedItem();
                // 建立一個SQL查詢語句字串，在donationrecord查memberId一樣的，印出所有資料
                String sql = "SELECT donationId, item, quantity, donationDate FROM donationrecord WHERE memberId = ?";
                // 取得今天的日期，作為篩選時間區間的基準
                LocalDate date = LocalDate.now();
                // 如果使用者不是選「全部」，就進行時間條件篩選
                if (!selection.equals("全部")) {
                    // 根據選項減去對應的時間，周、月、六月
                    if (selection.equals("近一週")) date = date.minusWeeks(1);
                    else if (selection.equals("近一個月")) date = date.minusMonths(1);
                    else if (selection.equals("近六個月")) date = date.minusMonths(6);
                    // 將 SQL 語句加上篩選條件：只查詢某時間之後的紀錄
                    sql += " AND donationDate >= ?";
                }
                // 建立一個預備查詢物件
                PreparedStatement ps = conn.prepareStatement(sql);
                // memberId丟進第一個問號
                ps.setInt(1, memberId);
                // 如果有時間篩選條件，就設定第二個參數
                if (!selection.equals("全部")) ps.setDate(2, Date.valueOf(date));
                //執行
                ResultSet rs = ps.executeQuery();
                // 將查詢結果丟到buildTableModel方法轉換為 JTable 可用的表格模型，顯示在畫面上
                table.setModel(buildTableModel(rs));
            } catch (SQLException ex) {
                // 資料庫錯誤處理
                ex.printStackTrace();
            }
        });

        // 將top跟新的table資料放到領養紀錄面板
        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        //回傳領養紀錄面板
        return panel;
    }
    // 將 ResultSet 轉為 JTable 可用的 TableModel 方法
    private static javax.swing.table.DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        // 從 ResultSet 取得資料表的欄位資訊
        ResultSetMetaData meta = rs.getMetaData();
        // 取得欄位總數
        int columnCount = meta.getColumnCount();

        // 用來存放欄位名稱的向量
        Vector<String> columnNames = new Vector<>();
        // 將每一個欄位名稱加到 columnNames 向量中（從第 1 欄開始）
        for (int i = 1; i <= columnCount; i++) columnNames.add(meta.getColumnName(i));

        // 用來存放每一筆資料列的向量
        Vector<Vector<Object>> data = new Vector<>();
        // 當還有下一筆資料時，逐筆讀取每一列
        while (rs.next()) {
            // 建立一個向量來存放這一列的欄位資料
            Vector<Object> row = new Vector<>();
            // 將這一列中每一欄的資料加入 row 向量中
            for (int i = 1; i <= columnCount; i++) row.add(rs.getObject(i));
            // 把整列資料加入總資料集合 data 中
            data.add(row);
        }
        // 將資料與欄位名稱傳入 DefaultTableModel，回傳給 JTable 使用
        return new javax.swing.table.DefaultTableModel(data, columnNames);
    }
}
