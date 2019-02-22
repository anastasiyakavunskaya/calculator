import java.util.EmptyStackException;
import java.util.Stack;

public class main {

    public static void main(String[] args){

    }

    public static String calculate(String str){
        char [] infixArray = str.toCharArray();
        String postfix = getPostfix(infixArray);

        if(postfix.startsWith("Ошибка")) return postfix;
        String[] postfixArray = postfix.split("\\s");
        str = ""+getResult(postfixArray);
        return str;
    }
    //Получаем обратную польскую запись
    public static String getPostfix(char[] input) {
        String output = "";
        Stack<Character> operatorsStack = new Stack<>();
        //Рассматриваем каждый элемент массива char элементов строки
        for (int i = 0;i<input.length;i++) {
            if (Character.isDigit(input[i]))
                output = output + input[i];
            else{
                switch (input[i]){
                    case '(':{
                        if((i!=0)&&(Character.isDigit(input[i-1]))) return "Ошибка знака перед '('";
                        operatorsStack.push(input[i]);
                        break;
                    }
                    case ')':{
                        try {
                            while (operatorsStack.peek() != '(') {
                                output +=" "+operatorsStack.pop();
                            }
                        }catch(EmptyStackException e)
                        { return "Ошибка c закрываюшей скобкой!";}
                        if((i!=0)&&(!Character.isDigit(input[i-1]))&&(input[i-1]!=')')) return "Ошибка c закрываюшей скобкой!";
                        operatorsStack.pop();
                        break;
                    }
                    case '-':{
                        if(i==input.length-1)return "Ошибка!'-' не может стоять в конце выражения";
                        if (i == 0) {
                            output += "-";
                        } else {
                            if (Character.isDigit(input[i - 1])) {
                                try {
                                    while (getPriority(input[i]) < getPriority(operatorsStack.peek())) {
                                        output += " " + operatorsStack.pop();
                                    }
                                } catch (EmptyStackException e) {
                                }
                                output += " -";
                                operatorsStack.push('+');
                            } else {
                                switch (input[i - 1]) {
                                    case '(': {
                                        output += "-";
                                        break;
                                    }
                                    case ')': {
                                        try {
                                            while (getPriority(input[i]) < getPriority(operatorsStack.peek())) {
                                                output += " " + operatorsStack.pop();
                                            }
                                        } catch (EmptyStackException e) {
                                        }
                                        output += " -";
                                        operatorsStack.push('+');
                                        break;
                                    }
                                    default:
                                        return "Ошибка при записи отрицательных чисел!";
                                }
                            }
                        }
                        break;
                    }
                    default:{
                        if(((i!=0)&&(!Character.isDigit(input[i-1])))||(i==input.length-1)) return "Ошибка с вводом операций!";
                        output += " ";
                        try{
                            while (getPriority(input[i])<=getPriority(operatorsStack.peek())){
                                output+=operatorsStack.pop()+" ";
                            }
                        }catch (EmptyStackException e)
                        {}
                        operatorsStack.push(input[i]);
                    }
                }
            }
        }
        while(!operatorsStack.empty()){
            if(operatorsStack.peek()=='(') return "Ошибка! Отсутствует закрывающия скобка";
            output+=" "+operatorsStack.pop();
        }
        return output;
    }
    //Получаем приоритет операций
    public static int getPriority(char s){
        switch (s)
        {
            case '(':
            case ')':
                return 0;
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 3;
        }
    }
    //Получаем результат
    public static double getResult (String[] input) {
        double result = 0;
        Stack<Double> resultStack = new Stack<>();
        for (int i = 0; i < input.length; i++) {
            if ((input[i].compareTo("+")!= 0) && ((input[i].compareTo("*")!= 0))&&((input[i].compareTo("/")!= 0))) {
                if(input[i].startsWith("--")){
                    char[] reduction = input[i].toCharArray();
                    int j = 0;
                    while(!Character.isDigit(reduction[j])){
                        j++;
                    }
                    if (j%2==0) input[i]=""+reduction[j];
                    else input[i]=""+reduction[j];
                }
                result = Double.parseDouble(input[i]);
                resultStack.push(result);
            }
            else {
                switch (input[i]){
                    case "+":{
                        result = resultStack.pop()+resultStack.pop();
                        resultStack.push(result);
                        break;
                    }
                    case "*":{
                            result = resultStack.pop()*resultStack.pop();
                            resultStack.push(result);
                            break;
                    }
                    case "/":{
                        double a = resultStack.pop(),b = resultStack.pop();
                        result = b/a;
                        resultStack.push(result);
                        break;
                    }
                }
            }
        }
        return result;
    }

}
