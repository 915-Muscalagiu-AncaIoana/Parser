import java.util.Scanner;

public class Main {

    public static void printMenu(){
        System.out.println("1. Print terminals");
        System.out.println("2. Print non terminals");
        System.out.println("3. Print productions");
        System.out.println("4. Print productions for given non terminal");
        System.out.println("5. Check if grammar is CFG");
    }

    public static void main (String[] args) {
        Grammar grammar = new Grammar();
        grammar.readFromFile("g2.txt");

        java.util.Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            System.out.println("Enter option: ");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    System.out.println("The terminals are: ");
                    grammar.printTerminals();
                    break;
                case 2:
                    System.out.println("The non terminals are: ");
                    grammar.printNonTerminals();
                    break;
                case 3:
                    System.out.println("The productions are: ");
                    grammar.printProductions();
                    break;
                case 4:
                    System.out.println("Non terminal = ");
                    scanner.nextLine();
                    String terminal = scanner.nextLine();
                    grammar.printProductionsForGivenNonTerminal(terminal);
                    break;
                case 5:
                    if(grammar.isCFG()){
                        System.out.println("The grammar is CFG");
                    }
                    else {
                        System.out.println("The grammar is not CFG");
                    }
                    break;
                default:
                    System.out.println("Invalid option");
            }
            System.out.println("------------------------------");
        }
    }
}