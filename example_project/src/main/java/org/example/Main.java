package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.exception.GenericJDBCException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        boolean exit = false;
        Scanner sc = new Scanner(System.in);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoUD3");
        EntityManager em = emf.createEntityManager();


        while(!exit) {

            System.out.println("""
                    \n——— OPCIONES ———
                    1) Listar BBDD
                    2) Añadir un producto
                    3) Encontrar un producto
                    4) Modificar un producto
                    5) Eliminar un producto
                    6) Salir
                    ——————————————————
                    """);

            System.out.print("Opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    listarBBDD(em);
                    break;

                case "2":
                    System.out.print("Nombre del producto: ");
                    String nA = sc.nextLine();

                    System.out.print("Precio del producto: ");
                    String prA = sc.nextLine();

                    if (nA.isBlank() || nA.isEmpty() || prA.isEmpty() || prA.isBlank()) {
                        System.out.println("El nombre/precio no puede estar vacío.");
                        break;
                    }

                    double pprA;
                    try {
                        pprA = Double.parseDouble(prA);
                    }catch (NumberFormatException e) {
                        System.out.println("Introduzca un número válido.");
                        break;
                    }

                    anadirProducto(em,nA,pprA);
                    break;

                case "3":
                    System.out.print("ID del producto: ");
                    String idS = sc.nextLine();

                    int pidS = -1;

                    try {
                        pidS = Integer.parseInt(idS);
                    }catch (NumberFormatException e) {
                        System.out.println("Introduzca un ID válido.");
                        break;
                    }

                    Producto pS = encontrarProducto(em,pidS);

                    if (pS != null) {
                        System.out.println(pS);
                    } else {
                        System.out.println("El producto con ID " + pidS + " no existe.");
                    }
                    break;

                case "4":

                    System.out.print("ID del producto a modificar: ");
                    String idM = sc.nextLine();

                    int pidM = -1;

                    try {
                        pidM = Integer.parseInt(idM);
                    }catch (NumberFormatException e) {
                        System.out.println("Introduzca un ID válido.");
                        break;
                    }

                    Producto pM = encontrarProducto(em,pidM);

                    if (pM != null) {
                        System.out.println("Modificando: " + pM);
                    } else {
                        System.out.println("El producto con ID " + pidM + " no existe.");
                        break;
                    }


                    System.out.print("Nuevo nombre del producto: ");
                    String nM = sc.nextLine();

                    System.out.print("Nuevo precio del producto: ");
                    String prM = sc.nextLine();

                    if (nM.isBlank() || nM.isEmpty() || prM.isEmpty() || prM.isBlank()) {
                        System.out.println("El nombre/precio no puede estar vacío.");
                        break;
                    }

                    double pprM = -1;
                    try {
                        pprM = Double.parseDouble(prM);
                    }catch (NumberFormatException e) {
                        System.out.println("Introduzca un número válido.");
                        break;
                    }

                    modificarProducto(em,pM,nM,pprM);

                    break;

                case "5":

                    System.out.print("ID del producto a eliminar: ");
                    String idD = sc.nextLine();

                    int pidD = -1;

                    try {
                        pidD = Integer.parseInt(idD);
                    }catch (NumberFormatException e) {
                        System.out.println("Introduzca un ID válido.");
                        break;
                    }

                    Producto pD = encontrarProducto(em,pidD);

                    if (pD != null) {
                        System.out.println("Se va a borrar: " + pD + "\n¿Está seguro?");
                    } else {
                        System.out.println("El producto con ID " + pidD + " no existe.");
                        break;
                    }
                    System.out.print("[S/N]: ");
                    String sn = sc.nextLine();

                    switch (sn.toLowerCase()) {
                        case "s" -> eliminarProducto(em,pD);
                        default -> System.out.println("Operación cancelada.");
                    }
                    break;

                case "6":
                    exit = true;
                    System.out.println("¡Hasta luego!\nTenga un buen día.");
                    break;

                default:
                    System.out.println("Opción desconocida.");
                    break;
            }

        }

        em.close();
        emf.close();
        sc.close();
    }

    public static void listarBBDD(EntityManager em) {

        em.getTransaction().begin();
        List<Producto> products = em.createQuery("FROM Producto", Producto.class).getResultList();
        System.out.println("*************************");
        for (Producto p : products) {
            System.out.println(p);
        }
        System.out.println("*************************");
        em.getTransaction().commit();
    }

    public static void anadirProducto(EntityManager em, String nombre, double precio) {

        em.getTransaction().begin();
        try {
            Producto p = new Producto(nombre,precio);
            em.persist(p);
            System.out.println("Producto guardado con éxito.");
        }catch (GenericJDBCException e) {
            System.out.println("El producto ya existe.");
        }
        em.getTransaction().commit();
    }

    public static Producto encontrarProducto(EntityManager em, int id) {

        em.getTransaction().begin();
        Producto p = em.find(Producto.class, id);
        em.getTransaction().commit();

        return p;
    }

    public static void modificarProducto(EntityManager em, Producto p, String nombre, double precio) {
        em.getTransaction().begin();
        if (p != null) {
            p.setNombre(nombre);
            p.setPrecio(precio);
            System.out.println("Modificación realizada con éxito.");
        } else {
            System.out.println("Error al modificar el producto.\nEl producto no existe.");
        }
        em.getTransaction().commit();
    }

    public static void eliminarProducto(EntityManager em, Producto p) {
        em.getTransaction().begin();
        if (p != null) {
            em.remove(p);
            System.out.println("Producto eliminado con éxito.");
        } else {
            System.out.println("Error al eliminar el producto.\nEl producto no existe.");
        }
        em.getTransaction().commit();
    }
}
