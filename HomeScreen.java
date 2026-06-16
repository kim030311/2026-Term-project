import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class HomeScreen extends JPanel {
    private MainFrame frame;
    private JLabel tokenLabel;
    private JTextArea roomTextArea;
    
    private JLabel backgroundLabel;
    private JLabel bookshelfLabel;
    private JLabel petLabel;
    private JLabel windowLabel;
    private JLabel sofaLabel;
    private JLabel lampLabel;

    public HomeScreen(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        // -------------------------------------------------------------
        // 1. 상단 영역: 타이틀 및 현재 토큰
        // -------------------------------------------------------------
        JPanel topPanel = new JPanel(new BorderLayout());
        
        JLabel titleLabel = new JLabel(" 나만의 방 꾸미기 ", JLabel.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        
        tokenLabel = new JLabel("현재 토큰: " + frame.getTokens() + "개  ");
        tokenLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        
        topPanel.add(titleLabel, BorderLayout.CENTER);
        topPanel.add(tokenLabel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // -------------------------------------------------------------
        // 2. 중앙 영역: JLayeredPane 생성 및 배경 배치
        // -------------------------------------------------------------
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(600, 350));

        // [배경 라벨 생성]
        ImageIcon bgIcon = new ImageIcon("room_bg.png");
        if (bgIcon.getIconWidth() > 0) {
            Image scaledBg = bgIcon.getImage().getScaledInstance(600, 350, Image.SCALE_SMOOTH);
            backgroundLabel = new JLabel(new ImageIcon(scaledBg));
        } else {
            backgroundLabel = new JLabel("<html><center>[ room_bg.png 이미지를 폴더에 넣어주세요 ]<br>기본 방 배경 상태</center></html>", JLabel.CENTER);
            backgroundLabel.setOpaque(true);
            backgroundLabel.setBackground(Color.LIGHT_GRAY);
        }
        backgroundLabel.setBounds(0, 0, 600, 350);
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

        // -------------------------------------------------------------
        // 3-1. 창문 라벨 생성 및 배치
        // -------------------------------------------------------------
        Image transparentWindowImage = makeWindowTransparentAdvanced("window.jpg");

        if (transparentWindowImage != null) {
            Image scaledWin = transparentWindowImage.getScaledInstance(120, 100, Image.SCALE_SMOOTH);
            windowLabel = new JLabel(new ImageIcon(scaledWin));
        } else {
            windowLabel = new JLabel("🏞️ [풍경]", JLabel.CENTER);
            windowLabel.setOpaque(true);
            windowLabel.setBackground(new Color(135, 206, 235)); 
            windowLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        }
        
        windowLabel.setBounds(100, 70, 120, 100);
        windowLabel.setVisible(false); 
        layeredPane.add(windowLabel, JLayeredPane.PALETTE_LAYER);

        // -------------------------------------------------------------
        // 3-2. 책장 라벨 생성 및 배치
        // -------------------------------------------------------------
        ImageIcon shelfIcon = new ImageIcon("bookshelf.png");
        if (shelfIcon.getIconWidth() > 0) {
            Image scaledShelf = shelfIcon.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
            bookshelfLabel = new JLabel(new ImageIcon(scaledShelf));
        } else {
            bookshelfLabel = new JLabel("🗄️ [책장]", JLabel.CENTER);
            bookshelfLabel.setOpaque(true);
            bookshelfLabel.setBackground(new Color(210, 180, 140)); 
            bookshelfLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        }
        
        bookshelfLabel.setBounds(260, 132, 100, 150);
        bookshelfLabel.setVisible(false); 
        layeredPane.add(bookshelfLabel, JLayeredPane.MODAL_LAYER);

        // -------------------------------------------------------------
        // 3-2. 소파 라벨 생성 및 배치
        // -------------------------------------------------------------
        Image transparentSofaImage = makeWhiteTransparent("sofa.png");

        if (transparentSofaImage != null) {
            Image scaledSofa = transparentSofaImage.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
            sofaLabel = new JLabel(new ImageIcon(scaledSofa));
        } else {
            sofaLabel = new JLabel("🛋️ [소파]", JLabel.CENTER);
            sofaLabel.setOpaque(true);
            sofaLabel.setBackground(new Color(255, 192, 203)); 
            sofaLabel.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
        }
        
        sofaLabel.setOpaque(false);
        sofaLabel.setBounds(100, 200, 150, 100);
        sofaLabel.setVisible(false); 
        layeredPane.add(sofaLabel, JLayeredPane.MODAL_LAYER);

        // -------------------------------------------------------------
        // 3-3. 전등 라벨 생성 및 배치
        // -------------------------------------------------------------
        Image transparentLampImage = makeWhiteTransparent("lamp.png");

        if (transparentLampImage != null) {
            // 전등 크기를 아담하게 70x90 크기로 조절합니다.
            Image scaledLamp = transparentLampImage.getScaledInstance(70, 90, Image.SCALE_SMOOTH);
            lampLabel = new JLabel(new ImageIcon(scaledLamp));
        } else {
            lampLabel = new JLabel("💡 [전등]", JLabel.CENTER);
            lampLabel.setOpaque(true);
            lampLabel.setBackground(new Color(255, 255, 224)); // 연노란색 배경
            lampLabel.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        }
        
        lampLabel.setOpaque(false);
        lampLabel.setBounds(300, -20, 70, 90);
        lampLabel.setVisible(false); // 초기 상태는 숨김
        layeredPane.add(lampLabel, JLayeredPane.MODAL_LAYER);

        // -------------------------------------------------------------
        // 3-4. 펫 라벨 생성 및 배치
        // -------------------------------------------------------------
        Image transparentPetImage = makeWhiteTransparent("pet.png");

        if (transparentPetImage != null) {
            Image scaledPet = transparentPetImage.getScaledInstance(80, 120, Image.SCALE_SMOOTH);
            petLabel = new JLabel(new ImageIcon(scaledPet));
        } else {
            petLabel = new JLabel("🐶 [펫]", JLabel.CENTER);
            petLabel.setOpaque(true);
            petLabel.setBackground(new Color(255, 235, 205)); 
            petLabel.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
        }
        
        petLabel.setOpaque(false);
        petLabel.setBounds(360, 232, 80, 120);
        petLabel.setVisible(false); 
        layeredPane.add(petLabel, JLayeredPane.POPUP_LAYER);

        // -------------------------------------------------------------
        // 4. 우측 하단 영역: 보유 가구 목록
        // -------------------------------------------------------------
        roomTextArea = new JTextArea(3, 15);
        roomTextArea.setEditable(false);
        roomTextArea.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        roomTextArea.setBorder(BorderFactory.createTitledBorder("보유 가구 목록"));
        
        JScrollPane scrollPane = new JScrollPane(roomTextArea, 
                JScrollPane.VERTICAL_SCROLLBAR_NEVER, 
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        JPanel textWrapper = new JPanel(new BorderLayout());
        textWrapper.add(scrollPane, BorderLayout.CENTER);
        textWrapper.setBounds(430, 240, 150, 90);
        
        layeredPane.add(textWrapper, JLayeredPane.DRAG_LAYER); 

        add(layeredPane, BorderLayout.CENTER);

        // -------------------------------------------------------------
        // 5. 하단 영역: 메뉴 이동 버튼들
        // -------------------------------------------------------------
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        JButton btnGame = new JButton("게임 시작 (행맨)");
        JButton btnShop = new JButton("상점 가기");
        JButton btnExit = new JButton("종료하기");

        btnGame.addActionListener(e -> frame.changeScreen("Game"));
        btnShop.addActionListener(e -> frame.changeScreen("Shop"));
        btnExit.addActionListener(e -> System.exit(0));

        buttonPanel.add(btnGame);
        buttonPanel.add(btnShop);
        buttonPanel.add(btnExit);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private Image makeWindowTransparentAdvanced(String imagePath) {
        try {
            File inFile = new File(imagePath);
            if (!inFile.exists()) return null;

            BufferedImage src = ImageIO.read(inFile);
            BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);

            for (int y = 0; y < src.getHeight(); y++) {
                for (int x = 0; x < src.getWidth(); x++) {
                    int rgb = src.getRGB(x, y);
                    Color color = new Color(rgb, true);
                    
                    int r = color.getRed();
                    int g = color.getGreen();
                    int b = color.getBlue();

                    boolean isWoodenFrame = (r > g + 25) && (r > b + 25);

                    if (!isWoodenFrame || (r >= 220 && g >= 220 && b >= 220)) {
                        dest.setRGB(x, y, 0x00000000); 
                    } else {
                        dest.setRGB(x, y, rgb); 
                    }
                }
            }
            return dest;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Image makeWhiteTransparent(String imagePath) {
        try {
            File inFile = new File(imagePath);
            if (!inFile.exists()) return null;

            BufferedImage src = ImageIO.read(inFile);
            BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);

            for (int y = 0; y < src.getHeight(); y++) {
                for (int x = 0; x < src.getWidth(); x++) {
                    int rgb = src.getRGB(x, y);
                    Color color = new Color(rgb, true);
                    
                    if (color.getRed() >= 240 && color.getGreen() >= 240 && color.getBlue() >= 240) {
                        dest.setRGB(x, y, 0x00000000); 
                    } else {
                        dest.setRGB(x, y, rgb); 
                    }
                }
            }
            return dest;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // -------------------------------------------------------------
    // 화면이 새로고침될 때마다 가구 실시간 시각화
    // -------------------------------------------------------------
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        tokenLabel.setText("현재 Token: " + frame.getTokens() + "개  ");
        
        if (frame.getMyItems().isEmpty()) {
            roomTextArea.setText("산 가구가 없습니다.");
            bookshelfLabel.setVisible(false); 
            petLabel.setVisible(false); 
            windowLabel.setVisible(false); 
            sofaLabel.setVisible(false); 
            lampLabel.setVisible(false);
        } else {
            roomTextArea.setText(String.join("\n", frame.getMyItems()));
            
            if (frame.getMyItems().contains("창문")) {
                windowLabel.setVisible(true); 
            }
            if (frame.getMyItems().contains("책장")) {
                bookshelfLabel.setVisible(true); 
            }
            if (frame.getMyItems().contains("소파")) {
                sofaLabel.setVisible(true); 
            }
            if (frame.getMyItems().contains("펫")) {
                petLabel.setVisible(true); 
            }
            if (frame.getMyItems().contains("전등")) {
                lampLabel.setVisible(true); 
            }
        }
    }
}