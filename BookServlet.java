package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import dao.BookDAO;
import model.Book;

@WebServlet("/addBook")
public class BookServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        
        // Server-side validation
        if (title == null || title.trim().isEmpty() || author == null || author.trim().isEmpty()) {
            request.setAttribute("error", "Invalid input!");
            request.getRequestDispatcher("addBook.jsp").forward(request, response);
            return;
        }
        
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        
        BookDAO bookDao = new BookDAO();
        if (bookDao.addBook(book)) {
            response.sendRedirect("bookList.jsp");
        } else {
            request.setAttribute("error", "Failed to add book");
            request.getRequestDispatcher("addBook.jsp").forward(request, response);
        }
    }
}