package Servlets;

import Clase.ConexionDB;
import ClaseObjeto.Libro;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {
static int id1=0;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String op;
            op = (request.getParameter("op") != null) ? request.getParameter("op") : "list";
            ArrayList<Libro> lista = new ArrayList<Libro>();
            ConexionDB canal = new ConexionDB();
            Connection conn = canal.conectar();
            PreparedStatement ps;
            ResultSet rs;

            if (op.equals("list")) {
                //para listar los datos
                String sql = "SELECT * FROM libros;";
                //consulta de seleccion y almacenamiento en una coleccion
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    Libro lib = new Libro();
                    lib.setId(rs.getInt("id"));
                    lib.setIsbn(rs.getString("isbn"));
                    lib.setTitulo(rs.getString("titulo"));
                    lib.setCategoria(rs.getString("categoria"));
                    lista.add(lib);
                }
                request.setAttribute("lista", lista);
                //envio al index.jsp para mostar la informacion
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            if (op.equals("nuevo")) {
                //instanciar un objeto de la clase libro
                Libro li = new Libro();
                System.out.println(li.toString());

                //el objeto se pone como atributo d erequest
                request.setAttribute("lib", li);
                //redireccion a editar.jsp
                request.getRequestDispatcher("editar.jsp").forward(request, response);
            }
              if(op.equals("modificar"))
                {
                  Libro li =new Libro();
              System.out.println(li.toString());
                   id1 = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("lib", li);
                    request.getRequestDispatcher("modificar.jsp").forward(request, response);
                
                }
            if (op.equals("eliminar")) {
                //obtener el id
                int id = Integer.parseInt(request.getParameter("id"));
                //Realizar la eliminacion en la base de datos 
                String sql = "delete from libros where id= ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
                // redireccionar a maincontroller
                response.sendRedirect("MainController");
            }
            
        } catch (SQLException e) {
            System.out.println("ERROR AL CONEXTAR" + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("VALOR DE ID " + id);
            String isbn = request.getParameter("isbn");
            String titulo = request.getParameter("titulo");
            String categoria = request.getParameter("categoria");

            Libro lib = new Libro();
            lib.setId(id);
            lib.setIsbn(isbn);
            lib.setTitulo(titulo);
            lib.setCategoria(categoria);

            ConexionDB canal = new ConexionDB();
            Connection conn = canal.conectar();
            PreparedStatement ps;
            ResultSet rs;

           if (id == 0) {
           //nuevo registro
             String sql = "insert into libros(isbn,titulo,categoria) values (?,?,?);";
           ps = conn.prepareStatement(sql);
           ps.setString(1, lib.getIsbn());
           ps.setString(2, lib.getTitulo());
           ps.setString(3, lib.getCategoria());
           ps.executeUpdate();
           }
          else{
         //       editar registro
            String sql = "UPDATE  libros  SET isbn = ?, titulo =?,categoria =? WHERE id= ?";
                ps = conn.prepareStatement(sql);
             ps.setString(1, lib.getIsbn());
            ps.setString(2, lib.getTitulo());
          ps.setString(3, lib.getCategoria());
         ps.setInt(4, id1);
         ps.executeUpdate();    
                
            }
         
            response.sendRedirect("MainController");
        } catch (SQLException e) {
            System.out.println("ERROR EN SQL " + e.getMessage());
        }
    }
}
