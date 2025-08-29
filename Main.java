import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


// Clase para definir colores en la consola
class Colores {
  public static final String AZUL = "\u001B[34;1;5m";
  public static final String ROJO = "\u001B[31;1;3m";
  public static final String BLANCO = "\u001B[37;1;3m";
  public static final String VERDE = "\u001B[32;1;3m";
  public static final String MORADO = "\u001B[35;1;3m";
  public static final String CIAN = "\u001B[36;1;3m";
  public static final String AMARILLO = "\u001B[33;1;3m";
  public static final String RESET = "\u001B[0m";
}

// Clase para la gestión del menú del restaurante
class Menu {
    private List<Platillo> menu;
    public Menu() {
        menu = new ArrayList<>();
        inicializarMenu();
    }

    // Método para inicializar el menú con algunos platillos
    private void inicializarMenu() {
        // Antipasti
        agregar(new Platillo(Colores.AMARILLO + "Bruschetta" + Colores.RESET, Colores.BLANCO + "Pan tostado con tomate, ajo y albahaca", 8.50, "Antipasti"));
        agregar(new Platillo(Colores.AMARILLO + "Antipasto Misto" + Colores.RESET, Colores.BLANCO + "Selección de carnes frías, quesos y aceitunas", 12.00, "Antipasti"));

        // Pasta
        agregar(new Platillo(Colores.AMARILLO + "Spaghetti Carbonara" + Colores.RESET, Colores.BLANCO + "Pasta con pancetta, huevo y queso parmesano", 14.50, "Pasta"));
        agregar(new Platillo(Colores.AMARILLO + "Fettuccine Alfredo" + Colores.RESET, Colores.BLANCO + "Pasta en salsa cremosa de mantequilla y parmesano", 13.00, "Pasta"));
        agregar(new Platillo(Colores.AMARILLO + "Lasagna della Casa" + Colores.RESET, Colores.BLANCO + "Lasaña tradicional con carne y bechamel", 16.00, "Pasta"));

        // Secondi
        agregar(new Platillo(Colores.AMARILLO + "Osso Buco" + Colores.RESET, Colores.BLANCO + "Jarrete de ternera braseado con vegetales", 22.00, "Secondi"));
        agregar(new Platillo(Colores.AMARILLO + "Pollo Parmigiana" + Colores.RESET, Colores.BLANCO + "Pechuga empanizada con salsa marinara y mozzarella", 18.50, "Secondi"));

        // Dolci
        agregar(new Platillo(Colores.AMARILLO + "Tiramisu" + Colores.RESET, Colores.BLANCO + "Postre tradicional con café y mascarpone", 7.50, "Dolci"));
        agregar(new Platillo(Colores.AMARILLO + "Panna Cotta" + Colores.RESET, Colores.BLANCO + "Postre cremoso con frutos rojos", 6.50, "Dolci"));
    }

    // Método para agregar un platillo al menú
    public void agregar(Platillo platillo) {
        // Definir el orden de las categorías
        String[] ordenCategorias = {"Antipasti", "Pasta", "Secondi", "Dolci"};

        // Encontrar la posición donde insertar el platillo
        int posicionInsertar = menu.size();

        for (int i = 0; i < menu.size(); i++) {
            String categoriaActual = menu.get(i).getCategoria();
            String categoriaNueva = platillo.getCategoria();

            // Obtener índices de categorías en el orden definido
            int indiceCategoriaActual = getIndicateCategoria(categoriaActual, ordenCategorias);
            int indiceCategoriaNueva = getIndicateCategoria(categoriaNueva, ordenCategorias);

            // Si encontramos una categoría que viene después en el orden
            if (indiceCategoriaActual > indiceCategoriaNueva) {
                posicionInsertar = i;
                break;
            }
        }

        menu.add(posicionInsertar, platillo);
    }

    // Método auxiliar para obtener el índice de una categoría
    private int getIndicateCategoria(String categoria, String[] ordenCategorias) {
        for (int i = 0; i < ordenCategorias.length; i++) {
            if (ordenCategorias[i].equalsIgnoreCase(categoria)) {
                return i;
            }
        }
        return ordenCategorias.length; // Si no se encuentra, va al final
    }

    // Método para agregar un platillo al menú
    public void agregarPlatillo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre del platillo: ");
        String nombre = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Precio: ");
        double precio = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Categoría: ");
        String categoria = scanner.nextLine();

        agregar(new Platillo(Colores.AMARILLO + nombre + Colores.RESET, Colores.BLANCO + descripcion, precio, categoria));
        System.out.println("Platillo agregado exitosamente.");
    }

    // Método para eliminar un platillo del menú
    public boolean eliminar(String nombre) {
        return menu.removeIf(platillo -> platillo.getNombre().replaceAll("\u001B\\[[;\\d]*m", "").equalsIgnoreCase(nombre));
    }

    // Método para buscar un platillo en el menú
    public Platillo buscar(String nombre) {
        for (Platillo platillo : menu) {
            String nombreLimpio = platillo.getNombre().replaceAll("\u001B\\[[;\\d]*m", "");
            if (nombreLimpio.equalsIgnoreCase(nombre)) {
                return platillo;
            }
        }
        return null;
    }

    // Método para mostrar el menú completo
    public void mostrarMenu() {
        String categoriaActual = "";
        System.out.println("\n=== MENÚ DOLCE ALBA ===");
        for (Platillo platillo : menu) {
            if (!platillo.getCategoria().equals(categoriaActual)) {
                categoriaActual = platillo.getCategoria();
                System.out.println(Colores.ROJO + "\n--- " + categoriaActual + " ---" + Colores.RESET);
            }
            System.out.println(platillo);
        }
        System.out.println();
    }

    // Método para mostrar los platillos de una categoría específica
    public void mostrarPorCategoria(String categoria) {
        System.out.println("\n--- " + categoria + " ---");
        for (Platillo platillo : menu) {
            if (platillo.getCategoria().equalsIgnoreCase(categoria)) {
                System.out.println(platillo);
            }
        }
        System.out.println();
    }

    public List<Platillo> getMenu() {
        return new ArrayList<>(menu);
    }
}

// Clase para representar un platillo en el menú
class Platillo {
    private String nombre;
    private String descripcion;
    private double precio;
    private String categoria;

    // Constructor de la clase Platillo
    public Platillo(String nombre, String descripcion, double precio, String categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
    }

    // Getters y Setters
    public String getNombre() { 
        return nombre; 
    }
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public String getDescripcion() { 
        return descripcion; 
    }
    public void setDescripcion(String descripcion) { 
        this.descripcion = descripcion; 
    }

    public double getPrecio() { 
        return precio; 
    }
    public void setPrecio(double precio) { 
        this.precio = precio; 
    }

    public String getCategoria() { 
        return categoria; 
    }
    public void setCategoria(String categoria) { 
        this.categoria = categoria; 
    }

    @Override
    public String toString() {
        return nombre + " - " + descripcion + " - $" + precio + " (" + categoria + ")";
    }
}

class Pila1 {
    private String[] data;
    private int top;
    private int capacity;

    // Constructor
    public Pila1(int capacity){
        this.capacity = capacity;
        data = new String [capacity];
        top = -1; // Pila vacía
    }

    // Métodos básicos de la pila
    public boolean isEmpty(){
        return top == -1;
    }

    public boolean isFull(){
        return top == capacity - 1;
    }

    public String push(String x){
        if(isFull()){
            Main.limpiarPantalla();
            System.out.println(Colores.ROJO + "\nCupo lleno, ya no se pueden agregar más insumos: " + Colores.BLANCO + x);
            return null;
        }
        Main.limpiarPantalla();
        System.out.println(Colores.VERDE + "\nInsumo agregado: " + Colores.BLANCO + x);
        return data[++top] = x ;
    }

    public String pop(){
        if (isEmpty()){
            System.out.println(Colores.ROJO + "No podemos quitar insumos, no hay ninguno." + Colores.RESET);
            return null;
        }
        return data[top--];
    }

    public String peek(){
        if (isEmpty()){
            System.out.println(Colores.ROJO + "No hay insumos en el inventario." + Colores.RESET);
            return null;
        }
        return data[top];
    }

    public void show(){
        if (isEmpty()) {
            System.out.println(Colores.ROJO + "El inventario está vacío." + Colores.RESET);
            return;
        }
        System.out.println(Colores.AMARILLO + "Contenido del inventario: \n" + Colores.RESET);
        for (int i = top; i >= 0; i--){
            System.out.println(Colores.CIAN + "| " + Colores.RESET + Colores.BLANCO + data[i] + Colores.RESET + Colores.CIAN + " |" + Colores.RESET);
        }
        System.out.println();
    }

    // 🔹 Método de gestión del inventario dentro de la misma clase
    public void gestionarInventario() {
        Scanner sc = new Scanner(System.in);
        boolean condition = true;

        while (condition) { 
            System.out.println(Colores.AZUL + "\n⭐🌟     GESTIÓN DE INVENTARIO (PILAS)    🌟⭐" + Colores.RESET);
            System.out.println(Colores.AZUL + "──────────────────────" + Colores.ROJO +"୨ৎ"+ Colores.AZUL + "─────────────────────" + Colores.RESET);
            System.out.println(Colores.BLANCO + "╔═══════════════════════════════════════════╗" + Colores.RESET);
            System.out.println(Colores.BLANCO + "║ 1. Añadir insumo al inventario ➕         ║");
            System.out.println("║ 2. Retirar insumo ➖                      ║");
            System.out.println("║ 3. Mostrar el último insumo agregado 📃   ║");
            System.out.println("║ 4. Mostrar inventario 📋                  ║");
            System.out.println("║ 0. Volver al menú principal 🏠︎            ║" + Colores.RESET);
            System.out.println(Colores.BLANCO + "╚═══════════════════════════════════════════╝" + Colores.RESET);
            System.out.print(Colores.MORADO + "\nSeleccione una opción: " + Colores.RESET + Colores.BLANCO);
            int option = sc.nextInt();

            switch (option) {
                case 1:
                    Main.limpiarPantalla();
                    System.out.println(Colores.AMARILLO + "⚠️    Recuerda agregar primero el que tenga mayor tiempo de caducidad." + Colores.RESET);
                    System.out.print(Colores.MORADO + "\nEscribe el nombre del producto para agregarlo: " + Colores.RESET + Colores.BLANCO);
                    String x = sc.next();
                    push(x);
                    break;
                case 2:
                    Main.limpiarPantalla();
                    String retirado = pop();
                    if (retirado != null) {
                        System.out.println(Colores.AMARILLO + "Insumo retirado: " + Colores.RESET + Colores.BLANCO + retirado);
                    }
                    break;
                case 3:
                    Main.limpiarPantalla();
                    String ultimo = peek();
                    if (ultimo != null) {
                        System.out.println(Colores.AMARILLO + "El último insumo agregado fue: " + Colores.RESET + Colores.BLANCO + ultimo);
                    }
                    break;
                case 4:
                    Main.limpiarPantalla();
                    show();
                    break;
                case 0:
                    condition = false;
                    System.out.println("Regresando al menú principal...");
                    Main.limpiarPantalla();
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}

class Node {
    String name;     // nombre del cliente
    int priority;    // 1 = con reservación (mas urgente), 2 = sin reservacion
    Node next;

    public Node(String name, int priority) {
        this.name = name;
        this.priority = priority;
        this.next = null;
    }
}

// Cola con prioridad para clientes
class PriorityQueue {
    private Node front; // frente de la cola (mayor prioridad al frente)

    public PriorityQueue() {
        front = null;
    }

    // Insertar cliente en funcion de su prioridad (1 mejor que 2)
    public void enqueue(String name, int priority) {
        Node newNode = new Node(name, priority);

        if (front == null || priority < front.priority) {
            newNode.next = front;
            front = newNode;
        } else {
            Node temp = front;
            while (temp.next != null && temp.next.priority <= priority) {
                temp = temp.next;
            }
            newNode.next = temp.next;
            temp.next = newNode;
        }
        System.out.println(Colores.CIAN + "Agregado: " + Colores.BLANCO + etiqueta(newNode));
    }

    // Atiende (elimina) al cliente del frente
    public void dequeue() {
        if (front == null) {
            System.out.println(Colores.ROJO + "La cola está vacia" + Colores.RESET);
            return;
        }
        System.out.println(Colores.CIAN + "Atendido: " + Colores.BLANCO + etiqueta(front));
        front = front.next;
    }

    // Elimina por nombre (en cualquier posicion)
    public void removeByName(String name) {
        if (front == null) {
            System.out.println(Colores.ROJO + "La cola está vacia" + Colores.RESET);
            return;
        }
        if (front.name.equalsIgnoreCase(name)) {
            System.out.println(Colores.ROJO + "Eliminado: " + Colores.BLANCO + etiqueta(front));
            front = front.next;
            return;
        }
        Node cur = front;
        while (cur.next != null && !cur.next.name.equalsIgnoreCase(name)) {
            cur = cur.next;
        }
        if (cur.next == null) {
            System.out.println(Colores.AMARILLO + "No se encontro al cliente: " + Colores.BLANCO + name);
        } else {
            System.out.println("Atendido con éxito: " + etiqueta(cur.next));
            cur.next = cur.next.next;
        }
    }

    // Ver próximo a atender
    public void peek() {
        if (front == null) {
            System.out.println(Colores.ROJO + "La cola está vacia" + Colores.RESET);
        } else {
            System.out.println(Colores.AMARILLO + "Siguiente: " + Colores.BLANCO + etiqueta(front));
        }
    }

    // Mostrar toda la cola
    public void display() {
        if (front == null) {
            System.out.println(Colores.ROJO + "La cola está vacia" + Colores.RESET);
            return;
        }
        Node temp = front;
        System.out.println(Colores.CIAN + "— Cola de clientes —\n" + Colores.RESET);
        while (temp != null) {
            System.out.println("  • " + etiqueta(temp));
            temp = temp.next;
        }
    }

    public boolean isEmpty() { return front == null; }

    private String etiqueta(Node n) {
    String tipo = (n.priority == 1) ? Colores.VERDE + "Con reservación" + Colores.RESET : Colores.ROJO + "Sin reservación" + Colores.RESET;
    return String.format(Colores.BLANCO + "%-15s | %s" + Colores.RESET, n.name, tipo);  
}
}

class Colas {
    public static void gestionarClientes() {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);
            PriorityQueue cola = new PriorityQueue();

            while (true) {
                System.out.println(Colores.AZUL + "\n⭐🌟     GESTIÓN DE CLIENTES (COLAS)    🌟⭐" + Colores.RESET);
                System.out.println(Colores.AZUL + "──────────────────────" + Colores.ROJO +"୨ৎ"+ Colores.AZUL + "─────────────────────" + Colores.RESET);
                System.out.println(Colores.BLANCO + "╔═══════════════════════════════════════════╗" + Colores.RESET);
                System.out.println(Colores.BLANCO + "║ 1. Agregar cliente                        ║");
                System.out.println("║ 2. Mostrar cliente al frente de la cola   ║");
                System.out.println("║ 3. Atender cliente al frente de la cola   ║");
                System.out.println("║ 4. Eliminar cliente por nombre            ║");
                System.out.println("║ 5. Ver toda la cola                       ║");
                System.out.println("║ 0. Salir                                  ║" + Colores.RESET);
                System.out.println(Colores.BLANCO + "╚═══════════════════════════════════════════╝" + Colores.RESET);
                System.out.print(Colores.MORADO + "\nSelecciones una opción: " + Colores.RESET + Colores.BLANCO);

                String op = sc.nextLine().trim();

                switch (op) {
                    case "1": { // agregar
                        Main.limpiarPantalla();
                        System.out.print(Colores.MORADO + "Nombre del cliente: " + Colores.RESET + Colores.BLANCO);
                        String nombre = sc.nextLine().trim();
                        if (nombre.isEmpty()) {
                            Main.limpiarPantalla();
                            System.out.println(Colores.ROJO + "\nEl nombre no puede estar vacio." + Colores.RESET);
                            break;
                        }
                        int prioridad = preguntarPrioridad(sc);
                        cola.enqueue(nombre, prioridad);
                        break;
                    }
                    case "2": // peek
                        Main.limpiarPantalla();
                        cola.peek();
                        break;

                    case "3": // dequeue
                        Main.limpiarPantalla();
                        cola.dequeue();
                        break;

                    case "4": { // remove by name
                        System.out.print(Colores.MORADO + "\nNombre a eliminar: " + Colores.RESET + Colores.BLANCO);
                        String nombre = sc.nextLine().trim();
                        Main.limpiarPantalla();
                        cola.removeByName(nombre);
                        break;
                    }
                    case "5": // display
                        Main.limpiarPantalla();
                        cola.display();
                        break;

                    case "0":
                        System.out.println("Regresando...");
                        Main.limpiarPantalla();
                        return;

                    default:
                        System.out.println("Opcion invalida.");
                }
            }
        }

        // Pregunta si tiene reservacion y devuelve la prioridad (1 o 2)
        private static int preguntarPrioridad(Scanner sc) {
            while (true) {
                System.out.print(Colores.AMARILLO + "\n¿Tiene reservación? (s/n): " + Colores.RESET + Colores.BLANCO);
                String s = sc.nextLine().trim().toLowerCase();
                if (s.equals("s") || s.equals("si") || s.equals("sí")) return 1; // más prioridad
                if (s.equals("n") || s.equals("no")) return 2;
                System.out.println(Colores.ROJO + "\nRespuesta inválida. Escribe 's' o 'n'." + Colores.RESET);
            }
    }
}


// Clase principal del programa
public class Main {
  private static Menu menu = new Menu();
  private static Scanner scanner = new Scanner(System.in);
  public static void main(String[] args) {
    menu = new Menu();
    mostarInterfaz();
  }

  // Método para mostrar la interfaz principal del programa
  private static void mostarInterfaz() {
    int opcion;

    do {
      System.out.println(Colores.AZUL + "┌─────────────────────────────────────┐" + Colores.RESET);
      System.out.println(Colores.AZUL + "│         ⭐🌟 DOLCE ALBA 🌟⭐        │" + Colores.RESET);
      System.out.println(Colores.AZUL + "└─────────────────────────────────────┘" + Colores.RESET);
      System.out.println(Colores.BLANCO + "╔═════════════════════════════════════╗" + Colores.RESET);
      System.out.println(Colores.BLANCO + "║ 1. Gestión de Menú 📖 (Listas)      ║");
      System.out.println("║ 2. Gestión de Clientes 🙋 (Colas)   ║");
      System.out.println("║ 3. Gestion de Inventario 📝 (Pilas) ║");
      System.out.println("║ 0. Salir 🚪                         ║" + Colores.RESET);
      System.out.println(Colores.BLANCO + "╚═════════════════════════════════════╝" + Colores.RESET);
      System.out.print(Colores.MORADO + "\nSeleccione una opción: " + Colores.RESET + Colores.BLANCO);

      opcion = scanner.nextInt();
      scanner.nextLine(); // Limpiar buffer

      switch (opcion) {
        case 1:
            limpiarPantalla();
            gestionarMenu();
            break;
        case 2:
            limpiarPantalla();
            Colas.gestionarClientes();
            break;
        case 3:
            limpiarPantalla();
            System.out.print(Colores.MORADO + "Inserta la capacidad del inventario (número de objetos almacenables): " + Colores.RESET + Colores.BLANCO);
            int capacity = scanner.nextInt();
            Pila1 pila = new Pila1(capacity);
            limpiarPantalla();
            pila.gestionarInventario();
            break;
        case 0:
            System.out.println(Colores.VERDE + "\n¡Gracias por usar el sistema de Dolce Alba!" + Colores.RESET);
            break;
        default:
            System.out.println("Opción inválida. Intente nuevamente.");
      }
    } while(opcion != 0);
  }

    // Método para limpiar la pantalla de la consola
    public static void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

  // Método para gestionar el menú del restaurante
  private static void gestionarMenu() {
      int opcion;

      do {
          System.out.println(Colores.AZUL + "\n⭐🌟    GESTIÓN DE MENÚ (LISTAS)   🌟⭐" + Colores.RESET);
          System.out.println(Colores.AZUL + "───────────────────" + Colores.ROJO +"୨ৎ"+ Colores.AZUL + "──────────────────" + Colores.RESET);
          System.out.println(Colores.BLANCO + "╔═════════════════════════════════════╗" + Colores.RESET);
          System.out.println(Colores.BLANCO + "║ 1. Mostrar menú completo 📋         ║");
          System.out.println("║ 2. Mostrar por categoría 📚         ║");
          System.out.println("║ 3. Buscar platillo 🔍               ║");
          System.out.println("║ 4. Agregar platillo ➕              ║");
          System.out.println("║ 5. Eliminar platillo 🗑️              ║");
          System.out.println("║ 0. Volver al menú principal 🚪      ║" + Colores.RESET);
          System.out.println(Colores.BLANCO + "╚═════════════════════════════════════╝" + Colores.RESET);
          System.out.print(Colores.MORADO + "\nSeleccione una opción: " + Colores.RESET + Colores.BLANCO);

          opcion = scanner.nextInt();
          scanner.nextLine();

          switch(opcion) {
              case 1:
                  limpiarPantalla();
                  menu.mostrarMenu();
                  break;
              case 2:
                  limpiarPantalla();
                  System.out.print(Colores.MORADO + "Ingrese la categoría (Antipasti/Pasta/Secondi/Dolci): " + Colores.RESET + Colores.BLANCO);
                  String categoria = scanner.nextLine();
                  menu.mostrarPorCategoria(categoria);
                  break;
              case 3:
                  limpiarPantalla();
                  System.out.print(Colores.MORADO + "Ingrese el nombre del platillo a buscar: " + Colores.RESET + Colores.BLANCO);
                  String nombre = scanner.nextLine();
                  Platillo platillo = menu.buscar(nombre);
                  if (platillo != null) {
                      System.out.println("Platillo encontrado: " + platillo);
                  }
                  else {
                      System.out.println("Platillo no encontrado.");
                  }
                  break;
              case 4:
                  limpiarPantalla();
                  menu.agregarPlatillo();
                  break;
              case 5:
                  limpiarPantalla();
                  menu.mostrarMenu();
                  System.out.print("\nIngrese el nombre del platillo a eliminar: ");
                  String nombreEliminar = scanner.nextLine();
                  if (menu.eliminar(nombreEliminar)) {
                      System.out.println("Platillo eliminado exitosamente.");
                  }
                  else {
                      System.out.println("Platillo no encontrado.");
                  }
                  break;
              case 0:
                  limpiarPantalla();
                  break;
              default:
          }
      } while (opcion != 0);
  }
}
