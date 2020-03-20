package clientview;

import javax.swing.*;
import java.awt.*;

public class ChatPanel extends JPanel {

    private static JEditorPane chatArea = new JEditorPane();
    private static StringBuffer chatContents = new StringBuffer();
    private static final int FONT_SIZE = 12;

    public ChatPanel() {
        this.setLayout(new BorderLayout());

        final JTextField inputArea = new ChatInputField();
        this.add(inputArea, BorderLayout.SOUTH);

        chatArea = new JEditorPane();
        chatArea.setEditable(false);
        chatArea.setContentType("text/html");
        chatArea.setFont(new Font("Courier New", Font.PLAIN, 12));
         JScrollPane jsp = new JScrollPane(chatArea);
        this.add(jsp);
        chatArea.setText("Send a message to load from server");

     //   chatArea.setText(chatContents.toString());

    }

    public static void addToChatMessages(String[] split) {
        String result = "";
        for (String s : split) {
            result += s + "<br/>";
        }
        //System.out.println("Added chat stuff: " + result);
        chatContents.append(result);

            chatArea.setContentType("text/html");
            chatArea.setText(chatContents.toString());
        chatArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        //chatArea.scrollToBottom();
    }
//

//
//    public void scrollToBottom() {
//        if (chatArea != null) {
//            chatArea.scrollToBottom();
//        }
//    }


}
