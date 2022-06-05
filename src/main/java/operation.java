import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class operation {
    private static final Pattern SUM = Pattern.compile("[\\+\\-\\/\\*]"); // 1
    private static final Pattern BRK = Pattern.compile("\\(.+?\\)"); // 2
    private static final Pattern NUMBER = Pattern.compile("(\\d+|~\\d+)"); // 0

//    public static void main(String[] args) {
//        String InputExp = "19+16*11+(12-16)*10+444-32+2*~12*(3+2)";
//        System.out.println(Calculate(CheckBrackets(InputExp)));
//    }

    public String CheckBrackets(String InputExp) {           // соответсвенно просчитывает через клкл скобки и возвращает выражение с решением скобками
        String ChangeEx = InputExp;
        Matcher matherBracket = BRK.matcher(ChangeEx);
        while (matherBracket.find()) {
            int value = Calculate(matherBracket.group());
            if (value < 0) {
                String value1 = String.valueOf(Math.abs(value));        // возвращает отрицательные числа в таком виде, так как когда парсер проходит по знакам, чтобы не брался минус числа как итерация, взял такой
                ChangeEx = ChangeEx.replace(matherBracket.group(), "~" + value1);     // через унарный минус пытался, но не понял как  я не понял как воттттт
            } else {
                String value1 = String.valueOf(value);                  // соответсвенно положительное число
                ChangeEx = ChangeEx.replace(matherBracket.group(), value1);
            }

        }
        return ChangeEx; // возращает строку
    }

    public int Calculate(String InputExp) {   // cчитает и скобки и само выражение, которое подается на вход строкой
        Matcher matcher = NUMBER.matcher(InputExp);
        Matcher matcherChar = SUM.matcher(InputExp);
        Stack<Integer> stackNumbers = new Stack<>();   // два стека один для чиселок другой для операций
        Stack<String> stackStr = new Stack<>();
        for (int i = 0; i < 30; i++) {                                      // как нить подругому цикл прогонять, я думал как количество чисел , мб так и правильно, но мне кажется через вайл который понимает когда остановиться лучше , мб даже ду вайл
            while (matcher.find()) {
                String b = String.valueOf(matcher.group().charAt(0));       // проверка на отрицательное число
                if (b.equals("~")) {
                    String z = matcher.group().replace("~", ""); // изменение знака и добавление в стек отрицательного числа
                    int a = -Integer.parseInt(z);
                    stackNumbers.push(a); // пушим
                    break;
                } else {
                    stackNumbers.push(Integer.valueOf(matcher.group())); // если число положительное пушим его
                    break;
                }
            }
            if (stackNumbers.size() > 1) { // проверка на заполненность стека числами (если там одно число или ноль то и операции не должны проходить )
                if (stackStr.peek().equals("*")) {   // умножение чисел из стека
                    int test = stackNumbers.pop() * stackNumbers.pop();
                    stackStr.pop();
                    stackNumbers.push(test);
                } else if (stackStr.peek().equals("/")) { // деление чисел из стека
                    int last = stackNumbers.pop();
                    int lastLast = stackNumbers.pop() / last;
                    stackNumbers.push(lastLast);
                    stackStr.pop();
                }
            }
            while (matcherChar.find()) {   // проходимся по операциям
                if (stackNumbers.size() > 1) {
                    if (matcherChar.group().equals("-") || matcherChar.group().equals("+")) { //проверка для приоретености операций
                        if (stackStr.peek().equals("-")) {
                            int last = stackNumbers.pop();// вычитание из стека
                            int lastLast = stackNumbers.pop() - last;
                            stackNumbers.push(lastLast);
                            stackStr.pop();
                        } else if (stackStr.peek().equals("+")) { // сложение
                            int test = stackNumbers.pop() + stackNumbers.pop();
                            stackStr.pop();
                            stackNumbers.push(test);
                        }
                    }
                }
                stackStr.push(String.valueOf(matcherChar.group())); //  пушим новый символ в стек для операций
                break;

            }
        }
        if (stackStr.empty()) { // если пустой возвращаем число оставшееся в стеке
            return stackNumbers.pop();

        } else if (stackStr.peek().equals("-")) {
            int last = stackNumbers.pop();
            int test = stackNumbers.pop() - last;
            stackStr.pop();  // последняя операция вычитания как самого низкого приоретета
            return test;
        } else if (stackStr.peek().equals("+")) {
            int test = stackNumbers.pop() + stackNumbers.pop(); // сложения соответсвенно
            stackStr.pop();
            return test;
        }
        return 0;
    }
}