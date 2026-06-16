import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameScreen extends JPanel {
    private MainFrame frame;
    private String secretWord;
    private char[] currentProgress;
    private int wrongGuesses = 0;
    private final int MAX_WRONG = 8; // 8회 기회

    private JLabel wordLabel;
    private JLabel statusLabel;
    private JTextField inputField;
    private JTextArea hangmanDrawing;
    
    private JTextArea wrongLettersArea;
    private List<Character> wrongLettersList;
    
    private List<String> wordList;
    private int currentWordIndex = 0;

    public GameScreen(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        // 단어 데이터셋 구축 및 셔플
        wordList = new ArrayList<>();
        wordList.add("APPLE");
        wordList.add("JAVA");
        wordList.add("COFFEE");
        wordList.add("COMPUTER");
        wordList.add("PROJECT");
        wordList.add("WINDOW");
        wordList.add("KANGAROO");
        wordList.add("BANANA");
        wordList.add("STUDENT");
        wordList.add("DESKTOP");
        Collections.shuffle(wordList);

        // 틀린 글자 저장용 리스트 초기화
        wrongLettersList = new ArrayList<>();

        // 우측 사이드 패널 생성 (틀린 글자 표시 + 나가기 버튼)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.setPreferredSize(new Dimension(150, 0)); 
        rightPanel.setBackground(new Color(240, 240, 240));

        // 나가기 버튼 배치
        JButton btnBack = new JButton("나가기 (홈)");
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.addActionListener(e -> frame.changeScreen("Home"));
        rightPanel.add(btnBack);
        
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // 틀린 글자 섹션 타이틀
        JLabel wrongTitleLabel = new JLabel("틀린 글자 목록");
        wrongTitleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        wrongTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(wrongTitleLabel);

        rightPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // 틀린 글자들이 나열될 텍스트 영역
        wrongLettersArea = new JTextArea(5, 10);
        wrongLettersArea.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        wrongLettersArea.setEditable(false);
        wrongLettersArea.setForeground(Color.RED); // 틀린 글자는 빨간색 표시
        wrongLettersArea.setBackground(new Color(240, 240, 240)); // 패널 배경색과 통일하여 일체감 주기
        wrongLettersArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // [수정] 가로/세로 스크롤 바를 절대 표시하지 않도록 NEVER 옵션 적용
        JScrollPane scrollPane = new JScrollPane(wrongLettersArea,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        // 스크롤 패널의 외곽선 및 배경 투명화 처리로 일체감 극대화
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setMaximumSize(new Dimension(130, 150));
        
        rightPanel.add(scrollPane);

        // 메인 패널에 우측 영역 붙이기
        add(rightPanel, BorderLayout.EAST);

        // 중앙 영역: 행맨 상태 그리기 및 언더바 영역
        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        hangmanDrawing = new JTextArea(10, 20);
        hangmanDrawing.setFont(new Font("Monospaced", Font.BOLD, 16));
        hangmanDrawing.setEditable(false);
        
        wordLabel = new JLabel("_ _ _ _", JLabel.CENTER);
        wordLabel.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        
        centerPanel.add(hangmanDrawing);
        centerPanel.add(wordLabel);
        add(centerPanel, BorderLayout.CENTER);

        // 하단 영역: 입력 인터페이스
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
        statusLabel = new JLabel("알파벳을 입력하고 Enter를 누르세요.", JLabel.CENTER);
        
        JPanel inputPanel = new JPanel();
        inputField = new JTextField(5);
        JButton btnSubmit = new JButton("입력");
        
        inputPanel.add(new JLabel("글자 입력: "));
        inputPanel.add(inputField);
        inputPanel.add(btnSubmit);
        
        bottomPanel.add(statusLabel);
        bottomPanel.add(inputPanel);
        add(bottomPanel, BorderLayout.SOUTH);

        // 엔터키 및 버튼 클릭 이벤트 연결
        ActionListener guessAction = e -> processGuess();
        inputField.addActionListener(guessAction);
        btnSubmit.addActionListener(guessAction);

        resetGame();
    }

    private void resetGame() {
        secretWord = wordList.get(currentWordIndex);
        
        currentWordIndex++;
        if (currentWordIndex >= wordList.size()) {
            Collections.shuffle(wordList);
            currentWordIndex = 0;
        }

        currentProgress = new char[secretWord.length()];
        for (int i = 0; i < currentProgress.length; i++) {
            currentProgress[i] = '_';
        }
        
        wrongGuesses = 0;
        wrongLettersList.clear(); 
        
        updateLabels();
        statusLabel.setText("새 게임이 시작되었습니다! 단어를 맞춰보세요.");
        inputField.setEditable(true);
    }

    private void updateLabels() {
        StringBuilder sb = new StringBuilder();
        for (char c : currentProgress) {
            sb.append(c).append(" ");
        }
        wordLabel.setText(sb.toString().trim());

        // 우측 상단 틀린 글자 컴포넌트 갱신 (가로로 쉼표 구분하여 나열하도록 깔끔하게 변경)
        StringBuilder sbWrong = new StringBuilder();
        for (int i = 0; i < wrongLettersList.size(); i++) {
            sbWrong.append(wrongLettersList.get(i));
            if (i < wrongLettersList.size() - 1) {
                sbWrong.append(", "); // 글자 사이에 쉼표 배치
            }
        }
        wrongLettersArea.setText(sbWrong.toString());

        // 행맨 그림 갱신
        StringBuilder hangman = new StringBuilder("  [ 행맨 상태 ]\n");
        if (wrongGuesses >= 1) hangman.append("   |     - 교수대 기둥\n");
        if (wrongGuesses >= 2) hangman.append("   |--   - 밧줄\n");
        if (wrongGuesses >= 3) hangman.append("   (O)   - 머리\n");
        if (wrongGuesses >= 4) hangman.append("    |    - 몸통\n");
        if (wrongGuesses >= 5) hangman.append("  / |    - 왼팔\n");
        if (wrongGuesses >= 6) hangman.append("  / | \\  - 오른팔\n");
        if (wrongGuesses >= 7) hangman.append("   /     - 왼다리\n");
        if (wrongGuesses >= 8) hangman.append("   / \\   - 오른다리 (게임 오버)\n");
        hangmanDrawing.setText(hangman.toString());
    }

    private void processGuess() {
        String input = inputField.getText().trim().toUpperCase();
        inputField.setText("");

        if (input.isEmpty() || input.length() > 1) {
            statusLabel.setText("한 글자의 알파벳만 입력해주세요!");
            return;
        }

        char guess = input.charAt(0);
        
        if (wrongLettersList.contains(guess)) {
            statusLabel.setText("이미 틀렸던 알파벳입니다: " + guess);
            return;
        }
        for (char c : currentProgress) {
            if (c == guess) {
                statusLabel.setText("이미 맞춘 알파벳입니다: " + guess);
                return;
            }
        }

        boolean found = false;
        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == guess) {
                currentProgress[i] = guess;
                found = true;
            }
        }

        if (!found) {
            wrongGuesses++;
            wrongLettersList.add(guess); 
            statusLabel.setText("틀렸습니다! (" + wrongGuesses + "/" + MAX_WRONG + ")");
        } else {
            statusLabel.setText("맞았습니다!");
        }

        updateLabels();

        if (new String(currentProgress).equals(secretWord)) {
            int reward = 50;
            frame.addTokens(reward);
            JOptionPane.showMessageDialog(this, "정답입니다! 승리하셨습니다.\n보상: " + reward + " 토큰");
            resetGame();
        } else if (wrongGuesses >= MAX_WRONG) {
            JOptionPane.showMessageDialog(this, "행맨이 완성되었습니다... 패배!\n정답은 " + secretWord + "였습니다.");
            resetGame();
        }
    }
}