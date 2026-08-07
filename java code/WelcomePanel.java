import java.awt.*;
import javax.swing.*;

// 名叫 WelcomePanel 的類別，繼承自 JPanel，是主面板中的歡迎頁面
public class WelcomePanel extends JPanel {
    // 建構子，參數是 MainFrame 的參考，可以呼叫 MainFrame 裡的方法
    public WelcomePanel(MainFrame mainFrame) {
        // setLayout(...)設定這個JPanel的排版方式，使用 BoxLayout設定版面配置為垂直排列（由上到下排）
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // setBorder(...)設定這個JPanel的邊框，建立一個只有空白間距的邊框（上130、左100、下100、右100 像素）
        setBorder(BorderFactory.createEmptyBorder(130, 100, 100, 100));

        // 建立一個標籤名叫title，顯示歡迎文字，並讓文字置中對齊
        JLabel title = new JLabel("歡迎使用寵物領養系統", SwingConstants.CENTER);
        // 讓標籤在垂直方向置中對齊
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 建立「會員登入」按鈕
        JButton loginButton = new JButton("會員登入");
        // 建立「會員註冊」按鈕
        JButton registerButton = new JButton("會員註冊");

        // 讓按鈕在垂直方向置中對齊
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 為登入按鈕加上事件監聽器，點擊時切換到登入畫面（呼叫 MainFrame 中的 showLoginPanel）
        loginButton.addActionListener(e -> mainFrame.showLoginPanel());
        // 為註冊按鈕加上事件監聽器，點擊時切換到註冊畫面（呼叫 MainFrame 中的 showRegisterPanel）
        registerButton.addActionListener(e -> mainFrame.showRegisterPanel());
        
        // 將元件依序加到面板中，排列順序就是畫面顯示順序
        add(title);// 加入標題
        // 加入垂直間隔 30 像素的空白區域
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(loginButton);// 加入登入按鈕
        // 加入垂直間隔 10 像素的空白區域
        add(Box.createRigidArea(new Dimension(0, 10)));
        // 加入註冊按鈕
        add(registerButton);
    }
}