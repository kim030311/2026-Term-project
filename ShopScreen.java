import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ShopScreen extends JPanel {
    private MainFrame frame;
    private JLabel tokenLabel;
    private HashMap<String, Integer> itemShop; // Map 기반 상점 아이템 명세

    public ShopScreen(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        // -------------------------------------------------------------
        // 1. 상점 데이터 세팅 (Map 자료구조 활용)
        // -------------------------------------------------------------
        itemShop = new HashMap<>();
        itemShop.put("창문", 40);
        itemShop.put("책장", 50);
        itemShop.put("소파", 60);
        itemShop.put("전등", 80);
        itemShop.put("펫", 100);

        // -------------------------------------------------------------
        // 2. 상단 바 영역 구성 (타이틀, 보유 토큰, 돌아가기 버튼)
        // -------------------------------------------------------------
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("  가구 상점 ", JLabel.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        tokenLabel = new JLabel("보유 토큰: " + frame.getTokens() + "개  ");
        tokenLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        
        JButton btnBack = new JButton("돌아가기");
        btnBack.addActionListener(e -> frame.changeScreen("Home"));

        topPanel.add(titleLabel, BorderLayout.CENTER);
        topPanel.add(tokenLabel, BorderLayout.WEST);
        topPanel.add(btnBack, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // -------------------------------------------------------------
        // 3. 중앙 영역: 아이템 목록 그리드 및 구매 레이아웃 빌드
        // -------------------------------------------------------------
        JPanel itemGrid = new JPanel(new GridLayout(itemShop.size(), 1, 5, 5));
        
        for (String itemName : itemShop.keySet()) {
            int price = itemShop.get(itemName);
            
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBorder(BorderFactory.createEtchedBorder());
            
            JLabel itemInfo = new JLabel("  " + itemName + " (가격: " + price + " 토큰)");
            itemInfo.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
            
            JButton btnBuy = new JButton("구매하기");
            btnBuy.addActionListener(e -> {
                // 이미 구매했는지 예외 검사
                if (frame.getMyItems().contains(itemName)) {
                    JOptionPane.showMessageDialog(this, "이미 구매한 아이템입니다!");
                    return;
                }
                
                // 토큰 차감 여부에 따른 조건문 분기 처리 및 경고 팝업
                if (frame.spendTokens(price)) {
                    frame.getMyItems().add(itemName);
                    tokenLabel.setText("보유 토큰: " + frame.getTokens() + "개  ");
                    JOptionPane.showMessageDialog(this, itemName + " 구매 완료!");
                } else {
                    // 토큰 부족 시 경고 창
                    JOptionPane.showMessageDialog(this, "토큰이 부족하여 구매할 수 없습니다!", "잔액 부족", JOptionPane.WARNING_MESSAGE);
                }
            });

            itemPanel.add(itemInfo, BorderLayout.CENTER);
            itemPanel.add(btnBuy, BorderLayout.EAST);
            itemGrid.add(itemPanel);
        }

        // 아이템 목록이 많아질 것에 대비해 스크롤이 가능한 구조로 배치
        add(new JScrollPane(itemGrid), BorderLayout.CENTER);
    }

    // 상점 화면이 다시 열릴(렌더링될) 때마다 최신 토큰 상태를 동기화
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        tokenLabel.setText("보유 토큰: " + frame.getTokens() + "개  ");
    }
}