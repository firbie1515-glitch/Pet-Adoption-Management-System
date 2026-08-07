import java.awt.*;
import javax.swing.*;

// 名叫 UnifiedPanel 的類別，繼承自 JPanel，是主面板中的功能畫面
class UnifiedPanel extends JPanel {
    // 建構子，接收會員 ID 作為參數
    public UnifiedPanel(int memberId) {
        // 使用 BorderLayout 作為版面配置
        setLayout(new BorderLayout());

        // 建立側邊選單面板，使用 BorderLayout 排版
        JPanel menuPanel = new JPanel(new BorderLayout());
        
        // 用字串陣列放入功能選單項目
        String[] menuItems = {"流浪狗查詢", "熱門捐贈項目", "志工活動", "會員主頁"};
        // 建立一個名叫menuList的物件，顯示選單項目，每個項目都會顯示為一列
        JList<String> menuList = new JList<>(menuItems);
        // 將選單清單加入到選單面板的中間位置
        menuPanel.add(menuList, BorderLayout.CENTER);
        //將選單面板加上淡灰框線
        menuPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        // setBorder(...)設定這個JPanel的邊框，建立一個只有空白間距的邊框（上3、左3、下3、右3 像素）
        menuList.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        
        // 加入一個標籤物件，標示功能選單
        JLabel titleLabel = new JLabel("功能選單");
        // setBorder(...)設定這個JLabel的邊框，建立一個只有空白間距的邊框（上3、左3、下3、右3 像素）
        titleLabel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        //將標籤加入選單面板的上方
        menuPanel.add(titleLabel, BorderLayout.NORTH);

        // 將選單面板加入主面板的左側（WEST）
        add(menuPanel, BorderLayout.WEST);

        // 建立內容區的面板，使用 CardLayout 來管理多個功能頁面
        JPanel contentPanel = new JPanel(new CardLayout());
        // 在內容面板中加入流浪狗查詢畫面，名稱為 "流浪狗查詢"，稍後可以用這個名稱切換
        contentPanel.add(new DogSearchPanel(), "流浪狗查詢");
        // 在內容面板中加入熱門捐贈項目查詢畫面，名稱為 "熱門捐贈項目"，稍後可以用這個名稱切換
        contentPanel.add(new TopDonationsPanel(), "熱門捐贈項目");
        // 在內容面板中加入志工活動查詢畫面，名稱為 "志工活動"，稍後可以用這個名稱切換
        contentPanel.add(new VolunteerActivityPanel(), "志工活動");
        // 在內容面板中加入會員主頁畫面，傳入 memberId，名稱為 "會員主頁"，稍後可以用這個名稱切換
        contentPanel.add(new MemberHomePanel(memberId), "會員主頁");

        // 將內容面板加到主面板中央
        add(contentPanel, BorderLayout.CENTER);

        // 為選單清單添加選擇事件監聽器
        menuList.addListSelectionListener(e -> {
            // 使用者變動中時回傳true，變動完成回傳flase，避免重複觸發
            if (!e.getValueIsAdjusting()) {
                // 呼叫contentPanel 的 getLayout() 方法取得內容面板使用的Layout Manager轉型為CardLayout
                CardLayout cl = (CardLayout) contentPanel.getLayout();
                // 取得目前選中的功能名稱，並指示CardLayout顯示對應名稱的卡片頁面
                cl.show(contentPanel, menuList.getSelectedValue());
            }
        });
    }
}
