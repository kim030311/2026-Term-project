import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {
    private int tokens = 100; // 초기 토큰 설정
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private List<String> myItems = new ArrayList<>(); // 구매한 가구 목록

    public MainFrame() {
        setTitle("Java GUI 행맨 & 방 꾸미기 텀 프로젝트");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null); // 화면 중앙 배치

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // 기능별 화면(패널) 등록
        mainPanel.add(new HomeScreen(this), "Home");
        mainPanel.add(new GameScreen(this), "Game");
        mainPanel.add(new ShopScreen(this), "Shop");

        add(mainPanel);
        cardLayout.show(mainPanel, "Home"); // 첫 화면 표시
    }

    // 화면 전환 메소드
    public void changeScreen(String screenName) {
        cardLayout.show(mainPanel, screenName);
        // 화면이 전환될 때마다 데이터를 최신 상태로 새로고침
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // 토큰 및 아이템 getter / setter
    public int getTokens() { return tokens; }
    public void addTokens(int amount) { this.tokens += amount; }
    public boolean spendTokens(int amount) {
        if (this.tokens >= amount) {
            this.tokens -= amount;
            return true;
        }
        return false;
    }

    public List<String> getMyItems() { return myItems; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}