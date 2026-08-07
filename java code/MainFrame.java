import java.awt.*;
import javax.swing.*;

// 定義一個名為 MainFrame 的類別，繼承自 JFrame，代表主視窗
public class MainFrame extends JFrame {
    // 宣告一個 cardLayout 變數，用來管理多個面板的切換
    private CardLayout cardLayout;
    // 宣告一個主面板 mainPanel，稍後會放多個子面板（卡片）
    private JPanel mainPanel;

    //MainFrame的建構子：當建立MainFrame物件時會執行這裡的程式碼
    public MainFrame() {
        // 設定視窗的標題為「寵物領養系統」
        setTitle("寵物領養系統");
        // 設定視窗的寬度為 500，高度為 400 像素
        setSize(800, 440); 
        // 設定當使用者關閉視窗時，整個程式會結束
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // 將視窗顯示在螢幕中央
        setLocationRelativeTo(null);

        // 建立一個 CardLayout，並指定給稍後要用的主面板
        cardLayout = new CardLayout();
        // 建立主面板，使用 cardLayout 來管理多個子畫面（頁面切換）
        mainPanel = new JPanel(cardLayout);

        // 在主面板中加入歡迎畫面，名稱為 "welcome"，稍後可以用這個名稱切換
        mainPanel.add(new WelcomePanel(this), "welcome");
        // 在主面板中加入登入畫面，名稱為 "login"
        mainPanel.add(new LoginPanel(this), "login");
        // 在主面板中加入註冊畫面，名稱為 "register"
        mainPanel.add(new RegisterPanel(this), "register");

        // 將主面板加到視窗上（MainFrame 繼承自 JFrame）
        add(mainPanel);
        // 顯示歡迎畫面（根據名稱 "welcome" 顯示對應的 JPanel）
        cardLayout.show(mainPanel, "welcome");
        // 讓視窗顯示出來
        setVisible(true);
    }

    // 顯示登入畫面的方法
    public void showLoginPanel() {
        // 顯示歡迎畫面（根據名稱 "login" 顯示對應的 JPanel）
        cardLayout.show(mainPanel, "login");
    }

    // 顯示註冊畫面的方法
    public void showRegisterPanel() {
        // 顯示歡迎畫面（根據名稱 "register" 顯示對應的 JPanel）
        cardLayout.show(mainPanel, "register");
    }

    // 顯示歡迎畫面的方法
    public void showWelcomePanel() {
        // 顯示歡迎畫面（根據名稱 "welcome" 顯示對應的 JPanel）
        cardLayout.show(mainPanel, "welcome");
    }

    // 登入成功後呼叫的方法，接收登入會員的 memberId
    public void loginSuccess(int memberId) {
        // 在主面板中加入功能畫面，名稱為 "Unified"，傳入登入會員的 memberId
        mainPanel.add(new UnifiedPanel(memberId), "Unified");
        // 切換顯示到剛加入的 "Unified" 畫面（會員登入後的主頁）
        cardLayout.show(mainPanel, "Unified");
    }
}
