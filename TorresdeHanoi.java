import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class TorresdeHanoi {

    static List<Stack<Integer>> torres;
    static int numDiscos;
    static int movimientos;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        saludar();

        System.out.print("Por favor, introduce el número de discos: ");
        numDiscos = scanner.nextInt();

        if (numDiscos <= 0) {
            System.out.println("El número de discos debe ser mayor que cero. Saliendo del juego...");
            return;
        }

        inicializarTorres();
        imprimirTorres();

        jugar();
    }

    public static void saludar() {
        System.out.println("");
        System.out.println("-----------------------");
        System.out.println("EYYY, HOLAAAA!!!!!!!");
        System.out.println("");
        String saludoUsuario = "";
        while (!saludoUsuario.equalsIgnoreCase("Hola")) {
            System.out.print("");
            saludoUsuario = scanner.nextLine();
            if (!saludoUsuario.equalsIgnoreCase("Hola")) {
                System.out.println("¡Sé que solo es un juego, pero por lo menos saluda!");
            }
        }
        String nombre = "";
        System.out.print("Por favor, introduce tu nombre, futuro participante: ");
        nombre = scanner.nextLine();
       
        String saludo = obtenerSaludo();
        System.out.println("Muy " + saludo + ", " + nombre + ".");
        System.out.println("-----------------------");
        System.out.println("");
    }

    public static String obtenerSaludo() {
        String saludo;
        int hora = java.time.LocalTime.now().getHour();
        if (hora >= 6 && hora < 12) {
            saludo = "buen día";
        } else if (hora >= 12 && hora < 20) {
            saludo = "buenas tardes";
        } else {
            saludo = "buenas noches";
        }
        return saludo;
    }

    public static void inicializarTorres() {
        torres = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            torres.add(new Stack<>());
        }
        Random rand = new Random();
        for (int i = numDiscos; i >= 1; i--) {
            torres.get(0).push(i); // Colocar todos los discos en la primera torre
        }
        movimientos = 0; // Reiniciar contador de movimientos
    }

    public static void imprimirTorres() {
        System.out.println("Estado actual de las torres:");
        int maxDiscos = numDiscos;
        for (int nivel = maxDiscos - 1; nivel >= 0; nivel--) {
            for (int torre = 0; torre < 3; torre++) {
                Stack<Integer> torreActual = torres.get(torre);
                int disco = nivel < torreActual.size() ? torreActual.get(nivel) : 0;
                dibujarDisco(disco);
            }
            System.out.println();
        }
        for (int i = 0; i < 3; i++) {
            System.out.print("Torre " + (i + 1) + "  ");
        }
        System.out.println();
        System.out.println("" );
        System.out.println("-------------------------------------------" );
        System.out.println("Movimientos realizados: " + movimientos);
        System.out.println("-------------------------------------------" );
        System.out.println("" );
        System.out.println();
    }

    public static void dibujarDisco(int size) {
        StringBuilder builder = new StringBuilder();
        if (size == 0) {
            for (int i = 0; i < numDiscos; i++) {
                builder.append("  ");
            }
        } else {
            int spaces = numDiscos - size;
            for (int i = 0; i < spaces; i++) {
                builder.append(" ");
            }
            for (int i = 0; i < size; i++) {
                builder.append("*");
            }
            for (int i = 0; i < spaces; i++) {
                builder.append(" ");
            }
        }
        System.out.print(builder.toString() + " ");
    }

    public static void moverDisco(int origen, int destino) {
        if (!torres.get(origen).isEmpty()) {
            int disco = torres.get(origen).pop(); // Sacar disco del poste de origen
            torres.get(destino).push(disco); // Colocar disco en el poste de destino
            movimientos++; // Incrementar contador de movimientos
        } else {
            System.out.println("El poste de origen está vacío. No se puede realizar el movimiento.");
            movimientos++; // Contabilizar el error como un movimiento
        }
    }

    public static void jugar() {
        boolean seguirJugando = true;
        while (seguirJugando) {
            while (!esSolucion()) {
                System.out.println("Ingresa el número del poste de origen y destino separados por un espacio (1, 2 o 3), o '0' para reiniciar, o 'M' para salir:");
                if (scanner.hasNextInt()) {
                    int origen = scanner.nextInt() - 1;

                    if (origen == -1) { // El juego se reinicia
                        System.out.println("" );
                        System.out.println("----------------------------------------------------------" );
                        System.out.println("Reiniciando el juego...");
                        System.out.println("Movimientos realizados en el juego anterior: " + movimientos);
                        System.out.println("----------------------------------------------------------" );
                        System.out.println("" );
                        System.out.print("¿Quieres mantener la misma cantidad de discos? (s/n): ");
                        char opcion = scanner.next().charAt(0);
                        if (opcion == 'n' || opcion == 'N') {
                            System.out.print("Por favor, introduce el nuevo número de discos: ");
                            numDiscos = scanner.nextInt();
                            if (numDiscos <= 0) {
                                System.out.println("El número de discos debe ser mayor que cero. Saliendo del juego...");
                                seguirJugando = false;
                                break;
                            }
                            inicializarTorres(); // Reinicar torres con el nuevo número de discos
                            imprimirTorres(); // Mostrar el estado de las torres con la nueva configuración
                        } else if (opcion == 's' || opcion == 'S') {
                            inicializarTorres(); // Reinicar torres con la misma cantidad de discos
                            imprimirTorres(); // Mostrar el estado de las torres con la misma configuración
                        }
                        continue;
                    }

                    int destino = scanner.nextInt() - 1;

                    if (origen < 0 || origen > 2 || destino < 0 || destino > 2) {
                        System.out.println("¡Entrada inválida! Los números deben ser 1, 2 o 3.");
                        movimientos++; // Contabilizar el error como un movimiento
                        continue;
                    }

                    if (torres.get(origen).isEmpty()) {
                        System.out.println("El poste de origen está vacío. Elige otro movimiento.");
                        movimientos++; // Contabilizar el error como un movimiento
                        continue;
                    }

                    if (!torres.get(destino).isEmpty() && torres.get(origen).peek() > torres.get(destino).peek()) {
                        System.out.println("No puedes poner un disco grande sobre uno más pequeño. Elige otro movimiento.");
                        movimientos++; // Contabilizar el error como un movimiento
                        continue;
                    }

                    moverDisco(origen, destino);
                    imprimirTorres();
                } else {
                    // Si no es un entero, verificar si es "M" para salir del juego
                    String input = scanner.next();
                    if (input.equalsIgnoreCase("M")) {
                        System.out.println("Saliendo del juego...");
                        seguirJugando = false;
                        break;
                    } else {
                        System.out.println("Entrada inválida. Por favor, ingresa un número o 'M' para salir.");
                    }
                }
            }
            if (seguirJugando) {
                System.out.println("¡Felicidades, has ganado!");
                System.out.println("¿Quieres jugar de nuevo? (s/n)");
                char opcion = scanner.next().charAt(0);
                seguirJugando = opcion == 's' || opcion == 'S';
                if (seguirJugando) {
                    inicializarTorres();
                    imprimirTorres();
                }
            }
        }
        scanner.close();
    }

    public static boolean esSolucion() {
        if (torres == null) return false; // Añadido para evitar NullPointerException
        return torres.get(2).size() == numDiscos;
    }
}

