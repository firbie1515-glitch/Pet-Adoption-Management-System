import javax.swing.*;

// 定義一個 PetAdoptionSystem 的類別，這是程式的進入點
public class PetAdoptionSystem {
    // Java 程式的主方法，從這裡開始執行
    public static void main(String[] args) {
        // 使用 SwingUtilities.invokeLater 確保 UI 相關的操作在事件派發執行緒上執行
        // 在 UI 執行緒中建立一個新的 MainFrame（主視窗），顯示出 GUI
        SwingUtilities.invokeLater(() -> new MainFrame());

    }
}