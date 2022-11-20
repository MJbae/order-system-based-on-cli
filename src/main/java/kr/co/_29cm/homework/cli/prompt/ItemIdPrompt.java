package kr.co._29cm.homework.cli.prompt;

import kr.co._29cm.homework.cli.interfaces.Prompt;
import org.springframework.stereotype.Component;

@Component
public class ItemIdPrompt implements Prompt {
    public final String idPrompt = "상품번호: ";

    @Override
    public void display() {
        System.out.print(idPrompt);
    }
}
